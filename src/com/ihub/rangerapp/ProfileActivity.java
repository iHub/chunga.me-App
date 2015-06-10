package com.ihub.rangerapp;

import com.ihub.rangerapp.data.service.UserService;
import com.ihub.rangerapp.data.service.UserServiceImpl;
import com.ihub.rangerapp.data.sqlite.DBPreferences;
import eu.inmite.android.lib.validations.form.FormValidator;
import eu.inmite.android.lib.validations.form.annotations.NotEmpty;
import eu.inmite.android.lib.validations.form.callback.SimpleErrorPopupCallback;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

public class ProfileActivity extends ActionBarActivity {
	
	@NotEmpty(messageId = R.string.validation_ranger_id, order = 1)
	EditText rangerID;
	Button goBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		rangerID = (EditText) findViewById(R.id.rangerID);
		
		goBtn = (Button) findViewById(R.id.goBtn);
		
		goBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(FormValidator.validate(ProfileActivity.this, new SimpleErrorPopupCallback(ProfileActivity.this, true))) {			
					new LoginTask().execute(rangerID.getText().toString());
				}
			}
		});
	}
	
	class LoginTask extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... params) {
			
			UserService service = new UserServiceImpl();
			service.login(params[0]);
			return params[0];
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			SharedPreferences prefs = ProfileActivity.this.getSharedPreferences(RangerApp.class.getName(), Context.MODE_PRIVATE);
			prefs.edit().putString(DBPreferences.RANGER_ID, result).commit();
			
			new AlertDialog.Builder(ProfileActivity.this)
			.setMessage("Do you want to sign in for a shift?")
			.setCancelable(false)
			.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int id) {
					
					Intent intent = new Intent(ProfileActivity.this, StartShiftActivity.class);
					intent.putExtra("OPEN_HOME", true);
					
					startActivity(intent);
					finish();
				}
			})
			.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int id) {
					Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
					startActivity(intent);
					finish();
				}
			})
			.show();
		}
	}
}
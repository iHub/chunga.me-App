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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class ProfileActivity extends ActionBarActivity {
	
//	@NotEmpty(messageId = R.string.validation_ranger_name, order = 1)
//	EditText rangerName;
	
	@NotEmpty(messageId = R.string.validation_ranger_id, order = 1)
	EditText rangerID;
	Button goBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
//		rangerName = (EditText) findViewById(R.id.rangerName);
		rangerID = (EditText) findViewById(R.id.rangerID);
		
		goBtn = (Button) findViewById(R.id.goBtn);
		
		goBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(FormValidator.validate(ProfileActivity.this, new SimpleErrorPopupCallback(ProfileActivity.this, true))) {			
					new LoginTask().execute();
				}
			}
		});
	}
	
	class LoginTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			
			DBPreferences.instance().setPreferenceValue(DBPreferences.RANGER_ID, rangerID.getText().toString());
			
			UserService service = new UserServiceImpl();
			service.login(rangerID.getText().toString());
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
			startActivity(intent);			
			finish();
		}
	}
}

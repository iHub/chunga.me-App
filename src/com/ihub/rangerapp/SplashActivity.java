package com.ihub.rangerapp;

import com.ihub.rangerapp.data.sqlite.DBPreferences;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;

public class SplashActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		new Task().execute();
	}
	
	class Task extends AsyncTask<Void, String, String> {
		
		@Override
		protected String doInBackground(Void... params) {
			//return DBPreferences.instance().getPreferenceValue(DBPreferences.RANGER_ID);
			return "";
		}
		
		@Override
		protected void onPostExecute(String rangerID) {
			
			Intent intent = null;
			
			SharedPreferences prefs = SplashActivity.this.getSharedPreferences(RangerApp.class.getName(), Context.MODE_PRIVATE);
			rangerID = prefs.getString(DBPreferences.RANGER_ID, "");
			
			if(TextUtils.isEmpty(rangerID))
				intent = new Intent(SplashActivity.this, ProfileActivity.class);
			else
				intent = new Intent(SplashActivity.this, HomeActivity.class);
			
			if(intent != null)
				startActivity(intent);
			
			finish();
		}
	}
}

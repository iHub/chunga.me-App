package com.ihub.rangerapp;

import com.ihub.rangerapp.data.sqlite.DBPreferences;
import android.app.Activity;
import android.content.Intent;
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
			
			return DBPreferences.instance().getPreferenceValue(DBPreferences.RANGER_NAME);
		}
		
		@Override
		protected void onPostExecute(String rangerName) {
			
			Intent intent = null;
			
			if(TextUtils.isEmpty(rangerName))
				intent = new Intent(SplashActivity.this, ProfileActivity.class);
			else
				intent = new Intent(SplashActivity.this, HomeActivity.class);
			
			if(intent != null)
				startActivity(intent);
			
			finish();
		}
	}
}

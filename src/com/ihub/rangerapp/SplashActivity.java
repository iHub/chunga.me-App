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
	
	class Task extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected Void doInBackground(Void... params) {
			
			try {
				Thread.sleep(2800);
			} catch (InterruptedException e) {}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			
			Intent intent = null;
			
			String rangerName = DBPreferences.instance().getPreferenceValue(DBPreferences.RANGER_NAME);
			
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

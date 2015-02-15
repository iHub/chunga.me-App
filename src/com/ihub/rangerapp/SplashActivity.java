package com.ihub.rangerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

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
			
			intent = new Intent(SplashActivity.this, ProfileActivity.class);
			
			if(intent != null)
				startActivity(intent);
		}
	}
}

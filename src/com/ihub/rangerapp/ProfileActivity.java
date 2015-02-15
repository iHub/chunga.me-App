package com.ihub.rangerapp;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;

public class ProfileActivity extends ActionBarActivity {
	
	EditText rangerName;
	EditText rangerID;
	Button signupBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		rangerName = (EditText) findViewById(R.id.rangerName);
		rangerID = (EditText) findViewById(R.id.rangerID);
		
		signupBtn = (Button) findViewById(R.id.signupBtn);
		
		signupBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
				startActivity(intent);
			}
		});
	}
}

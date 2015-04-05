package com.ihub.rangerapp;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SuspiciousActivitiesActivity extends ActionBarActivity {
	
	Spinner actionTakenSpinner;
	EditText extraNotes;
	Button saveBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_suspicious_activities);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuspiciousActivitiesActivity.this.onBackPressed();
            }
        });
        
        actionTakenSpinner = (Spinner) findViewById(R.id.actionTakenSpinner);
        extraNotes = (EditText) findViewById(R.id.extraNotes);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        
        ArrayAdapter<CharSequence> actionTakenAdapter = ArrayAdapter.createFromResource(this,
                R.array.suspicious_activities_actions_taken, android.R.layout.simple_spinner_item);
        actionTakenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        actionTakenSpinner.setAdapter(actionTakenAdapter);
	}
}
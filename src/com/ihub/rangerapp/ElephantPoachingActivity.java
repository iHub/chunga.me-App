package com.ihub.rangerapp;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ElephantPoachingActivity extends ActionBarActivity {
	
	Spinner toolsUsedSpinner;
	EditText noOfAnimalsView;
	Spinner ageSpinner;
	Spinner sexSpinner;
	Spinner ivoryPresenceSpinner;
	Spinner actionTakenSpinner;
	EditText extraNotes;
	Button saveBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_elephant_poaching);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ElephantPoachingActivity.this.onBackPressed();
            }
        });
        
        toolsUsedSpinner = (Spinner) findViewById(R.id.toolsUsedSpinner);
        noOfAnimalsView = (EditText) findViewById(R.id.noOfAnimalsView);
        ageSpinner = (Spinner) findViewById(R.id.ageSpinner);
        sexSpinner = (Spinner) findViewById(R.id.sexSpinner);
        ivoryPresenceSpinner = (Spinner) findViewById(R.id.ivoryPresenceSpinner);
        actionTakenSpinner = (Spinner) findViewById(R.id.actionTakenSpinner);
        extraNotes = (EditText) findViewById(R.id.extraNotes);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        
        		
		ArrayAdapter<CharSequence> toolsUsedAdapter = ArrayAdapter.createFromResource(this,
                R.array.elephant_poaching_tools_used, android.R.layout.simple_spinner_item);
		toolsUsedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        toolsUsedSpinner.setAdapter(toolsUsedAdapter);
        
        
        ArrayAdapter<CharSequence> ageAdapter = ArrayAdapter.createFromResource(this,
                R.array.age_array, android.R.layout.simple_spinner_item);
		ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        ageSpinner.setAdapter(ageAdapter);
        
        
        ArrayAdapter<CharSequence> sexAdapter = ArrayAdapter.createFromResource(this,
                R.array.sex_array, android.R.layout.simple_spinner_item);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        sexSpinner.setAdapter(sexAdapter);
        
        
//        ArrayAdapter<CharSequence> actionTakenAdapter = ArrayAdapter.createFromResource(this,
//                R.array.sex_array, android.R.layout.simple_spinner_item);
//        actionTakenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        
//        sexSpinner.setAdapter(actionTakenAdapter);
	}
}
package com.ihub.rangerapp;

import java.util.Map;

import com.ihub.rangerapp.data.service.ElephantService;
import com.ihub.rangerapp.data.service.ElephantServiceImpl;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ElephantPoachingActivity extends CameraGPSActionBarActivity {
	
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
        
        Intent data = getIntent();
        
        initViews();
        
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
        
        
        ArrayAdapter<CharSequence> actionTakenAdapter = ArrayAdapter.createFromResource(this,
                R.array.elephant_poaching_action_taken, android.R.layout.simple_spinner_item);
        actionTakenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        actionTakenSpinner.setAdapter(actionTakenAdapter);
        
        
        ArrayAdapter<CharSequence> ivoryPresenceAdapter = ArrayAdapter.createFromResource(this,
                R.array.ivory_presence, android.R.layout.simple_spinner_item);
        ivoryPresenceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        ivoryPresenceSpinner.setAdapter(ivoryPresenceAdapter);
        
        saveBtn.setOnClickListener(new View.OnClickListener() {
        	
			@Override
			public void onClick(View v) {
				if(isValid())
					save();
			}
		});
        
        if(mode != 1) {
        	
        	if(data.hasExtra("noOfAnimals"))
            	noOfAnimalsView.setText(data.getIntExtra("noOfAnimals", 0) + "");
        	
        	if(!TextUtils.isEmpty(getIntent().getStringExtra("toolsUsed")))
            	for(int i = 0; i < toolsUsedAdapter.getCount(); i++) {
            		if(toolsUsedSpinner.getItemAtPosition(i).toString().equals(getIntent().getStringExtra("toolsUsed")))
            			toolsUsedSpinner.setSelection(i);
            	}
        	
        	if(!TextUtils.isEmpty(getIntent().getStringExtra("age")))
            	for(int i = 0; i < ageAdapter.getCount(); i++) {
            		if(ageSpinner.getItemAtPosition(i).toString().equals(getIntent().getStringExtra("age")))
            			ageSpinner.setSelection(i);
            	}
        	
        	if(!TextUtils.isEmpty(getIntent().getStringExtra("sex")))
            	for(int i = 0; i < sexAdapter.getCount(); i++) {
            		if(sexSpinner.getItemAtPosition(i).toString().equals(getIntent().getStringExtra("sex")))
            			sexSpinner.setSelection(i);
            	}
        	
        	
        	if(!TextUtils.isEmpty(getIntent().getStringExtra("ivoryPresence")))
            	for(int i = 0; i < ivoryPresenceAdapter.getCount(); i++) {
            		if(ivoryPresenceSpinner.getItemAtPosition(i).toString().equals(getIntent().getStringExtra("ivoryPresence")))
            			ivoryPresenceSpinner.setSelection(i);
            	}
        	
        	if(!TextUtils.isEmpty(getIntent().getStringExtra("actionTaken")))
            	for(int i = 0; i < actionTakenAdapter.getCount(); i++) {
            		if(actionTakenSpinner.getItemAtPosition(i).toString().equals(getIntent().getStringExtra("actionTaken")))
            			actionTakenSpinner.setSelection(i);
            	}
        	
        	
        	if(!TextUtils.isEmpty(getIntent().getStringExtra("extraNotes")))
        		extraNotes.setText(getIntent().getStringExtra("extraNotes"));
            
            if(mode == 2) {
            	saveBtn.setText(getString(R.string.edit));
            } else {
            	saveBtn.setText(getString(R.string.close));
            }
        }
	}
	
	protected void save() {
		
		ElephantService service = new ElephantServiceImpl();
		
		Integer noOfAnimals = 0;
		
		try {
			noOfAnimals = Integer.valueOf(noOfAnimalsView.getText().toString());
		} catch(Exception e) {}
		
		Integer id = -1;
		if(mode == 2)
			id = getIntent().getIntExtra("id", -1);
		
		Map<String, Object> result = service.save(
				id,
				toolsUsedSpinner.getSelectedItem().toString(), 
				noOfAnimals, 
				ageSpinner.getSelectedItem().toString(), 
				sexSpinner.getSelectedItem().toString(), 
				ivoryPresenceSpinner.getSelectedItem().toString(), 
				actionTakenSpinner.getSelectedItem().toString(), 
				extraNotes.getText().toString(), 
				imagePath, getWP());
		
		showSaveResult(result);
	}
	
	protected Boolean isValid() {
		
		Boolean isValid = true;
		
		if(TextUtils.isEmpty(imagePath)) {
			isValid = false;
			Toast toast = Toast.makeText(this, getString(R.string.validation_photo), Toast.LENGTH_LONG);
			toast.setGravity(Gravity.TOP, 0, 0);
			toast.show();
		}
		
		return isValid;
	}
}
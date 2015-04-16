package com.ihub.rangerapp;

import java.util.Map;

import com.ihub.rangerapp.data.service.ElephantService;
import com.ihub.rangerapp.data.service.ElephantServiceImpl;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
	}
	
	protected void save() {
		
		ElephantService service = new ElephantServiceImpl();
		
		Integer noOfAnimals = 0;
		
		try {
			noOfAnimals = Integer.valueOf(noOfAnimalsView.getText().toString());
		} catch(Exception e) {}
		
		Map<String, Object> result = service.save(
				toolsUsedSpinner.getSelectedItem().toString(), 
				noOfAnimals, 
				ageSpinner.getSelectedItem().toString(), 
				sexSpinner.getSelectedItem().toString(), 
				ivoryPresenceSpinner.getSelectedItem().toString(), 
				actionTakenSpinner.getSelectedItem().toString(), 
				extraNotes.getText().toString(), 
				fileName, getWP());
		
		showSaveResult(result);
	}
	
	protected Boolean isValid() {
		
		Boolean isValid = true;
		
		if(TextUtils.isEmpty(fileName)) {
			isValid = false;
			Toast toast = Toast.makeText(this, getString(R.string.validation_photo), Toast.LENGTH_LONG);
			toast.setGravity(Gravity.TOP, 0, 0);
			toast.show();
		}
		
		return isValid;
	}
}
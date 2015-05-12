package com.ihub.rangerapp;

import java.util.Map;

import com.ihub.rangerapp.data.service.SuspiciousActivitiesService;
import com.ihub.rangerapp.data.service.SuspiciousActivitiesServiceImpl;
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

public class SuspiciousActivitiesActivity extends CameraGPSActionBarActivity {
	
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
        
        Intent data = getIntent();
        
        initViews();
        
        actionTakenSpinner = (Spinner) findViewById(R.id.actionTakenSpinner);
        extraNotes = (EditText) findViewById(R.id.extraNotes);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        
        ArrayAdapter<CharSequence> actionTakenAdapter = ArrayAdapter.createFromResource(this,
                R.array.suspicious_activities_actions_taken, android.R.layout.simple_spinner_item);
        actionTakenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        actionTakenSpinner.setAdapter(actionTakenAdapter);
        
        saveBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mode == 3)
					finish();
				else
					if(isValid())
						save();
			}
		});
        
        if(mode != 1) {
        	
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
		
		Integer id = -1;
		if(mode == 2)
			id = getIntent().getIntExtra("id", -1);
		
		SuspiciousActivitiesService service = new SuspiciousActivitiesServiceImpl();
		Map<String, Object> result = service.save(id, actionTakenSpinner.getSelectedItem().toString(), extraNotes.getText().toString(), imagePath, getWP());
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
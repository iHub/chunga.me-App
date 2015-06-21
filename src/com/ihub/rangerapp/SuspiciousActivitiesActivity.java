package com.ihub.rangerapp;

import java.util.Map;

import com.ihub.rangerapp.data.service.SuspiciousActivitiesService;
import com.ihub.rangerapp.data.service.SuspiciousActivitiesServiceImpl;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
					if(isValid()) {
						
						View[] fields = new View[] {actionTakenSpinner, extraNotes};
						
						if(hasInvalidFields(fields)) {
							
							String msg = "The following fields have no values.\n\n" + getInvalidFields(fields) + "\nDo you wish to continue?";
							new AlertDialog.Builder(SuspiciousActivitiesActivity.this)
								.setMessage(msg)
								.setCancelable(false)
								.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
									
									public void onClick(DialogInterface dialog, int id) {
										save();
									}
								})
								.setNegativeButton(R.string.no, null)
								.show();
							
						} else {
							save();
						}
					}
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
        	
        	latView.setText(getIntent().getStringExtra("lat"));
            longView.setText(getIntent().getStringExtra("lon"));
            
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
		
		String action = actionTakenSpinner.getSelectedItemPosition() == 0 ? "" : actionTakenSpinner.getSelectedItem().toString();
		
		SuspiciousActivitiesService service = new SuspiciousActivitiesServiceImpl();
		Map<String, Object> result = service.save(id, action, extraNotes.getText().toString(), imagePath, latView.getText().toString(), longView.getText().toString());
		
		if(mode == 2) {
			Intent data = new Intent();
			
			data.putExtra("imagePath", imagePath);
			data.putExtra("id", id);
			data.putExtra("actionTaken", action);
			data.putExtra("extraNotes", extraNotes.getText().toString());
			
			setResult(RESULT_OK, data);
		}
		
		showSaveResult(result);
	}
	
	public String[] getLabels() {
		return new String[]{"Action Taken", "Extra Notes"};
	}
}
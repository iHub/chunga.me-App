package com.ihub.rangerapp;

import java.util.Map;

import com.ihub.rangerapp.data.service.ElephantService;
import com.ihub.rangerapp.data.service.ElephantServiceImpl;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class ElephantPoachingActivity extends CameraGPSActionBarActivity {
	
	Spinner toolsUsedSpinner;
	EditText noOfAnimalsView;
	Spinner ageSpinner;
	Spinner sexSpinner;
	Spinner actionTakenSpinner;
	EditText extraNotes;
	Button saveBtn;
	
	EditText juvenilesCountView;
	EditText semiAdultsCountView;
	EditText adultsCountView;
	EditText femaleCountView;
	EditText maleCountView;
	
	LinearLayout multiAnimalsView;
	LinearLayout singleAnimalView;
	
	Boolean tusksPresent = false;
	
	RadioButton hasTusksYes;
	RadioButton hasTusksNo;
	
	Spinner ranchSpinner;
	
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
        
        juvenilesCountView = (EditText) findViewById(R.id.juvenilesCountView);
        semiAdultsCountView = (EditText) findViewById(R.id.semiAdultsCountView);
        adultsCountView = (EditText) findViewById(R.id.adultsCountView);
        femaleCountView = (EditText) findViewById(R.id.femaleCountView);
        maleCountView = (EditText) findViewById(R.id.maleCountView);
        
        multiAnimalsView = (LinearLayout) findViewById(R.id.multiAnimalsView);
        singleAnimalView = (LinearLayout) findViewById(R.id.singleAnimalView);
        
        toolsUsedSpinner = (Spinner) findViewById(R.id.toolsUsedSpinner);
        noOfAnimalsView = (EditText) findViewById(R.id.noOfAnimalsView);
        ageSpinner = (Spinner) findViewById(R.id.ageSpinner);
        sexSpinner = (Spinner) findViewById(R.id.sexSpinner);
        actionTakenSpinner = (Spinner) findViewById(R.id.actionTakenSpinner);
        extraNotes = (EditText) findViewById(R.id.extraNotes);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        
        hasTusksYes = (RadioButton) findViewById(R.id.radio_yes);
        hasTusksNo = (RadioButton) findViewById(R.id.radio_no);
        
        noOfAnimalsView.addTextChangedListener(new TextWatcher() {
        	
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				try {
					int noOfAnimals = Integer.valueOf(noOfAnimalsView.getText().toString());
					
					if(noOfAnimals > 0) {
						singleAnimalView.setVisibility(noOfAnimals == 1 ? View.VISIBLE : View.GONE);
						multiAnimalsView.setVisibility(noOfAnimals > 1 ? View.VISIBLE : View.GONE);
					} else {
						singleAnimalView.setVisibility(View.GONE);
						multiAnimalsView.setVisibility(View.GONE);
					}
				} catch (Exception e) {
					singleAnimalView.setVisibility(View.GONE);
					multiAnimalsView.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {}
		});
        
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
        
        ranchSpinner = (Spinner) findViewById(R.id.ranchSpinner);
        ArrayAdapter<CharSequence> ranchesAdapter = ArrayAdapter.createFromResource(this,
                R.array.ranches, android.R.layout.simple_spinner_item);
        ranchesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ranchSpinner.setAdapter(ranchesAdapter);
        
        saveBtn.setOnClickListener(new View.OnClickListener() {
        	
			@Override
			public void onClick(View v) {
				if(isValid()) {
					//save();
					//return new String[]{"Animal", "Number of Animals", "Action Taken", "Extra Notes"};
					
					View[] fields = new View[] {toolsUsedSpinner, noOfAnimalsView, actionTakenSpinner, extraNotes};
					
					if(hasInvalidFields(fields)) {
						
						String msg = "The following fields have no values.\n\n" + getInvalidFields(fields) + "\nDo you wish to continue?";
						new AlertDialog.Builder(ElephantPoachingActivity.this)
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
        	
        	if(data.hasExtra("noOfAnimals"))
            	noOfAnimalsView.setText(data.getIntExtra("noOfAnimals", 0) + "");
        	
        	Integer noOfAnimals = 0;
        	
        	try {
        		noOfAnimals = Integer.valueOf(data.getIntExtra("noOfAnimals", 0));
        	} catch(Exception e) {}
        	
        	if(!TextUtils.isEmpty(getIntent().getStringExtra("toolsUsed")))
            	for(int i = 0; i < toolsUsedAdapter.getCount(); i++) {
            		if(toolsUsedSpinner.getItemAtPosition(i).toString().equals(getIntent().getStringExtra("toolsUsed")))
            			toolsUsedSpinner.setSelection(i);
            	}
        	
        	Integer maleCount = 0;
    		Integer femaleCount = 0;
    		Integer adultsCount = 0;
    		Integer semiAdultsCount = 0;
    		Integer juvenileCount= 0;
    		
    		try {
				maleCount = getIntent().getIntExtra("maleCount", 0);
			} catch (Exception e) {}
			
			try {
				femaleCount = getIntent().getIntExtra("femaleCount", 0);
			} catch (Exception e) {}
			
			try {
				adultsCount = getIntent().getIntExtra("adultsCount", 0);
			} catch (Exception e) {}
			
			try {
				semiAdultsCount = getIntent().getIntExtra("semiAdultsCount", 0);
			} catch (Exception e) {}
			
			try {
				juvenileCount = getIntent().getIntExtra("juvenileCount", 0);
			} catch (Exception e) {}
        	
        	if(noOfAnimals == 1) {
        		
        		if(maleCount == 1)
        			sexSpinner.setSelection(1);
        		else if(femaleCount == 1)
        			sexSpinner.setSelection(2);
        		
        		if(adultsCount == 1)
        			ageSpinner.setSelection(1);
        		else if(semiAdultsCount == 1)
        			ageSpinner.setSelection(2);
        		else if(juvenileCount == 1)
        			ageSpinner.setSelection(3);
        			
        	} else if(noOfAnimals > 1) {
        		
        		if(maleCount > 0)
        			maleCountView.setText(maleCount + "");
        		if(femaleCount > 0)
        			femaleCountView.setText(femaleCount + "");
        		
        		if(adultsCount > 0)
        			adultsCountView.setText(adultsCount + "");
        		if(semiAdultsCount > 0)
        			semiAdultsCountView.setText(semiAdultsCount + "");
        		if(juvenileCount > 0)
        			juvenilesCountView.setText(juvenileCount + "");
        		
        	}
        	
        	if(!TextUtils.isEmpty(getIntent().getStringExtra("age")))
            	for(int i = 0; i < ageAdapter.getCount(); i++) {
            		if(ageSpinner.getItemAtPosition(i).toString().equals(getIntent().getStringExtra("age")))
            			ageSpinner.setSelection(i);
            	}
        	
        	if(!TextUtils.isEmpty(getIntent().getStringExtra("ivoryPresence"))) {
        		
        		if("Yes".equals(getIntent().getStringExtra("ivoryPresence")))
        			hasTusksYes.setChecked(true);
        		else
        			hasTusksNo.setChecked(true);
        	}
        	
        	if(!TextUtils.isEmpty(getIntent().getStringExtra("actionTaken")))
            	for(int i = 0; i < actionTakenAdapter.getCount(); i++) {
            		if(actionTakenSpinner.getItemAtPosition(i).toString().equals(getIntent().getStringExtra("actionTaken")))
            			actionTakenSpinner.setSelection(i);
            	}
        	
        	
        	if(!TextUtils.isEmpty(getIntent().getStringExtra("extraNotes")))
        		extraNotes.setText(getIntent().getStringExtra("extraNotes"));
        	
        	waypointView.setText(getIntent().getStringExtra("waypoint"));
        	
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
		
		Integer maleCount = 0;
		Integer femaleCount = 0;
		Integer adultsCount = 0;
		Integer semiAdultsCount = 0;
		Integer juvenileCount= 0;
		
		if(noOfAnimals == 1) {
			
			if(ageSpinner.getSelectedItemPosition() == 1)
				adultsCount = 1;
			else if(ageSpinner.getSelectedItemPosition() == 2)
				semiAdultsCount = 1;
			else if(ageSpinner.getSelectedItemPosition() == 3)
				juvenileCount = 1;
			
			if(sexSpinner.getSelectedItemPosition() ==1)
				maleCount = 1;
			else if(sexSpinner.getSelectedItemPosition() == 2)
				femaleCount = 1;
			
		} else if(noOfAnimals > 1) {
			
			try {
				maleCount = Integer.valueOf(maleCountView.getText().toString());
			} catch (Exception e) {}
			
			try {
				femaleCount = Integer.valueOf(femaleCountView.getText().toString());
			} catch (Exception e) {}
			
			try {
				adultsCount = Integer.valueOf(adultsCountView.getText().toString());
			} catch (Exception e) {}
			
			try {
				semiAdultsCount = Integer.valueOf(semiAdultsCountView.getText().toString());
			} catch (Exception e) {}
			
			try {
				juvenileCount = Integer.valueOf(juvenilesCountView.getText().toString());
			} catch (Exception e) {}
			
		}
		
		String toolUsed = toolsUsedSpinner.getSelectedItemPosition() == 0 ? "" : toolsUsedSpinner.getSelectedItem().toString();
		String age = ageSpinner.getSelectedItemPosition() == 0 ? "" : ageSpinner.getSelectedItem().toString();
		String sex = sexSpinner.getSelectedItemPosition() == 0 ? "" : sexSpinner.getSelectedItem().toString();
		String action = actionTakenSpinner.getSelectedItemPosition() == 0 ? "" : actionTakenSpinner.getSelectedItem().toString();
		
		Map<String, Object> result = service.save(
				id,
				toolUsed, 
				noOfAnimals, 
				maleCount,
				femaleCount,
				adultsCount,
				semiAdultsCount,
				juvenileCount,
				tusksPresent ? "Yes" : "No", 
				action,
				extraNotes.getText().toString(), 
				imagePath,
				waypointView.getText().toString());
				
		if(mode == 2) {
			Intent data = new Intent();
			Toast.makeText(this, id + "ss", 	Toast.LENGTH_LONG).show();
			
			data.putExtra("imagePath", imagePath);
			data.putExtra("id", id);
			data.putExtra("toolsUsed", toolUsed);
			data.putExtra("noOfAnimals", noOfAnimals);
			
			data.putExtra("maleCount", maleCount);
			data.putExtra("femaleCount", femaleCount);
			data.putExtra("adultsCount", adultsCount);
			data.putExtra("semiAdultsCount", semiAdultsCount);
			data.putExtra("juvenileCount", juvenileCount);
			data.putExtra("ivoryPresence", tusksPresent ? "Yes" : "No");
			
			
			data.putExtra("actionTaken", action);
			data.putExtra("extraNotes", extraNotes.getText().toString());
			
			setResult(RESULT_OK, data);
		}
		
		showSaveResult(result);
	}
	
	public void onTusksRadioButtonClicked(View view) {

		switch (view.getId()) {
		case R.id.radio_yes:
			tusksPresent = true;
			break;
		case R.id.radio_no:
			tusksPresent = false;
			break;
		default:
			break;
		}
	}
	
	@Override
	public String[] getLabels() {
		return new String[] {"Tool Used", "Number of Animals", "Action Taken", "Extra Notes"};
	}
	
	@Override
	protected Boolean isValid() {
		Boolean isValid =  super.isValid();
		
		Boolean focus = isValid ? false : true;
		
		Integer noOfAnimals = 0;
		
		try {
    		noOfAnimals = Integer.valueOf(noOfAnimalsView.getText().toString().trim());
    	} catch(Exception e) {}
    	
    	if(noOfAnimals > 1) {
    		Integer maleCount = 0;
    		Integer femaleCount = 0;
    		Integer adultsCount = 0;
    		Integer semiAdultsCount = 0;
    		Integer juvenileCount= 0;
    		
    		try {
				maleCount = Integer.valueOf(maleCountView.getText().toString());
			} catch (Exception e) {}
			
			try {
				femaleCount = Integer.valueOf(femaleCountView.getText().toString());
			} catch (Exception e) {}
			
			try {
				adultsCount = Integer.valueOf(adultsCountView.getText().toString());
			} catch (Exception e) {}
			
			try {
				semiAdultsCount = Integer.valueOf(semiAdultsCountView.getText().toString());
			} catch (Exception e) {}
			
			try {
				juvenileCount = Integer.valueOf(juvenilesCountView.getText().toString());
			} catch (Exception e) {}
			
    		if((maleCount + femaleCount) != noOfAnimals) {
    			isValid = false;
    			femaleCountView.setError("Male and Female counts is not equal to the no of animals count.");
    			
    			if((adultsCount + semiAdultsCount + juvenileCount) != noOfAnimals) {
	    			isValid = false;
	    			juvenilesCountView.setError("Adults, Semi-adults and Juvenile counts is not equal to the no of animals count.");
	    		}
    			
    			if(!focus)
    				femaleCountView.requestFocus();
    		}
    		
    		if(isValid) {
    			if((adultsCount + semiAdultsCount + juvenileCount) != noOfAnimals) {
	    			isValid = false;
	    			juvenilesCountView.setError("Adults, Semi-adults and Juvenile counts is not equal to the no of animals count.");
	    			if(!focus)
	    				juvenilesCountView.requestFocus();
	    		}
    		}
		}
		
		return isValid;
	}
}
package com.ihub.rangerapp;

import java.util.Map;
import com.ihub.rangerapp.data.service.AnimalSightingsService;
import com.ihub.rangerapp.data.service.AnimalSightingsServiceImpl;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.content.Intent;
import android.os.Bundle;

public class AnimalsSightingsActivity extends CameraGPSActionBarActivity {
	
	LinearLayout individualLayout;
	LinearLayout herdLayout;
	
	EditText animalNameView;
	RadioGroup genderGroup;
	Spinner ageSpinner;
	EditText distanceSeenView;
	EditText extraNotes;
	Button saveBtn;
	
	EditText herdNameView;
	EditText typeSpeciesView;
	EditText herdNoOfAnimalsView;
	Spinner herdAgeSpinner;
	EditText herdDistanceSeenView;
	
	Boolean isMale = false;
	Boolean isIndividualView = true;
	
	LinearLayout switchView;
	RadioButton radioGenderMale;
	RadioButton radioGenderFemale;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animals_sightings);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
		getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimalsSightingsActivity.this.onBackPressed();
            }
        });
        
        switchView = (LinearLayout) findViewById(R.id.switchView);
        
        radioGenderMale = (RadioButton) findViewById(R.id.radio_gender_male);
        radioGenderFemale = (RadioButton) findViewById(R.id.radio_gender_female);
        
        individualLayout = (LinearLayout) findViewById(R.id.individualLayout);
        herdLayout = (LinearLayout) findViewById(R.id.herdLayout);
        
        Intent data = getIntent();
        
        initViews();
        
        ageSpinner = (Spinner) findViewById(R.id.ageSpinner);
        ArrayAdapter<CharSequence> ageAdapter = ArrayAdapter.createFromResource(this,
                R.array.age_array, android.R.layout.simple_spinner_item);
		ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
        ageSpinner.setAdapter(ageAdapter);
        
        animalNameView = (EditText) findViewById(R.id.animalNameView);
        distanceSeenView = (EditText) findViewById(R.id.distanceSeenView);
        extraNotes = (EditText) findViewById(R.id.extraNotes);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        
        herdNameView = (EditText) findViewById(R.id.herdNameView);
        typeSpeciesView = (EditText) findViewById(R.id.typeSpeciesView);
        herdNoOfAnimalsView = (EditText) findViewById(R.id.herdNoOfAnimalsView);
        herdAgeSpinner = (Spinner) findViewById(R.id.herdAgeSpinner);
        herdDistanceSeenView = (EditText) findViewById(R.id.herdDistanceSeenView);
        
        ArrayAdapter<CharSequence> herdAgeAdapter = ArrayAdapter.createFromResource(this,
                R.array.age_array, android.R.layout.simple_spinner_item);
        herdAgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
		herdAgeSpinner.setAdapter(herdAgeAdapter);
		
        saveBtn.setOnClickListener(new View.OnClickListener() {
        	
			@Override
			public void onClick(View v) {
				
				if(mode == 3) {
					finish();
				} else
					if(isIndividualView)
						saveIndividual();
					else
						saveHerd();
				
				
			}
		});
        
        if(mode != 1) {
        	switchView.setVisibility(View.GONE);
        	
        	if(mode == 2) {
            	saveBtn.setText(getString(R.string.edit));
            } else {
            	saveBtn.setText(getString(R.string.close));
            }
        	
        	if(data.hasExtra("view")) {
        		
        		if("herd".equals(data.getStringExtra("view"))) {
        			initHerdData();
        		} else
        			initIndividualData();
        	}
        }
	}
	
	private void initIndividualData() {
		herdLayout.setVisibility(View.GONE);
    	individualLayout.setVisibility(View.VISIBLE);
    	isIndividualView = true;
    	
    	if(!TextUtils.isEmpty(getIntent().getStringExtra("animal")))
    		animalNameView.setText(getIntent().getStringExtra("animal"));
    	
    	if(!TextUtils.isEmpty(getIntent().getStringExtra("gender"))) {
    		
    		if("M".equals(getIntent().getStringExtra("gender")))
    			radioGenderMale.setChecked(true);
    		else
    			radioGenderFemale.setChecked(true);
    	}
    	
    	if(!TextUtils.isEmpty(getIntent().getStringExtra("age")))
        	for(int i = 0; i < ageSpinner.getAdapter().getCount(); i++) {
        		if(ageSpinner.getItemAtPosition(i).toString().equals(getIntent().getStringExtra("age")))
        			ageSpinner.setSelection(i);
        	}
        
        if(getIntent().hasExtra("distanceSeen"))
        	distanceSeenView.setText(getIntent().getIntExtra("distanceSeen", 0) + "");
        
        if(!TextUtils.isEmpty(getIntent().getStringExtra("extraNotes")))
    		extraNotes.setText(getIntent().getStringExtra("extraNotes"));
	}

	private void initHerdData() {
		
		herdLayout.setVisibility(View.VISIBLE);
    	individualLayout.setVisibility(View.GONE);
    	isIndividualView = false;
    	
    	if(!TextUtils.isEmpty(getIntent().getStringExtra("name")))
    		herdNameView.setText(getIntent().getStringExtra("name"));
    	
    	if(!TextUtils.isEmpty(getIntent().getStringExtra("type")))
    		typeSpeciesView.setText(getIntent().getStringExtra("type"));
    	
    	if(getIntent().hasExtra("noOfAnimals"))
        	herdNoOfAnimalsView.setText(getIntent().getIntExtra("noOfAnimals", 0) + "");
    	
    	if(!TextUtils.isEmpty(getIntent().getStringExtra("age")))
        	for(int i = 0; i < herdAgeSpinner.getAdapter().getCount(); i++) {
        		if(herdAgeSpinner.getItemAtPosition(i).toString().equals(getIntent().getStringExtra("age")))
        			herdAgeSpinner.setSelection(i);
        	}
        
        if(getIntent().hasExtra("distanceSeen"))
        	herdDistanceSeenView.setText(getIntent().getIntExtra("distanceSeen", 0) + "");
        
        if(!TextUtils.isEmpty(getIntent().getStringExtra("extraNotes")))
    		extraNotes.setText(getIntent().getStringExtra("extraNotes"));
	}

	protected void saveIndividual() {
		
		if(isValid()) {
			
			Integer distanceSeen = 0;
			
			try {
				distanceSeen = Integer.valueOf(distanceSeenView.getText().toString());
			} catch (Exception e) {}
			
			Integer id = -1;
			if(mode == 2)
				id = getIntent().getIntExtra("id", -1);
			
			AnimalSightingsService service = new AnimalSightingsServiceImpl();
			Map<String, Object> result = service.saveIndividualAnimal(
					id,
					animalNameView.getText().toString(), 
					isMale ? "M" : "F", 
					ageSpinner.getSelectedItem().toString(), 
					distanceSeen, 
					extraNotes.getText().toString(), 
					imagePath, 
					getWP());
			
			showSaveResult(result);
		}
	}

	protected void saveHerd() {
		
		if(isValid()) {
			Integer noOfAnimals = 0;
			Integer distanceSeen = 0;
			
			try {
				noOfAnimals = Integer.valueOf(herdNameView.getText().toString());
			} catch (Exception e) {}
			
			try {
				distanceSeen = Integer.valueOf(herdDistanceSeenView.getText().toString());
			} catch (Exception e) {}
			
			AnimalSightingsService service = new AnimalSightingsServiceImpl();
			
			Integer id = -1;
			if(mode == 2)
				id = getIntent().getIntExtra("id", -1);
			
			Map<String , Object> result = service.saveHerd(
					id,
					herdNameView.getText().toString(), 
					typeSpeciesView.getText().toString(), 
					noOfAnimals, 
					herdAgeSpinner.getSelectedItem().toString(), 
					distanceSeen, extraNotes.getText().toString(), imagePath, getWP());
			
			showSaveResult(result);
		}
	}

	public void onGenderRadioButtonClicked(View view) {

		switch (view.getId()) {
		case R.id.radio_gender_male:
			isMale = true;
			break;
		case R.id.radio_gender_female:
			isMale = false;
			break;
		default:
			break;
		}
	}
	
	public void onTypeRadioButtonClicked(View view) {
			    
	    switch(view.getId()) {
	        case R.id.radio_individual:
	        	herdLayout.setVisibility(View.GONE);
	        	individualLayout.setVisibility(View.VISIBLE);
	        	isIndividualView = true;
	            break;
	        case R.id.radio_herd:
	        	herdLayout.setVisibility(View.VISIBLE);
	        	individualLayout.setVisibility(View.GONE);
	        	isIndividualView = false;
	            break;
	    }
	}
}

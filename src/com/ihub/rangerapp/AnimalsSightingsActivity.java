package com.ihub.rangerapp;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
        
        individualLayout = (LinearLayout) findViewById(R.id.individualLayout);
        herdLayout = (LinearLayout) findViewById(R.id.herdLayout);
        
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
				if(isIndividualView)
					saveIndividual();
				else
					saveHerd();
			}
		});
	}
	
	protected void saveHerd() {
		
	}
	
	protected void saveIndividual() {
		
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

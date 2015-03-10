package com.ihub.rangerapp;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.os.Bundle;

public class AnimalsSightingsActivity extends ActionBarActivity {
	
//	Spinner genderSpinner;
//	ArrayAdapter<CharSequence> genderAdapter;
	
	LinearLayout individualLayout;
	LinearLayout herdLayout;
	
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
        
//        genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
//        
//        genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
//		genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		genderSpinner.setAdapter(genderAdapter);
	}
	
	public void onRadioButtonClicked(View view) {
		
	}
	
	public void onTypeRadioButtonClicked(View view) {
		
		boolean checked = ((RadioButton) view).isChecked();
	    
	    switch(view.getId()) {
	        case R.id.radio_individual:
	        	herdLayout.setVisibility(View.GONE);
	        	individualLayout.setVisibility(View.VISIBLE);
	            break;
	        case R.id.radio_herd:
	        	herdLayout.setVisibility(View.VISIBLE);
	        	individualLayout.setVisibility(View.GONE);
	            break;
	    }
	}
}

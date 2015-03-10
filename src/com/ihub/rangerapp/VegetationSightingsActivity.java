package com.ihub.rangerapp;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class VegetationSightingsActivity extends ActionBarActivity {
	
	Spinner seasonSpinner;
	Spinner currentStateSpinner;
	
	ArrayAdapter<CharSequence> seasonsAdapter;
	ArrayAdapter<CharSequence> currentStateAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vegetation_sightings);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
		getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VegetationSightingsActivity.this.onBackPressed();
            }
        });
        
        seasonSpinner = (Spinner) findViewById(R.id.seasonSpinner);
        currentStateSpinner = (Spinner) findViewById(R.id.currentStateSpinner);
        
        seasonsAdapter = ArrayAdapter.createFromResource(this, R.array.seasons_array, android.R.layout.simple_spinner_item);
		seasonsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		seasonSpinner.setAdapter(seasonsAdapter);
		
		currentStateAdapter = ArrayAdapter.createFromResource(this, R.array.vegetation_current_states_array, android.R.layout.simple_spinner_item);
		currentStateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		currentStateSpinner.setAdapter(currentStateAdapter);
	}
}

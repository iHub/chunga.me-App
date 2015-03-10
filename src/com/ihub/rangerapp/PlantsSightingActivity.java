package com.ihub.rangerapp;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class PlantsSightingActivity extends ActionBarActivity {

	Spinner unitOfMeasureSpinner;
	Spinner ageMeasureSpinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plants);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
		getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlantsSightingActivity.this.onBackPressed();
            }
        });
        
        unitOfMeasureSpinner = (Spinner) findViewById(R.id.unitOfMeasureSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.plants_unit_of_measure, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitOfMeasureSpinner.setAdapter(adapter);
        
        ageMeasureSpinner = (Spinner) findViewById(R.id.ageMeasureSpinner);
        ArrayAdapter<CharSequence> ageMeasureAdapter = ArrayAdapter.createFromResource(this, R.array.age_units_array, android.R.layout.simple_spinner_item);
        ageMeasureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageMeasureSpinner.setAdapter(ageMeasureAdapter);
        
	}
}
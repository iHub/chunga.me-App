package com.ihub.rangerapp;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.os.Bundle;

public class CharcoalBurningActivity extends ActionBarActivity {
    
	
	LinearLayout kilnsLayout;
	LinearLayout bagsLayout;
	
	Spinner modeOfTransportSpinner;
	Spinner bagsActionTakenSpinner;
	
	ArrayAdapter<CharSequence> kilnsModeOfTransportAdapter;
	ArrayAdapter<CharSequence> kilnsActionTakenAdapter;
	
	Spinner kilnActionTakenSpinner;
	Spinner freshnessLevelSpinner;
	
	ArrayAdapter<CharSequence> kilnActionTakenAdapter;
	ArrayAdapter<CharSequence> freshnessLevelAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_charcoal_burning);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharcoalBurningActivity.this.onBackPressed();
            }
        });
        
        kilnsLayout = (LinearLayout) findViewById(R.id.kilnsLayout);
        bagsLayout = (LinearLayout) findViewById(R.id.bagsLayout);
        
        initBagsView();
        initKilnsView();
	}
	
	private void initKilnsView() {
		
		freshnessLevelSpinner = (Spinner) findViewById(R.id.freshnessLevelSpinner);
		
		freshnessLevelAdapter = ArrayAdapter.createFromResource(this,
                R.array.kiln_freshness_levels, android.R.layout.simple_spinner_item);
		freshnessLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        freshnessLevelSpinner.setAdapter(freshnessLevelAdapter);
		
		
		
		
		
		
		kilnActionTakenSpinner = (Spinner) findViewById(R.id.kilnActionTakenSpinner);
		
		kilnActionTakenAdapter = ArrayAdapter.createFromResource(this,
                R.array.charcoal_actions_taken, android.R.layout.simple_spinner_item);
        kilnActionTakenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        kilnActionTakenSpinner.setAdapter(kilnActionTakenAdapter);
	}
	
	private void initBagsView() {
		
		modeOfTransportSpinner = (Spinner) findViewById(R.id.modeOfTransportSpinner);
		bagsActionTakenSpinner = (Spinner) findViewById(R.id.bagsActionTakenSpinner);
        
        kilnsModeOfTransportAdapter = ArrayAdapter.createFromResource(this,
                R.array.charcoal_modes_of_transport, android.R.layout.simple_spinner_item);
        kilnsModeOfTransportAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        modeOfTransportSpinner.setAdapter(kilnsModeOfTransportAdapter);
        
        kilnsActionTakenAdapter = ArrayAdapter.createFromResource(this,
                R.array.charcoal_actions_taken, android.R.layout.simple_spinner_item);
        kilnsActionTakenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        bagsActionTakenSpinner.setAdapter(kilnsActionTakenAdapter);
	}
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
	
	public void onTypeRadioButtonClicked(View view) {
		
		boolean checked = ((RadioButton) view).isChecked();
	    
	    switch(view.getId()) {
	        case R.id.radio_kilns:
	        	bagsLayout.setVisibility(View.GONE);
	        	kilnsLayout.setVisibility(View.VISIBLE);
	            break;
	        case R.id.radio_bags:
	        	bagsLayout.setVisibility(View.VISIBLE);
	        	kilnsLayout.setVisibility(View.GONE);
	            break;
	    }
	}
}
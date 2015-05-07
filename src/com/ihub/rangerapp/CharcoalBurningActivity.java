package com.ihub.rangerapp;

import java.util.Map;

import com.ihub.rangerapp.data.service.CharcoalService;
import com.ihub.rangerapp.data.service.CharcoalServiceImpl;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;

public class CharcoalBurningActivity extends CameraGPSActionBarActivity {
	
	LinearLayout kilnsLayout;
	LinearLayout bagsLayout;
	
	EditText noOfKilnsView;
	EditText treeUsedView;
	
	EditText noOfBagsView;
	
	Spinner modeOfTransportSpinner;
	Spinner bagsActionTakenSpinner;
	
	ArrayAdapter<CharSequence> kilnsModeOfTransportAdapter;
	ArrayAdapter<CharSequence> kilnsActionTakenAdapter;
	
	Spinner kilnActionTakenSpinner;
	Spinner freshnessLevelSpinner;
	
	ArrayAdapter<CharSequence> kilnActionTakenAdapter;
	ArrayAdapter<CharSequence> freshnessLevelAdapter;
	
	Boolean isKilnsView = true;
	
	EditText extraNotes;
	Button saveBtn;
	
	LinearLayout switchView;
	
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
        
        Intent data = getIntent();
        
        initViews();
        
        switchView = (LinearLayout) findViewById(R.id.switchView);
        kilnsLayout = (LinearLayout) findViewById(R.id.kilnsLayout);
        bagsLayout = (LinearLayout) findViewById(R.id.bagsLayout);
        
        initBagsView();
        initKilnsView();
        
        extraNotes = (EditText) findViewById(R.id.extraNotes);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        
        saveBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(mode == 3) {
					finish();
				} else
					if(isKilnsView)
						saveKilns();
					else
						saveBags();
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
        		
        		if("bag".equals(data.getStringExtra("view"))) {
        			initBagData();
        		} else
        			initKilnData();
        	}
        }
	}
	
	private void initKilnData() {
		bagsLayout.setVisibility(View.GONE);
    	kilnsLayout.setVisibility(View.VISIBLE);
    	isKilnsView = true; 
    	
    	if(getIntent().hasExtra("noOfKilns"))
    		noOfKilnsView.setText(getIntent().getIntExtra("noOfKilns", 0) + "");
    	
    	if(!TextUtils.isEmpty(getIntent().getStringExtra("freshnessLevels")))
        	for(int i = 0; i < freshnessLevelSpinner.getAdapter().getCount(); i++) {
        		if(freshnessLevelSpinner.getItemAtPosition(i).toString().equals(getIntent().getStringExtra("freshnessLevels")))
        			freshnessLevelSpinner.setSelection(i);
        	}
    	
    	if(!TextUtils.isEmpty(getIntent().getStringExtra("treeUsed")))
    		treeUsedView.setText(getIntent().getStringExtra("treeUsed"));
        
        if(!TextUtils.isEmpty(getIntent().getStringExtra("actionTaken")))
        	for(int i = 0; i < kilnActionTakenSpinner.getAdapter().getCount(); i++) {
        		if(kilnActionTakenSpinner.getItemAtPosition(i).toString().equals(getIntent().getStringExtra("actionTaken")))
        			kilnActionTakenSpinner.setSelection(i);
        	}
        
        if(!TextUtils.isEmpty(getIntent().getStringExtra("extraNotes")))
    		extraNotes.setText(getIntent().getStringExtra("extraNotes"));
    	
	}

	private void initBagData() {
		bagsLayout.setVisibility(View.VISIBLE);
    	kilnsLayout.setVisibility(View.GONE);
    	isKilnsView = false;
    	
    	if(getIntent().hasExtra("noOfBags"))
    		noOfBagsView.setText(getIntent().getIntExtra("noOfBags", 0) + "");
    	
    	if(!TextUtils.isEmpty(getIntent().getStringExtra("modeOfTransport")))
        	for(int i = 0; i < modeOfTransportSpinner.getAdapter().getCount(); i++) {
        		if(modeOfTransportSpinner.getItemAtPosition(i).toString().equals(getIntent().getStringExtra("modeOfTransport")))
        			modeOfTransportSpinner.setSelection(i);
        	}
        
        if(!TextUtils.isEmpty(getIntent().getStringExtra("actionTaken")))
        	for(int i = 0; i < bagsActionTakenSpinner.getAdapter().getCount(); i++) {
        		if(bagsActionTakenSpinner.getItemAtPosition(i).toString().equals(getIntent().getStringExtra("actionTaken")))
        			bagsActionTakenSpinner.setSelection(i);
        	}
        
        if(!TextUtils.isEmpty(getIntent().getStringExtra("extraNotes")))
    		extraNotes.setText(getIntent().getStringExtra("extraNotes"));
	}

	protected void saveKilns() {
		
		Integer noOfKilns = 0;
		
		try {
			noOfKilns = Integer.valueOf(noOfKilnsView.getText().toString());
		} catch (Exception e) {}
		
		if(isValid()) {
			
			Integer id = -1;
			if(mode == 2)
				id = getIntent().getIntExtra("id", -1);
			
			CharcoalService service = new CharcoalServiceImpl();
			Map<String, Object> result = service.saveKilns(
					id,
					noOfKilns, 
					freshnessLevelSpinner.getSelectedItem().toString(), 
					treeUsedView.getText().toString(), 
					kilnActionTakenSpinner.getSelectedItem().toString(), 
					extraNotes.getText().toString(), 
					fileName, 
					getWP());
			
			showSaveResult(result);
		}
	}
	
	protected void saveBags() {
		
	Integer noOfBags = 0;
	
		try {
			noOfBags = Integer.valueOf(noOfBagsView.getText().toString());
		} catch (Exception e) {}
		
		if(isValid()) {
			
			Integer id = -1;
			if(mode == 2)
				id = getIntent().getIntExtra("id", -1);
			
			CharcoalService service = new CharcoalServiceImpl();
			Map<String, Object> result = service.saveBagsData(
					id,
					noOfBags, 
					modeOfTransportSpinner.getSelectedItem().toString(), 
					bagsActionTakenSpinner.getSelectedItem().toString(), 
					extraNotes.getText().toString(), fileName, getWP());
			
			showSaveResult(result);
		}
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
	
	private void initKilnsView() {
		
		noOfKilnsView = (EditText) findViewById(R.id.noOfKilnsView);
		treeUsedView = (EditText) findViewById(R.id.treeUsedView);
		
		freshnessLevelSpinner = (Spinner) findViewById(R.id.freshnessLevelSpinner);
		
		freshnessLevelAdapter = ArrayAdapter.createFromResource(this,
                R.array.kiln_freshness_levels, android.R.layout.simple_spinner_item);
		freshnessLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        freshnessLevelSpinner.setAdapter(freshnessLevelAdapter);
		
		kilnActionTakenSpinner = (Spinner) findViewById(R.id.kilnActionTakenSpinner);
		
		kilnActionTakenAdapter = ArrayAdapter.createFromResource(this,
                R.array.charcoal_kilns_actions_taken, android.R.layout.simple_spinner_item);
        kilnActionTakenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        kilnActionTakenSpinner.setAdapter(kilnActionTakenAdapter);
	}
	
	private void initBagsView() {
		
		noOfBagsView = (EditText) findViewById(R.id.noOfBagsView);
		
		modeOfTransportSpinner = (Spinner) findViewById(R.id.modeOfTransportSpinner);
		bagsActionTakenSpinner = (Spinner) findViewById(R.id.bagsActionTakenSpinner);
        
        kilnsModeOfTransportAdapter = ArrayAdapter.createFromResource(this,
                R.array.charcoal_modes_of_transport, android.R.layout.simple_spinner_item);
        kilnsModeOfTransportAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        modeOfTransportSpinner.setAdapter(kilnsModeOfTransportAdapter);
        
        kilnsActionTakenAdapter = ArrayAdapter.createFromResource(this,
                R.array.charcoal_bags_actions_taken, android.R.layout.simple_spinner_item);
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
	        	isKilnsView = true;
	            break;
	        case R.id.radio_bags:
	        	bagsLayout.setVisibility(View.VISIBLE);
	        	kilnsLayout.setVisibility(View.GONE);
	        	isKilnsView = false;
	            break;
	    }
	}
}
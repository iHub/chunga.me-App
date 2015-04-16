package com.ihub.rangerapp;

import java.util.Map;

import com.ihub.rangerapp.data.service.CharcoalService;
import com.ihub.rangerapp.data.service.CharcoalServiceImpl;

import android.support.v7.app.ActionBarActivity;
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
        
        initViews();
        
        kilnsLayout = (LinearLayout) findViewById(R.id.kilnsLayout);
        bagsLayout = (LinearLayout) findViewById(R.id.bagsLayout);
        
        initBagsView();
        initKilnsView();
        
        extraNotes = (EditText) findViewById(R.id.extraNotes);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        
        saveBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(isKilnsView)
					saveKilns();
				else
					saveBags();
			}
		});
	}
	
	protected void saveKilns() {
		
		Integer noOfKilns = 0;
		
		try {
			noOfKilns = Integer.valueOf(noOfKilnsView.getText().toString());
		} catch (Exception e) {}
		
		if(isValid()) {
			CharcoalService service = new CharcoalServiceImpl();
			Map<String, Object> result = service.saveKilns(
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
			noOfBags = Integer.valueOf(noOfKilnsView.getText().toString());
		} catch (Exception e) {}
		
		if(isValid()) {
			CharcoalService service = new CharcoalServiceImpl();
			Map<String, Object> result = service.saveBagsData(
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
                R.array.charcoal_actions_taken, android.R.layout.simple_spinner_item);
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
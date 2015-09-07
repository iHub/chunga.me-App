package com.ihub.rangerapp;

import java.util.Map;
import com.ihub.rangerapp.data.service.CharcoalService;
import com.ihub.rangerapp.data.service.CharcoalServiceImpl;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class CharcoalBurningActivity extends CameraGPSActionBarActivity {
	
	LinearLayout kilnsLayout;
	LinearLayout bagsLayout;
	
	EditText noOfKilnsView;
	AutoCompleteTextView treeUsedView;
	
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
	
	RadioGroup radioGroup;
	
	String trees[] = {"Acacia bussei","Acacia etbaica","Acacia hockii","Acacia mellifera","Acacia nilotica","Acacia robusta","Acacia senegal","Acacia sp","Acacia thomasii","Acacia tortilis","Acacia zanzibarica","Albizia anthelmintica","Albizia harveyi","Albizia zimmermannii","Balanites aegyptiaca","Balanites pedicellaris","Boscia angustifolia","Boscia coriacea","Boswellia neglecta","Cassia abbreviata","Cassia siamea","Commiphora africana","Commiphora baluensis","Commiphora campestris","Commiphora confusa","Commiphora edulis","Commiphora eminii","Commiphora holtziana","Commiphora lindensis","Commiphora schimperi","Commiphora sp","Dalbergia melanoxylon","Delonix elata","Diospyros consolatae","Diospyros mespiliformis","Dobera glabra","Erythrina abyssinica","Euclea divinorum","Euphorbia bussei","Euphorbia quinquecostata","Ficus lutea","Gardenia volkensii","Haplocoelum foliolosum","Lannea alata","Lannea rivae","Lannea schweinfurthii","Lannea sp","Manilkara mochisia","Manilkara sulcata","Melia volkensii","Newtonia hildebrandtii","Ormocarpum kirkii","Pittosporum viridiflorum","Platycelyphium voense","Salvadora persica","Sterculia africana","Tamarindus indica","Terminalia brownii","Terminalia prunoides","Terminalia spinosa","Unknown","Zanthoxylum chalybeum"};
	
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
        
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
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
					if(isKilnsView) {
						
						if(isValid()) {
							
							if(isValid()) {
								View[] fields = new View[] {noOfKilnsView, freshnessLevelSpinner, treeUsedView, kilnActionTakenSpinner, extraNotes};
								String fieldNames[] = {"No of Kilns", "Kiln State", "Tree Used", "Action Taken", "Extra Notes"};
								
								if(hasInvalidFields(fields)) {
									
									String msg = "The following fields have no values.\n\n" + getInvalidFields(fields, fieldNames) + "\nDo you wish to continue?";
									new AlertDialog.Builder(CharcoalBurningActivity.this)
										.setMessage(msg)
										.setCancelable(false)
										.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
											
											public void onClick(DialogInterface dialog, int id) {
												saveKilns();
											}
										})
										.setNegativeButton(R.string.no, null)
										.show();
									
								} else {
									saveKilns();
								}
							}
						}

					} else							
						if(isValid()) {
							View[] fields = new View[] {noOfBagsView, modeOfTransportSpinner, bagsActionTakenSpinner, extraNotes};
							String fieldNames[] = {"No of Bags", "Mode of Transport", "Action Taken", "Extra Notes"};
							
							if(hasInvalidFields(fields)) {
								
								String msg = "The following fields have no values.\n\n" + getInvalidFields(fields, fieldNames) + "\nDo you wish to continue?";
								new AlertDialog.Builder(CharcoalBurningActivity.this)
									.setMessage(msg)
									.setCancelable(false)
									.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
										
										public void onClick(DialogInterface dialog, int id) {
											saveBags();
										}
									})
									.setNegativeButton(R.string.no, null)
									.show();
								
							} else {
								saveBags();
							}
						}
			}
		});
        
        if(mode != 1) {
        	radioGroup.setVisibility(View.GONE);
        	
        	waypointView.setText(getIntent().getStringExtra("waypoint"));
        	
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
			
			String freshnessLevel = freshnessLevelSpinner.getSelectedItemPosition() == 0 ? "" : freshnessLevelSpinner.getSelectedItem().toString();
			String kilnActionTaken = kilnActionTakenSpinner.getSelectedItemPosition() == 0 ? "" : kilnActionTakenSpinner.getSelectedItem().toString();
			
			CharcoalService service = new CharcoalServiceImpl();
			Map<String, Object> result = service.saveKilns(
					id,
					noOfKilns, 
					freshnessLevel, 
					treeUsedView.getText().toString(), 
					kilnActionTaken, 
					extraNotes.getText().toString(), 
					imagePath, 
					waypointView.getText().toString());
			
			if(mode == 2) {
				Intent data = new Intent();
				
				data.putExtra("imagePath", imagePath);
				data.putExtra("id", id);
				data.putExtra("noOfKilns", noOfKilns);
				data.putExtra("freshnessLevels", freshnessLevel);
				data.putExtra("treeUsed", treeUsedView.getText().toString());
				data.putExtra("actionTaken", kilnActionTaken);
				data.putExtra("extraNotes", extraNotes.getText().toString());
				
				setResult(RESULT_OK, data);
			}
			
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
			
			String modeOfTransport = modeOfTransportSpinner.getSelectedItemPosition() == 0 ? "" : modeOfTransportSpinner.getSelectedItem().toString();
			String bagsActionTaken = bagsActionTakenSpinner.getSelectedItemPosition() == 0 ? "" : bagsActionTakenSpinner.getSelectedItem().toString();
			
			CharcoalService service = new CharcoalServiceImpl();
			Map<String, Object> result = service.saveBagsData(
					id,
					noOfBags, 
					modeOfTransport, 
					bagsActionTaken, 
					extraNotes.getText().toString(), 
					imagePath,
					waypointView.getText().toString());
			
			if(mode == 2) {
				Intent data = new Intent();
				
				data.putExtra("imagePath", imagePath);
				data.putExtra("id", id);
				data.putExtra("noOfBags", noOfBags);
				data.putExtra("modeOfTransport", modeOfTransport);
				data.putExtra("actionTaken", bagsActionTaken);
				data.putExtra("extraNotes", extraNotes.getText().toString());
				
				setResult(RESULT_OK, data);
			}
			
			showSaveResult(result);
		}
	}
	
	private void initKilnsView() {
		
		noOfKilnsView = (EditText) findViewById(R.id.noOfKilnsView);
		treeUsedView = (AutoCompleteTextView) findViewById(R.id.treeUsedView);
		
		ArrayAdapter<String> treesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, trees);
        treeUsedView.setAdapter(treesAdapter);
        treeUsedView.setThreshold(1);
		
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
	
	public String getInvalidFields(View[] views, String[] fieldNames) {
		
		String str = "";
		
		for(int i = 0; i < views.length; i++) {
			if(views[i] instanceof TextView) {
				if(((EditText)views[i]).getText().toString().trim().equals("")) {
					str += fieldNames[i] + ".\n";
				}
			} else if(views[i] instanceof Spinner) {
				if(((Spinner)views[i]).getSelectedItemPosition() == 0) {
					str += fieldNames[i] + ".\n";
				}
			}
		}
		
		if(TextUtils.isEmpty(imagePath))
			str += "\nYou have not attached a photo" + ".\n";
		
		return str;
	}
}
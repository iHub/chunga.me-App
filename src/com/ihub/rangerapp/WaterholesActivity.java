package com.ihub.rangerapp;

import java.util.Map;
import com.ihub.rangerapp.data.service.WaterholeService;
import com.ihub.rangerapp.data.service.WaterholeServiceImpl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class WaterholesActivity extends CameraGPSActionBarActivity {
	
	EditText waterholeName;
	Spinner levelOfWaterSpinner;
	EditText noOfAnimalsSeenView;
	EditText extraNotes;
	Button saveBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_waterholes);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WaterholesActivity.this.onBackPressed();
            }
        });
        
        Intent data = getIntent();
        
        initViews();
        
        waterholeName = (EditText) findViewById(R.id.waterholeName);
        levelOfWaterSpinner = (Spinner) findViewById(R.id.levelOfWaterSpinner);
        noOfAnimalsSeenView = (EditText) findViewById(R.id.noOfAnimalsSeenView);
        extraNotes = (EditText) findViewById(R.id.extraNotes);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        
        levelOfWaterSpinner = (Spinner) findViewById(R.id.levelOfWaterSpinner);
        ArrayAdapter<CharSequence> levelOfWaterAdapter = ArrayAdapter.createFromResource(this,
                R.array.levels_of_water_array, android.R.layout.simple_spinner_item);
        levelOfWaterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        levelOfWaterSpinner.setAdapter(levelOfWaterAdapter);
        
        saveBtn.setOnClickListener(new View.OnClickListener() {
        	
			@Override
			public void onClick(View v) {
				if(mode == 3)
					finish();
				else
					if(isValid())
						save();
			}
		});
        
        if(mode != 1) {
        	
        	//TODO prefill values
        	
        	if(!TextUtils.isEmpty(getIntent().getStringExtra("name")))
        		waterholeName.setText(getIntent().getStringExtra("name"));
        	
        	if(!TextUtils.isEmpty(getIntent().getStringExtra("levelOfWater")))
            	for(int i = 0; i < levelOfWaterAdapter.getCount(); i++) {
            		if(levelOfWaterSpinner.getItemAtPosition(i).toString().equals(getIntent().getStringExtra("levelOfWater")))
            			levelOfWaterSpinner.setSelection(i);
            	}
        	
        	if(data.hasExtra("numberOfAnimals"))
            	noOfAnimalsSeenView.setText(data.getIntExtra("numberOfAnimals", 0) + "");
        	
        	if(!TextUtils.isEmpty(getIntent().getStringExtra("extraNotes")))
        		extraNotes.setText(getIntent().getStringExtra("extraNotes"));
            
            if(mode == 2) {
            	saveBtn.setText(getString(R.string.edit));
            } else {
            	saveBtn.setText(getString(R.string.close));
            }
        }
	}
	
	protected void save() {
		
		Integer noOfAnimals = 0;
		
		try {
			noOfAnimals = Integer.valueOf(noOfAnimalsSeenView.getText().toString());
		} catch (Exception e) {}
		
		Integer id = -1;
		if(mode == 2)
			id = getIntent().getIntExtra("id", -1);
		
		WaterholeService service = new WaterholeServiceImpl();
		Map<String, Object> result = service.save(
			id,
			waterholeName.getText().toString(), 
			levelOfWaterSpinner.getSelectedItem().toString(), 
			noOfAnimals, 
			extraNotes.getText().toString(), 
			imagePath, 
			getWP());
		
		showSaveResult(result);
	}
}
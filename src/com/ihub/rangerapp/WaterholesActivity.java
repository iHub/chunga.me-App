package com.ihub.rangerapp;

import java.util.Map;
import com.ihub.rangerapp.data.service.WaterholeService;
import com.ihub.rangerapp.data.service.WaterholeServiceImpl;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
				if(isValid())
					save();
			}
		});
	}
	
	protected void save() {
		
		Integer noOfAnimals = 0;
		
		try {
			noOfAnimals = Integer.valueOf(noOfAnimalsSeenView.getText().toString());
		} catch (Exception e) {}
		
		WaterholeService service = new WaterholeServiceImpl();
		Map<String, Object> result = service.save(
			waterholeName.getText().toString(), 
			levelOfWaterSpinner.getSelectedItem().toString(), 
			noOfAnimals, 
			extraNotes.getText().toString(), 
			fileName, 
			getWP());
		
		showSaveResult(result);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.waterholes, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
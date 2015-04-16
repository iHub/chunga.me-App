package com.ihub.rangerapp;

import java.util.Map;

import com.ihub.rangerapp.data.service.GameMeatService;
import com.ihub.rangerapp.data.service.GameMeatServiceImpl;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Bundle;

public class GameMeatActivity extends CameraGPSActionBarActivity {
	
	EditText animalView;
	EditText noOfAnimalsView;
	Spinner actionTakenSpinner;
	
	EditText extraNotes;
	Button saveBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_meat);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameMeatActivity.this.onBackPressed();
            }
        });
        
        initViews();
        
        animalView = (EditText) findViewById(R.id.animalView);
        noOfAnimalsView = (EditText) findViewById(R.id.noOfAnimalsView);
        actionTakenSpinner = (Spinner) findViewById(R.id.actionTakenSpinner);
        extraNotes = (EditText) findViewById(R.id.extraNotes);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        
        ArrayAdapter<CharSequence> actionTakenAdapter = ArrayAdapter.createFromResource(this,
                R.array.suspicious_activities_actions_taken, android.R.layout.simple_spinner_item);
        actionTakenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        actionTakenSpinner.setAdapter(actionTakenAdapter);
        
        saveBtn.setOnClickListener(new View.OnClickListener() {
        	
			@Override
			public void onClick(View v) {
				if(isValid())
					save();
			}
		});
	}
	
	protected void save() {
		
		GameMeatService service = new GameMeatServiceImpl();
		Integer noOfAnimals = 0;
		try {
			noOfAnimals = Integer.valueOf(noOfAnimalsView.getText().toString());
		} catch (Exception e) {}
		
		Map<String, Object> result = service.save(
				animalView.getText().toString(), 
				noOfAnimals, 
				actionTakenSpinner.getSelectedItem().toString(), 
				extraNotes.getText().toString(), 
				fileName, 
				getWP());
		
		showSaveResult(result);
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
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
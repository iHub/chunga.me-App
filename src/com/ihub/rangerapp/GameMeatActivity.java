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
import android.content.Intent;
import android.os.Bundle;

public class GameMeatActivity extends CameraGPSActionBarActivity {
	
	EditText animalView;
	EditText noOfAnimalsView;
	Spinner actionTakenSpinner;
	
	EditText extraNotes;
	Button saveBtn;
	
	//Boolean isEdit = false;
	
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
        
        Intent data = getIntent();
        
        initViews();
        
        animalView = (EditText) findViewById(R.id.animalView);
        noOfAnimalsView = (EditText) findViewById(R.id.noOfAnimalsView);
        actionTakenSpinner = (Spinner) findViewById(R.id.actionTakenSpinner);
        extraNotes = (EditText) findViewById(R.id.extraNotes);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        
        ArrayAdapter<CharSequence> actionTakenAdapter = ArrayAdapter.createFromResource(this,
                R.array.game_meat_actions_taken, android.R.layout.simple_spinner_item);
        actionTakenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        actionTakenSpinner.setAdapter(actionTakenAdapter);
        
        saveBtn.setOnClickListener(new View.OnClickListener() {
        	
			@Override
			public void onClick(View v) {
				
				if(mode == 3) {
					finish();
				} else
					if(isValid())
						save();
			}
		});
        
        switch(mode) {
        	case 1:
        		
        		break;
        	case 2:
        	case 3:
        		if(!TextUtils.isEmpty(getIntent().getStringExtra("animal")))
            		animalView.setText(getIntent().getStringExtra("animal"));
        		
                if(data.hasExtra("noOfAnimals"))
                	noOfAnimalsView.setText(data.getIntExtra("noOfAnimals", 0) + "");
                
                if(!TextUtils.isEmpty(getIntent().getStringExtra("actionTaken")))
                	for(int i = 0; i < actionTakenAdapter.getCount(); i++) {
                		if(actionTakenSpinner.getItemAtPosition(i).toString().equals(getIntent().getStringExtra("actionTaken")))
                			actionTakenSpinner.setSelection(i);
                	}
                
                if(!TextUtils.isEmpty(getIntent().getStringExtra("extraNotes")))
            		extraNotes.setText(getIntent().getStringExtra("extraNotes"));
                
                if(mode == 2) {
                	saveBtn.setText(getString(R.string.edit));
                } else {
                	saveBtn.setText(getString(R.string.close));
                }
                
        		break;
        	default:
        		
        		break;
        }
	}
	
	protected void save() {
		
		GameMeatService service = new GameMeatServiceImpl();
		Integer noOfAnimals = 0;
		try {
			noOfAnimals = Integer.valueOf(noOfAnimalsView.getText().toString());
		} catch (Exception e) {}
		
		Integer id = -1;
		if(mode == 2)
			id = getIntent().getIntExtra("id", -1);
		
		Map<String, Object> result = service.save(
				id,
				animalView.getText().toString(), 
				noOfAnimals, 
				actionTakenSpinner.getSelectedItem().toString(), 
				extraNotes.getText().toString(), 
				imagePath, 
				getWP());
		
		if(mode == 2) {
			Intent data = new Intent();
			
			setResult(RESULT_OK, data);
		}
		
		showSaveResult(result);
	}
	
	protected Boolean isValid() {
		
		Boolean isValid = true;
		
		if(TextUtils.isEmpty(imagePath)) {
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
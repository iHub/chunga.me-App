package com.ihub.rangerapp;

import java.util.Map;
import com.ihub.rangerapp.data.service.GameMeatService;
import com.ihub.rangerapp.data.service.GameMeatServiceImpl;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class GameMeatActivity extends CameraGPSActionBarActivity {
	
	AutoCompleteTextView animalView;
	EditText noOfAnimalsView;
	Spinner actionTakenSpinner;
	
	EditText extraNotes;
	Button saveBtn;
	
	String animals[] = {"Antelope", "Buffalo", "Bush Pig", "Crested Guineafowl", "Giragge", "Impala", "Kudu", "Porcupine", "Ostrich", "Rabbit", "Thomson's Gazelle", "Warthog", "Zebra"};
	
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
        
        animalView = (AutoCompleteTextView) findViewById(R.id.animalView);
        noOfAnimalsView = (EditText) findViewById(R.id.noOfAnimalsView);
        actionTakenSpinner = (Spinner) findViewById(R.id.actionTakenSpinner);
        extraNotes = (EditText) findViewById(R.id.extraNotes);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        
        ArrayAdapter<CharSequence> actionTakenAdapter = ArrayAdapter.createFromResource(this,
                R.array.game_meat_actions_taken, android.R.layout.simple_spinner_item);
        actionTakenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        ArrayAdapter<String> animalsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, animals);
        animalView.setAdapter(animalsAdapter);
        animalView.setThreshold(1);
        
        actionTakenSpinner.setAdapter(actionTakenAdapter);
        
        saveBtn.setOnClickListener(new View.OnClickListener() {
        	
			@Override
			public void onClick(View v) {
				
				if(mode == 3) {
					finish();
				} else
					if(isValid()) {
						View[] fields = new View[] {animalView, noOfAnimalsView, actionTakenSpinner, extraNotes};
						
						if(hasInvalidFields(fields)) {
							
							String msg = "The following fields have no values.\n\n" + getInvalidFields(fields) + "\nDo you wish to continue?";
							new AlertDialog.Builder(GameMeatActivity.this)
								.setMessage(msg)
								.setCancelable(false)
								.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
									
									public void onClick(DialogInterface dialog, int id) {
										save();
									}
								})
								.setNegativeButton(R.string.no, null)
								.show();
							
						} else {
							save();
						}
					}
				//TODO show error msgs at this point
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
                
                latView.setText(getIntent().getStringExtra("lat"));
                longView.setText(getIntent().getStringExtra("lon"));
                
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
		
		String actionTaken = actionTakenSpinner.getSelectedItemPosition() == 0 ? "" : actionTakenSpinner.getSelectedItem().toString();
		
		Map<String, Object> result = service.save(
				id,
				animalView.getText().toString(), 
				noOfAnimals, 
				actionTaken, 
				extraNotes.getText().toString(), 
				imagePath, 
				latView.getText().toString(),
				longView.getText().toString());
		
		if(mode == 2) {
			Intent data = new Intent();
			
			data.putExtra("id", id);
			data.putExtra("animal", animalView.getText().toString());
			data.putExtra("noOfAnimals", noOfAnimals);
			data.putExtra("actionTaken", actionTaken);
			data.putExtra("extraNotes", extraNotes.getText().toString());
			
			setResult(RESULT_OK, data);
		}
		
		showSaveResult(result);
	}
	
	public String[] getLabels() {
		return new String[]{"Animal", "Number of Animals", "Action Taken", "Extra Notes"};
	}
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
package com.ihub.rangerapp;

import java.util.Map;
import com.ihub.rangerapp.data.service.ShiftService;
import com.ihub.rangerapp.data.service.ShiftServiceImpl;

import eu.inmite.android.lib.validations.form.FormValidator;
import eu.inmite.android.lib.validations.form.annotations.MinLength;
import eu.inmite.android.lib.validations.form.annotations.MinNumberValue;
import eu.inmite.android.lib.validations.form.annotations.NotEmpty;
import eu.inmite.android.lib.validations.form.callback.SimpleErrorPopupCallback;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class StartShiftActivity extends ActionBarActivity {
	
	@NotEmpty(messageId = R.string.validation_leader, order = 1)
	@MinLength(value = 5, messageId = R.string.validation_leader_length, order = 2)
	EditText leaderView;
	
	@MinNumberValue(value= "1", messageId = R.string.validation_min_member_count, order = 3)
	EditText noOfMembersView;
	
	@NotEmpty(messageId = R.string.validation_start_weight_point, order = 4)
	EditText startWPView;
	
	@NotEmpty(messageId = R.string.validation_end_weight_point, order = 5)
	EditText endWPView;
	
	@NotEmpty(messageId = R.string.validation_purpose, order = 6)
	EditText purposeView;
	
	Button startShiftBtn;
	
	//change to drop downs
	Spinner stationSpinner;
	Spinner ranchSpinner;
	EditText routeView;
	Spinner modeSpinner;
	Spinner weatherSpinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_shift);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartShiftActivity.this.onBackPressed();
            }
        });
        
        leaderView = (EditText) findViewById(R.id.leaderView);
        noOfMembersView = (EditText) findViewById(R.id.noOfMembersView);
        startWPView = (EditText) findViewById(R.id.startWPView);
        endWPView = (EditText) findViewById(R.id.endWPView);
        purposeView = (EditText) findViewById(R.id.purposeView);
        
        startShiftBtn = (Button) findViewById(R.id.startShiftBtn);
        startShiftBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(!hasOpenShift())
					if(isValid())
						startShift();
			}
		});
        
        stationSpinner = (Spinner) findViewById(R.id.stationSpinner);
        ranchSpinner = (Spinner) findViewById(R.id.ranchSpinner);
        routeView = (EditText) findViewById(R.id.routeView);
        modeSpinner = (Spinner) findViewById(R.id.modeSpinner);
        weatherSpinner = (Spinner) findViewById(R.id.weatherSpinner);
        
        
        ArrayAdapter<CharSequence> stationsAdapter = ArrayAdapter.createFromResource(this,
                R.array.base_stations, android.R.layout.simple_spinner_item);
        stationsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        stationSpinner.setAdapter(stationsAdapter);
        
        
        ArrayAdapter<CharSequence> ranchesAdapter = ArrayAdapter.createFromResource(this,
                R.array.ranches, android.R.layout.simple_spinner_item);
        ranchesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        ranchSpinner.setAdapter(ranchesAdapter);
        
        
        ArrayAdapter<CharSequence> modeAdapter = ArrayAdapter.createFromResource(this,
                R.array.transport_modes, android.R.layout.simple_spinner_item);
        modeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        modeSpinner.setAdapter(modeAdapter);
        
        ArrayAdapter<CharSequence> weatherAdapter = ArrayAdapter.createFromResource(this,
                R.array.weather_conditions, android.R.layout.simple_spinner_item);
        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        weatherSpinner.setAdapter(weatherAdapter);
	}
	
	private Boolean isValid() {
		
		Boolean isValid = FormValidator.validate(this, new SimpleErrorPopupCallback(this));
		
		//validate station
		//validate ranch
		//validate mode
		//validate weather
		
		if(isValid) {
			
			if("".equals(stationSpinner.getSelectedItem().toString())) {
				isValid = false;
				showPopupError(getString(R.string.validation_station));
			}
			
			if(isValid && "".equals(ranchSpinner.getSelectedItem().toString())) {
				isValid = false;
				showPopupError(getString(R.string.validation_ranch));
			}
			
			if(isValid && "".equals(modeSpinner.getSelectedItem().toString())) {
				isValid = false;
				showPopupError(getString(R.string.validation_transport_mode));
			}
			
			if(isValid && "".equals(weatherSpinner.getSelectedItem().toString())) {
				isValid = false;
				showPopupError(getString(R.string.validation_weather));
			}
		}
		
		return isValid;
	}
	
	private void showPopupError(String msg) {
		Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.TOP, 0, 0);
		toast.show();
	}
	
	protected void startShift() {
		//call service which saves data to local db
		//use asynctask
		
		new StartShiftTask().execute(
			stationSpinner.getSelectedItem().toString(),
			ranchSpinner.getSelectedItem().toString(),
			leaderView.getText().toString(),
			noOfMembersView.getText().toString(),
			routeView.getText().toString(),
			modeSpinner.getSelectedItem().toString(),
			weatherSpinner.getSelectedItem().toString(),
			startWPView.getText().toString(),
			endWPView.getText().toString(),
			purposeView.getText().toString());
	}
	
	private boolean hasOpenShift() { //TODO implement
		return false;
	}
	
	class StartShiftTask extends AsyncTask<String , String, Map<String, Object>> {
		
		@Override
		protected Map<String, Object> doInBackground(String... params) {
			
			ShiftService service = new ShiftServiceImpl();
			return service.startShift(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9]);
		}
		
		@Override
		protected void onPostExecute(Map<String, Object> result) {
			super.onPostExecute(result);
			
			String status = result.get("status") + "";
			
			if("error".equals(status)) {
				Toast toast = Toast.makeText(StartShiftActivity.this, result.get("message") + "", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.TOP, 0, 0);
				toast.show();
			} else {
				Toast.makeText(StartShiftActivity.this, getString(R.string.start_shift_successful), Toast.LENGTH_LONG).show();
				
				setResult(RESULT_OK, new Intent());
				finish();
			}
		}
	}
}
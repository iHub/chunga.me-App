package com.ihub.rangerapp;

import java.util.HashMap;
import java.util.Map;

import com.ihub.rangerapp.data.service.ShiftService;
import com.ihub.rangerapp.data.service.ShiftServiceImpl;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.AsyncTask;
import android.os.Bundle;

public class StartShiftActivity extends ActionBarActivity {
	
	EditText leaderView;
	EditText noOfMembersView;
	EditText startWPView;
	EditText endWPView;
	EditText purposeView;
	
	Button startShiftBtn;
	
	
	//change to drop downs
	EditText stationView;
	EditText ranchView;
	EditText routeView;
	EditText modeView;
	EditText weatherView;
	
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
        
        stationView = (EditText) findViewById(R.id.stationView);
        ranchView = (EditText) findViewById(R.id.ranchView);
        routeView = (EditText) findViewById(R.id.routeView);
        modeView = (EditText) findViewById(R.id.modeView);
        weatherView = (EditText) findViewById(R.id.weatherView);
	}
	
	protected void startShift() {
		//call service which saves data to local db
		//use asynctask
		
		new StartShiftTask().execute(
				stationView.getText().toString(),
				ranchView.getText().toString(),
				leaderView.getText().toString(),
				noOfMembersView.getText().toString(),
				routeView.getText().toString(),
				modeView.getText().toString(),
				weatherView.getText().toString(),
				startWPView.getText().toString(),
				endWPView.getText().toString(),
				purposeView.getText().toString());
	}

	private boolean isValid() { //TODO implement
		
		return true;
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
			
			Toast.makeText(StartShiftActivity.this, result.get("status") + "", Toast.LENGTH_LONG).show();
		}
	}
}
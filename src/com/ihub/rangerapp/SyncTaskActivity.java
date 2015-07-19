package com.ihub.rangerapp;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ihub.rangerapp.data.service.GameMeatService;
import com.ihub.rangerapp.data.service.GameMeatServiceImpl;
import com.ihub.rangerapp.data.service.ShiftService;
import com.ihub.rangerapp.data.service.ShiftServiceImpl;
import com.ihub.rangerapp.data.service.SyncService;
import com.ihub.rangerapp.data.service.SyncServiceImpl;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SyncTaskActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sync_task);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SyncTaskActivity.this.onBackPressed();
            }
        });
        
//        ShiftService service = new ShiftServiceImpl();
//        Long sID = service.getCurrentShiftID();
//        
        
        //game meat
        
        GameMeatService service = new GameMeatServiceImpl();
        service.sync(1, new JsonHttpResponseHandler() {
        	
        	@Override
        	public void onSuccess(int statusCode, Header[] headers,JSONObject response) {
        		super.onSuccess(statusCode, headers, response);
        		Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
        	}
		});
        
        
//        service.syncShift(sID.intValue(), new JsonHttpResponseHandler() {
//        	
//        	@Override
//        	public void onSuccess(int statusCode, Header[] headers,JSONObject response) {
//        		super.onSuccess(statusCode, headers, response);
//        		Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
//        	}
//		});
	}
}
package com.ihub.rangerapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ihub.rangerapp.adapter.SyncAdapter;
import com.ihub.rangerapp.data.service.SyncService;
import com.ihub.rangerapp.data.service.SyncServiceImpl;
import com.ihub.rangerapp.data.sqlite.DBPreferences;
import com.ihub.rangerapp.entity.SyncModel;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SyncActivity extends ActionBarActivity {
	
	ListView list;
	
	SyncAdapter adapter;
	
	TextView titleView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sync);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SyncActivity.this.onBackPressed();
            }
        });
        
        titleView = (TextView) findViewById(android.R.id.title);
        
        list = (ListView) findViewById(android.R.id.list);
        adapter = new SyncAdapter(this, 0, new ArrayList<SyncModel>());
                
        list.setAdapter(adapter);
        
        //load last sync date
        //load sync counts
        
        SharedPreferences prefs = getSharedPreferences(RangerApp.class.getName(), Context.MODE_PRIVATE);
		String rangerID = prefs.getString(DBPreferences.RANGER_ID, "");
		
        new Task().execute(rangerID);
	}
	
	class Task extends AsyncTask<String, Void, Map<String, Object>> {

		@Override
		protected Map<String, Object> doInBackground(String... params) {

			String rangerID = params[0];
			
			Map<String, Object> data = new HashMap<String, Object>();
			
			SyncService service = new SyncServiceImpl();
	        Date lastSyncDate = service.loadLastSyncDate(Integer.valueOf(rangerID));
	        
	        if(lastSyncDate != null) {
	        	data.put("lastSyncDate", lastSyncDate);
	        }
	        
	        data.put("rows", service.loadCounts(Integer.valueOf(rangerID)));
	        
			
			return data;
		}
		
		@Override
		protected void onPostExecute(Map<String, Object> result) {
			
			if(result.containsKey("lastSyncDate")) {
				
				Date lastSyncDate = (Date) result.get("lastSyncDate");
				String syncStr = getString(R.string.sync_str);
	        	syncStr = syncStr.replace("{date}", new SimpleDateFormat( "yyyy-MM-dd HH:MM" ).format(lastSyncDate));
	        	
	        	titleView.setText(syncStr);
			}
			
			Map<String, Integer> rows = (Map<String, Integer>) result.get("rows");
        	
        	Set<String> keys = rows.keySet();
        	
        	for(String s : keys) {
        		
        		Integer count = rows.get(s);
        		
        		if(count > 0)
        			adapter.add(new SyncModel(s, count));
        	}

			super.onPostExecute(result);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sync, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
//		if (id == R.id.action_logout) {
//			logout();
//		} else if(id == R.id.action_export) {
//			showSyncView();
//		}
		return super.onOptionsItemSelected(item);
	}
}
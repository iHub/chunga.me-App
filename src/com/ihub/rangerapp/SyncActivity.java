package com.ihub.rangerapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.ihub.rangerapp.adapter.SyncAdapter;
import com.ihub.rangerapp.data.service.SyncService;
import com.ihub.rangerapp.data.service.SyncServiceImpl;
import com.ihub.rangerapp.data.sqlite.DBPreferences;
import com.ihub.rangerapp.entity.SyncModel;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class SyncActivity extends ActionBarActivity {
	
	ListView list;
	
	SyncAdapter adapter;
	
	TextView titleView;
	
	Boolean hasSync = false;
	
	List<Pair<String, Integer>> rows;
	
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
        
        new Task().execute();
	}
	
	class Task extends AsyncTask<String, Void, Map<String, Object>> {
		
		@Override
		protected Map<String, Object> doInBackground(String... params) {
			
			Map<String, Object> data = new HashMap<String, Object>();
			
			SyncService service = new SyncServiceImpl();
	        Date lastSyncDate = service.loadLastSyncDate();
	        
	        if(lastSyncDate != null) {
	        	data.put("lastSyncDate", lastSyncDate);
	        }
	        
	        data.put("rows", service.loadCounts());
	        
			return data;
		}
		
		@Override
		protected void onPostExecute(Map<String, Object> result) {
			
			if(result.containsKey("lastSyncDate")) {
				
				Date lastSyncDate = (Date) result.get("lastSyncDate");
				String syncStr = getString(R.string.sync_str);
				
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:MM");
				df.setTimeZone(TimeZone.getTimeZone("UTC"));
				
	        	syncStr = syncStr.replace("{date}", df.format(lastSyncDate));
	        	
	        	titleView.setText(syncStr);
			}
			
			rows = (List<Pair<String, Integer>>) result.get("rows");
        	
        	for(Pair<String, Integer> row : rows) {
        		
        		String value = row.first;
        		Integer count = row.second;
        		
        		if(count > 0) {
        			hasSync = true;
        			supportInvalidateOptionsMenu();
        			adapter.add(new SyncModel(value, count));
        		}
        	}
        	
			super.onPostExecute(result);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sync, menu);
		
		MenuItem sync = menu.findItem(R.id.action_sync);		
		sync.setVisible(hasSync);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_sync) {
			if(isNetworkAvailable()) {
				sync();
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Please check your Internet connection")
				       .setCancelable(false)
				       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
			}
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void sync() {
		
		Intent intent = new Intent(SyncActivity.this, SyncTaskActivity.class);
		
		if(rows != null) {
			
			String[] names = new String[rows.size()];
			int[] values = new int[rows.size()];
			
			int total = 0;
			
			int i = 0;
			
			for(Pair<String, Integer> row : rows) {
        		
        		String name = row.first;
        		Integer count = row.second;
        		
        		names[i] = name;
				values[i] = count;
				total += count;
				
				intent.putExtra("names", names);
				intent.putExtra("values", values);
				intent.putExtra("total", total);
        		
        		i++;
        	}
		}
		
		startActivity(intent);
		finish();
	}
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
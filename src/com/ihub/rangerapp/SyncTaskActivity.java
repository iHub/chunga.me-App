package com.ihub.rangerapp;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import com.ihub.rangerapp.adapter.SyncTaskAdapter;
import com.ihub.rangerapp.data.service.AnimalSightingsService;
import com.ihub.rangerapp.data.service.AnimalSightingsServiceImpl;
import com.ihub.rangerapp.data.service.CharcoalService;
import com.ihub.rangerapp.data.service.CharcoalServiceImpl;
import com.ihub.rangerapp.data.service.ElephantService;
import com.ihub.rangerapp.data.service.ElephantServiceImpl;
import com.ihub.rangerapp.data.service.GameMeatService;
import com.ihub.rangerapp.data.service.GameMeatServiceImpl;
import com.ihub.rangerapp.data.service.ShiftService;
import com.ihub.rangerapp.data.service.ShiftServiceImpl;
import com.ihub.rangerapp.data.service.SuspiciousActivitiesService;
import com.ihub.rangerapp.data.service.SuspiciousActivitiesServiceImpl;
import com.ihub.rangerapp.data.service.SyncService;
import com.ihub.rangerapp.data.service.SyncServiceImpl;
import com.ihub.rangerapp.data.service.WaterholeService;
import com.ihub.rangerapp.data.service.WaterholeServiceImpl;
import com.ihub.rangerapp.data.sqlite.Schemas;
import com.ihub.rangerapp.entity.SyncTaskModel;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SyncTaskActivity extends ActionBarActivity {
	
	SyncTaskAdapter adapter;
	ListView list;
	
	TextView titleView;
	
	String[] names;
	int[] values;
	Integer total;
	Integer processingCount = 0;
	Integer syncID = 0;
	Integer errorCount = 0;
	
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
        
        list = (ListView) findViewById(android.R.id.list);
        adapter = new SyncTaskAdapter(this, 0, new ArrayList<SyncTaskModel>());
        list.setAdapter(adapter);
        
        titleView = (TextView) findViewById(android.R.id.title);
        
        if(getIntent().hasExtra("names")) {
        	
        	String[] dnames = getIntent().getStringArrayExtra("names");
        	int[] dvalues = getIntent().getIntArrayExtra("values");
        	total = getIntent().getIntExtra("total", 0);
        	
        	int c = 0;
        	
        	for (int i : dvalues) {
        		
        		if(i > 0)
        			c++;
        	}
        	
        	names = new String[c];
        	values = new int[c];
        	
        	int counter = 0;
        	
        	for(int i = 0; i < dvalues.length; i++) {
        		
        		if(dvalues[i] > 0) {
        			values[counter] = dvalues[i];
        			names[counter] = dnames[i];
        			counter++;
        		}
        	}
        	
        	String title = getString(R.string.sync_record_progress);
        	title = title.replace("{from}", "1").replace("{to}", total +"");
        	
        	titleView.setText(title);
        	
        	startSync();
        }
	}
	
	private void startSync() {
		new StartSyncTask().execute();
	}
	
	private void syncGroup(int group) {
		String name = names[group];
		Integer count = values[group];
		
		String progress = "1 / " + count;
		adapter.add(new SyncTaskModel(name, progress, 0));
		
		//load ids of item
		
		String tableName = getTableName(name);
		new IdsTask().execute(tableName, group + "");
	}
	
	private void syncItem(int group, int item, List<Integer> ids) {
		
		if(ids.size() == 0) {
			
			if(group + 1 < names.length) {
				syncGroup(group + 1);
			} else {
				showSuccessDialog();
			}
			
		} else {
			
			if(item < ids.size()) {
				
				processingCount += 1;
				String progress =  (item + 1) + " / " + ids.size();
				//
				SyncTaskModel model = adapter.getItem(group);
				model.setProgress(progress);
				adapter.notifyDataSetChanged();
				
				String title = getString(R.string.sync_record_progress);
	        	title = title.replace("{from}", processingCount + "").replace("{to}", total +"");
	        	titleView.setText(title);
				
				//TODO
				
				String tableName = getTableName(names[group]);
				
				if(Schemas.SHIFTS_TABLE.equals(tableName)) {
					syncShifts(group, item, ids);
				} else if(Schemas.BUSH_MEAT_TABLE.equals(tableName)) {
					syncGameMeat(group, item, ids);
				} else if(Schemas.CHARCOAL_BAGS_TABLE.equals(tableName)) {
					syncCharcoalBags(group, item, ids);
				} else if(Schemas.CHARCOAL_KILN_TABLE.equals(tableName)) {
					syncCharcoalKilns(group, item, ids);
				} else if(Schemas.ELEPHANT_POACHING_TABLE.equals(tableName)) {
					syncElephantPoaching(group, item, ids);
				} else if(Schemas.SUSPICIOUS_ACTIVITIES_TABLE.equals(tableName)) {
					syncSuspiciousActivities(group, item, ids);
				} else if(Schemas.INDIVIDUAL_ANIMAL_SIGHTING_TABLE.equals(tableName)) {
					syncIndividualAnimalSighting(group, item, ids);
				} else if(Schemas.ANIMAL_HERD_SIGHTING_TABLE.equals(tableName)) {
					syncHerdSighting(group, item, ids);
				} else if(Schemas.WATER_HOLES_TABLE.equals(tableName)) {
					syncWaterhole(group, item, ids);
				}
				
			} else {
				
				if(group + 1 < names.length) {
					syncGroup(group + 1);
				} else {
					showSuccessDialog();
				}
				
			}
			
		}
	}
	
	private void syncWaterhole(int group, int item, List<Integer> ids) {
		int id = ids.get(item);
		WaterholeService service = new WaterholeServiceImpl();
		service.sync(id, jsonHandler(group, item, ids));
	}

	private void syncHerdSighting(int group, int item, List<Integer> ids) {
		int id = ids.get(item);
		AnimalSightingsService service = new AnimalSightingsServiceImpl();
		service.syncHerd(id, jsonHandler(group, item, ids));
	}

	private void syncIndividualAnimalSighting(int group, int item, List<Integer> ids) {
		int id = ids.get(item);
		AnimalSightingsService service = new AnimalSightingsServiceImpl();
		service.syncIndividual(id, jsonHandler(group, item, ids));
	}

	private void syncSuspiciousActivities(int group, int item, List<Integer> ids) {
		int id = ids.get(item);
		SuspiciousActivitiesService service = new SuspiciousActivitiesServiceImpl();
		service.sync(id, jsonHandler(group, item, ids));
	}

	private void syncElephantPoaching(final int group, final int item, final List<Integer> ids) {
		int id = ids.get(item);
		ElephantService service = new ElephantServiceImpl();
		service.sync(id, jsonHandler(group, item, ids));
	}

	private void syncCharcoalKilns(final int group, final int item, final List<Integer> ids) {
		int id = ids.get(item);
		CharcoalService service = new CharcoalServiceImpl();
		service.syncKilns(id, jsonHandler(group, item, ids));
	}

	private void syncCharcoalBags(final int group, final int item, final List<Integer> ids) {
		int id = ids.get(item);
		CharcoalService service = new CharcoalServiceImpl();
		service.syncBags(id, jsonHandler(group, item, ids));
	}

	private void syncGameMeat(final int group, final int item, final List<Integer> ids) {
		
		int id = ids.get(item);
		GameMeatService service = new GameMeatServiceImpl();
		service.sync(id, jsonHandler(group, item, ids));
	}

	private void syncShifts(final int group, final int item, final List<Integer> ids) {
		//
		int id = ids.get(item);
		ShiftService service = new ShiftServiceImpl();
		service.syncShift(id, jsonHandler(group, item, ids));
	}
	
	private AsyncHttpResponseHandler jsonHandler(final int group, final int item, final List<Integer> ids) {
		
		return new JsonHttpResponseHandler() {
			
        	@Override
        	public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        		super.onSuccess(statusCode, headers, response);
        		
        		Integer id = ids.get(item);
        		String tableName = getTableName(names[group]);
        		
        		new UpdateSyncTask().execute(tableName, id);
        		
        		//show sync progress
        		syncItem(group, item + 1, ids);
        		
        	}
        	
        	@Override
        	public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        		super.onFailure(statusCode, headers, throwable, errorResponse);
        		errorCount++;
        		syncItem(group, item + 1, ids);
        	}
        	
        	@Override
        	public void onFailure(int statusCode, Header[] headers,
        			Throwable throwable, JSONObject errorResponse) {
        		super.onFailure(statusCode, headers, throwable, errorResponse);
        		errorCount++;
        		syncItem(group, item + 1, ids);
        	}
		};
	}
	
	public String getTableName(String groupName) {
		
		if("Shifts".equals(groupName)) {
			return Schemas.SHIFTS_TABLE;
		} else if("Bush Meat".equals(groupName)) {
			return Schemas.BUSH_MEAT_TABLE;
		} else if("Charcoal Bags Incidents".equals(groupName)) {
			return Schemas.CHARCOAL_BAGS_TABLE;
		} else if("Charcoal Kilns Incidents".equals(groupName)) {
			return Schemas.CHARCOAL_KILN_TABLE;
		} else if("Elephant Poaching Incidents".equals(groupName)) {
			return Schemas.ELEPHANT_POACHING_TABLE;
		} else if("Suspicious Activities".equals(groupName)) {
			return Schemas.SUSPICIOUS_ACTIVITIES_TABLE;
		} else if("Individual Animals Sightings".equals(groupName)) {
			return Schemas.INDIVIDUAL_ANIMAL_SIGHTING_TABLE;
		} else if("Herd Sightings".equals(groupName)) {
			return Schemas.ANIMAL_HERD_SIGHTING_TABLE;
		} else if("Waterhole Sightings".equals(groupName)) {
			return Schemas.WATER_HOLES_TABLE;
		}
		return "";
	}
	
	class IdsTask extends AsyncTask<String, Void, List<Integer>> {
		
		Integer groupID;

		@Override
		protected List<Integer> doInBackground(String... params) {
			
			String tableName = params[0];
			groupID = Integer.valueOf(params[1]);
			SyncService service = new SyncServiceImpl();
			return service.loadIds(tableName);
		}
		
		@Override
		protected void onPostExecute(List<Integer> result) {
			super.onPostExecute(result);
			
			if(result != null)
				syncItem(groupID, 0, result);
		}
	}
	
	class StartSyncTask extends AsyncTask<Void, Void, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {
			
			SyncService service = new SyncServiceImpl();
			return service.startSync();
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			syncID = result;
			syncGroup(0);
		}
	}
	
	class EndSyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			SyncService service = new SyncServiceImpl();
			service.endSync(syncID);
			return null;
		}
		
	}
	
	class UpdateSyncTask extends AsyncTask<Object, Void, Void> {

		@Override
		protected Void doInBackground(Object... params) {
			
			String tableName = (String) params[0];
			Integer id = (Integer) params[1];
			SyncService service = new SyncServiceImpl();
			service.updateRecordLastSyncd(syncID, id, tableName);
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
		}
	}
	
	private void showSuccessDialog() {
		
		new EndSyncTask().execute();
		
		String message = "Done";
		
		if(errorCount > 0)
			message += "\n " + errorCount + " errors were encountered";
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
		       .setCancelable(false)
		       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               SyncTaskActivity.this.onBackPressed();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	@Override
	public void onBackPressed() {
		
		Intent intent = new Intent(SyncTaskActivity.this, SyncActivity.class);
		startActivity(intent);
		finish();
	}
}
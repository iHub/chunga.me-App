package com.ihub.rangerapp.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import com.ihub.rangerapp.RangerApp;
import com.ihub.rangerapp.data.sqlite.DB;
import com.ihub.rangerapp.listener.DataLoadAdapter;
import com.ihub.rangerapp.listener.DataLoadListener;
import com.ihub.rangerapp.model.Model;
import com.ihub.rangerapp.util.PagingLoadConfig;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public abstract class DataLoader implements DataLoadListener {
	
	DataLoadAdapter adapter;
	
	Boolean isLoading = false;
	
	Map<String, Object> paramsBucket = new HashMap<String, Object>();
	
	List<Model> dataBucket = new ArrayList<Model>();
	
	public DataLoader() {
	}
	
	public void load(final PagingLoadConfig config, final Activity activity) {
		
		if(!isLoading()) {
			isLoading = true;
			
			clear();
			
			new AsyncTask<Void, Void, Map<String, Object>>() {

				@Override
				protected Map<String, Object> doInBackground(Void... params) {
					
					//count local records
					//handle data loading logic here

					int count = countLocalRecords();
					int offset = config.offset();
					int limit = config.limit();
					
					Map<String, Object> results = new HashMap<String, Object>();

					List<Model> localData = loadFromLocalDb(config);
					results.put("data", localData);
					results.put("count", count);
					results.put("offset", offset);
					results.put("limit", limit);
					results.put("page", config.getPage());

					return results;
				}
				
				@Override
				protected void onPostExecute(final Map<String, Object> result) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							
							List<Model> data = null;

							if (result != null) {

								if (result.get("data") != null) {
									data = (List<Model>) result.get("data");
									if (data != null)
										dataBucket.addAll(data);
								}
							}
							
							if(result.containsKey("count"))
								paramsBucket.put("count", result.get("count"));
							else if(result.containsKey("recordCount"))
								paramsBucket.put("count", result.get("recordCount"));
							
							if(result.containsKey("limit"))
								paramsBucket.put("limit", result.get("limit"));
							
							if(result.containsKey("offset"))
								paramsBucket.put("offset", result.get("offset"));
							
							if(result.containsKey("page"))
								paramsBucket.put("page", result.get("page"));

							onLoadTaskCompleted();
						}
					});
				};
				
			}.execute();
			
		}
	}
	
	protected void onLoadTaskCompleted() {

		if (adapter != null) {
			adapter.onLoad(dataBucket, paramsBucket);
		}
		clear();
	}
	
	public List<Model> loadFromLocalDb(PagingLoadConfig config) {
		return new ArrayList<Model>();
	}

	public int countLocalRecords() {

		int count = 0;

		RangerApp application = RangerApp.get();

		SQLiteDatabase db = DB.instance().getWritableDatabase(
				application.getApplicationContext());

		String sql = getCountSQL();
		Cursor mcursor = db.rawQuery(sql, null);
		if (mcursor.moveToFirst()) {
			count = mcursor.getInt(0);
			mcursor.close();
		}

		return count;
	}
	
	public String getCountSQL() {
		return "SELECT count(*) FROM " + tableName() + ";";
	}
	
	private void clear() {
		dataBucket.clear();
		paramsBucket.clear();
		isLoading = false;
	}
	
	public abstract String tableName();
	
	public Boolean isLoading() {
		return isLoading;
	}
	
	public Map<String, Object> getParamsBucket() {
		return paramsBucket;
	}
	
	public void setParamsBucket(Map<String, Object> paramsBucket) {
		this.paramsBucket = paramsBucket;
	}
	
	@Override
	public void addLoadListener(DataLoadAdapter adapter) {
		this.adapter = adapter;
	}
	
	public DataLoadAdapter getAdapter() {
		return this.adapter;
	}	
}
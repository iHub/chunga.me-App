package com.ihub.rangerapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.ihub.rangerapp.adapter.HomeMenuAdapter;
import com.ihub.rangerapp.anim.CustomItemAnimator;
import com.ihub.rangerapp.data.service.ShiftService;
import com.ihub.rangerapp.data.service.ShiftServiceImpl;
import com.ihub.rangerapp.data.service.UserService;
import com.ihub.rangerapp.data.service.UserServiceImpl;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeActivity extends ActionBarActivity implements OnClickListener {
	
	private List<com.ihub.rangerapp.entity.MenuItem> itemsList = new ArrayList<com.ihub.rangerapp.entity.MenuItem>();
	
	private HomeMenuAdapter mAdapter;
    private RecyclerView mRecyclerView;
    
    private Button shiftBtn;
    private Button reportsBtn; 
    private View endShiftView;
    private AlertDialog endDialog;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        	mRecyclerView.setItemAnimator(new CustomItemAnimator());
		}
        
        mAdapter = new HomeMenuAdapter(new ArrayList<com.ihub.rangerapp.entity.MenuItem>(), R.layout.row_default, HomeActivity.this);
        mRecyclerView.setAdapter(mAdapter);
        
        shiftBtn = (Button) findViewById(R.id.shiftBtn);
        shiftBtn.setOnClickListener(this);
        
        reportsBtn = (Button) findViewById(R.id.reportsBtn);
        reportsBtn.setOnClickListener(this);
                
        new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				new InitializeApplicationsTask().execute();
			}
		}, 300);
        
        checkHasOpenShift();
        
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	private void checkHasOpenShift() {
		new CheckHasOpenShiftApplicationTask().execute();
	}

	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    
    private class CheckHasOpenShiftApplicationTask extends AsyncTask<Void, Void, Boolean> {
    	
		@Override
		protected Boolean doInBackground(Void... params) {
			ShiftService service = new ShiftServiceImpl();
			return service.hasOpenShift();
		}
		
		@Override
		protected void onPostExecute(Boolean hasOpenShift) {
			super.onPostExecute(hasOpenShift);
			
			if(hasOpenShift)
				shiftBtn.setText(getString(R.string.end_shift));
		}
    }
    
    class EndShiftTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			
			String waypoint = params[0];
			
			ShiftService service = new ShiftServiceImpl();
			service.endCurrentShift(waypoint);
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			shiftBtn.setText(getString(R.string.start_shift));
			
			Toast toast = Toast.makeText(getApplicationContext(), R.string.shift_successfully_ended, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.TOP, 0, 0);
			toast.show();
			
			if(endDialog != null)
				endDialog.dismiss();
		}
    	
    }
    
    private class InitializeApplicationsTask extends AsyncTask<Void, Void, Void> {
    	
        @Override
        protected void onPreExecute() {
            mAdapter.clearApplications();
            super.onPreExecute();
        }
        
        @Override
        protected Void doInBackground(Void... params) {
        	
            itemsList.clear();
            
            //Query the applications
            final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            
            String menuNames[] = new String[]{
	    		getString(R.string.incidents), 
	    		getString(R.string.sightings)
            };
            
            Integer drawables[] = new Integer[] {};
            
            for (String s : menuNames) {
            	itemsList.add(new com.ihub.rangerapp.entity.MenuItem(s, null));
            }
            
            Collections.sort(itemsList);
            
            return null;
        }
        
        @Override
        protected void onPostExecute(Void result) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mAdapter.addItems(itemsList);
            super.onPostExecute(result);
        }
    }
    
	@Override
	public void onClick(View v) {
		
		if(v == shiftBtn) {
			
			if(shiftBtn.getText().equals(getString(R.string.start_shift))) {
				Intent intent = new Intent(this, StartShiftActivity.class);
				startActivityForResult(intent, 100);
			} else {
				
				endCurrentShift();
			}
			
		} else if(v == reportsBtn) {
			Intent intent = new Intent(this, ReportsActivity.class);
			startActivity(intent);
		}
	}
	
	private void endCurrentShift() {
		
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		
		endShiftView = getLayoutInflater().inflate(R.layout.end_shift_view, null, false);
		
		final EditText waypointView = (EditText) endShiftView.findViewById(R.id.waypointView);
		
		alert.setView(endShiftView);
	    alert.setPositiveButton("Ok", null);

	    alert.setNegativeButton("Cancel", null);
	    
	    endDialog = alert.create();
	    
	    endDialog.setOnShowListener(new DialogInterface.OnShowListener() {

	        @Override
	        public void onShow(DialogInterface dialog) {

	            Button b = endDialog.getButton(AlertDialog.BUTTON_POSITIVE);
	            b.setOnClickListener(new View.OnClickListener() {

	                @Override
	                public void onClick(View view) {
	                    // TODO Do something
	                	
	                	Boolean isValid = true;
	                	
	                	String waypoint = waypointView.getText().toString().trim();
	                	
	                	if(TextUtils.isEmpty(waypoint)) {
	                		waypointView.setError(getString(R.string.validation_waypoint));
	                		
	                		waypointView.requestFocus();
	                		isValid = false;
	                	}
	                	
	                	if(isValid)
	                		new EndShiftTask().execute(waypoint);
	                }
	            });
	        }
	    });
	    
	    endDialog.show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 100) {
			
			if (resultCode == RESULT_OK) {
				shiftBtn.setText(getString(R.string.end_shift));
	        }
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.action_logout) {
			logout();
		} else if(id == R.id.action_export) {
			showSyncView();
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void showSyncView() {
		Intent intent = new Intent(HomeActivity.this, SyncActivity.class);
		startActivity(intent);
	}
	
	private void logout() {
		new LogoutTask().execute("-");
	}
	
	class LogoutTask extends AsyncTask<String, Void, Void> {
		
		@Override
		protected Void doInBackground(String... params) {
			String waypoint = params[0];
			
			ShiftService shiftService = new ShiftServiceImpl();
			shiftService.endCurrentShift(waypoint);
			
			UserService service = new UserServiceImpl();
			service.logout();
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			finish();
			SharedPreferences prefs = HomeActivity.this.getSharedPreferences(RangerApp.class.getName(), Context.MODE_PRIVATE);
			prefs.edit().clear().commit();
			Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
			startActivity(intent);
		}
	}
}
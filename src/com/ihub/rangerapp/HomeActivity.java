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
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
    
    LocationManager locationManager;
	LocationListener locationListener;
	
	Location lastLocation;
    
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
        
        initLocationManager();
	}
	
	private void initLocationManager() {
		
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		
		if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
	        buildAlertMessageNoGps();
	    }

		locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				lastLocation = location;
				
				if(endDialog != null && endDialog.isShowing()) {
					if(endShiftView != null) {
						EditText endLat = (EditText) endDialog.findViewById(R.id.latitudeView);
						EditText endLon = (EditText) endDialog.findViewById(R.id.longitudeView);
						
						endLat.setText(String.valueOf(location.getLatitude()));
						endLon.setText(String.valueOf(location.getLongitude()));
					}
				}
		    }

		    public void onStatusChanged(String provider, int status, Bundle extras) {}

		    public void onProviderEnabled(String provider) {}

		    public void onProviderDisabled(String provider) {}
		};

		  locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
	}
	
	private void buildAlertMessageNoGps() {
	    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
	           .setCancelable(false)
	           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	               public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
	                   startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	               }
	           })
	           .setNegativeButton("No", new DialogInterface.OnClickListener() {
	               public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
	                    dialog.cancel();
	               }
	           });
	    final AlertDialog alert = builder.create();
	    alert.show();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(locationListener);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if(locationManager == null)
			initLocationManager();
		
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
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
			
			String lat = params[0];
			String lon = params[1];
			
			ShiftService service = new ShiftServiceImpl();
			service.endCurrentShift(lat, lon);
			
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
		
		final EditText endLatView = (EditText) endShiftView.findViewById(R.id.latitudeView);
		final EditText endLonView = (EditText) endShiftView.findViewById(R.id.longitudeView);
		
		if(lastLocation != null) {
			endLatView.setText(String.valueOf(lastLocation.getLatitude()));
			endLonView.setText(String.valueOf(lastLocation.getLongitude()));
		}
		
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
	                	
	                	String endLat = endLatView.getText().toString().trim();
	                	String endLon = endLonView.getText().toString().trim();
	                	
	                	if(TextUtils.isEmpty(endLat)) {
	                		endLatView.setError(getString(R.string.validation_lat));
	                		
	                		endLatView.requestFocus();
	                		isValid = false;
	                	}
	                	
	                	if(isValid) {
	                		if(TextUtils.isEmpty(endLon)) {
	                			endLonView.setError(getString(R.string.validation_long));
		                		isValid = false;
		                		endLonView.requestFocus();
		                	}
	                	}
	                	
	                	if(isValid)
	                		new EndShiftTask().execute(endLat, endLon);

	                    //Dismiss once everything is OK.
	                    //d.dismiss();
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
			showExportView();
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void showExportView() {
		Toast.makeText(this, "Export", Toast.LENGTH_LONG).show();
	}
	
	private void logout() {
		
		String endLat = "-";
		String endLon = "-";
		if(lastLocation != null) {
			endLat = lastLocation.getLatitude() + "";
			endLon = lastLocation.getLongitude() + "";
		}        	
	                	
		new LogoutTask().execute(endLat, endLon);
	}
	
	class LogoutTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			String lat = params[0];
			String lon = params[1];
			
			ShiftService shiftService = new ShiftServiceImpl();
			shiftService.endCurrentShift(lat, lon);
			
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
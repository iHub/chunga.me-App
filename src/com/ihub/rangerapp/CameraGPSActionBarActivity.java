package com.ihub.rangerapp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ihub.rangerapp.location.Coordinate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class CameraGPSActionBarActivity extends ActionBarActivity {

	ImageButton gpsBtn;
	ImageButton cameraBtn;
	
	TextView gpsText;
	ImageView cameraImageView;
	
	LocationManager locationManager;
	LocationListener locationListener;
	
	Location lastLocation;
	
	String fileName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initLocationManager();
	}
	
	private void initLocationManager() {
		
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
		      //makeUseOfNewLocation(location);
				lastLocation = location;
				
				if(gpsText != null) {
					gpsText.setText(locationToString(location));
				}
		    }

		    public void onStatusChanged(String provider, int status, Bundle extras) {}

		    public void onProviderEnabled(String provider) {}

		    public void onProviderDisabled(String provider) {}
		};

		  locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(locationListener);
	}
	//http://stackoverflow.com/questions/17519198/how-to-get-the-current-location-latitude-and-longitude-in-android
	//TODO
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if(locationManager == null)
			initLocationManager();
		
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
	}

	protected void initViews() {
		gpsBtn = (ImageButton) findViewById(R.id.gpsBtn);
		cameraBtn = (ImageButton) findViewById(R.id.cameraBtn);
		
		gpsText = (TextView) findViewById(R.id.gpsText);
		cameraImageView = (ImageView) findViewById(R.id.cameraImageView);
		
		cameraBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Uri uri = getOutputMediaFileUri();
        		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        		startActivityForResult(intent, 300);
			}
		});
		
		gpsBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String l = "";
				if(lastLocation != null)
					l = lastLocation.getLatitude() + "     " + lastLocation.getLatitude();
				
				Location lc  = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			
				if(gpsText != null) {
					
					if(lc != null)
						gpsText.setText(locationToString(lc));
				}
				
			}
		});
	}
	
	public String locationToString(Location location) {
		Coordinate c = new Coordinate(location.getLatitude(), location.getLongitude());
		return c.toString();
	}
	
	private Uri getOutputMediaFileUri(){
	      return Uri.fromFile(getOutputMediaFile());
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == Activity.RESULT_OK) {
			
			if(requestCode == 300) {
				
				if(cameraImageView != null) {
			    	Bitmap myBitmap = BitmapFactory.decodeFile(fileName);

			    	cameraImageView.setImageBitmap(myBitmap);
			    	//cameraImageView.setImageResource(R.drawable.hacksaw);
			    	cameraImageView.setVisibility(View.VISIBLE);
			    	cameraImageView.invalidate();
			    	
			    }
			}
		}
		
		
		
	}
	
	private File getOutputMediaFile() {
		
	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "Kendy");
	    
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("MyCameraApp", "failed to create directory");
	            return null;
	        }
	    }
	    
	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
		        "IMG_"+ timeStamp + ".jpg");
		        fileName = mediaFile.getAbsolutePath();
	    
	    return mediaFile;
	}
}

package com.ihub.rangerapp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import com.ihub.rangerapp.location.Coordinate;
import eu.inmite.android.lib.validations.form.annotations.NotEmpty;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CameraGPSActionBarActivity extends ActionBarActivity {
	
	ImageButton gpsBtn;
	ImageButton cameraBtn;
	
	ProgressBar progressBar;
	
	@NotEmpty(messageId = R.string.validation_lat, order = 1)
	EditText latView;
	
	@NotEmpty(messageId = R.string.validation_long, order = 2)
	EditText longView;
	
	ImageView imageView;
	
	LocationManager locationManager;
	LocationListener locationListener;
	
	Location lastLocation;
	
	String cameraNewImageUrl = "";
	String imagePath;
	
	Integer mode = 1; //1 - Create, 2 - Edit, 3 - View
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent data = getIntent();
        mode = data.getIntExtra("mode", 1);
        
		initLocationManager();
	}
	
	
	
	private void initLocationManager() {
		
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		
		if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
	        buildAlertMessageNoGps();
	    }
	    startLocationListener();
	}
	
	private void startLocationListener() {
		locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {

				lastLocation = location;
				
				if(mode == 1) {
					
					if(latView != null) {
						latView.setText(String.valueOf(location.getLatitude()));
						longView.setText(String.valueOf(location.getLongitude()));
					}
					
					if(progressBar != null)
						progressBar.setIndeterminate(false);
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
	                   startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 323);
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
		if(locationListener != null)
			locationManager.removeUpdates(locationListener);
	}
	//http://stackoverflow.com/questions/17519198/how-to-get-the-current-location-latitude-and-longitude-in-android
	//TODO
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if(locationManager == null)
			initLocationManager();
		
		if(locationListener != null)
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
	}

	protected void initViews() {
		gpsBtn = (ImageButton) findViewById(R.id.gpsBtn);
		cameraBtn = (ImageButton) findViewById(R.id.cameraBtn);
		
		latView = (EditText) findViewById(R.id.latView);
		longView = (EditText) findViewById(R.id.longView);
		
		imageView = (ImageView) findViewById(R.id.imageView);
		
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		progressBar.setIndeterminate(mode == 1);
		
		cameraBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showUploadPopup(v);
			}
		});
		
		imageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				zoom();
			}
		});
		
		if(mode != 1) {
			cameraBtn.setEnabled(false);
			
			if(getIntent().hasExtra("imagePath")) {
				try {
					imagePath = getIntent().getStringExtra("imagePath");
					showImage(imagePath);
				} catch (Exception e) {}
			}
			
			if(getIntent().hasExtra("wp") && !TextUtils.isEmpty(getIntent().getStringExtra("wp"))) {
			}
		}
		
		if(mode != 1) {
			
			gpsBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String l = "";
					if(lastLocation != null)
						l = lastLocation.getLatitude() + "     " + lastLocation.getLatitude();
					
					Location lc  = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					
					if(lc != null) {
						latView.setText(String.valueOf(lc.getLatitude()));
						longView.setText(String.valueOf(lc.getLongitude()));
					}
				}
			});
		}
		
		
	}
	
	protected void zoom() {

		if(!TextUtils.isEmpty(CameraGPSActionBarActivity.this.imagePath)) {
			
			Intent intent = new Intent(CameraGPSActionBarActivity.this, PhotoActivity.class);
			intent.putExtra("path", imagePath);
			startActivity(intent);
		}
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
				
				this.imagePath = cameraNewImageUrl;
				showImage(imagePath);
				
			} else if (requestCode == 200) {
				
				Uri selectedimg = data.getData();
								
				String[] filePathColumn = { MediaStore.Images.Media.DATA };

	            Cursor cursor = getContentResolver().query(
	            		selectedimg, filePathColumn, null, null, null);
	            cursor.moveToFirst();

	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            String filePath = cursor.getString(columnIndex);
	            cursor.close();
	            
	            this.imagePath = filePath;
	            
	            showImage(imagePath);
			} else if(requestCode == 323) {
		            String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		            if(provider != null){
		            }else{}
			}
		}
	}
	
	protected void showImage(final String path) {
		
		Bitmap myBitmap = null;
		
		try {
			myBitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(path), 48, 48);
		} catch (Exception e) {}
		
		if(myBitmap != null)
			imageView.setImageBitmap(myBitmap);
	}
	
	private File getOutputMediaFile() {
		
	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "RangerApp");
	    
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
	    cameraNewImageUrl = mediaFile.getAbsolutePath();
	    
	    return mediaFile;
	}
	
	protected void showUploadPopup(View v) {
    	PopupMenu popup = new PopupMenu(this, v);
    	popup.getMenuInflater().inflate(R.menu.upload_popup, popup.getMenu());
    	
    	popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            
    		@Override
            public boolean onMenuItemClick(MenuItem item) {
            	
            	if(item.getItemId() == R.id.openCamera) {
            		
            		Uri uri = getOutputMediaFileUri();
            		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            		startActivityForResult(intent, 300);
            		
            	} else if(item.getItemId() == R.id.fromGallery) {
            		
            		Intent intent = new Intent();
            	    intent.setType("image/*");
            	    intent.setAction(Intent.ACTION_GET_CONTENT);

            	    startActivityForResult(Intent.createChooser(intent,
            	            getString(R.string.open_gallery)), 200);
            	    
            	}
            	
            	return true;
            }
        });
        popup.show();
	}
	
	protected void showSaveResult(Map<String, Object> result) {
		
		String status = result.get("status").toString();
		
		if("success".equals(status)) {
			
			Toast toast = Toast.makeText(this, mode == 1 ? R.string.record_save_success_msg : R.string.record_update_success_msg, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.TOP, 0, 0);
			toast.show();
			
			finish();
			
		} else {
			
			Toast toast = Toast.makeText(this, result.get("message").toString(), Toast.LENGTH_LONG);
			toast.setGravity(Gravity.TOP, 0, 0);
			toast.show();
			
		}
	}
	
	protected Boolean isValid() {
					
		Boolean isValid = true;
		
		if(latView.getText().toString().length() == 0) {
			
			latView.setError(getString(R.string.validation_lat));
			latView.requestFocus();
			isValid = false;
		}
		
		if(isValid) {
			if(longView.getText().toString().length() == 0) {
				
				longView.setError(getString(R.string.validation_long));
				longView.requestFocus();
				isValid = false;
			}
		}
						
		if(isValid) {
			
			if(TextUtils.isEmpty(imagePath)) {
				isValid = false;
				Toast toast = Toast.makeText(this, getString(R.string.validation_photo), Toast.LENGTH_LONG);
				toast.setGravity(Gravity.TOP, 0, 0);
				toast.show();
			}
		}
		
		return isValid;
	}
	
	public String[] getLabels() {
		return new String[]{};
	}

	public String getInvalidFields(View...views) {
		
		String str = "";
		
		for(int i = 0; i < views.length; i++) {
			if(views[i] instanceof TextView) {
				if(((EditText)views[i]).getText().toString().trim().equals("")) {
					str += getLabels()[i] + ".\n";
				}
			} else if(views[i] instanceof Spinner) {
				if(((Spinner)views[i]).getSelectedItemPosition() == 0) {
					str += getLabels()[i] + ".\n";
				}
			}
		}
		
		
		return str;
	}
	
	protected Boolean hasInvalidFields(View...views) {
		
		Boolean hasInvalidFields = false;
		
		for(int i = 0; i < views.length; i++) {
			if(views[i] instanceof TextView) {
				if(((EditText)views[i]).getText().toString().trim().equals("")) {
					hasInvalidFields = true;
					break;
				}
			} else if(views[i] instanceof Spinner) {
				if(((Spinner)views[i]).getSelectedItemPosition() == 0) {
					hasInvalidFields = true;
					break;
				}
			}
		}
		
		return hasInvalidFields;
	}
	
	@Override
	public void onBackPressed() {
		
		new AlertDialog.Builder(this)
			.setMessage(getString(R.string.exit_confirmation))
			.setCancelable(false)
			.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int id) {
					finish();
				}
			})
			.setNegativeButton(R.string.no, null)
			.show();
	}
}
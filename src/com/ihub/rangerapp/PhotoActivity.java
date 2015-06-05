package com.ihub.rangerapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.widget.ImageView;

public class PhotoActivity extends ActionBarActivity {
	
	ImageView imageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		
		imageView = (ImageView) findViewById(R.id.imageView);
		
		try {
	        File f = new File(getIntent().getStringExtra("path"));
	        ExifInterface exif = new ExifInterface(f.getPath());
	        int orientation = exif.getAttributeInt(
	                ExifInterface.TAG_ORIENTATION,
	                ExifInterface.ORIENTATION_NORMAL);

	        int angle = 0;

	        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
	            angle = 90;
	        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
	            angle = 180;
	        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
	            angle = 270;
	        }

	        Bitmap bitmap = null;
	        Matrix mat = new Matrix();
	        mat.postRotate(angle);
	        BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inSampleSize = 2;

	        Bitmap bmp = BitmapFactory.decodeStream(new FileInputStream(f),
	                null, options);
	        bitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
	                bmp.getHeight(), mat, true);
	        imageView.setImageBitmap(bitmap);
	        
	    } catch (IOException e) {
	        Log.w("TAG", "-- Error in setting image");
	    } catch (OutOfMemoryError oom) {
	        Log.w("TAG", "-- OOM Error in setting image");
	    }
	}
}
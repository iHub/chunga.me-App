package com.ihub.rangerapp;

import android.support.v7.app.ActionBarActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.widget.ImageView;

public class PhotoActivity extends ActionBarActivity {

	ImageView imageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		
		imageView = (ImageView) findViewById(R.id.imageView);
		
		if(getIntent().hasExtra("path")) {
			
			showImage(getIntent().getStringExtra("path"));
		}
	}
	
	protected void showImage(final String path) {
		
		Bitmap myBitmap = null;
		
		try {
			myBitmap = BitmapFactory.decodeFile(path);
		} catch (Exception e) {}
		
		if(myBitmap != null)
			imageView.setImageBitmap(myBitmap);
	}
}

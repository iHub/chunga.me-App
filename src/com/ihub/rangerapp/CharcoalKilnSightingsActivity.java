package com.ihub.rangerapp;

import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;

public class CharcoalKilnSightingsActivity extends CameraGPSActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_charcoal_kiln_sightings);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
		getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharcoalKilnSightingsActivity.this.onBackPressed();
            }
        });
        
        initViews();
	}
}

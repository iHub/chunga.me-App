package com.ihub.rangerapp;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.os.Bundle;

public class SuspiciousAspectsActivity extends CameraGPSActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suspicious_aspects);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
		getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuspiciousAspectsActivity.this.onBackPressed();
            }
        });
        
        initViews();
	}
}

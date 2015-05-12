package com.ihub.rangerapp;

import com.ihub.rangerapp.view.DateFragment;
import com.ihub.rangerapp.view.ReportsView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;

public class ReportsActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reports);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportsActivity.this.onBackPressed();
            }
        });
        
        if (savedInstanceState != null) {
            return;
        }
        
      showReportsView();
      
	}
	
	public void showReportsView() {
		FragmentManager fragmentManager = getSupportFragmentManager();
        
        ReportsView fragment = new ReportsView();
        fragmentManager.beginTransaction()
        	.replace(android.R.id.content, fragment)
        	.commit();
	}
}
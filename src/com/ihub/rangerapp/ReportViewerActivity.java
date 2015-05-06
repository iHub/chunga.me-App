package com.ihub.rangerapp;

import com.ihub.rangerapp.view.reports.GameMeatReport;
import com.ihub.rangerapp.view.reports.ReportFragment;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.app.Fragment;
import android.os.Bundle;

public class ReportViewerActivity extends ActionBarActivity {
	
	ReportFragment fragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_viewer);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportViewerActivity.this.onBackPressed();
            }
        });
        
        if (savedInstanceState == null) {
			fragment = getFragment();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, fragment).commit();
		}
	}
	
	protected ReportFragment getFragment() {
		
		String viewClass = getIntent().getStringExtra("viewClass");
		
		if(GameMeatReport.class.getSimpleName().equals(viewClass))
			return new GameMeatReport();
		
		return new GameMeatReport();
	}
}
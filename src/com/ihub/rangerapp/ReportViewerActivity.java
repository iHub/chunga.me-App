package com.ihub.rangerapp;

import java.util.ArrayList;
import java.util.List;

import com.ihub.rangerapp.entity.SummaryItem;
import com.ihub.rangerapp.view.reports.CharcoalBurningBagsReport;
import com.ihub.rangerapp.view.reports.CharcoalBurningKilnsReport;
import com.ihub.rangerapp.view.reports.ElephantPoachingReport;
import com.ihub.rangerapp.view.reports.GameMeatReport;
import com.ihub.rangerapp.view.reports.HerdsReport;
import com.ihub.rangerapp.view.reports.IndividualAnimalsReport;
import com.ihub.rangerapp.view.reports.ReportFragment;
import com.ihub.rangerapp.view.reports.SuspiciousActivitiesReport;
import com.ihub.rangerapp.view.reports.WaterholesReport;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.os.Bundle;

public class ReportViewerActivity extends ActionBarActivity {
	
	ReportFragment fragment;
	
	public List<SummaryItem> reviewItems = new ArrayList<SummaryItem>();
	
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
        
        if(getIntent().hasExtra("title"))
        	getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
	}
	
	protected ReportFragment getFragment() {
		
		String viewClass = getIntent().getStringExtra("viewClass");
		
		if(GameMeatReport.class.getSimpleName().equals(viewClass))
			return new GameMeatReport();
		else if(CharcoalBurningBagsReport.class.getSimpleName().equals(viewClass))
			return new CharcoalBurningBagsReport();
		else if(CharcoalBurningKilnsReport.class.getSimpleName().equals(viewClass))
			return new CharcoalBurningKilnsReport();
		else if(ElephantPoachingReport.class.getSimpleName().equals(viewClass))
			return new ElephantPoachingReport();
		else if(GameMeatReport.class.getSimpleName().equals(viewClass))
			return new GameMeatReport();
		else if(HerdsReport.class.getSimpleName().equals(viewClass))
			return new HerdsReport();
		else if(IndividualAnimalsReport.class.getSimpleName().equals(viewClass))
			return new IndividualAnimalsReport();
		else if(SuspiciousActivitiesReport.class.getSimpleName().equals(viewClass))
			return new SuspiciousActivitiesReport();
		else if(WaterholesReport.class.getSimpleName().equals(viewClass))
			return new WaterholesReport();
		
		return new GameMeatReport();
	}

	public List<SummaryItem> getReviewItems() {
		return reviewItems;
	}

	public void setReviewItems(List<SummaryItem> reviewItems) {
		this.reviewItems = reviewItems;
	}
	
	public void addReviewItem(SummaryItem item) {
		this.reviewItems.add(item);
	}
	
	public void clearReviewItems() {
		reviewItems.clear();
	}
}
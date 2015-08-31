package com.ihub.rangerapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.ihub.rangerapp.adapter.ReportsAdapter;
import com.ihub.rangerapp.anim.CustomItemAnimator;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class ReportsActivity extends ActionBarActivity {
	
	private ReportsAdapter adapter;
	private RecyclerView mRecyclerView;
	
	private List<com.ihub.rangerapp.entity.MenuItem> itemsList = new ArrayList<com.ihub.rangerapp.entity.MenuItem>();
	
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
        
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        	mRecyclerView.setItemAnimator(new CustomItemAnimator());
		}	
        
		adapter = new ReportsAdapter(new ArrayList<com.ihub.rangerapp.entity.MenuItem>(), R.layout.row_reports, this);
        mRecyclerView.setAdapter(adapter);
        
        new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				new InitializeApplicationsTask().execute();
			}
		}, 300);
      
	}
	
	private class InitializeApplicationsTask extends AsyncTask<Void, Void, Void> {
		
        @Override
        protected void onPreExecute() {
            adapter.clearApplications();
            super.onPreExecute();
        }
        
        @Override
        protected Void doInBackground(Void... params) {
        	
            itemsList.clear();
            
            //Query the applications
            final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            
            String menuNames[] = new String[]{
	    		getString(R.string.bush_meat), 
	    		getString(R.string.charcoal_burning),
	    		getString(R.string.elephant_poaching),
	    		getString(R.string.suspicious_activities),
	    		getString(R.string.animal_sighting),
	    		getString(R.string.waterhole_sighting)
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
            adapter.addItems(itemsList);
            super.onPostExecute(result);
        }
    }
}
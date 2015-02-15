package com.ihub.rangerapp;

import java.util.ArrayList;
import java.util.List;
import com.ihub.rangerapp.adapter.PoachingMenuAdapter;
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

public class PoachingIncidencesActivity extends ActionBarActivity {

	private List<com.ihub.rangerapp.entity.MenuItem> itemsList = new ArrayList<com.ihub.rangerapp.entity.MenuItem>();
	
	private PoachingMenuAdapter mAdapter;
    private RecyclerView mRecyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poaching_incidences);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PoachingIncidencesActivity.this.onBackPressed();
            }
        });
        
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        	mRecyclerView.setItemAnimator(new CustomItemAnimator());
		}
        
        mAdapter = new PoachingMenuAdapter(new ArrayList<com.ihub.rangerapp.entity.MenuItem>(), R.layout.row_application, PoachingIncidencesActivity.this);
        mRecyclerView.setAdapter(mAdapter);
        
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
            mAdapter.clearApplications();
            super.onPreExecute();
        }
        
        @Override
        protected Void doInBackground(Void... params) {
        	
            itemsList.clear();
            
            //Query the applications
            final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            
            String menuNames[] = new String[]{
	    		"Ivory",
	    		"Game Meat",
	    		"Charcoal",
	    		"Suspicious Aspects",
            };
            
            for (String s : menuNames) {
            	itemsList.add(new com.ihub.rangerapp.entity.MenuItem(s, null));
            }
            
            return null;
        }
        
        @Override
        protected void onPostExecute(Void result) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mAdapter.addItems(itemsList);
            super.onPostExecute(result);
        }
    }
}
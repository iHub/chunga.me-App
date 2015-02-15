package com.ihub.rangerapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.ihub.rangerapp.adapter.CharcoalMenuAdapter;
import com.ihub.rangerapp.adapter.GameMeatMenuAdapter;
import com.ihub.rangerapp.anim.CustomItemAnimator;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

public class CharcoalActivity extends ActionBarActivity {

private List<com.ihub.rangerapp.entity.MenuItem> itemsList = new ArrayList<com.ihub.rangerapp.entity.MenuItem>();
	
	private CharcoalMenuAdapter mAdapter;
    private RecyclerView mRecyclerView;
    //private ProgressBar mProgressBar;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharcoalActivity.this.onBackPressed();
            }
        });
        
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        	mRecyclerView.setItemAnimator(new CustomItemAnimator());
		}
        
        mAdapter = new CharcoalMenuAdapter(new ArrayList<com.ihub.rangerapp.entity.MenuItem>(), R.layout.row_application, CharcoalActivity.this);
        mRecyclerView.setAdapter(mAdapter);
                
        new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				new InitializeApplicationsTask().execute();
			}
		}, 300);
	}
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
            
            final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            
            String menuNames[] = new String[]{
	    		"Kiln Sightings", 
	    		"Arrests",
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
            mAdapter.addItems(itemsList);
            super.onPostExecute(result);
        }
    }
}
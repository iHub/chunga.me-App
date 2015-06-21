package com.ihub.rangerapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.ihub.rangerapp.adapter.SightingsMenuAdapter;
import com.ihub.rangerapp.anim.CustomItemAnimator;
import com.ihub.rangerapp.data.service.ShiftService;
import com.ihub.rangerapp.data.service.ShiftServiceImpl;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;

public class SightingsActivity extends ActionBarActivity {
	
	private List<com.ihub.rangerapp.entity.MenuItem> itemsList = new ArrayList<com.ihub.rangerapp.entity.MenuItem>();
	
	private SightingsMenuAdapter mAdapter;
    private RecyclerView mRecyclerView;
    
    Intent openIntent;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_sightings);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SightingsActivity.this.onBackPressed();
            }
        });
        
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        	mRecyclerView.setItemAnimator(new CustomItemAnimator());
		}
        
        mAdapter = new SightingsMenuAdapter(new ArrayList<com.ihub.rangerapp.entity.MenuItem>(), R.layout.row_sightings, SightingsActivity.this);
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
            
            //Query the applications
            final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            
            String menuNames[] = new String[]{
	    		"Animals", 
	    		"Waterholes",
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
    
    public void open(Intent intent) {
		this.openIntent = intent;
		new CheckHasOpenShiftApplicationTask().execute(intent);
	}
	
	private class CheckHasOpenShiftApplicationTask extends AsyncTask<Intent, Void, Pair<Boolean, Intent>> {
    	
		@Override
		protected Pair<Boolean, Intent> doInBackground(Intent... intents) {
			ShiftService service = new ShiftServiceImpl();
			
			return new Pair<Boolean, Intent>(service.hasOpenShift(), intents[0]);
		}
		
		@Override
		protected void onPostExecute(Pair<Boolean, Intent> result) {
			super.onPostExecute(result);
			
			if(result.first) {
				startActivity(result.second);
			} else {
				
				new AlertDialog.Builder(SightingsActivity.this)
		          .setMessage("Please start a shift to continue.")
		          .setCancelable(false)
		          .setNegativeButton("Cancel", null)
		          .setPositiveButton("Start Shift", new OnClickListener() {
		              @Override
		              public void onClick(DialogInterface dialog, int which) {
		            	  Intent intent = new Intent(SightingsActivity.this, StartShiftActivity.class);
		            	  startActivityForResult(intent, 10);
		              }
		              
		          }).create().show();
				
			}
		}
    }
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == Activity.RESULT_OK) {
			
			if(requestCode == 10) {
				
				if(openIntent != null)
					startActivity(openIntent);
			}
		}
	}
}
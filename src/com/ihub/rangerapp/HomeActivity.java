package com.ihub.rangerapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.ihub.rangerapp.adapter.HomeMenuAdapter;
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
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeActivity extends ActionBarActivity implements OnClickListener {
	
	private List<com.ihub.rangerapp.entity.MenuItem> itemsList = new ArrayList<com.ihub.rangerapp.entity.MenuItem>();
	
	private HomeMenuAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private Button shiftBtn;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        	mRecyclerView.setItemAnimator(new CustomItemAnimator());
		}
        
        mAdapter = new HomeMenuAdapter(new ArrayList<com.ihub.rangerapp.entity.MenuItem>(), R.layout.row_application, HomeActivity.this);
        mRecyclerView.setAdapter(mAdapter);
        
        shiftBtn = (Button) findViewById(R.id.shiftBtn);
        shiftBtn.setOnClickListener(this);
                
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
	
    View.OnClickListener fabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //uploadComponentInfoTask = UploadHelper.getInstance(MainActivity.this, applicationList).uploadAll();
        }
    };
    
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
	    		getString(R.string.incidences), 
	    		getString(R.string.sightings), 
	    		getString(R.string.reports)
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

	@Override
	public void onClick(View v) {
		if(v == shiftBtn) {
			Intent intent = new Intent(this, StartShiftActivity.class);
			startActivity(intent);
		}
	}
}
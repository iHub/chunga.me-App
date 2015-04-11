package com.ihub.rangerapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.ihub.rangerapp.adapter.HomeMenuAdapter;
import com.ihub.rangerapp.anim.CustomItemAnimator;
import com.ihub.rangerapp.data.service.ShiftService;
import com.ihub.rangerapp.data.service.ShiftServiceImpl;
import com.ihub.rangerapp.data.sqlite.DBPreferences;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends ActionBarActivity implements OnClickListener {
	
	private List<com.ihub.rangerapp.entity.MenuItem> itemsList = new ArrayList<com.ihub.rangerapp.entity.MenuItem>();
	
	private HomeMenuAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private Button shiftBtn;
    private Button reportsBtn;
    
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
        
        reportsBtn = (Button) findViewById(R.id.reportsBtn);
        reportsBtn.setOnClickListener(this);
                
        new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				new InitializeApplicationsTask().execute();
			}
		}, 300);
        
        checkHasOpenShift();
	}
	
	private void checkHasOpenShift() {
		new CheckHasOpenShiftApplicationTask().execute();
	}

	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    
    private class CheckHasOpenShiftApplicationTask extends AsyncTask<Void, Void, Void> {
    	
		@Override
		protected Void doInBackground(Void... params) {
			ShiftService service = new ShiftServiceImpl();
			
			if(service.hasOpenShift())
				shiftBtn.setText(getString(R.string.end_shift));
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
		}
    }
    
    class EndShiftTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			
			ShiftService service = new ShiftServiceImpl();
			service.endCurrentShift();
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			shiftBtn.setText(getString(R.string.start_shift));
			
			Toast toast = Toast.makeText(getApplicationContext(), R.string.shift_successfully_ended, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.TOP, 0, 0);
			toast.show();
		}
    	
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
	    		getString(R.string.incidents), 
	    		getString(R.string.sightings)
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
			
			if(shiftBtn.getText().equals(getString(R.string.start_shift))) {
				Intent intent = new Intent(this, StartShiftActivity.class);
				startActivityForResult(intent, 100);
			} else {
				endCurrentShift();
			}
			
		} else if(v == reportsBtn)
			Toast.makeText(this, "//TODO implement reports feature", Toast.LENGTH_LONG).show();
	}
	
	private void endCurrentShift() {
		
		new AlertDialog.Builder(this)
		.setMessage(getString(R.string.end_shift_confirmation))
		.setCancelable(false)
		.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int id) {
				
				new EndShiftTask().execute();
			}
		})
		.setNegativeButton(R.string.no, null)
		.show();
		
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 100) {

			if (resultCode == RESULT_OK) {
				shiftBtn.setText(getString(R.string.end_shift));
	        }
	    }
	}
}
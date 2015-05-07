package com.ihub.rangerapp.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.ihub.rangerapp.R;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ihub.rangerapp.adapter.ReportsAdapter;

public class ReportsView extends Fragment {
	
	private ReportsAdapter adapter;
	private RecyclerView mRecyclerView;
	
	private List<com.ihub.rangerapp.entity.MenuItem> itemsList = new ArrayList<com.ihub.rangerapp.entity.MenuItem>();
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.reports_view, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		
		adapter = new ReportsAdapter(new ArrayList<com.ihub.rangerapp.entity.MenuItem>(), R.layout.row_application, getActivity());
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
	    		getString(R.string.game_meat), 
	    		getString(R.string.charcoal_burning_bags),
	    		getString(R.string.charcoal_burning_kilns),
	    		getString(R.string.elephant_poaching),
	    		getString(R.string.suspicious_activities),
	    		getString(R.string.animals_individual),
	    		getString(R.string.animals_herds),
	    		getString(R.string.waterholes)
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
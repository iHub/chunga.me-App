package com.ihub.rangerapp.view.reports;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ihub.rangerapp.ReportViewerActivity;
import com.ihub.rangerapp.SuspiciousActivitiesActivity;
import com.ihub.rangerapp.adapter.AmazingAdapter;
import com.ihub.rangerapp.adapter.SuspiciousActivitiesReportAdapter;
import com.ihub.rangerapp.entity.SummaryItem;
import com.ihub.rangerapp.loader.SuspiciousActivitiesLoader;
import com.ihub.rangerapp.model.GameMeatModel;
import com.ihub.rangerapp.model.Model;
import com.ihub.rangerapp.model.SuspiciousActivityModel;
import com.ihub.rangerapp.util.DateUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

public class SuspiciousActivitiesReport extends ReportFragment {

AmazingAdapter adapter;
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		
		SuspiciousActivityModel model = (SuspiciousActivityModel) adapter.getItem(position);
		
		Date date = null;
		
		Boolean canEdit = false;
		
		try {
			date = DateUtil.parse(model.getDateCreated());
		} catch (Exception e) {}
				
		if(date != null) {
			date  = DateUtil.addDays(1, date);
			
			if(date.after(new Date()))
				canEdit = true;
		}
		
		setIsSelectedEditable(canEdit);
		setExtras(model.getExtras());
		setRecordID(model.getId());
		
		addReviewItems(model, date);
		showSummaryView();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		getActivity().onBackPressed();
		
		if (resultCode == Activity.RESULT_OK) {
		
			int rID = data.getIntExtra("id", 0);
					
			for(int i = 0; i < adapter.getCount(); i++) {
				SuspiciousActivityModel m = (SuspiciousActivityModel) adapter.getItem(i);
				
				if(m.getId() == rID) {
					m.setImagePath(data.getStringExtra("imagePath"));
					m.setActionTaken(data.getStringExtra("actionTaken"));
					m.setExtraNotes(data.getStringExtra("extraNotes"));
					
					adapter.notifyDataSetChanged();
					break;
					
				}
			}
		}
	}

	public void addReviewItems(Model m, Date date){
		
		SuspiciousActivityModel model = (SuspiciousActivityModel) m;
		
		ReportViewerActivity activity = (ReportViewerActivity) getActivity();
		
		activity.clearReviewItems();
		activity.addReviewItem(new SummaryItem("Image", model.getImagePath(), "", 1));
		activity.addReviewItem(new SummaryItem("Waypoint", model.getWaypoint(), "", 2));
		activity.addReviewItem(new SummaryItem("Action Taken", model.getActionTaken(), "", 4));
		activity.addReviewItem(new SummaryItem("Extra Notes", model.getExtraNotes(), "", 5));
		
		if(date != null)
			activity.addReviewItem(new SummaryItem("Date Created", new SimpleDateFormat( "yyyy-MM-dd" ).format(date), "", 6));
	}
	
	@Override
	public String getLoaderName() {
		return SuspiciousActivitiesLoader.class.getSimpleName();
	}
	
	@Override
	public BaseAdapter getAdapter(Context context) {
		
		if(adapter == null) {
			adapter = new SuspiciousActivitiesReportAdapter(getActivity()) {
				@Override
				protected void onNextPageRequested(int page) {
					onNextPage();
				}
			};
		}
		
		return adapter;
	}


	@Override
	public Class<?> getEditActivity() {
		return SuspiciousActivitiesActivity.class;
	}
}
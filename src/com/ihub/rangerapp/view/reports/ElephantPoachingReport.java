package com.ihub.rangerapp.view.reports;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ihub.rangerapp.ElephantPoachingActivity;
import com.ihub.rangerapp.ReportViewerActivity;
import com.ihub.rangerapp.adapter.AmazingAdapter;
import com.ihub.rangerapp.adapter.ElephantPoachingReportAdapter;
import com.ihub.rangerapp.entity.SummaryItem;
import com.ihub.rangerapp.loader.ElephantPoachingLoader;
import com.ihub.rangerapp.model.ElephantPoachingModel;
import com.ihub.rangerapp.model.GameMeatModel;
import com.ihub.rangerapp.model.Model;
import com.ihub.rangerapp.util.DateUtil;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

public class ElephantPoachingReport extends ReportFragment {

	AmazingAdapter adapter;
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		
		ElephantPoachingModel model = (ElephantPoachingModel) adapter.getItem(position);
		
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
	
	public void addReviewItems(Model m, Date date){
		
		ElephantPoachingModel model = (ElephantPoachingModel) m;
		
		ReportViewerActivity activity = (ReportViewerActivity) getActivity();
		
		activity.clearReviewItems();
		
		activity.addReviewItem(new SummaryItem("Image", model.getImagePath(), "", 1));
		activity.addReviewItem(new SummaryItem("Latitude", model.getLatitude(), "", 2));
		activity.addReviewItem(new SummaryItem("Longitude", model.getLongitude(), "", 3));
		activity.addReviewItem(new SummaryItem("Tool Used", model.getToolsUsed(), "", 4));
		activity.addReviewItem(new SummaryItem("No of Animals", model.getNoOfAnimals() + "", "", 5));
		activity.addReviewItem(new SummaryItem("Age", model.getAge(), "", 6));
		activity.addReviewItem(new SummaryItem("Gender", model.getSex() , "", 7));
		activity.addReviewItem(new SummaryItem("Action Taken", model.getActionTaken(), "", 8));
		activity.addReviewItem(new SummaryItem("Extra Notes", model.getExtraNotes(), "", 9));
		
		if(date != null)
			activity.addReviewItem(new SummaryItem("Date Created", new SimpleDateFormat( "yyyy-MM-dd" ).format(date), "", 10));
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		getActivity().onBackPressed();
		
		int rID = data.getIntExtra("id", 0);
				
		for(int i = 0; i < adapter.getCount(); i++) {
			ElephantPoachingModel m = (ElephantPoachingModel) adapter.getItem(i);
			
			if(m.getId() == rID) {
				
				m.setToolsUsed(data.getStringExtra("toolsUsed"));
				m.setNoOfAnimals(data.getIntExtra("noOfAnimals", 0));
				m.setActionTaken(data.getStringExtra("actionTaken"));
				m.setExtraNotes(data.getStringExtra("extraNotes"));
				
				m.setAge(data.getStringExtra("age"));
				m.setSex(data.getStringExtra("sex"));
				m.setIvoryPresence(data.getStringExtra("ivoryPresence"));
				
				adapter.notifyDataSetChanged();
				break;
				
			}
		}
	}
	
	@Override
	public String getLoaderName() {
		return ElephantPoachingLoader.class.getSimpleName();
	}
	
	@Override
	public BaseAdapter getAdapter(Context context) {
		
		if(adapter == null) {
			adapter = new ElephantPoachingReportAdapter(getActivity()) {
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
		return ElephantPoachingActivity.class;
	}
}
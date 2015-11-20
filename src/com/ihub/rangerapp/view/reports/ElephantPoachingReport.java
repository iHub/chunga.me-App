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
import com.ihub.rangerapp.model.Model;
import com.ihub.rangerapp.util.DateUtil;

import android.app.Activity;
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
		activity.addReviewItem(new SummaryItem("Waypoint", model.getWaypoint(), "", 2));
		activity.addReviewItem(new SummaryItem("Tool Used", model.getToolsUsed(), "", 3));
		activity.addReviewItem(new SummaryItem("No of Animals", model.getNoOfAnimals() + "", "", 4));
		
		Integer noOfAnimals = model.getNoOfAnimals();
		
		if(noOfAnimals == 1) {
			
			activity.addReviewItem(new SummaryItem("Age", model.getAdultsCount() == 1 ? "Adult" : (model.getSemiAdultsCount() == 1 ? "Sub-Adult" : (model.getJuvenileCount() == 1 ? "Juvenile" : "")), "", 5));
			activity.addReviewItem(new SummaryItem("Sex", model.getMaleCount() == 1 ? "Male" : (model.getFemaleCount() == 1 ? "Female" : "") , "", 6));
			
		} else if(noOfAnimals > 0) {
			activity.addReviewItem(new SummaryItem("Male Count", model.getMaleCount() + "", "", 5));
			activity.addReviewItem(new SummaryItem("Female Count", model.getFemaleCount() + "", "", 6));
			activity.addReviewItem(new SummaryItem("Adult Count", model.getAdultsCount() + "", "", 7));
			activity.addReviewItem(new SummaryItem("Sub-Adults Count", model.getSemiAdultsCount() + "", "", 8));
			activity.addReviewItem(new SummaryItem("Juvenile Count", model.getJuvenileCount() + "", "", 8));
		}
		
		activity.addReviewItem(new SummaryItem("Tusks Present", model.getIvoryPresence(), "", 9));
		activity.addReviewItem(new SummaryItem("Action Taken", model.getActionTaken(), "", 10));
		activity.addReviewItem(new SummaryItem("Extra Notes", model.getExtraNotes(), "", 11));
		activity.addReviewItem(new SummaryItem("Ranch", model.getRanch(), "", 12));
		
		if("yes".equals(model.getIvoryPresence().toLowerCase())) {
			activity.addReviewItem(new SummaryItem("Left Tusk Weight", model.getLeftTuskWeight() + "", "", 13));
			activity.addReviewItem(new SummaryItem("Right Tusk Weight", model.getRightTuskWeight() + "", "", 14));
		}
		 
		if(date != null)
			activity.addReviewItem(new SummaryItem("Date Created", new SimpleDateFormat( "yyyy-MM-dd" ).format(date), "", 16));
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		getActivity().onBackPressed();
		
		if (resultCode == Activity.RESULT_OK) {
			
			int rID = data.getIntExtra("id", 0);
			
			for(int i = 0; i < adapter.getCount(); i++) {
				ElephantPoachingModel m = (ElephantPoachingModel) adapter.getItem(i);
				
				if(m.getId() == rID) {
					
					m.setImagePath(data.getStringExtra("imagePath"));
					m.setToolsUsed(data.getStringExtra("toolsUsed"));
					m.setNoOfAnimals(data.getIntExtra("noOfAnimals", 0));
					m.setActionTaken(data.getStringExtra("actionTaken"));
					m.setExtraNotes(data.getStringExtra("extraNotes"));
					
					m.setMaleCount(Integer.valueOf(data.getIntExtra("maleCount", 0)));
					m.setFemaleCount(Integer.valueOf(data.getIntExtra("femaleCount", 0)));
					m.setAdultsCount(Integer.valueOf(data.getIntExtra("adultsCount", 0)));
					m.setSemiAdultsCount(Integer.valueOf(data.getIntExtra("semiAdultsCount", 0)));
					m.setJuvenileCount(Integer.valueOf(data.getIntExtra("juvenileCount", 0)));
					m.setIvoryPresence(data.getStringExtra("ivoryPresence"));
					m.setRanch(data.getStringExtra("ranch"));
					
					if("Yes".equals(data.getStringExtra("ivoryPresence"))) {
						
						try {
							m.setLeftTuskWeight(Integer.valueOf(data.getStringExtra("leftTuskWeight")));
						} catch(Exception e) {}
						
						try {
							m.setRightTuskWeight(Integer.valueOf(data.getStringExtra("rightTuskWeight")));
						} catch(Exception e) {}
						
					}
					
					adapter.notifyDataSetChanged();
					break;
					
				}
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
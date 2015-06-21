package com.ihub.rangerapp.view.reports;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ihub.rangerapp.GameMeatActivity;
import com.ihub.rangerapp.ReportViewerActivity;
import com.ihub.rangerapp.WaterholesActivity;
import com.ihub.rangerapp.adapter.AmazingAdapter;
import com.ihub.rangerapp.adapter.GameMeatReportAdapter;
import com.ihub.rangerapp.adapter.WaterholesReportAdapter;
import com.ihub.rangerapp.entity.SummaryItem;
import com.ihub.rangerapp.loader.GameMeatLoader;
import com.ihub.rangerapp.loader.WaterholesLoader;
import com.ihub.rangerapp.model.GameMeatModel;
import com.ihub.rangerapp.model.Model;
import com.ihub.rangerapp.model.SuspiciousActivityModel;
import com.ihub.rangerapp.model.WaterholeModel;
import com.ihub.rangerapp.util.DateUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

public class WaterholesReport extends ReportFragment {

	AmazingAdapter adapter;
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		
		WaterholeModel model = (WaterholeModel) adapter.getItem(position);
		
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
				WaterholeModel m = (WaterholeModel) adapter.getItem(i);
				
				if(m.getId() == rID) {
					m.setImagePath(data.getStringExtra("imagePath"));
					m.setName(data.getStringExtra("name"));
					m.setNumberOfAnimals(data.getIntExtra("numberOfAnimals", 0));
					m.setLevelOfWater(data.getStringExtra("levelOfWater"));
					m.setExtraNotes(data.getStringExtra("extraNotes"));
					
					adapter.notifyDataSetChanged();
					break;
					
				}
			}
		}
	}
	
	public void addReviewItems(Model m, Date date){
		
		WaterholeModel model = (WaterholeModel) m;
		
		ReportViewerActivity activity = (ReportViewerActivity) getActivity();
		
		activity.clearReviewItems();
		activity.addReviewItem(new SummaryItem("Image", model.getImagePath(), "", 1));
		activity.addReviewItem(new SummaryItem("Latitude", model.getLatitude(), "", 2));
		activity.addReviewItem(new SummaryItem("Longitude", model.getLongitude(), "", 3));
		activity.addReviewItem(new SummaryItem("Name", model.getName(), "", 4));
		activity.addReviewItem(new SummaryItem("Water Level", model.getLevelOfWater(), "", 5));
		activity.addReviewItem(new SummaryItem("No of Animals", model.getNumberOfAnimals() + "", "", 6));
		activity.addReviewItem(new SummaryItem("Extra Notes", model.getExtraNotes(), "", 7));
		
		if(date != null)
			activity.addReviewItem(new SummaryItem("Date Created", new SimpleDateFormat( "yyyy-MM-dd" ).format(date), "", 8));
	}
	
	@Override
	public String getLoaderName() {
		return WaterholesLoader.class.getSimpleName();
	}
	
	@Override
	public BaseAdapter getAdapter(Context context) {
		
		if(adapter == null) {
			adapter = new WaterholesReportAdapter(getActivity()) {
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
		return WaterholesActivity.class;
	}
}
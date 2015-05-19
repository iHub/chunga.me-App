package com.ihub.rangerapp.view.reports;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ihub.rangerapp.CharcoalBurningActivity;
import com.ihub.rangerapp.ReportViewerActivity;
import com.ihub.rangerapp.adapter.AmazingAdapter;
import com.ihub.rangerapp.adapter.CharcoalKilnsReportAdapter;
import com.ihub.rangerapp.entity.SummaryItem;
import com.ihub.rangerapp.loader.CharcoalKilnsLoader;
import com.ihub.rangerapp.model.CharcoalKilnModel;
import com.ihub.rangerapp.model.GameMeatModel;
import com.ihub.rangerapp.model.Model;
import com.ihub.rangerapp.util.DateUtil;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

public class CharcoalBurningKilnsReport extends ReportFragment {

	AmazingAdapter adapter;
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		
		CharcoalKilnModel model = (CharcoalKilnModel) adapter.getItem(position);
		
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
		
		int rID = data.getIntExtra("id", 0);
				
		for(int i = 0; i < adapter.getCount(); i++) {
			CharcoalKilnModel m = (CharcoalKilnModel) adapter.getItem(i);
			
			if(m.getId() == rID) {
				
				m.setFreshnessLevels(data.getStringExtra("freshnessLevels"));
				m.setNoOfKilns(data.getIntExtra("noOfKilns", 0));
				m.setTreeUsed(data.getStringExtra("treeUsed"));
				m.setActionTaken(data.getStringExtra("actionTaken"));
				m.setExtraNotes(data.getStringExtra("extraNotes"));
				
				adapter.notifyDataSetChanged();
				break;
				
			}
		}
	}
	
	public void addReviewItems(Model m, Date date){
		
		CharcoalKilnModel model = (CharcoalKilnModel) m;
		
		ReportViewerActivity activity = (ReportViewerActivity) getActivity();
		
		activity.clearReviewItems();
		activity.addReviewItem(new SummaryItem("Image", model.getImagePath(), "", 1));
		activity.addReviewItem(new SummaryItem("Latitude", model.getLatitude(), "", 2));
		activity.addReviewItem(new SummaryItem("Longitude", model.getLongitude(), "", 3));
		activity.addReviewItem(new SummaryItem("Tree Used", model.getTreeUsed() + "", "", 4));
		activity.addReviewItem(new SummaryItem("No of Kilns", model.getNoOfKilns() + "", "", 5));
		activity.addReviewItem(new SummaryItem("Freshness Level", model.getFreshnessLevels(), "", 6));
		activity.addReviewItem(new SummaryItem("Action Taken", model.getActionTaken(), "", 7));
		activity.addReviewItem(new SummaryItem("Extra Notes", model.getExtraNotes(), "", 8));
		
		if(date != null)
			activity.addReviewItem(new SummaryItem("Date Created", new SimpleDateFormat( "yyyy-MM-dd" ).format(date), "", 9));
	}
	
	@Override
	public String getLoaderName() {
		return CharcoalKilnsLoader.class.getSimpleName();
	}
	
	@Override
	public BaseAdapter getAdapter(Context context) {
		
		if(adapter == null) {
			adapter = new CharcoalKilnsReportAdapter(getActivity()) {
				@Override
				protected void onNextPageRequested(int page) {
					onNextPage();
				}
			};
		}
		
		return adapter;
	}
	
	public void startEdit() {
		Intent intent = new Intent(getActivity(), getEditActivity());
		intent.putExtras(getExtras());
		intent.putExtra("view", "kiln");
		intent.putExtra("mode", getIsSelectedEditable() ? 2 : 3);
		startActivityForResult(intent, 100);
	}

	@Override
	public Class<?> getEditActivity() {
		return CharcoalBurningActivity.class;
	}
}
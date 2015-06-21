package com.ihub.rangerapp.view.reports;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ihub.rangerapp.AnimalsSightingsActivity;
import com.ihub.rangerapp.ReportViewerActivity;
import com.ihub.rangerapp.adapter.AmazingAdapter;
import com.ihub.rangerapp.adapter.HerdReportAdapter;
import com.ihub.rangerapp.entity.SummaryItem;
import com.ihub.rangerapp.loader.HerdsLoader;
import com.ihub.rangerapp.model.HerdModel;
import com.ihub.rangerapp.model.Model;
import com.ihub.rangerapp.util.DateUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

public class HerdsReport extends ReportFragment {
	
	AmazingAdapter adapter;
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		
		HerdModel model = (HerdModel) adapter.getItem(position);
		
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
				HerdModel m = (HerdModel) adapter.getItem(i);
				
				if(m.getId() == rID) {
					m.setImagePath(data.getStringExtra("imagePath"));
					m.setName(data.getStringExtra("name"));
					m.setType(data.getStringExtra("type"));
					m.setNoOfAnimals(data.getIntExtra("noOfAnimals", 0));
					m.setAdultsCount(Integer.valueOf(data.getIntExtra("adultsCount", 0)));
					m.setSemiAdultsCount(Integer.valueOf(data.getIntExtra("semiAdultsCount", 0)));
					m.setJuvenileCount(Integer.valueOf(data.getIntExtra("juvenileCount", 0)));
					m.setDistanceSeen(data.getIntExtra("distanceSeen", 0));
					m.setExtraNotes(data.getStringExtra("extraNotes"));
					
					adapter.notifyDataSetChanged();
					break;
				}
			}
		}
	}
	
	public void addReviewItems(Model m, Date date){
		
		HerdModel model = (HerdModel) m;
		
		ReportViewerActivity activity = (ReportViewerActivity) getActivity();
		
		activity.clearReviewItems();
		activity.addReviewItem(new SummaryItem("Image", model.getImagePath(), "", 1));
		activity.addReviewItem(new SummaryItem("Latitude", model.getLatitude(), "", 2));
		activity.addReviewItem(new SummaryItem("Longitude", model.getLongitude(), "", 3));
		activity.addReviewItem(new SummaryItem("Name", model.getName(), "", 4));
		activity.addReviewItem(new SummaryItem("Type", model.getType(), "", 5));
		activity.addReviewItem(new SummaryItem("No of Animals", model.getNoOfAnimals() + "", "", 6));
		activity.addReviewItem(new SummaryItem("Adult Count", model.getAdultsCount() + "", "", 7));
		activity.addReviewItem(new SummaryItem("Semi-Adults Count", model.getSemiAdultsCount() + "", "", 8));
		activity.addReviewItem(new SummaryItem("Juvenile Count", model.getJuvenileCount() + "", "", 9));
		activity.addReviewItem(new SummaryItem("Distance Seen (Mtrs)", model.getDistanceSeen() + "", "", 10));
		activity.addReviewItem(new SummaryItem("Extra Notes", model.getExtraNotes(), "", 11));
		
		if(date != null)
			activity.addReviewItem(new SummaryItem("Date Created", new SimpleDateFormat( "yyyy-MM-dd" ).format(date), "", 10));
	}
	
	@Override
	public String getLoaderName() {
		return HerdsLoader.class.getSimpleName();
	}
	
	@Override
	public BaseAdapter getAdapter(Context context) {
		
		if(adapter == null) {
			adapter = new HerdReportAdapter(getActivity()) {
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
		intent.putExtra("view", "herd");
		intent.putExtra("mode", getIsSelectedEditable() ? 2 : 3);
		startActivityForResult(intent, 100);
	}

	@Override
	public Class<?> getEditActivity() {
		return AnimalsSightingsActivity.class;
	}
}
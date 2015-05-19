package com.ihub.rangerapp.view.reports;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ihub.rangerapp.CharcoalBurningActivity;
import com.ihub.rangerapp.GameMeatActivity;
import com.ihub.rangerapp.ReportViewerActivity;
import com.ihub.rangerapp.adapter.AmazingAdapter;
import com.ihub.rangerapp.adapter.CharcoalBagsReportAdapter;
import com.ihub.rangerapp.adapter.GameMeatReportAdapter;
import com.ihub.rangerapp.entity.SummaryItem;
import com.ihub.rangerapp.loader.CharcoalBagsLoader;
import com.ihub.rangerapp.loader.GameMeatLoader;
import com.ihub.rangerapp.model.CharcoalBagModel;
import com.ihub.rangerapp.model.CharcoalKilnModel;
import com.ihub.rangerapp.model.GameMeatModel;
import com.ihub.rangerapp.model.Model;
import com.ihub.rangerapp.util.DateUtil;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

public class CharcoalBurningBagsReport extends ReportFragment {

	AmazingAdapter adapter;
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		
		CharcoalBagModel model = (CharcoalBagModel) adapter.getItem(position);
		
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
				
//		Intent intent = new Intent(getActivity(), CharcoalBurningActivity.class);
//		intent.putExtra("view", "bag");
//		intent.putExtras(model.getExtras());
//		
//		intent.putExtra("mode", canEdit ? 2 : 3);
//		
//		getActivity().startActivity(intent);
		
		addReviewItems(model, date);
		showSummaryView();
	}
	
	public void addReviewItems(Model m, Date date){
		
		CharcoalBagModel model = (CharcoalBagModel) m;
		
		ReportViewerActivity activity = (ReportViewerActivity) getActivity();
		
		activity.clearReviewItems();
		activity.addReviewItem(new SummaryItem("Image", model.getImagePath(), "", 1));
		activity.addReviewItem(new SummaryItem("Latitude", model.getLatitude(), "", 2));
		activity.addReviewItem(new SummaryItem("Longitude", model.getLongitude(), "", 3));
		activity.addReviewItem(new SummaryItem("No of Bags", model.getNoOfBags() + "", "", 4));
		activity.addReviewItem(new SummaryItem("Mode of Transport", model.getModeOfTransport(), "", 5));
		activity.addReviewItem(new SummaryItem("Action Taken", model.getActionTaken(), "", 6));
		activity.addReviewItem(new SummaryItem("Extra Notes", model.getExtraNotes(), "", 7));
		
		if(date != null)
			activity.addReviewItem(new SummaryItem("Date Created", new SimpleDateFormat( "yyyy-MM-dd" ).format(date), "", 9));
	}
	
	@Override
	public String getLoaderName() {
		return CharcoalBagsLoader.class.getSimpleName();
	}
	
	@Override
	public BaseAdapter getAdapter(Context context) {
		
		if(adapter == null) {
			adapter = new CharcoalBagsReportAdapter(getActivity()) {
				@Override
				protected void onNextPageRequested(int page) {
					onNextPage();
				}
			};
		}
		
		return adapter;
	}
}
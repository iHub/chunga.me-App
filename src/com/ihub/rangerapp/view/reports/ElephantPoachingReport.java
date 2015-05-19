package com.ihub.rangerapp.view.reports;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.ihub.rangerapp.ReportViewerActivity;
import com.ihub.rangerapp.adapter.AmazingAdapter;
import com.ihub.rangerapp.adapter.ElephantPoachingReportAdapter;
import com.ihub.rangerapp.entity.SummaryItem;
import com.ihub.rangerapp.loader.ElephantPoachingLoader;
import com.ihub.rangerapp.model.ElephantPoachingModel;
import com.ihub.rangerapp.model.Model;
import com.ihub.rangerapp.util.DateUtil;
import android.content.Context;
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
		//TODO check date before editing
		
//		Intent intent = new Intent(getActivity(), ElephantPoachingActivity.class);
//		intent.putExtras(model.getExtras());
//		
//		intent.putExtra("mode", canEdit ? 2 : 3);
//		
//		getActivity().startActivity(intent);
		
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
}
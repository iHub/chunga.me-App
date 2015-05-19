package com.ihub.rangerapp.view.reports;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ihub.rangerapp.AnimalsSightingsActivity;
import com.ihub.rangerapp.ReportViewerActivity;
import com.ihub.rangerapp.adapter.AmazingAdapter;
import com.ihub.rangerapp.adapter.IndividualAnimalAdapter;
import com.ihub.rangerapp.entity.SummaryItem;
import com.ihub.rangerapp.loader.IndividualAnimalsLoader;
import com.ihub.rangerapp.model.IndividualAnimalModel;
import com.ihub.rangerapp.model.Model;
import com.ihub.rangerapp.model.SuspiciousActivityModel;
import com.ihub.rangerapp.util.DateUtil;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

public class IndividualAnimalsReport extends ReportFragment {

	AmazingAdapter adapter;
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		
		IndividualAnimalModel model = (IndividualAnimalModel) adapter.getItem(position);
		
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
		
//		Intent intent = new Intent(getActivity(), AnimalsSightingsActivity.class);
//		intent.putExtras(model.getExtras());
//		intent.putExtra("view", "individual");
//		
//		intent.putExtra("mode", canEdit ? 2 : 3);
//		
//		getActivity().startActivity(intent);
		
		addReviewItems(model, date);
		showSummaryView();
	}
	
	public void addReviewItems(Model m, Date date){
		
		IndividualAnimalModel model = (IndividualAnimalModel) m;
		
		ReportViewerActivity activity = (ReportViewerActivity) getActivity();
		
		activity.clearReviewItems();
		activity.addReviewItem(new SummaryItem("Image", model.getImagePath(), "", 1));
		activity.addReviewItem(new SummaryItem("Latitude", model.getLatitude(), "", 2));
		activity.addReviewItem(new SummaryItem("Longitude", model.getLongitude(), "", 3));
		activity.addReviewItem(new SummaryItem("Animal", model.getAnimal(), "", 4));
		activity.addReviewItem(new SummaryItem("Gender", model.getGender(), "", 5));
		activity.addReviewItem(new SummaryItem("Age", model.getAge(), "", 6));
		activity.addReviewItem(new SummaryItem("Distance Seen (Mtrs)", model.getDistanceSeen() + "", "", 7));
		activity.addReviewItem(new SummaryItem("Extra Notes", model.getExtraNotes(), "", 8));
		
		if(date != null)
			activity.addReviewItem(new SummaryItem("Date Created", new SimpleDateFormat( "yyyy-MM-dd" ).format(date), "", 9));
	}
	
	@Override
	public String getLoaderName() {
		return IndividualAnimalsLoader.class.getSimpleName();
	}
	
	@Override
	public BaseAdapter getAdapter(Context context) {
		
		if(adapter == null) {
			adapter = new IndividualAnimalAdapter(getActivity()) {
				@Override
				protected void onNextPageRequested(int page) {
					onNextPage();
				}
			};
		}
		
		return adapter;
	}
}
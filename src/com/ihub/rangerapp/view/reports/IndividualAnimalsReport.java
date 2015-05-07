package com.ihub.rangerapp.view.reports;

import java.util.Date;

import com.ihub.rangerapp.AnimalsSightingsActivity;
import com.ihub.rangerapp.adapter.AmazingAdapter;
import com.ihub.rangerapp.adapter.IndividualAnimalAdapter;
import com.ihub.rangerapp.loader.IndividualAnimalsLoader;
import com.ihub.rangerapp.model.IndividualAnimalModel;
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
		
		Intent intent = new Intent(getActivity(), AnimalsSightingsActivity.class);
		intent.putExtras(model.getExtras());
		intent.putExtra("view", "individual");
		
		intent.putExtra("mode", canEdit ? 2 : 3);
		
		getActivity().startActivity(intent);
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
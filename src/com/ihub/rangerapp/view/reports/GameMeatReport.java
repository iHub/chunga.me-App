package com.ihub.rangerapp.view.reports;

import java.util.Calendar;
import java.util.Date;

import com.ihub.rangerapp.GameMeatActivity;
import com.ihub.rangerapp.adapter.AmazingAdapter;
import com.ihub.rangerapp.adapter.GameMeatReportAdapter;
import com.ihub.rangerapp.loader.GameMeatLoader;
import com.ihub.rangerapp.model.GameMeatModel;
import com.ihub.rangerapp.util.DateUtil;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

public class GameMeatReport extends ReportFragment {
	
	AmazingAdapter adapter;
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		
		GameMeatModel model = (GameMeatModel) adapter.getItem(position);
		
		Date date = null;
		
		Boolean canEdit = false;
		
		try {
			date = DateUtil.parse(model.getDateCreated());
		} catch (Exception e) {}
		
		if(date != null) {
			date  = DateUtil.addDays(1, new Date());
			
			if(date.after(new Date()))
				canEdit = true;
		}
		
		//TODO check date before editing
		
		Intent intent = new Intent(getActivity(), GameMeatActivity.class);
		intent.putExtras(model.getExtras());
		
		if(canEdit)
			intent.putExtra("edit", true);
		
		getActivity().startActivity(intent);
	}
	
	@Override
	public String getLoaderName() {
		return GameMeatLoader.class.getSimpleName();
	}
	
	@Override
	public BaseAdapter getAdapter(Context context) {
		
		if(adapter == null) {
			adapter = new GameMeatReportAdapter(getActivity()) {
				@Override
				protected void onNextPageRequested(int page) {
					onNextPage();
				}
			};
		}
		
		return adapter;
	}
}
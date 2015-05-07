package com.ihub.rangerapp.view.reports;

import java.util.Date;

import com.ihub.rangerapp.CharcoalBurningActivity;
import com.ihub.rangerapp.adapter.AmazingAdapter;
import com.ihub.rangerapp.adapter.CharcoalKilnsReportAdapter;
import com.ihub.rangerapp.loader.CharcoalKilnsLoader;
import com.ihub.rangerapp.model.CharcoalKilnModel;
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
		
		Intent intent = new Intent(getActivity(), CharcoalBurningActivity.class);
		intent.putExtra("view", "bag");
		intent.putExtras(model.getExtras());
		
		intent.putExtra("mode", canEdit ? 2 : 3);
		
		getActivity().startActivity(intent);
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
}
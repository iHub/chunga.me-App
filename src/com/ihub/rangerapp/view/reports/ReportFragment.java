package com.ihub.rangerapp.view.reports;

import java.util.Date;

import com.ihub.rangerapp.R;
import com.ihub.rangerapp.list.RefreshLoadMoreListView;
import com.ihub.rangerapp.model.Model;
import com.ihub.rangerapp.view.SummaryFragment;

public abstract class ReportFragment extends RefreshLoadMoreListView {
	
	public void addReviewItems(Model model, Date date){}
	
	protected void showSummaryView() {
		getActivity().getSupportFragmentManager().beginTransaction()
			.replace(R.id.container, new SummaryFragment()).addToBackStack("").commit();
	}
}
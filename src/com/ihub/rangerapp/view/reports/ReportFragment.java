package com.ihub.rangerapp.view.reports;

import java.util.Date;

import android.content.Intent;
import com.ihub.rangerapp.R;
import com.ihub.rangerapp.list.RefreshLoadMoreListView;
import com.ihub.rangerapp.model.Model;
import com.ihub.rangerapp.view.SummaryFragment;

public abstract class ReportFragment extends RefreshLoadMoreListView {
	
	Boolean isSelectedEditable = false;
	Intent extras = new Intent();
	Integer recordID = 0;
	
	public void addReviewItems(Model model, Date date){}
	
	protected void showSummaryView() {
		getActivity().getSupportFragmentManager().beginTransaction()
			.replace(R.id.container, new SummaryFragment()).addToBackStack("").commit();
	}
	
	public Integer getRecordID() {
		return 0;
	}

	public Boolean getIsSelectedEditable() {
		return isSelectedEditable;
	}

	public void setIsSelectedEditable(Boolean isSelectedEditable) {
		this.isSelectedEditable = isSelectedEditable;
	}

	public Intent getExtras() {
		return extras;
	}

	public void setExtras(Intent intent) {
		this.extras = intent;
	}

	public void setRecordID(Integer recordID) {
		this.recordID = recordID;
	}
	
	public void startEdit() {
		Intent intent = new Intent(getActivity(), getEditActivity());
		intent.putExtras(getExtras());
		intent.putExtra("mode", getIsSelectedEditable() ? 2 : 3);
		startActivityForResult(intent, 100);
	}

	public abstract Class<?> getEditActivity();
}
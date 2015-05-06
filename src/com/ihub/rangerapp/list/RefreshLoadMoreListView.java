package com.ihub.rangerapp.list;

import com.ihub.rangerapp.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class RefreshLoadMoreListView extends RefreshListView {
	
	public RefreshLoadMoreListView() {}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.swiperefresh_loadmore_list, container, false);
	}
}
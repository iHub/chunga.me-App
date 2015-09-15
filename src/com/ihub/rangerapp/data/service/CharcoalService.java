package com.ihub.rangerapp.data.service;

import java.util.Map;

import com.loopj.android.http.AsyncHttpResponseHandler;

public interface CharcoalService {

	public Map<String, Object> saveBagsData(
		Integer id,
		Integer noOfBags,
		String mode,
		String actionTaken,
		String extraNotes,
		String imagePath,
		String waypoint,
		String ranch
	);
	
	public void syncBags(Integer id, AsyncHttpResponseHandler handler);
	
	public Map<String, Object> saveKilns(
		Integer id,
		Integer noOfKilns,
		String freshnessLevels,
		String treeUsed,
		String actionTaken,
		String extraNotes,
		String imagePath,
		String waypoint,
		String ranch
	);
	
	public void syncKilns(Integer id, AsyncHttpResponseHandler handler);
}
package com.ihub.rangerapp.data.service;

import java.util.Map;

import com.loopj.android.http.AsyncHttpResponseHandler;

public interface SuspiciousActivitiesService {
	
	public Map<String, Object> save(
		Integer id, 
		String actionTaken,
		String extraNotes,
		String imagePath,
		String waypoint);
	
	public void sync(Integer id, AsyncHttpResponseHandler handler);
}

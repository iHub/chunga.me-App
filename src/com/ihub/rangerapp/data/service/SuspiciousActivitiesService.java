package com.ihub.rangerapp.data.service;

import java.util.Map;

public interface SuspiciousActivitiesService {
	
	public Map<String, Object> save(
		Integer id, 
		String actionTaken,
		String extraNotes,
		String imagePath,
		String lat,
		String lon);
}

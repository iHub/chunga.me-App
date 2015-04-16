package com.ihub.rangerapp.data.service;

import java.util.Map;

public interface SuspiciousActivitiesService {
	
	public Map<String, Object> save(
		String actionTaken,
		String extraNotes,
		String imagePath,
		String wp);
}

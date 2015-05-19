package com.ihub.rangerapp.data.service;

import java.util.Map;

public interface ElephantService {

	public Map<String, Object> save(
		Integer id,
		String toolUsed,
		Integer noOfAnimals,
		String age,
		String sex,
		String ivoryPresence,
		String actionTaken,
		String extraNotes,
		String imagePath,
		String lat,
		String lon
	);
}

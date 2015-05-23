package com.ihub.rangerapp.data.service;

import java.util.Map;

public interface ElephantService {

	public Map<String, Object> save(
		Integer id,
		String toolUsed,
		Integer noOfAnimals,
		Integer maleCount,
		Integer femaleCount,
		Integer adultsCount,
		Integer semiAdultsCount,
		Integer juvenileCount,
		String ivoryPresence,
		String actionTaken,
		String extraNotes,
		String imagePath,
		String lat,
		String lon
	);
}
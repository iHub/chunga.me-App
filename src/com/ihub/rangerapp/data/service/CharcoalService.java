package com.ihub.rangerapp.data.service;

import java.util.Map;

public interface CharcoalService {

	public Map<String, Object> saveBagsData(
		Integer id,
		Integer noOfBags,
		String mode,
		String actionTaken,
		String extraNotes,
		String imagePath,
		String wp
	);
	
	public Map<String, Object> saveKilns(
		Integer id,
		Integer noOfKilns,
		String freshnessLevels,
		String treeUsed,
		String actionTaken,
		String extraNotes,
		String imagePath,
		String wp
	);
}
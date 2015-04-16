package com.ihub.rangerapp.data.service;

import java.util.Map;

public interface WaterholeService {

	public Map<String, Object> save(
			String name,
			String level,
			Integer noOfAnimalsSeen,
			String extraNotes,
			String imagePath,
			String wp);
}

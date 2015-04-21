package com.ihub.rangerapp.data.service;

import java.util.Map;

public interface AnimalSightingsService {
	
	public Map<String, Object> saveIndividualAnimal(
			String name,
			String gender,
			String age,
			Integer distanceSeen,
			String extraNotes,
			String imagePath,
			String wp);
	
	public Map<String, Object> saveHerd(
			String name,
			String species,
			Integer noOfAnimals,
			String age,
			Integer distanceSeen,
			String extraNotes,
			String imagePath,
			String wp);
}

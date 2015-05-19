package com.ihub.rangerapp.data.service;

import java.util.Map;

public interface AnimalSightingsService {
	
	public Map<String, Object> saveIndividualAnimal(
			Integer id,
			String name,
			String gender,
			String age,
			Integer distanceSeen,
			String extraNotes,
			String imagePath,
			String lat,
			String lon);
	
	public Map<String, Object> saveHerd(
			Integer id,
			String name,
			String species,
			Integer noOfAnimals,
			String age,
			Integer distanceSeen,
			String extraNotes,
			String imagePath,
			String lat,
			String lon);
}

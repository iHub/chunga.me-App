package com.ihub.rangerapp.data.service;

import java.util.Map;

public interface GameMeatService {

	public Map<String, Object> save(
		Integer id,
		String animal, 
		Integer noOfAnimals, 
		String actionTaken, 
		String extraNotes,
		String imagePath,
		String lat,
		String lon);		
	
	public Map<String, Object> edit(
		Integer id, 
		String animal, 
		Integer noOfAnimals, 
		String actionTaken, 
		String extraNotes,
		String imagePath,
		String lat,
		String lon);
}

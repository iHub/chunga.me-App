package com.ihub.rangerapp.data.service;

import java.util.Map;

import com.loopj.android.http.AsyncHttpResponseHandler;

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
	
	public void sync(Integer id, AsyncHttpResponseHandler handler);
}

package com.ihub.rangerapp.data.service;

import java.util.Map;

import com.loopj.android.http.AsyncHttpResponseHandler;

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
	
	public void syncIndividual(Integer id, AsyncHttpResponseHandler handler);
	
	public Map<String, Object> saveHerd(
			Integer id,
			String name,
			String species,
			Integer noOfAnimals,
			Integer adultsCount,
			Integer semiAdultsCount,
			Integer juvenileCount,
			Integer distanceSeen,
			String extraNotes,
			String imagePath,
			String lat,
			String lon);
	
	public void syncHerd(Integer id, AsyncHttpResponseHandler handler);
}
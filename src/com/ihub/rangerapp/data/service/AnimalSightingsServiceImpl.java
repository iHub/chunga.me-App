package com.ihub.rangerapp.data.service;

import java.util.HashMap;
import java.util.Map;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.ihub.rangerapp.RangerApp;
import com.ihub.rangerapp.data.sqlite.Schemas;

public class AnimalSightingsServiceImpl extends DatabaseService implements AnimalSightingsService {

	@Override
	public Map<String, Object> saveIndividualAnimal(String name, String gender, String age, Integer distanceSeen, String extraNotes, String imagePath, String wp) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
 		ContentValues values = new ContentValues();
 		values.put(Schemas.IndividualAnimalSighting.ANIMAL, name);
 		values.put(Schemas.IndividualAnimalSighting.GENDER, gender);
 		values.put(Schemas.IndividualAnimalSighting.AGE, age);
 		values.put(Schemas.IndividualAnimalSighting.DISTANCE_SEEN, distanceSeen);
 		values.put(Schemas.IndividualAnimalSighting.EXTRA_NOTES, extraNotes);
 		values.put(Schemas.IndividualAnimalSighting.IMAGE_PATH, imagePath);
 		values.put(Schemas.IndividualAnimalSighting.WP, wp);
 		
 		try {
 			db.insert(Schemas.INDIVIDUAL_ANIMAL_SIGHTING_TABLE, null, values);
 			result.put("status", "success");
 		} catch (Exception e) {
 			result.put("status", "error");
 			result.put("message", e.getMessage());
 		}
 		
		return result;
	}

	@Override
	public Map<String, Object> saveHerd(String name, String species, Integer noOfAnimals, String age, Integer distanceSeen, String extraNotes, String imagePath, String wp) {

		Map<String, Object> result = new HashMap<String, Object>();
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
 		ContentValues values = new ContentValues();
 		values.put(Schemas.AnimalHerdSighting.NAME, name);
 		values.put(Schemas.AnimalHerdSighting.TYPE, species);
 		values.put(Schemas.AnimalHerdSighting.NUMBER_OF_ANIMALS, noOfAnimals);
 		values.put(Schemas.AnimalHerdSighting.AGE, age);
 		values.put(Schemas.AnimalHerdSighting.DISTANCE_SEEN, distanceSeen);
 		values.put(Schemas.AnimalHerdSighting.EXTRA_NOTES, extraNotes);
 		values.put(Schemas.AnimalHerdSighting.IMAGE_PATH, imagePath);
 		values.put(Schemas.AnimalHerdSighting.WP, wp);
 		
 		try {
 			db.insert(Schemas.ANIMAL_HERD_SIGHTING_TABLE, null, values);
 			result.put("status", "success");
 		} catch (Exception e) {
 			result.put("status", "error");
 			result.put("message", e.getMessage());
 		}
 		
		return result;
	}

}

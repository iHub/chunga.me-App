package com.ihub.rangerapp.data.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.validation.Schema;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.ihub.rangerapp.RangerApp;
import com.ihub.rangerapp.data.sqlite.Schemas;

public class GameMeatServiceImpl extends DatabaseService implements GameMeatService {
	
	@Override
	public Map<String, Object> save(String animal, Integer noOfAnimals, String actionTaken, String extraNotes, String imagePath, String wp) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
 		ContentValues values = new ContentValues();
 		values.put(Schemas.GameMeat.ANIMAL, animal);
 		values.put(Schemas.GameMeat.NO_OF_ANIMALS, noOfAnimals);
 		values.put(Schemas.GameMeat.ACTION_TAKEN, actionTaken);
 		values.put(Schemas.GameMeat.EXTRA_NOTES, extraNotes);
 		values.put(Schemas.GameMeat.WP, wp);
 		values.put(Schemas.GameMeat.IMAGE_PATH, imagePath);
 		
 		try {
 			db.insert(Schemas.GAME_MEAT_TABLE, null, values);
 			result.put("status", "success");
 		} catch (Exception e) {
 			result.put("status", "error");
 			result.put("message", e.getMessage());
 		}
 		
		return result;
	}
	
	@Override
	public Map<String, Object> edit(Integer id, String animal, Integer noOfAnimals, String actionTaken, String extraNotes, String imagePath, String wp) {
		
		return null;
	}
}
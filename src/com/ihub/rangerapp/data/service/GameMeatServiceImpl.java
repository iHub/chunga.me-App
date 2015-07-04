package com.ihub.rangerapp.data.service;

import java.util.HashMap;
import java.util.Map;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.ihub.rangerapp.RangerApp;
import com.ihub.rangerapp.data.sqlite.Schemas;

public class GameMeatServiceImpl extends DatabaseService implements GameMeatService {
	
	@Override
	public Map<String, Object> save(Integer id, String animal, Integer noOfAnimals, String actionTaken, String extraNotes, String imagePath, String lat, String lon) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
		ShiftService service = new ShiftServiceImpl();
		Long shiftID = service.getCurrentShiftID();
		
 		ContentValues values = new ContentValues();
 		if(id == -1)
 			values.put(Schemas.SHIFT_ID, shiftID);
 		
 		values.put(Schemas.GameMeat.ANIMAL, animal);
 		values.put(Schemas.GameMeat.NO_OF_ANIMALS, noOfAnimals);
 		values.put(Schemas.GameMeat.ACTION_TAKEN, actionTaken);
 		values.put(Schemas.GameMeat.EXTRA_NOTES, extraNotes);
 		values.put(Schemas.GameMeat.LAT, lat);
 		values.put(Schemas.GameMeat.LON, lon);
 		values.put(Schemas.GameMeat.IMAGE_PATH, imagePath);
 		
 		try {
 			
 			if(id == -1) {
 				db.insert(Schemas.GAME_MEAT_TABLE, null, values);
 	 			result.put("status", "success");
 			} else {
 				db.update(Schemas.GAME_MEAT_TABLE, values, BaseColumns._ID + "=" + id, null);
 				result.put("status", "success");
 			}
 			
 		} catch (Exception e) {
 			result.put("status", "error");
 			result.put("message", e.getMessage());
 		}
 		
		return result;
	}
	
	@Override
	public Map<String, Object> edit(Integer id, String animal, Integer noOfAnimals, String actionTaken, String extraNotes, String imagePath, String lat, String lon) {
		
		return null;
	}
}
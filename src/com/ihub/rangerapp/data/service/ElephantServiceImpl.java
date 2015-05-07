package com.ihub.rangerapp.data.service;

import java.util.HashMap;
import java.util.Map;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.ihub.rangerapp.RangerApp;
import com.ihub.rangerapp.data.sqlite.Schemas;

public class ElephantServiceImpl extends DatabaseService implements ElephantService {
	
	@Override
	public Map<String, Object> save(Integer id, String toolUsed, Integer noOfAnimals,
			String age, String sex, String ivoryPresence, String actionTaken,
			String extraNotes, String imagePath, String wp) {

		Map<String, Object> result = new HashMap<String, Object>();
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
 		ContentValues values = new ContentValues();
 		values.put(Schemas.ElephantPoaching.TOOLS_USED, toolUsed);
 		values.put(Schemas.ElephantPoaching.NO_OF_ANIMALS, noOfAnimals);
 		values.put(Schemas.ElephantPoaching.AGE, age);
 		values.put(Schemas.ElephantPoaching.SEX, sex);
 		values.put(Schemas.ElephantPoaching.IVORY_PRESENCE, ivoryPresence);
 		values.put(Schemas.ElephantPoaching.ACTION_TAKEN, actionTaken);
 		values.put(Schemas.ElephantPoaching.EXTRA_NOTES, extraNotes);
 		values.put(Schemas.ElephantPoaching.IMAGE_PATH, imagePath);
 		values.put(Schemas.ElephantPoaching.WP, wp);
 		
 		try {
 			
 			if(id == -1) {
 				db.insert(Schemas.ELEPHANT_POACHING_TABLE, null, values);
 	 			result.put("status", "success");
 			} else {
 				db.update(Schemas.ELEPHANT_POACHING_TABLE, values, BaseColumns._ID + "=" + id, null);
 				result.put("status", "success");
 			}
 			
 		} catch (Exception e) {
 			result.put("status", "error");
 			result.put("message", e.getMessage());
 		}
 		
		return result;
	}
}
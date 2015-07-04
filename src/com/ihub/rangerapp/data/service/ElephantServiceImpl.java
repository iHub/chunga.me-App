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
	public Map<String, Object> save(
			Integer id,
			String toolUsed,
			Integer noOfAnimals,
			Integer maleCount,
			Integer femaleCount,
			Integer adultsCount,
			Integer semiAdultsCount,
			Integer juvenileCount,
			String ivoryPresence,
			String actionTaken,
			String extraNotes,
			String imagePath,
			String lat,
			String lon) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
		ShiftService service = new ShiftServiceImpl();
		Long shiftID = service.getCurrentShiftID();
		
 		ContentValues values = new ContentValues();
 		if(id == -1)
 			values.put(Schemas.SHIFT_ID, shiftID);
 		
 		values.put(Schemas.ElephantPoaching.TOOLS_USED, toolUsed);
 		values.put(Schemas.ElephantPoaching.NO_OF_ANIMALS, noOfAnimals);
 		values.put(Schemas.ElephantPoaching.MALE_COUNT, maleCount);
 		values.put(Schemas.ElephantPoaching.FEMALE_COUNT, femaleCount);
 		
 		values.put(Schemas.ElephantPoaching.ADULTS_COUNT, adultsCount);
 		values.put(Schemas.ElephantPoaching.SEMI_ADULTS_COUNT, semiAdultsCount);
 		values.put(Schemas.ElephantPoaching.JUVENILE_COUNT, juvenileCount);
 		
 		values.put(Schemas.ElephantPoaching.IVORY_PRESENCE, ivoryPresence);
 		values.put(Schemas.ElephantPoaching.ACTION_TAKEN, actionTaken);
 		values.put(Schemas.ElephantPoaching.EXTRA_NOTES, extraNotes);
 		values.put(Schemas.ElephantPoaching.IMAGE_PATH, imagePath);
 		values.put(Schemas.ElephantPoaching.LAT, lat);
 		values.put(Schemas.ElephantPoaching.LON, lon);
 		
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
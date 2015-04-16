package com.ihub.rangerapp.data.service;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.ihub.rangerapp.RangerApp;
import com.ihub.rangerapp.data.sqlite.Schemas;

public class SuspiciousActivitiesServiceImpl extends DatabaseService implements SuspiciousActivitiesService {

	@Override
	public Map<String, Object> save(String actionTaken, String extraNotes, String imagePath, String wp) {
		
		Map<String, Object> result = new HashMap<String, Object>();
	
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
		ContentValues values = new ContentValues();
		values.put(Schemas.SuspiciousActivities.ACTION_TAKEN, actionTaken);
		values.put(Schemas.ElephantPoaching.EXTRA_NOTES, extraNotes);
		values.put(Schemas.ElephantPoaching.IMAGE_PATH, imagePath);
		values.put(Schemas.ElephantPoaching.WP, wp);
		
		try {
			db.insert(Schemas.ELEPHANT_POACHING_TABLE, null, values);
			result.put("status", "success");
		} catch (Exception e) {
			result.put("status", "error");
			result.put("message", e.getMessage());
		}
			
		return result;
	}

}

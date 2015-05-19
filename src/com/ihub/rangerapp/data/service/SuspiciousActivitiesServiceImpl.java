package com.ihub.rangerapp.data.service;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.ihub.rangerapp.RangerApp;
import com.ihub.rangerapp.data.sqlite.Schemas;

public class SuspiciousActivitiesServiceImpl extends DatabaseService implements SuspiciousActivitiesService {

	@Override
	public Map<String, Object> save(Integer id, String actionTaken, String extraNotes, String imagePath, String lat, String lon) {
		
		Map<String, Object> result = new HashMap<String, Object>();
	
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
		ContentValues values = new ContentValues();
		values.put(Schemas.SuspiciousActivities.ACTION_TAKEN, actionTaken);
		values.put(Schemas.SuspiciousActivities.EXTRA_NOTES, extraNotes);
		values.put(Schemas.SuspiciousActivities.IMAGE_PATH, imagePath);
		values.put(Schemas.SuspiciousActivities.LAT, lat);
		values.put(Schemas.SuspiciousActivities.LON, lon);
		
		try {
			
			if(id == -1) {
 				db.insert(Schemas.SUSPICIOUS_ACTIVITIES_TABLE, null, values);
 	 			result.put("status", "success");
 			} else {
 				db.update(Schemas.SUSPICIOUS_ACTIVITIES_TABLE, values, BaseColumns._ID + "=" + id, null);
 				result.put("status", "success");
 			}
			
		} catch (Exception e) {
			result.put("status", "error");
			result.put("message", e.getMessage());
		}
			
		return result;
	}

}

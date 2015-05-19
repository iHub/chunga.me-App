package com.ihub.rangerapp.data.service;

import java.util.HashMap;
import java.util.Map;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.ihub.rangerapp.RangerApp;
import com.ihub.rangerapp.data.sqlite.Schemas;

public class CharcoalServiceImpl extends DatabaseService implements CharcoalService {

	@Override
	public Map<String, Object> saveBagsData(Integer id, Integer noOfBags, String mode,
		String actionTaken, String extraNotes, String imagePath, String lat, String lon) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
 		ContentValues values = new ContentValues();
 		values.put(Schemas.CharcoalBags.NO_OF_BAGS, noOfBags);
 		values.put(Schemas.CharcoalBags.MODE_OF_TRANSPORT, mode);
 		values.put(Schemas.CharcoalBags.ACTION_TAKEN, actionTaken);
 		values.put(Schemas.CharcoalBags.EXTRA_NOTES, extraNotes);
 		values.put(Schemas.CharcoalBags.IMAGE_PATH, imagePath);
 		values.put(Schemas.CharcoalBags.LAT, lat);
 		values.put(Schemas.CharcoalBags.LON, lon);
 		
 		try {
 			
 			if(id == -1) {
 				db.insert(Schemas.CHARCOAL_BAGS_TABLE, null, values);
 	 			result.put("status", "success");
 			} else {
 				db.update(Schemas.CHARCOAL_BAGS_TABLE, values, BaseColumns._ID + "=" + id, null);
 				result.put("status", "success");
 			}
 			
 		} catch (Exception e) {
 			result.put("status", "error");
 			result.put("message", e.getMessage());
 		}
 		
		return result;
	}

	@Override
	public Map<String, Object> saveKilns(Integer id, Integer noOfKilns,
		String freshnessLevels, String treeUsed, String actionTaken,
		String extraNotes, String imagePath, String lat, String lon) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
 		ContentValues values = new ContentValues();
 		values.put(Schemas.CharcoalKilns.NO_OF_KILNS, noOfKilns);
 		values.put(Schemas.CharcoalKilns.FRESHNESS_LEVELS, freshnessLevels);
 		values.put(Schemas.CharcoalKilns.TREE_USED, treeUsed);
 		values.put(Schemas.CharcoalKilns.ACTION_TAKEN, actionTaken);
 		values.put(Schemas.CharcoalKilns.EXTRA_NOTES, extraNotes);
 		values.put(Schemas.CharcoalKilns.IMAGE_PATH, imagePath);
 		values.put(Schemas.CharcoalKilns.LAT, lat);
 		values.put(Schemas.CharcoalKilns.LON, lon);
 		
 		try {
 			
 			if(id == -1) {
 				db.insert(Schemas.CHARCOAL_KILN_TABLE, null, values);
 	 			result.put("status", "success");
 			} else {
 				db.update(Schemas.CHARCOAL_KILN_TABLE, values, BaseColumns._ID + "=" + id, null);
 				result.put("status", "success");
 			}
 			
 		} catch (Exception e) {
 			result.put("status", "error");
 			result.put("message", e.getMessage());
 		}
 		
		return result;
	}
}
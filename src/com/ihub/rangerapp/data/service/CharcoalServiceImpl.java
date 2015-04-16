package com.ihub.rangerapp.data.service;

import java.util.HashMap;
import java.util.Map;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.ihub.rangerapp.RangerApp;
import com.ihub.rangerapp.data.sqlite.Schemas;

public class CharcoalServiceImpl extends DatabaseService implements CharcoalService {

	@Override
	public Map<String, Object> saveBagsData(Integer noOfBags, String mode,
		String actionTaken, String extraNotes, String imagePath, String wp) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
 		ContentValues values = new ContentValues();
 		values.put(Schemas.CharcoalBags.NO_OF_BAGS, noOfBags);
 		values.put(Schemas.CharcoalBags.MODE_OF_TRANSPORT, mode);
 		values.put(Schemas.CharcoalBags.ACTION_TAKEN, actionTaken);
 		values.put(Schemas.CharcoalBags.EXTRA_NOTES, extraNotes);
 		values.put(Schemas.CharcoalBags.IMAGE_PATH, imagePath);
 		values.put(Schemas.CharcoalBags.WP, wp);
 		
 		try {
 			db.insert(Schemas.CHARCOAL_BAGS_TABLE, null, values);
 			result.put("status", "success");
 		} catch (Exception e) {
 			result.put("status", "error");
 			result.put("message", e.getMessage());
 		}
 		
		return result;
	}

	@Override
	public Map<String, Object> saveKilns(Integer noOfKilns,
		String freshnessLevels, String treeUsed, String actionTaken,
		String extraNotes, String imagePath, String wp) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
 		ContentValues values = new ContentValues();
 		values.put(Schemas.CharcoalKilns.NO_OF_KILNS, noOfKilns);
 		values.put(Schemas.CharcoalKilns.FRESHNESS_LEVELS, freshnessLevels);
 		values.put(Schemas.CharcoalKilns.TREE_USED, treeUsed);
 		values.put(Schemas.CharcoalKilns.ACTION_TAKEN, actionTaken);
 		values.put(Schemas.CharcoalKilns.EXTRA_NOTES, extraNotes);
 		values.put(Schemas.CharcoalKilns.IMAGE_PATH, imagePath);
 		values.put(Schemas.CharcoalKilns.WP, wp); 		
 		
 		try {
 			db.insert(Schemas.CHARCOAL_KILN_TABLE, null, values);
 			result.put("status", "success");
 		} catch (Exception e) {
 			result.put("status", "error");
 			result.put("message", e.getMessage());
 		}
 		
		return result;
	}
}
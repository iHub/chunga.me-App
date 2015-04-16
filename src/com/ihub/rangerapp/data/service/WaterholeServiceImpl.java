package com.ihub.rangerapp.data.service;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.ihub.rangerapp.RangerApp;
import com.ihub.rangerapp.data.sqlite.Schemas;

public class WaterholeServiceImpl extends DatabaseService implements WaterholeService {

	@Override
	public Map<String, Object> save(String name, String level, Integer noOfAnimalsSeen, String extraNotes, String imagePath, String wp) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
		ContentValues values = new ContentValues();
		values.put(Schemas.Waterhole.NAME, name);
		values.put(Schemas.Waterhole.LEVEL_OF_WATER, level);
		values.put(Schemas.Waterhole.NUMBER_OF_ANIMALS, noOfAnimalsSeen);
		values.put(Schemas.Waterhole.EXTRA_NOTES, extraNotes);
		values.put(Schemas.Waterhole.IMAGE_PATH, imagePath);
		values.put(Schemas.Waterhole.WP, wp);
		
		try {
			db.insert(Schemas.WATER_HOLES_TABLE, null, values);
			result.put("status", "success");
		} catch (Exception e) {
			result.put("status", "error");
			result.put("message", e.getMessage());
		}
			
		return result;
	}

}

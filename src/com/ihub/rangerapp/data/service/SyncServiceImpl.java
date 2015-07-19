package com.ihub.rangerapp.data.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ihub.rangerapp.RangerApp;
import com.ihub.rangerapp.data.sqlite.DB;
import com.ihub.rangerapp.data.sqlite.Schemas;

public class SyncServiceImpl extends DatabaseService implements SyncService {
	
	@Override
	public Date loadLastSyncDate(Integer rangerID) {
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
		String sql = "SELECT * FROM " + 
		Schemas.EXPORTS_TABLE + " WHERE " + Schemas.RANGER_ID + " = " + rangerID + " ORDER BY " + Schemas.ExportsTable.START_TIME + " DESC" + " LIMIT <skip>, <count>";
		sql = sql.replace("<skip>", 0 + "").replace(" <count>", 1 + "");
				
		Cursor cursor = db.rawQuery(sql, null);
		
		if (cursor.moveToFirst()) {
			
			do {
				Integer startTime = cursor.getInt(2);
				
				Date date = new Date(startTime);
				return date;
				
			} while (cursor.moveToNext());
		}
		
		return null;
	}

	@Override
	public Map<String, Integer> loadCounts(Integer rangerID) {
		
		Map<String, Integer> data = new HashMap<String, Integer>();
		
		String sql = "SELECT count(*) FROM " + 
		Schemas.SHIFTS_TABLE + " WHERE " + Schemas.LAST_SYNC_DATE + " IS NULL OR " + Schemas.REQUIRES_SYNC + " = 1";

		data.put("Shifts", getCount(sql));
		
		sql = "SELECT count(*) FROM " + 
				Schemas.GAME_MEAT_TABLE + " WHERE " + Schemas.LAST_SYNC_DATE + " IS NULL OR " + Schemas.REQUIRES_SYNC + " = 1";
		
		data.put("Game Meat", getCount(sql));
		
		sql = "SELECT count(*) FROM " + 
				Schemas.CHARCOAL_BAGS_TABLE + " WHERE " + Schemas.LAST_SYNC_DATE + " IS NULL OR " + Schemas.REQUIRES_SYNC + " = 1";
		
		data.put("Charcoal Bags Incidents", getCount(sql));
		
		sql = "SELECT count(*) FROM " + 
				Schemas.CHARCOAL_KILN_TABLE + " WHERE " + Schemas.LAST_SYNC_DATE + " IS NULL OR " + Schemas.REQUIRES_SYNC + " = 1";
		
		data.put("Charcoal Kilns Incidents", getCount(sql));
		
		sql = "SELECT count(*) FROM " + 
				Schemas.ELEPHANT_POACHING_TABLE + " WHERE " + Schemas.LAST_SYNC_DATE + " IS NULL OR " + Schemas.REQUIRES_SYNC + " = 1";
		
		data.put("Elephant Poaching Incidents", getCount(sql));
		
		sql = "SELECT count(*) FROM " + 
				Schemas.SUSPICIOUS_ACTIVITIES_TABLE + " WHERE " + Schemas.LAST_SYNC_DATE + " IS NULL OR " + Schemas.REQUIRES_SYNC + " = 1";
		
		data.put("Suspicious Activities", getCount(sql));
		
		sql = "SELECT count(*) FROM " + 
				Schemas.INDIVIDUAL_ANIMAL_SIGHTING_TABLE + " WHERE " + Schemas.LAST_SYNC_DATE + " IS NULL OR " + Schemas.REQUIRES_SYNC + " = 1";
		
		data.put("Individual Animals Sightings", getCount(sql));
		
		sql = "SELECT count(*) FROM " + 
				Schemas.ANIMAL_HERD_SIGHTING_TABLE + " WHERE " + Schemas.LAST_SYNC_DATE + " IS NULL OR " + Schemas.REQUIRES_SYNC + " = 1";
		
		data.put("Herd Sightings", getCount(sql));
		
		sql = "SELECT count(*) FROM " + 
				Schemas.WATER_HOLES_TABLE + " WHERE " + Schemas.LAST_SYNC_DATE + " IS NULL OR " + Schemas.REQUIRES_SYNC + " = 1";
		
		data.put("Waterhole Sightings", getCount(sql));
		
		return data;
	}
	
	protected int getCount(String sql) {

		int count = 0;

		RangerApp application = RangerApp.get();

		SQLiteDatabase db = DB.instance().getWritableDatabase(
				application.getApplicationContext());
		Cursor mcursor = db.rawQuery(sql, null);
		if (mcursor.moveToFirst()) {
			count = mcursor.getInt(0);
			mcursor.close();
		}

		return count;
	}
}
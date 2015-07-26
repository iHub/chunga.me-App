package com.ihub.rangerapp.data.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;
import android.util.Pair;
import com.ihub.rangerapp.RangerApp;
import com.ihub.rangerapp.data.sqlite.DB;
import com.ihub.rangerapp.data.sqlite.Schemas;

public class SyncServiceImpl extends DatabaseService implements SyncService {
	
	public Integer startSync() {
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
		ContentValues values = new ContentValues();
		
		UserService userService = new UserServiceImpl();
		Long userID = userService.getCurrentUserID();
 		
 		values.put(Schemas.RANGER_ID, userID);
 		values.put(Schemas.ExportsTable.START_TIME, new Date().getTime() + "");
 		
 		Long id = db.insert(Schemas.EXPORTS_TABLE, null, values);
		
		return id.intValue();
	}
	
	public void endSync(Integer id) {
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
		ContentValues values = new ContentValues();
		values.put(Schemas.ExportsTable.END_TIME, new Date().getTime() + "");
		db.update(Schemas.EXPORTS_TABLE, values, BaseColumns._ID + "=" + id, null);
	}
	
	@Override
	public Date loadLastSyncDate() {
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
		UserService userService = new UserServiceImpl();
		Long userID = userService.getCurrentUserID();
		
		String sql = "SELECT * FROM " + 
		Schemas.EXPORTS_TABLE + " WHERE " + Schemas.RANGER_ID + " = '" + userID + "' ORDER BY " + Schemas.ExportsTable.START_TIME + " DESC" + " LIMIT <skip>, <count>";
		sql = sql.replace("<skip>", 0 + "").replace(" <count>", 1 + "");
				
		Cursor cursor = db.rawQuery(sql, null);
		
		if (cursor.moveToFirst()) {
			
			do {
				Long startTime = Long.valueOf(cursor.getString(2));
				
				Date date = new Date(startTime);
				return date;
				
			} while (cursor.moveToNext());
		}
		
		return null;
	}

	@Override
	public List<Pair<String, Integer>> loadCounts() {
		
		UserService userService = new UserServiceImpl();
		Long userID = userService.getCurrentUserID();
		
		List<Pair<String, Integer>> data = new ArrayList<Pair<String, Integer>>();
		
		String sql = "SELECT count(*) FROM " + 
		Schemas.SHIFTS_TABLE + " WHERE " + Schemas.LAST_SYNC_DATE + " IS NULL OR " + Schemas.REQUIRES_SYNC + " = 1";

		data.add(new Pair<String, Integer>("Shifts", getCount(sql)));
		
		sql = "SELECT count(*) FROM " + 
				Schemas.GAME_MEAT_TABLE + " WHERE " + Schemas.LAST_SYNC_DATE + " IS NULL OR " + Schemas.REQUIRES_SYNC + " = 1";
		
		data.add(new Pair<String, Integer>("Game Meat", getCount(sql)));
		
		sql = "SELECT count(*) FROM " + 
				Schemas.CHARCOAL_BAGS_TABLE + " WHERE " + Schemas.LAST_SYNC_DATE + " IS NULL OR " + Schemas.REQUIRES_SYNC + " = 1";
		
		data.add(new Pair<String, Integer>("Charcoal Bags Incidents", getCount(sql)));
		
		sql = "SELECT count(*) FROM " + 
				Schemas.CHARCOAL_KILN_TABLE + " WHERE " + Schemas.LAST_SYNC_DATE + " IS NULL OR " + Schemas.REQUIRES_SYNC + " = 1";
		
		data.add(new Pair<String, Integer>("Charcoal Kilns Incidents", getCount(sql)));
		
		sql = "SELECT count(*) FROM " + 
				Schemas.ELEPHANT_POACHING_TABLE + " WHERE " + Schemas.LAST_SYNC_DATE + " IS NULL OR " + Schemas.REQUIRES_SYNC + " = 1";
		
		data.add(new Pair<String, Integer>("Elephant Poaching Incidents", getCount(sql)));
		
		sql = "SELECT count(*) FROM " + 
				Schemas.SUSPICIOUS_ACTIVITIES_TABLE + " WHERE " + Schemas.LAST_SYNC_DATE + " IS NULL OR " + Schemas.REQUIRES_SYNC + " = 1";
		
		data.add(new Pair<String, Integer>("Suspicious Activities", getCount(sql)));
		
		sql = "SELECT count(*) FROM " + 
				Schemas.INDIVIDUAL_ANIMAL_SIGHTING_TABLE + " WHERE " + Schemas.LAST_SYNC_DATE + " IS NULL OR " + Schemas.REQUIRES_SYNC + " = 1";
		
		data.add(new Pair<String, Integer>("Individual Animals Sightings", getCount(sql)));
		
		sql = "SELECT count(*) FROM " + 
				Schemas.ANIMAL_HERD_SIGHTING_TABLE + " WHERE " + Schemas.LAST_SYNC_DATE + " IS NULL OR " + Schemas.REQUIRES_SYNC + " = 1";
		
		data.add(new Pair<String, Integer>("Herd Sightings", getCount(sql)));
		
		sql = "SELECT count(*) FROM " + 
				Schemas.WATER_HOLES_TABLE + " WHERE " + Schemas.LAST_SYNC_DATE + " IS NULL OR " + Schemas.REQUIRES_SYNC + " = 1";
		
		data.add(new Pair<String, Integer>("Waterhole Sightings", getCount(sql)));
		
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

	@Override
	public List<Integer> loadIds(String tableName) {
		
		List<Integer> idsList = new ArrayList<Integer>();
		
		String sql = "SELECT " + BaseColumns._ID + " FROM " + 
				tableName + " WHERE " + Schemas.LAST_SYNC_DATE + " IS NULL OR " + Schemas.REQUIRES_SYNC + " = 1";
		
		RangerApp application = RangerApp.get();
		
		SQLiteDatabase db = DB.instance().getWritableDatabase(
				application.getApplicationContext());

		Cursor cursor = db.rawQuery(sql, null);
		
		if (cursor.moveToFirst()) {
			do {
				
				Integer id = cursor.getInt(0);
				idsList.add(id);
				
			} while (cursor.moveToNext());
		}
		return idsList;
	}

	@Override
	public String updateRecordLastSyncd(int syncID, int id, String tableName) {
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
		ContentValues values = new ContentValues();
		values.put(Schemas.LAST_SYNC_DATE, new Date().getTime());
 		values.put(Schemas.REQUIRES_SYNC, 0);
 		values.put(Schemas.SYNC_ID, syncID);
 		
		db.update(tableName, values, BaseColumns._ID + "=" + id, null);
		
		return "done";
	}
}
package com.ihub.rangerapp.data.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import com.ihub.rangerapp.RangerApp;
import com.ihub.rangerapp.data.sqlite.Schemas;
import com.ihub.rangerapp.data.sqlite.Schemas.Shift;

public class ShiftServiceImpl extends DatabaseService implements ShiftService {
	
	public ShiftServiceImpl() {}
	
	@Override
	public Map<String, Object> startShift(
			String station, 
			String ranch, 
			String leader, 
			String noOfMembers, 
			String route, 
			String mode, 
			String weather, 
			String lat, 
			String lon, 
			String purpose) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(hasOpenShift()) {
			result.put("status", "error");
 			result.put("message", "Please end the current shift before proceeding.");
 			
 			return result;
		}
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
 		ContentValues values = new ContentValues();
 		values.put(Schemas.Shift.STATION, station);
 		values.put(Schemas.Shift.RANCH, ranch);
 		values.put(Schemas.Shift.LEADER, leader);
 		values.put(Schemas.Shift.NO_OF_MEMBERS, Integer.valueOf(noOfMembers));
 		values.put(Schemas.Shift.ROUTE, route);
 		values.put(Schemas.Shift.MODE, mode);
 		values.put(Schemas.Shift.WEATHER, weather);
 		values.put(Schemas.Shift.START_LAT, lat);
 		values.put(Schemas.Shift.START_LON, lon);
 		values.put(Schemas.Shift.PURPOSE, purpose);
 		
 		try {
 			
 			db.insert(Schemas.SHIFTS_TABLE, null, values);
 			result.put("status", "success");
 		} catch (Exception e) {
 			result.put("status", "error");
 			result.put("message", e.getMessage());
 		}
 		
		return result;
	}
	
	@Override
	public Boolean hasOpenShift() {
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
		String sql = "SELECT count(*) FROM " + Schemas.SHIFTS_TABLE + " WHERE " + Schemas.Shift.END_TIME + " IS NULL";
		
		SQLiteStatement s = db.compileStatement( sql );
		
		long count = s.simpleQueryForLong();
		
		return count > 0;
	}
	
	@Override
	public Shift getOpenShift() {
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
		String sql = "SELECT * FROM " + Schemas.SHIFTS_TABLE + " WHERE " + Schemas.Shift.END_TIME + " IS NULL";
		
		SQLiteStatement s = db.compileStatement( sql );
		
		//TODO
		
		return null;
	}

	@Override
	public void endCurrentShift(String lat, String lon) {
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
		Long currentShiftID = getCurrentShiftID();
		
		ContentValues args = new ContentValues();
	    args.put(Schemas.Shift.END_TIME, new Date().getTime());
	    args.put(Schemas.Shift.END_LAT, lat);
	    args.put(Schemas.Shift.END_LON, lon);
	    db.update(Schemas.SHIFTS_TABLE, args, BaseColumns._ID + "=" + currentShiftID, null);
	}

	@Override
	public Long getCurrentShiftID() {
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
		String sql = "SELECT " + BaseColumns._ID + " FROM " + Schemas.SHIFTS_TABLE + " WHERE " + Schemas.Shift.END_TIME + " IS NULL";
		 
		SQLiteStatement s = db.compileStatement( sql );
		
		return s.simpleQueryForLong();
	}	
}
package com.ihub.rangerapp.data.service;

import java.util.HashMap;
import java.util.Map;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.ihub.rangerapp.RangerApp;
import com.ihub.rangerapp.data.sqlite.Schemas;

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
			String startWP, 
			String endWP, 
			String purpose) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
 		ContentValues values = new ContentValues();
 		values.put(Schemas.Shift.STATION, station);
 		values.put(Schemas.Shift.RANCH, ranch);
 		values.put(Schemas.Shift.LEADER, leader);
 		values.put(Schemas.Shift.NO_OF_MEMBERS, Integer.valueOf(noOfMembers));
 		values.put(Schemas.Shift.ROUTE, route);
 		values.put(Schemas.Shift.MODE, mode);
 		values.put(Schemas.Shift.WEATHER, weather);
 		values.put(Schemas.Shift.START_WP, Integer.valueOf(startWP));
 		values.put(Schemas.Shift.END_WP, Integer.valueOf(endWP));
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
}
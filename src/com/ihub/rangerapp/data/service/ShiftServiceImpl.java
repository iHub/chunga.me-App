package com.ihub.rangerapp.data.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.ihub.rangerapp.RangerApp;
import com.ihub.rangerapp.data.sqlite.Schemas;
import com.ihub.rangerapp.data.sqlite.Schemas.Shift;
import com.ihub.rangerapp.util.DateUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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
			String waypoint,
			String purpose) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(hasOpenShift()) {
			result.put("status", "error");
 			result.put("message", "Please end the current shift before proceeding.");
 			
 			return result;
		}
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
		UserService userService = new UserServiceImpl();
		Long userID = userService.getCurrentUserID();
		
 		ContentValues values = new ContentValues();
 		values.put(Schemas.RANGER_ID, userID);
 		values.put(Schemas.Shift.STATION, station);
 		values.put(Schemas.Shift.RANCH, ranch);
 		values.put(Schemas.Shift.LEADER, leader);
 		values.put(Schemas.Shift.NO_OF_MEMBERS, Integer.valueOf(noOfMembers));
 		values.put(Schemas.Shift.ROUTE, route);
 		values.put(Schemas.Shift.MODE, mode);
 		values.put(Schemas.Shift.WEATHER, weather);
 		values.put(Schemas.Shift.WAYPOINT, waypoint);
 		values.put(Schemas.Shift.PURPOSE, purpose);
 		

 		Boolean isValid = true;
 		
 		if(TextUtils.isEmpty(waypoint))
 			isValid = false;
 		
 		
 		values.put(Schemas.REQUIRES_SYNC, isValid ? 1 : 0); //TODO check data changes
		values.put(Schemas.CAN_SYNC, isValid ? 1 : 0);
 		
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
	public void endCurrentShift(String waypoint) {
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
		try {
			Long currentShiftID = getCurrentShiftID();
			
			ContentValues args = new ContentValues();
		    args.put(Schemas.Shift.END_TIME, new Date().getTime());
		    args.put(Schemas.Shift.END_WAYPOINT, waypoint);
		    
	 		args.put(Schemas.REQUIRES_SYNC, 1);
			args.put(Schemas.CAN_SYNC, 1);
		    
		    db.update(Schemas.SHIFTS_TABLE, args, BaseColumns._ID + "=" + currentShiftID, null);
		} catch (Exception e) {}
	}
	
	@Override
	public Long getCurrentShiftID() {
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
		String sql = "SELECT " + BaseColumns._ID + " FROM " + Schemas.SHIFTS_TABLE + " WHERE " + Schemas.Shift.END_TIME + " IS NULL";
		 
		SQLiteStatement s = db.compileStatement( sql );
		
		return s.simpleQueryForLong();
	}
	
	public String getShiftUniqueRecordID(Integer shiftID) {
		
		Cursor cursor = null;
		
		try {
			
        	SQLiteDatabase db = getWritableDatabase(RangerApp.get());
            cursor = db.rawQuery("SELECT * FROM " + Schemas.SHIFTS_TABLE +" WHERE " + BaseColumns._ID +"=?", new String[] {shiftID + ""});
            
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                
                String dateCreated = cursor.getString(11);
                
                try {
        			return RangerApp.getUniqueDeviceID() + "-" + DateUtil.parse(dateCreated).getTime();
        		} catch (Exception e) {
        			e.printStackTrace();
        		}  
            }
        }finally {
            cursor.close();
        }
		
		return RangerApp.getUniqueDeviceID();
	}

	@Override
	public void syncShift(Integer shiftID, AsyncHttpResponseHandler handler) {
		
		String url = UrlUtils.SHIFTS_URL;
		
		Cursor cursor = null;
		
		RequestParams params = new RequestParams();

		try{
        	
        	SQLiteDatabase db = getWritableDatabase(RangerApp.get());
        	
            cursor = db.rawQuery("SELECT * FROM " + Schemas.SHIFTS_TABLE +" WHERE " + BaseColumns._ID +"=?", new String[] {shiftID + ""});

            if(cursor.getCount() > 0) {
            	
                cursor.moveToFirst();
                
                params.put("device_record_id", cursor.getInt(0));
                params.put("station", cursor.getString(1));
                params.put("ranch", cursor.getString(2));
                params.put("leader", cursor.getString(3));
                params.put("no_of_members", cursor.getInt(4));
                params.put("route", cursor.getString(5));
                params.put("mode", cursor.getString(6));
                params.put("weather", cursor.getString(7));
                params.put("purpose", cursor.getString(8));
                
                String startTime = cursor.getString(9);
                
                try {
        			params.put("start_time", DateUtil.parse(startTime).getTime() + "");
        		} catch (Exception e) {}
                
                
                String endTime = cursor.getString(10);
                
                try {
        			params.put("end_time", DateUtil.parse(endTime).getTime() + "");
        		} catch (Exception e) {}
                
                String dateCreated = cursor.getString(11);
                
                try {
        			params.put("record_date_created", DateUtil.parse(dateCreated).getTime() + "");
        			params.put("unique_record_id", RangerApp.getUniqueDeviceID() + "-" + DateUtil.parse(dateCreated).getTime());
        		} catch (Exception e) {
        			e.printStackTrace();
        		}  
                
                params.put("start_waypoint", cursor.getString(12));
                params.put("end_waypoint", cursor.getString(13));
                params.put("ranger_id", cursor.getString(14));
            }
        }finally {
            cursor.close();
        }
		
		AsyncHttpClient client = new AsyncHttpClient();
		
		client.post(url, params, handler);
	}	
}
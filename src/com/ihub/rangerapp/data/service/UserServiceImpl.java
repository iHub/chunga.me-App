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
public class UserServiceImpl extends DatabaseService implements UserService {
	
	@Override
	public Map<String, Object> login(String rangerID) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
		ContentValues values = new ContentValues();
		//values.put(Schemas.User.RANGER_NAME, rangerName);
		values.put(Schemas.User.RANGER_ID, rangerID);
		
		try {
			
			db.insert(Schemas.USERS_TABLE, null, values);
			result.put("status", "success");
			
		} catch (Exception e) {
			result.put("status", "error");
			result.put("message", e.getMessage());
		}
			
		return result;
	}
	
	public Long getCurrentUserID() {
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
		String sql = "SELECT " + BaseColumns._ID + " FROM " + Schemas.USERS_TABLE + " WHERE " + Schemas.Shift.END_TIME + " IS NULL";
		 
		SQLiteStatement s = db.compileStatement( sql );
		
		return s.simpleQueryForLong();
	}	
	
	@Override
	public Map<String, Object> logout() {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
		Long currentUserID = getCurrentUserID();
		
		ContentValues args = new ContentValues();
	    args.put(Schemas.User.END_TIME, new Date().getTime());
	    db.update(Schemas.USERS_TABLE, args, BaseColumns._ID + "=" + currentUserID, null);
	    
	    result.put("status", "success");
	    return result;
	}
}
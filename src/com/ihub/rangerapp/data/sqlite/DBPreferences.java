package com.ihub.rangerapp.data.sqlite;
 
import com.ihub.rangerapp.RangerApp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBPreferences {
	 
 	static DBPreferences instance;
 	
 	SQLiteDatabase database;
 	 	
 	public static String VERSION = "version";
 	
 	//public static String RANGER_NAME = "ranger_name";
 	
 	public static String RANGER_ID = "ranger_id";
 	 	
 	public static DBPreferences instance() {
 		if (instance == null)
 			instance = new DBPreferences();
 		return instance;
 	}
 	
 	public DBPreferences() {
 	}
 	
 	public String getPreferenceValue(String key) {

 		String value = null;

 		String selectQuery = "SELECT  " + DatabaseHandler.VALUE + " FROM "
 				+ DatabaseHandler.TABLE_PREFERENCES + " WHERE "
 				+ DatabaseHandler.KEY + " ='" + key + "';";

 		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
 		Cursor cursor = db.rawQuery(selectQuery, null);

 		if (cursor.moveToFirst()) {
 			value = cursor.getString(0);
 		}
 		try {
 			cursor.close();
 		} catch (Exception e) {
 		}

 		return value;
 	}
 	
 	public void setPreferenceValue(String key, String value) {

 		String prefValue = getPreferenceValue(key);

 		SQLiteDatabase db = getWritableDatabase(RangerApp.get());

 		ContentValues values = new ContentValues();
 		values.put(DatabaseHandler.KEY, key);
 		values.put(DatabaseHandler.VALUE, value);

 		if (prefValue == null) {
 			db.insert(DatabaseHandler.TABLE_PREFERENCES, null, values);
 		} else {
 			db.update(DatabaseHandler.TABLE_PREFERENCES, values,
 					DatabaseHandler.KEY + " = ?", new String[] { key });
 		}
 	}
 	
 	public void clear() {
 		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
 		db.delete(DatabaseHandler.TABLE_PREFERENCES, null, null);
 	}
 	
 	public SQLiteDatabase getWritableDatabase(Context context) {
 		if (database == null) {
 			DatabaseHandler helper = new DatabaseHandler(context);
 			database = helper.getWritableDatabase();
 		}
 		return database;
 	}
 }
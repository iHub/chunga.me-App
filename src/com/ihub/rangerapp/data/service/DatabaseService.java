package com.ihub.rangerapp.data.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.ihub.rangerapp.data.sqlite.DatabaseHandler;

public class DatabaseService {
	
	SQLiteDatabase database;
	
	public DatabaseService() {}
	
	public SQLiteDatabase getWritableDatabase(Context context) {
 		if (database == null) {
 			DatabaseHandler helper = new DatabaseHandler(context);
 			database = helper.getWritableDatabase();
 		}
 		return database;
 	}
}
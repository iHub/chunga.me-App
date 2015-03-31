package com.ihub.rangerapp.data.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DB {
	
	static DB instance;
	
	SQLiteDatabase database;
	
	public static DB instance() {
		if (instance == null)
			instance = new DB();
		return instance;
	}

	public DB() {
	}
	
	public Cursor query(Context context, String table, String orderBy, String limit) {
		SQLiteDatabase db = getWritableDatabase(context);
		return db.query(table, null, null, null, null, null, orderBy, limit);
	}

	public long insert(Context context, String table,
			ContentValues values) {
		SQLiteDatabase db = getWritableDatabase(context);
		long id = db.insert(table, null, values);
		return id;
	}

	public SQLiteDatabase getWritableDatabase(Context context) {
		if (database == null) {
			DatabaseHandler helper = new DatabaseHandler(context);
			database = helper.getWritableDatabase();
		}
		return database;
	}

	public void clear(Context context) {
		if (database != null)
			database.close();
		
		database = null;
		
		context.deleteDatabase(DatabaseHandler.DATABASE_NAME);
	}
	
	public void clearTable(Context context, String tableName) {
		SQLiteDatabase db = getWritableDatabase(context);
		db.delete(tableName, null, null);
	}
}
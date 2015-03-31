package com.ihub.rangerapp.data.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class Schemas {
	
	public static String COUNTRIES_TABLE = "Countries";
	
	public Schemas() {}
	
	public static void initialize(SQLiteDatabase db) {
		createCountriesTable(db);
	}
	
	public static void createCountriesTable(SQLiteDatabase db) {
		String sql = "create table " + COUNTRIES_TABLE + "(" + 
				BaseColumns._ID + " integer primary key autoincrement," +
				Countries.COUNTRY_ID + " text," + 
				Countries.NAME + " text," + 
				Countries.ISO_CODE + " text," +
				Countries.CURRENCY + " text" + 
		");";
		
		db.execSQL(sql);
	}
	
	public static class Countries {
		public static String COUNTRY_ID = "country_id";
		public static String NAME = "name";
		public static String ISO_CODE = "iso_code";
		public static String CURRENCY = "currency";
	}
	
	public static void onUpgrage(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
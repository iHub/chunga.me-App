package com.ihub.rangerapp.data.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class Schemas {
	
	public static String COUNTRIES_TABLE = "Countries";
	public static String SHIFTS_TABLE = "shifts";
	
	public Schemas() {}
	
	public static void initialize(SQLiteDatabase db) {
		createCountriesTable(db);
		createShiftTable(db);
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
	
	public static void createShiftTable(SQLiteDatabase db) {
		
		String sql = "create table " + SHIFTS_TABLE + "(" + 
				BaseColumns._ID + " integer primary key autoincrement," +
				Shift.STATION + " text," + 
				Shift.RANCH + " text," + 
				Shift.LEADER + " text," +
				Shift.NO_OF_MEMBERS + " INTEGER," +
				Shift.ROUTE + " text," +
				Shift.MODE + " text," +
				Shift.WEATHER + " text," +
				Shift.START_WP + " INTEGER," +
				Shift.END_WP + " INTEGER," +
				Shift.PURPOSE + " text," +
				Shift.START_TIME + " DATE DEFAULT (datetime('now','localtime'))," +
				Shift.END_TIME + " DATE," +
				Shift.DATE_CREATED + " DATE DEFAULT (datetime('now','localtime'))" +
		");";
		
		db.execSQL(sql);
	}
	
	public static class Countries {
		public static String COUNTRY_ID = "country_id";
		public static String NAME = "name";
		public static String ISO_CODE = "iso_code";
		public static String CURRENCY = "currency";
	}
	
	public static class Shift {
		public static String STATION = "station";
		public static String RANCH = "ranch";
		public static String LEADER = "leader";
		public static String NO_OF_MEMBERS = "no_of_members";
		public static String ROUTE = "route";
		public static String MODE = "mode";
		public static String WEATHER = "weather";
		public static String START_WP = "start_wp";
		public static String END_WP = "end_wp";
		public static String PURPOSE = "purpose";
		public static String START_TIME = "start_time";
		public static String END_TIME = "end_time";
		public static String DATE_CREATED = "date_created";
	}
	
	public static void onUpgrage(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
package com.ihub.rangerapp.data.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;

	public static final String DATABASE_NAME = "RangerApp";

	public static final String TABLE_PREFERENCES = "Preferences";

	public static final String ID = "id";
	public static final String KEY = "key";
	public static final String VALUE = "value";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		Schemas.initialize(db);

		String CREATE_PREFERENCES_TABLE = "CREATE TABLE " + TABLE_PREFERENCES
				+ "(" + ID + " INTEGER PRIMARY KEY," + KEY + " TEXT," + VALUE
				+ " TEXT" + ")";
		
		db.execSQL(CREATE_PREFERENCES_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREFERENCES);
		Schemas.onUpgrage(db, oldVersion, newVersion);
		onCreate(db);
	}
}
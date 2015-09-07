package com.ihub.rangerapp.data.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class Schemas {
	
	public static String USERS_TABLE = "tbl_users";
	public static String SHIFTS_TABLE = "tbl_shifts";
	public static String GAME_MEAT_TABLE = "tbl_game_meat";
	public static String CHARCOAL_KILN_TABLE = "tbl_charcoal_kilns";
	public static String CHARCOAL_LOGS_TABLE = "tbl_charcoal_logs";
	public static String CHARCOAL_BAGS_TABLE = "tbl_charcoal_bags";
	public static String ELEPHANT_POACHING_TABLE = "tbl_elephant_poaching";
	public static String SUSPICIOUS_ACTIVITIES_TABLE = "tbl_suspicious_activities";
	public static String INDIVIDUAL_ANIMAL_SIGHTING_TABLE = "tbl_individual_animal_sighting";
	public static String ANIMAL_HERD_SIGHTING_TABLE = "tbl_animal_herd_sighting";
	public static String WATER_HOLES_TABLE = "tbl_waterhole";
	public static String EXPORTS_TABLE = "tbl_exports";
	public static String EXPORT_ITEMS_TABLE = "tbl_export_items";
	public static String SHIFT_ID = "shift_id";
	public static String CAN_SYNC = "can_sync";
	public static String RANGER_ID = "ranger_id";
	public static String SYNC_ID = "last_sync_id";
	public static String LAST_SYNC_DATE = "last_sync_date";
	public static String REQUIRES_SYNC = "requires_sync";
	public static String RANCH = "ranch";
	
	public static void initialize(SQLiteDatabase db) {
		
		createUsersTable(db);
		createShiftTable(db);
		createGameMeatTable(db);
		createCharcoalKilnsTable(db);
		createCharcoalLogsTable(db);
		createCharcoalBagsTable(db);
		createElephantPoachingTable(db);
		createSuspiciousActivitiesTable(db);
		createWaterholesTable(db);
		createIndividualAnimalSightingsTable(db);
		createAnimalHerdSightingsTable(db);
		createExportsTable(db);
		createExportItemsTable(db);
	}
	
	public static class ExportItemsTable {
		public static String NAME = "name";
		public static String EXPORT_ID = "export_id";
		public static String RECORD_ID = "record_id";
		public static String DATE_CREATED = "date_created";
	}
	
	private static void createExportItemsTable(SQLiteDatabase db) {
		
		String sql = "create table " + EXPORT_ITEMS_TABLE + "(" +
				BaseColumns._ID + " integer primary key autoincrement," +
				ExportItemsTable.EXPORT_ID + " INTEGER," +
				ExportItemsTable.RECORD_ID + " INTEGER," +
				ExportItemsTable.DATE_CREATED + " DATE DEFAULT (datetime('now','localtime'))" +
			");";
		
			db.execSQL(sql);
	}
	
	public static class ExportsTable {
		public static String START_TIME = "start_time";
		public static String END_TIME = "end_time";
	}
	
	private static void createExportsTable(SQLiteDatabase db) {
		
		String sql = "create table " + EXPORTS_TABLE + "(" +
				BaseColumns._ID + " integer primary key autoincrement," +
				RANGER_ID + " TEXT," +
				ExportsTable.START_TIME + " TEXT," +
				ExportsTable.END_TIME + " TEXT" +
			");";
			
			db.execSQL(sql);
	}
	
	private static void createAnimalHerdSightingsTable(SQLiteDatabase db) {
		String sql = "create table " + ANIMAL_HERD_SIGHTING_TABLE + "(" +
				BaseColumns._ID + " integer primary key autoincrement," +
				AnimalHerdSighting.NAME + " text," +
				AnimalHerdSighting.TYPE + " text," +
				AnimalHerdSighting.NUMBER_OF_ANIMALS + " integer," +
				AnimalHerdSighting.ADULTS_COUNT + " INTEGER," +
				AnimalHerdSighting.SEMI_ADULTS_COUNT + " INTEGER," +
				AnimalHerdSighting.JUVENILE_COUNT + " INTEGER," +
				AnimalHerdSighting.DISTANCE_SEEN + " integer," +
				AnimalHerdSighting.EXTRA_NOTES + " text," +
				AnimalHerdSighting.WAYPOINT + " text," +
				AnimalHerdSighting.IMAGE_PATH + " text," +
				AnimalHerdSighting.DATE_CREATED + " DATE DEFAULT (datetime('now','localtime'))," +
				SHIFT_ID + " INTEGER," +
				SYNC_ID + " INTEGER," +
				LAST_SYNC_DATE + " INTEGER," +
				REQUIRES_SYNC + " INTEGER DEFAULT 1," +
				CAN_SYNC + " INTEGER DEFAULT 1," +
				RANCH + " text," + 
				AnimalHerdSighting.MALE_COUNT + " INTEGER," +
				AnimalHerdSighting.FEMALE_COUNT + " INTEGER" +
			");";
		
			db.execSQL(sql);
	}
	
	private static void createIndividualAnimalSightingsTable(SQLiteDatabase db) {
		String sql = "create table " + INDIVIDUAL_ANIMAL_SIGHTING_TABLE + "(" +
			BaseColumns._ID + " integer primary key autoincrement," +
			IndividualAnimalSighting.ANIMAL + " text," +
			IndividualAnimalSighting.GENDER + " text," +
			IndividualAnimalSighting.AGE + " text," +
			IndividualAnimalSighting.DISTANCE_SEEN + " integer," +
			IndividualAnimalSighting.EXTRA_NOTES + " text," +
			IndividualAnimalSighting.WAYPOINT + " text," +
			IndividualAnimalSighting.IMAGE_PATH + " text," +
			IndividualAnimalSighting.DATE_CREATED + " DATE DEFAULT (datetime('now','localtime'))," +
			SHIFT_ID + " INTEGER," +
			SYNC_ID + " INTEGER," +
			LAST_SYNC_DATE + " INTEGER," +
			REQUIRES_SYNC + " INTEGER DEFAULT 1," +
			CAN_SYNC + " INTEGER DEFAULT 1," +
			RANCH + " text" + 
		");";
		db.execSQL(sql);
	}
	
	public static class IndividualAnimalSighting {
		public static String ANIMAL = "animal";
		public static String GENDER = "gender";
		public static String AGE = "age";
		public static String DISTANCE_SEEN = "distance_seen";
		public static String EXTRA_NOTES = "extra_notes";
		public static String WAYPOINT = "waypoint";
		public static String IMAGE_PATH = "image_path";
		public static String DATE_CREATED = "date_created";
	}
	
	public static class AnimalHerdSighting {
		public static String NAME = "name";
		public static String TYPE = "type";
		public static String NUMBER_OF_ANIMALS = "no_of_animals";
		public static String ADULTS_COUNT = "adults_count";
		public static String SEMI_ADULTS_COUNT = "semi_adults_count";
		public static String JUVENILE_COUNT = "juvenile_count";
		public static String MALE_COUNT = "male_count";
		public static String FEMALE_COUNT = "female_count";
		public static String DISTANCE_SEEN = "distance_seen";
		public static String EXTRA_NOTES = "extra_notes";
		public static String WAYPOINT = "waypoint";
		public static String IMAGE_PATH = "image_path";
		public static String DATE_CREATED = "date_created";
	}
	
	private static void createWaterholesTable(SQLiteDatabase db) {
		String sql = "create table " + WATER_HOLES_TABLE + "(" +
				BaseColumns._ID + " integer primary key autoincrement," +
				Waterhole.NAME + " text," +
				Waterhole.LEVEL_OF_WATER + " text," +
				Waterhole.NUMBER_OF_ANIMALS + " integer," +
				Waterhole.EXTRA_NOTES + " text," +
				Waterhole.WAYPOINT + " text," +
				Waterhole.IMAGE_PATH + " text," +
				Waterhole.DATE_CREATED + " DATE DEFAULT (datetime('now','localtime'))," +
				SHIFT_ID + " INTEGER," +
				SYNC_ID + " INTEGER," +
				LAST_SYNC_DATE + " INTEGER," +
				REQUIRES_SYNC + " INTEGER DEFAULT 1," +
				CAN_SYNC + " INTEGER DEFAULT 1," +
				RANCH + " text" + 
			");";
		
			db.execSQL(sql);
	}
	
	private static void createSuspiciousActivitiesTable(SQLiteDatabase db) {
		String sql = "create table " + SUSPICIOUS_ACTIVITIES_TABLE + "(" +
				BaseColumns._ID + " integer primary key autoincrement," +
				SuspiciousActivities.ACTION_TAKEN + " text," +
				SuspiciousActivities.EXTRA_NOTES + " text," +
				SuspiciousActivities.WAYPOINT + " text," +
				SuspiciousActivities.IMAGE_PATH + " text," +
				SuspiciousActivities.DATE_CREATED + " DATE DEFAULT (datetime('now','localtime'))," +
				SHIFT_ID + " INTEGER," +
				SYNC_ID + " INTEGER," +
				LAST_SYNC_DATE + " INTEGER," +
				REQUIRES_SYNC + " INTEGER DEFAULT 1," +
				CAN_SYNC + " INTEGER DEFAULT 1," +
				RANCH + " text," + 
				SuspiciousActivities.ACTIVITY + " text" + 
			");";
		
			db.execSQL(sql);
	}
	
	public static void createElephantPoachingTable(SQLiteDatabase db) {
		String sql = "create table " + ELEPHANT_POACHING_TABLE + "(" +
				BaseColumns._ID + " integer primary key autoincrement," +
				ElephantPoaching.NO_OF_ANIMALS + " INTEGER, " +
				ElephantPoaching.TOOLS_USED + " text, " +
				ElephantPoaching.MALE_COUNT + " INTEGER," +
				ElephantPoaching.FEMALE_COUNT + " INTEGER," +
				ElephantPoaching.ADULTS_COUNT + " INTEGER," +
				ElephantPoaching.SEMI_ADULTS_COUNT + " INTEGER," +
				ElephantPoaching.JUVENILE_COUNT + " INTEGER," +
				ElephantPoaching.IVORY_PRESENCE + " text," +
				ElephantPoaching.ACTION_TAKEN + " text," +
				ElephantPoaching.EXTRA_NOTES + " text," +
				ElephantPoaching.WAYPOINT + " text," +
				ElephantPoaching.IMAGE_PATH + " text," +
				ElephantPoaching.DATE_CREATED + " DATE DEFAULT (datetime('now','localtime'))," +
				SHIFT_ID + " INTEGER," +
				SYNC_ID + " INTEGER," +
				LAST_SYNC_DATE + " INTEGER," +
				REQUIRES_SYNC + " INTEGER DEFAULT 1," +
				CAN_SYNC + " INTEGER DEFAULT 1," +
				RANCH + " text," + 
				ElephantPoaching.TUSKS_FOUND + " INTEGER," +
				ElephantPoaching.LEFT_TUSK_WEIGHT + " INTEGER," +
				ElephantPoaching.RIGHT_TUSK_WEIGHT + " INTEGER" +
			");";
		
			db.execSQL(sql);
	}
	
	private static void createCharcoalBagsTable(SQLiteDatabase db) {
		String sql = "create table " + CHARCOAL_BAGS_TABLE + "(" +
				BaseColumns._ID + " integer primary key autoincrement," +
				CharcoalBags.NO_OF_BAGS + " INTEGER, " +
				CharcoalBags.MODE_OF_TRANSPORT + " text, " +
				CharcoalBags.ACTION_TAKEN + " text," +
				CharcoalBags.EXTRA_NOTES + " text," +
				CharcoalBags.WAYPOINT + " text," +
				CharcoalBags.IMAGE_PATH + " text," +
				CharcoalBags.DATE_CREATED + " DATE DEFAULT (datetime('now','localtime'))," +
				SHIFT_ID + " INTEGER," +
				SYNC_ID + " INTEGER," +
				LAST_SYNC_DATE + " INTEGER," +
				REQUIRES_SYNC + " INTEGER DEFAULT 1," +
				CAN_SYNC + " INTEGER DEFAULT 1," +
				RANCH + " text" + 
			");";
		
			db.execSQL(sql);
	}
	
	private static void createGameMeatTable(SQLiteDatabase db) {
		String sql = "create table " + GAME_MEAT_TABLE + "(" +
			BaseColumns._ID + " integer primary key autoincrement," +
			GameMeat.ANIMAL + " text," +
			GameMeat.NO_OF_ANIMALS + " INTEGER, " +
			GameMeat.ACTION_TAKEN + " text," +
			GameMeat.EXTRA_NOTES + " text," +
			GameMeat.WAYPOINT + " text," +
			GameMeat.IMAGE_PATH + " text," +
			GameMeat.DATE_CREATED + " DATE DEFAULT (datetime('now','localtime'))," +
			SHIFT_ID + " INTEGER," +
			SYNC_ID + " INTEGER," +
			LAST_SYNC_DATE + " INTEGER," +
			REQUIRES_SYNC + " INTEGER DEFAULT 1," +
			CAN_SYNC + " INTEGER DEFAULT 1," +
			RANCH + " text" + 
		");";
		
		db.execSQL(sql);
	}
	
	private static void createCharcoalKilnsTable(SQLiteDatabase db) {
		String sql = "create table " + CHARCOAL_KILN_TABLE + "(" +
			BaseColumns._ID + " integer primary key autoincrement," +
			CharcoalKilns.NO_OF_KILNS + " INTEGER, " +
			CharcoalKilns.FRESHNESS_LEVELS + " text," +
			CharcoalKilns.TREE_USED + " text," +
			CharcoalKilns.ACTION_TAKEN + " text," +
			CharcoalKilns.EXTRA_NOTES + " text," +
			CharcoalKilns.WAYPOINT + " text," +
			CharcoalKilns.IMAGE_PATH + " text," +
			CharcoalKilns.DATE_CREATED + " DATE DEFAULT (datetime('now','localtime'))," +
			SHIFT_ID + " INTEGER," +
			SYNC_ID + " INTEGER," +
			LAST_SYNC_DATE + " INTEGER," +
			REQUIRES_SYNC + " INTEGER DEFAULT 1," +
			CAN_SYNC + " INTEGER DEFAULT 1," +
			RANCH + " text" + 
		");";
		db.execSQL(sql);
	}
	
	private static void createCharcoalLogsTable(SQLiteDatabase db) {
		String sql = "create table " + CHARCOAL_KILN_TABLE + "(" +
			BaseColumns._ID + " integer primary key autoincrement," +
			CharcoalLogs.NO_OF_LOGS + " INTEGER, " +
			CharcoalLogs.TREE + " text," +
			CharcoalLogs.ACTION_TAKEN + " text," +
			CharcoalLogs.EXTRA_NOTES + " text," +
			CharcoalLogs.WAYPOINT + " text," +
			CharcoalLogs.IMAGE_PATH + " text," +
			CharcoalLogs.DATE_CREATED + " DATE DEFAULT (datetime('now','localtime'))," +
			SHIFT_ID + " INTEGER," +
			SYNC_ID + " INTEGER," +
			LAST_SYNC_DATE + " INTEGER," +
			REQUIRES_SYNC + " INTEGER DEFAULT 1," +
			CAN_SYNC + " INTEGER DEFAULT 1," +
			RANCH + " text" + 
		");";
		db.execSQL(sql);
	}
	
	public static void createUsersTable(SQLiteDatabase db) {
		
		String sql = "create table " + USERS_TABLE + "(" + 
			BaseColumns._ID + " integer primary key autoincrement," +
			User.RANGER_ID + " text," +
			SYNC_ID + " INTEGER," +
			LAST_SYNC_DATE + " INTEGER," +
			REQUIRES_SYNC + " INTEGER DEFAULT 1," +
			User.START_TIME + " DATE DEFAULT (datetime('now','localtime'))," +
			User.END_TIME + " DATE" +
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
				Shift.PURPOSE + " text," +
				Shift.START_TIME + " DATE DEFAULT (datetime('now','localtime'))," +
				Shift.END_TIME + " DATE," +
				Shift.DATE_CREATED + " DATE DEFAULT (datetime('now','localtime'))," +
				Shift.WAYPOINT + " TEXT," +
				Shift.END_WAYPOINT + " TEXT," +
				RANGER_ID + " TEXT," +
				SYNC_ID + " INTEGER," +
				LAST_SYNC_DATE + " INTEGER," +
				REQUIRES_SYNC + " INTEGER DEFAULT 1," +
				CAN_SYNC + " INTEGER DEFAULT 1" +
		");";
		
		db.execSQL(sql);
	}
	
	public static class Waterhole {
		public static String NAME = "name";
		public static String LEVEL_OF_WATER = "level_of_water";
		public static String NUMBER_OF_ANIMALS = "no_of_animals";
		public static String EXTRA_NOTES = "extra_notes";
		public static String WAYPOINT = "waypoint";
		public static String IMAGE_PATH = "image_path";
		public static String DATE_CREATED = "date_created";
	}
	
	public static class GameMeat {
		public static String ANIMAL = "animal";
		public static String NO_OF_ANIMALS = "no_of_animals";
		public static String ACTION_TAKEN = "action_taken";
		public static String EXTRA_NOTES = "extra_notes";
		public static String WAYPOINT = "waypoint";
		public static String IMAGE_PATH = "image_path";
		public static String DATE_CREATED = "date_created";
	}
	
	public static class CharcoalKilns {
		public static String NO_OF_KILNS = "no_of_kilns";
		public static String FRESHNESS_LEVELS = "freshness_levels";
		public static String TREE_USED = "tree_used";
		public static String ACTION_TAKEN = "action_taken";
		public static String EXTRA_NOTES = "extra_notes";
		public static String WAYPOINT = "waypoint";
		public static String IMAGE_PATH = "image_path";
		public static String DATE_CREATED = "date_created";
	}
	
	public static class CharcoalLogs {
		public static String NO_OF_LOGS = "no_of_logs";
		public static String TREE = "tree";
		public static String ACTION_TAKEN = "action_taken";
		public static String EXTRA_NOTES = "extra_notes";
		public static String WAYPOINT = "waypoint";
		public static String IMAGE_PATH = "image_path";
		public static String DATE_CREATED = "date_created";
	}
	
	public static class CharcoalBags {
		public static String NO_OF_BAGS = "no_of_bags";
		public static String MODE_OF_TRANSPORT = "mode_of_transport";
		public static String ACTION_TAKEN = "action_taken";
		public static String EXTRA_NOTES = "extra_notes";
		public static String WAYPOINT = "waypoint";
		public static String IMAGE_PATH = "image_path";
		public static String DATE_CREATED = "date_created";
	}
	
	public static class ElephantPoaching {
		public static String TOOLS_USED = "tools_used";
		public static String NO_OF_ANIMALS = "no_of_animals";
		public static String MALE_COUNT = "male_count";
		public static String FEMALE_COUNT = "female_count";
		public static String ADULTS_COUNT = "adults_count";
		public static String SEMI_ADULTS_COUNT = "semi_adults_count";
		public static String JUVENILE_COUNT = "juvenile_count";
		public static String IVORY_PRESENCE = "ivory_presence";
		public static String ACTION_TAKEN = "action_taken";
		public static String EXTRA_NOTES = "extra_notes";
		public static String WAYPOINT = "waypoint";
		public static String IMAGE_PATH = "image_path";
		public static String DATE_CREATED = "date_created";
		public static String TUSKS_FOUND = "tusks_found";
		public static String LEFT_TUSK_WEIGHT = "left_tusk_weight";
		public static String RIGHT_TUSK_WEIGHT = "right_tusk_weight";
	}
	
	public static class SuspiciousActivities {
		public static String ACTIVITY = "activity";
		public static String ACTION_TAKEN = "action_taken";
		public static String EXTRA_NOTES = "extra_notes";
		public static String WAYPOINT = "waypoint";
		public static String IMAGE_PATH = "image_path";
		public static String DATE_CREATED = "date_created";
	}
	
	public static class User {
		//public static String RANGER_NAME = "ranger_name";
		public static String RANGER_ID = "ranger_id";
		public static String START_TIME = "start_time";
		public static String END_TIME = "end_time";
	}
	
	public static class Shift {
		public static String STATION = "station";
		public static String RANCH = "ranch";
		public static String LEADER = "leader";
		public static String NO_OF_MEMBERS = "no_of_members";
		public static String ROUTE = "route";
		public static String MODE = "mode";
		public static String WEATHER = "weather";
		public static String PURPOSE = "purpose";
		public static String START_TIME = "start_time";
		public static String END_TIME = "end_time";
		public static String DATE_CREATED = "date_created";
		public static String WAYPOINT = "waypoint";
		public static String END_WAYPOINT = "end_waypoint";
	}
	
	public static void onUpgrage(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + SHIFTS_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + GAME_MEAT_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + CHARCOAL_KILN_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + CHARCOAL_LOGS_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + CHARCOAL_BAGS_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + ELEPHANT_POACHING_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + SUSPICIOUS_ACTIVITIES_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + INDIVIDUAL_ANIMAL_SIGHTING_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + ANIMAL_HERD_SIGHTING_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + WATER_HOLES_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + EXPORTS_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + EXPORT_ITEMS_TABLE);
	}
}
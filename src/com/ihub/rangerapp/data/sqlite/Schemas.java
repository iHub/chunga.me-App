package com.ihub.rangerapp.data.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class Schemas {
	
	public static String SHIFTS_TABLE = "tbl_shifts";
	public static String GAME_MEAT_TABLE = "tbl_game_meat";
	public static String CHARCOAL_KILN_TABLE = "tbl_charcoal_kilns";
	public static String CHARCOAL_BAGS_TABLE = "tbl_charcoal_bags";
	public static String ELEPHANT_POACHING_TABLE = "tbl_elephant_poaching";
	public static String SUSPICIOUS_ACTIVITIES_TABLE = "tbl_suspicious_activities";
	public static String INDIVIDUAL_ANIMAL_SIGHTING_TABLE = "tbl_individual_animal_sighting";
	public static String ANIMAL_HERD_SIGHTING_TABLE = "tbl_animal_herd_sighting";
	public static String WATER_HOLES_TABLE = "tbl_waterhole";
	
	public static void initialize(SQLiteDatabase db) {
		createShiftTable(db);
		createGameMeatTable(db);
		createCharcoalKilnsTable(db);
		createCharcoalBagsTable(db);
		createElephantPoachingTable(db);
		createSuspiciousActivitiesTable(db);
		createWaterholesTable(db);
		createIndividualAnimalSightingsTable(db);
		createAnimalHerdSightingsTable(db);
	}
	
	private static void createAnimalHerdSightingsTable(SQLiteDatabase db) {
		String sql = "create table " + ANIMAL_HERD_SIGHTING_TABLE + "(" +
				BaseColumns._ID + " integer primary key autoincrement," +
				AnimalHerdSighting.NAME + " text," +
				AnimalHerdSighting.TYPE + " text," +
				AnimalHerdSighting.NUMBER_OF_ANIMALS + " integer," +
				AnimalHerdSighting.AGE + " text," +
				AnimalHerdSighting.DISTANCE_SEEN + " integer," +
				AnimalHerdSighting.EXTRA_NOTES + " text," +
				AnimalHerdSighting.WP + " text," +
				AnimalHerdSighting.IMAGE_PATH + " text," +
				AnimalHerdSighting.DATE_CREATED + " DATE DEFAULT (datetime('now','localtime'))" +
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
			IndividualAnimalSighting.WP + " text," +
			IndividualAnimalSighting.IMAGE_PATH + " text," +
			IndividualAnimalSighting.DATE_CREATED + " DATE DEFAULT (datetime('now','localtime'))" +
		");";
		
		db.execSQL(sql);
	}
	
	public static class IndividualAnimalSighting {
		public static String ANIMAL = "animal";
		public static String GENDER = "gender";
		public static String AGE = "age";
		public static String DISTANCE_SEEN = "distance_seen";
		public static String EXTRA_NOTES = "extra_notes";
		public static String WP = "wp";
		public static String IMAGE_PATH = "image_path";
		public static String DATE_CREATED = "date_created";
	}
	
	public static class AnimalHerdSighting {
		public static String NAME = "name";
		public static String TYPE = "type";
		public static String NUMBER_OF_ANIMALS = "no_of_animals";
		public static String AGE = "age";
		public static String DISTANCE_SEEN = "distance_seen";
		public static String EXTRA_NOTES = "extra_notes";
		public static String WP = "wp";
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
				Waterhole.WP + " text," +
				Waterhole.IMAGE_PATH + " text," +
				Waterhole.DATE_CREATED + " DATE DEFAULT (datetime('now','localtime'))" +
			");";
			
			db.execSQL(sql);
	}

	private static void createSuspiciousActivitiesTable(SQLiteDatabase db) {
		String sql = "create table " + SUSPICIOUS_ACTIVITIES_TABLE + "(" +
				BaseColumns._ID + " integer primary key autoincrement," +
				SuspiciousActivities.ACTION_TAKEN + " text," +
				SuspiciousActivities.EXTRA_NOTES + " text," +
				SuspiciousActivities.WP + " text," +
				SuspiciousActivities.IMAGE_PATH + " text," +
				SuspiciousActivities.DATE_CREATED + " DATE DEFAULT (datetime('now','localtime'))" +
			");";
			
			db.execSQL(sql);
	}
	
	public static void createElephantPoachingTable(SQLiteDatabase db) {
		String sql = "create table " + ELEPHANT_POACHING_TABLE + "(" +
				BaseColumns._ID + " integer primary key autoincrement," +
				ElephantPoaching.NO_OF_ANIMALS + " INTEGER, " +
				ElephantPoaching.TOOLS_USED + " text, " +
				ElephantPoaching.AGE + " text," +
				ElephantPoaching.SEX + " text," +
				ElephantPoaching.IVORY_PRESENCE + " text," +
				ElephantPoaching.ACTION_TAKEN + " text," +
				ElephantPoaching.EXTRA_NOTES + " text," +
				ElephantPoaching.WP + " text," +
				ElephantPoaching.IMAGE_PATH + " text," +
				ElephantPoaching.DATE_CREATED + " DATE DEFAULT (datetime('now','localtime'))" +
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
				CharcoalBags.WP + " text," +
				CharcoalBags.IMAGE_PATH + " text," +
				CharcoalBags.DATE_CREATED + " DATE DEFAULT (datetime('now','localtime'))" +
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
			GameMeat.WP + " text," +
			GameMeat.IMAGE_PATH + " text," +
			GameMeat.DATE_CREATED + " DATE DEFAULT (datetime('now','localtime'))" +
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
			CharcoalKilns.WP + " text," +
			CharcoalKilns.IMAGE_PATH + " text," +
			CharcoalKilns.DATE_CREATED + " DATE DEFAULT (datetime('now','localtime'))" +
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
				Shift.START_LAT + " TEXT," +
				Shift.START_LON + " TEXT," +
				Shift.END_LAT + " TEXT," +
				Shift.END_LON + " TEXT" +
		");";
		
		db.execSQL(sql);
	}
	
	public static class Waterhole {
		public static String NAME = "name";
		public static String LEVEL_OF_WATER = "level_of_water";
		public static String NUMBER_OF_ANIMALS = "no_of_animals";
		public static String EXTRA_NOTES = "extra_notes";
		public static String WP = "wp";
		public static String IMAGE_PATH = "image_path";
		public static String DATE_CREATED = "date_created";
	}
	
	public static class GameMeat {
		public static String ANIMAL = "animal";
		public static String NO_OF_ANIMALS = "no_of_animals";
		public static String ACTION_TAKEN = "action_taken";
		public static String EXTRA_NOTES = "extra_notes";
		public static String WP = "wp";
		public static String IMAGE_PATH = "image_path";
		public static String DATE_CREATED = "date_created";
	}
	
	public static class CharcoalKilns {
		public static String NO_OF_KILNS = "no_of_kilns";
		public static String FRESHNESS_LEVELS = "freshness_levels";
		public static String TREE_USED = "tree_used";
		public static String ACTION_TAKEN = "action_taken";
		public static String EXTRA_NOTES = "extra_notes";
		public static String WP = "wp";
		public static String IMAGE_PATH = "image_path";
		public static String DATE_CREATED = "date_created";
	}
	
	public static class CharcoalBags {
		public static String NO_OF_BAGS = "no_of_bags";
		public static String MODE_OF_TRANSPORT = "mode_of_transport";
		public static String ACTION_TAKEN = "action_taken";
		public static String EXTRA_NOTES = "extra_notes";
		public static String WP = "wp";
		public static String IMAGE_PATH = "image_path";
		public static String DATE_CREATED = "date_created";
	}
	
	public static class ElephantPoaching {
		public static String TOOLS_USED = "tools_used";
		public static String NO_OF_ANIMALS = "no_of_animals";
		public static String AGE = "age";
		public static String SEX = "sex";
		public static String IVORY_PRESENCE = "ivory_presence";
		public static String ACTION_TAKEN = "action_taken";
		public static String EXTRA_NOTES = "extra_notes";
		public static String WP = "wp";
		public static String IMAGE_PATH = "image_path";
		public static String DATE_CREATED = "date_created";
	}
	
	public static class SuspiciousActivities {
		public static String ACTION_TAKEN = "action_taken";
		public static String EXTRA_NOTES = "extra_notes";
		public static String WP = "wp";
		public static String IMAGE_PATH = "image_path";
		public static String DATE_CREATED = "date_created";
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
		public static String START_LAT = "start_latitude";
		public static String START_LON = "start_longitude";
		public static String END_LAT = "end_latitude";
		public static String END_LON = "end_longitude";
	}
	
	public static void onUpgrage(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
package com.ihub.rangerapp.loader;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ihub.rangerapp.RangerApp;
import com.ihub.rangerapp.data.sqlite.DB;
import com.ihub.rangerapp.data.sqlite.Schemas;
import com.ihub.rangerapp.model.HerdModel;
import com.ihub.rangerapp.model.Model;
import com.ihub.rangerapp.util.PagingLoadConfig;

public class HerdsLoader extends DataLoader {
	
	@Override
	public String tableName() {
		return Schemas.ANIMAL_HERD_SIGHTING_TABLE;
	}
	
	public List<Model> loadFromLocalDb(PagingLoadConfig config) {
		
		List<Model> data = new ArrayList<Model>();
		
		RangerApp application = RangerApp.get();
	
		SQLiteDatabase db = DB.instance().getWritableDatabase(
				application.getApplicationContext());
	
		String sql =  "SELECT * FROM " + tableName() + " LIMIT <skip>, <count>";
		sql = sql.replace("<skip>", config.offset() + "").replace(" <count>", config.limit() + "");
		
		Cursor cursor = db.rawQuery(sql, null);
		
		if (cursor.moveToFirst()) {
			do {
				
				Integer id = cursor.getInt(0);
				String name = cursor.getString(1);
				String type = cursor.getString(2);
				Integer noOfAnimals = cursor.getInt(3);
				
				Integer adultsCount = cursor.getInt(4);
				Integer semiAdultsCount = cursor.getInt(5);
				Integer juvenileCount = cursor.getInt(6);
				
				Integer distanceSeen = cursor.getInt(7);
				String extraNotes = cursor.getString(8);
				String waypoint = cursor.getString(9);
				String imagePath = cursor.getString(10);
				String dateCreated = cursor.getString(11);
				String ranch = cursor.getString(17);
				
				Integer maleCount = cursor.getInt(18);
				Integer femaleCount = cursor.getInt(19);
												
				HerdModel  model = new HerdModel();
				model.setId(id);
				model.setName(name);
				model.setType(type);
				model.setNoOfAnimals(noOfAnimals);
				model.setAdultsCount(adultsCount);
				model.setSemiAdultsCount(semiAdultsCount);
				model.setJuvenileCount(juvenileCount);
				model.setDistanceSeen(distanceSeen);
				model.setExtraNotes(extraNotes);
				model.setWaypoint(waypoint);
				model.setImagePath(imagePath);
				model.setDateCreated(dateCreated);
				model.setRanch(ranch);
				model.setMaleCount(maleCount);
				model.setFemaleCount(femaleCount);
				
				data.add(model);
				
			} while (cursor.moveToNext());
	    }
				
		return data;
	}
}
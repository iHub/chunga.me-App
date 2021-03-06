package com.ihub.rangerapp.loader;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ihub.rangerapp.RangerApp;
import com.ihub.rangerapp.data.sqlite.DB;
import com.ihub.rangerapp.data.sqlite.Schemas;
import com.ihub.rangerapp.model.IndividualAnimalModel;
import com.ihub.rangerapp.model.Model;
import com.ihub.rangerapp.util.PagingLoadConfig;

public class IndividualAnimalsLoader extends DataLoader {
	
	@Override
	public String tableName() {
		return Schemas.INDIVIDUAL_ANIMAL_SIGHTING_TABLE;
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
				String animal = cursor.getString(1);
				String gender = cursor.getString(2);
				String age = cursor.getString(3);
				Integer distanceSeen = cursor.getInt(4);
				
				String extraNotes = cursor.getString(5);
				String waypoint = cursor.getString(6);
				String imagePath = cursor.getString(7);
				String dateCreated = cursor.getString(8);
				String ranch = cursor.getString(14);
												
				IndividualAnimalModel  model = new IndividualAnimalModel();
				model.setId(id);
				model.setAnimal(animal);
				model.setGender(gender);
				model.setAge(age);
				model.setDistanceSeen(distanceSeen);
				model.setExtraNotes(extraNotes);
				model.setWaypoint(waypoint);
				model.setImagePath(imagePath);
				model.setDateCreated(dateCreated);
				model.setRanch(ranch);
				
				data.add(model);
				
			} while (cursor.moveToNext());
	    }
				
		return data;
	}
}
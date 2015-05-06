package com.ihub.rangerapp.loader;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.ihub.rangerapp.RangerApp;
import com.ihub.rangerapp.data.sqlite.DB;
import com.ihub.rangerapp.data.sqlite.Schemas;
import com.ihub.rangerapp.data.sqlite.Schemas.AnimalHerdSighting;
import com.ihub.rangerapp.model.HerdModel;
import com.ihub.rangerapp.model.IndividualAnimalModel;
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
				String age = cursor.getString(4);
				Integer distanceSeen = cursor.getInt(5);
				
				String extraNotes = cursor.getString(6);
				String wp = cursor.getString(7);
				String imagePath = cursor.getString(8);
				String dateCreated = cursor.getString(9);
												
				HerdModel  model = new HerdModel();
				model.setId(id);
				model.setName(name);
				model.setType(type);
				model.setNoOfAnimals(noOfAnimals);
				model.setAge(age);
				model.setDistanceSeen(distanceSeen);
				model.setExtraNotes(extraNotes);
				model.setWp(wp);
				model.setImagePath(imagePath);
				model.setDateCreated(dateCreated);
				
				data.add(model);
				
			} while (cursor.moveToNext());
	    }
				
		return data;
	}
}
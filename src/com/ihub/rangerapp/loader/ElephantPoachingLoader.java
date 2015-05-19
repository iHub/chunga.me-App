package com.ihub.rangerapp.loader;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ihub.rangerapp.RangerApp;
import com.ihub.rangerapp.data.sqlite.DB;
import com.ihub.rangerapp.data.sqlite.Schemas;
import com.ihub.rangerapp.model.ElephantPoachingModel;
import com.ihub.rangerapp.model.Model;
import com.ihub.rangerapp.util.PagingLoadConfig;

public class ElephantPoachingLoader extends DataLoader {
	
	@Override
	public String tableName() {
		return Schemas.ELEPHANT_POACHING_TABLE;
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
				Integer noOfAnimals = cursor.getInt(1);
				String toolsUsed = cursor.getString(2);
				String age = cursor.getString(3);
				String sex = cursor.getString(4);
				String ivoryPresence = cursor.getString(5);
				
				String actionTaken = cursor.getString(6);
				String extraNotes = cursor.getString(7);
				String latitude = cursor.getString(8);
				String longitude = cursor.getString(9);
				String imagePath = cursor.getString(10);
				String dateCreated = cursor.getString(11);
												
				ElephantPoachingModel  model = new ElephantPoachingModel();
				model.setId(id);
				model.setNoOfAnimals(noOfAnimals);
				model.setToolsUsed(toolsUsed);
				model.setAge(age);
				model.setSex(sex);
				model.setIvoryPresence(ivoryPresence);
				model.setActionTaken(actionTaken);
				
				model.setExtraNotes(extraNotes);
				model.setLatitude(latitude);
				model.setLongitude(longitude);
				model.setImagePath(imagePath);
				model.setDateCreated(dateCreated);
				
				data.add(model);
				
			} while (cursor.moveToNext());
	    }
				
		return data;
	}
}
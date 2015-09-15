package com.ihub.rangerapp.loader;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ihub.rangerapp.RangerApp;
import com.ihub.rangerapp.data.sqlite.DB;
import com.ihub.rangerapp.data.sqlite.Schemas;
import com.ihub.rangerapp.model.Model;
import com.ihub.rangerapp.model.WaterholeModel;
import com.ihub.rangerapp.util.PagingLoadConfig;

public class WaterholesLoader extends DataLoader {
	
	@Override
	public String tableName() {
		return Schemas.WATER_HOLES_TABLE;
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
				String levelOfWater = cursor.getString(2);
				String extraNotes = cursor.getString(4);
				String imagePath = cursor.getString(6);
				String dateCreated = cursor.getString(7);
				String ranch = cursor.getString(13);
				
				WaterholeModel  model = new WaterholeModel();
				model.setId(id);
				model.setName(name);
				model.setLevelOfWater(levelOfWater);
				model.setExtraNotes(extraNotes);
				model.setImagePath(imagePath);
				model.setDateCreated(dateCreated);
				model.setId(id);
				model.setRanch(ranch);
				
				data.add(model);
				
			} while (cursor.moveToNext());
	    }
				
		return data;
	}
}
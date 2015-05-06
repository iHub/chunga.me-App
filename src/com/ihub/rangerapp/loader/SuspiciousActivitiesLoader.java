package com.ihub.rangerapp.loader;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ihub.rangerapp.RangerApp;
import com.ihub.rangerapp.data.sqlite.DB;
import com.ihub.rangerapp.data.sqlite.Schemas;
import com.ihub.rangerapp.model.Model;
import com.ihub.rangerapp.model.SuspiciousActivityModel;
import com.ihub.rangerapp.util.PagingLoadConfig;

public class SuspiciousActivitiesLoader extends DataLoader {
	
	@Override
	public String tableName() {
		return Schemas.SUSPICIOUS_ACTIVITIES_TABLE;
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
				String actionTaken = cursor.getString(1);
				String extraNotes = cursor.getString(2);
				String wp = cursor.getString(3);
				String imagePath = cursor.getString(4);
				String dateCreated = cursor.getString(5);
												
				SuspiciousActivityModel  model = new SuspiciousActivityModel();
				model.setId(id);
				model.setActionTaken(actionTaken);
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
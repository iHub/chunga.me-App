package com.ihub.rangerapp.loader;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.ihub.rangerapp.RangerApp;
import com.ihub.rangerapp.data.sqlite.DB;
import com.ihub.rangerapp.data.sqlite.Schemas;
import com.ihub.rangerapp.data.sqlite.Schemas.CharcoalKilns;
import com.ihub.rangerapp.model.CharcoalKilnModel;
import com.ihub.rangerapp.model.ElephantPoachingModel;
import com.ihub.rangerapp.model.Model;
import com.ihub.rangerapp.util.PagingLoadConfig;

public class CharcoalKilnsLoader extends DataLoader {
	
	@Override
	public String tableName() {
		return Schemas.CHARCOAL_KILN_TABLE;
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
				Integer noOfKilns = cursor.getInt(1);
				String freshnessLevels = cursor.getString(2);
				String treeUsed = cursor.getString(3);
				
				String actionTaken = cursor.getString(4);
				String extraNotes = cursor.getString(5);
				String waypoint = cursor.getString(6);
				String imagePath = cursor.getString(7);
				String dateCreated = cursor.getString(8);
				String ranch = cursor.getString(14);
												
				CharcoalKilnModel  model = new CharcoalKilnModel();
				model.setId(id);
				model.setNoOfKilns(noOfKilns);
				model.setFreshnessLevels(freshnessLevels);
				model.setTreeUsed(treeUsed);

				model.setActionTaken(actionTaken);
				
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
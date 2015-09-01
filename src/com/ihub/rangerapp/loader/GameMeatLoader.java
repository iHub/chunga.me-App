package com.ihub.rangerapp.loader;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ihub.rangerapp.RangerApp;
import com.ihub.rangerapp.data.sqlite.DB;
import com.ihub.rangerapp.data.sqlite.Schemas;
import com.ihub.rangerapp.model.GameMeatModel;
import com.ihub.rangerapp.model.Model;
import com.ihub.rangerapp.util.PagingLoadConfig;

public class GameMeatLoader extends DataLoader {

	@Override
	public String tableName() {
		return Schemas.GAME_MEAT_TABLE;
	}

	public List<Model> loadFromLocalDb(PagingLoadConfig config) {
		
		List<Model> data = new ArrayList<Model>();
		
		RangerApp application = RangerApp.get();

		SQLiteDatabase db = DB.instance().getWritableDatabase(
				application.getApplicationContext());

		String sql =  "SELECT * FROM " + tableName() + " order by _id DESC LIMIT <skip>, <count>";
		sql = sql.replace("<skip>", config.offset() + "").replace(" <count>", config.limit() + "");
		
		Log.v(GameMeatLoader.class.getSimpleName(), sql);
		Cursor cursor = db.rawQuery(sql, null);
		
		if (cursor.moveToFirst()) {
			do {
				
				Integer id = cursor.getInt(0);
				String animal = cursor.getString(1);
				Integer noOfAnimals = cursor.getInt(2);
				String actionTaken = cursor.getString(3);
				String extraNotes = cursor.getString(4);
				String waypoint = cursor.getString(5);
				String imagePath = cursor.getString(6);
				String dateCreated = cursor.getString(7);
								
				GameMeatModel model = new GameMeatModel();
				model.setId(id);
				model.setAnimal(animal);
				model.setNoOfAnimals(noOfAnimals);
				model.setExtraNotes(extraNotes);
				model.setActionTaken(actionTaken);
				model.setWaypoint(waypoint);
				model.setImagePath(imagePath);
				model.setDateCreated(dateCreated);
				
				data.add(model);
				
			} while (cursor.moveToNext());
	    }
				
		return data;
	}
}
package com.ihub.rangerapp.data.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.ihub.rangerapp.RangerApp;
import com.ihub.rangerapp.data.sqlite.Schemas;
import com.ihub.rangerapp.util.DateUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class GameMeatServiceImpl extends DatabaseService implements GameMeatService {
	
	@Override
	public Map<String, Object> save(Integer id, String animal, Integer noOfAnimals, String actionTaken, String extraNotes, String imagePath, String lat, String lon) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
		ShiftService service = new ShiftServiceImpl();
		Long shiftID = service.getCurrentShiftID();
		
 		ContentValues values = new ContentValues();
 		if(id == -1)
 			values.put(Schemas.SHIFT_ID, shiftID);
 		
 		values.put(Schemas.GameMeat.ANIMAL, animal);
 		values.put(Schemas.GameMeat.NO_OF_ANIMALS, noOfAnimals);
 		values.put(Schemas.GameMeat.ACTION_TAKEN, actionTaken);
 		values.put(Schemas.GameMeat.EXTRA_NOTES, extraNotes);
 		values.put(Schemas.GameMeat.LAT, lat);
 		values.put(Schemas.GameMeat.LON, lon);
 		values.put(Schemas.GameMeat.IMAGE_PATH, imagePath);
 		
 		Boolean isValid = true;
 		
 		if(TextUtils.isEmpty(animal))
 			isValid = false;
 		
 		if(TextUtils.isEmpty(lat))
 			isValid = false;
 		
 		if(TextUtils.isEmpty(lon))
 			isValid = false;
 		
 		if(TextUtils.isEmpty(actionTaken))
 			isValid = false;
 		
 		values.put(Schemas.REQUIRES_SYNC, isValid ? 1 : 0); //TODO check data changes
		values.put(Schemas.CAN_SYNC, isValid ? 1 : 0);
 		
 		try {
 			
 			if(id == -1) {
 				db.insert(Schemas.GAME_MEAT_TABLE, null, values);
 	 			result.put("status", "success");
 			} else {
 				db.update(Schemas.GAME_MEAT_TABLE, values, BaseColumns._ID + "=" + id, null);
 				result.put("status", "success");
 			}
 			
 		} catch (Exception e) {
 			result.put("status", "error");
 			result.put("message", e.getMessage());
 		}
 		
		return result;
	}
	
	@Override
	public Map<String, Object> edit(Integer id, String animal, Integer noOfAnimals, String actionTaken, String extraNotes, String imagePath, String lat, String lon) {
		
		return null;
	}

	@Override
	public void sync(Integer id, AsyncHttpResponseHandler handler) {
		
		String url = UrlUtils.GAME_MEAT_URL;
		
		Cursor cursor = null;
		
		RequestParams params = new RequestParams();

		try{
        	
        	SQLiteDatabase db = getWritableDatabase(RangerApp.get());
            cursor = db.rawQuery("SELECT * FROM " + Schemas.GAME_MEAT_TABLE +" WHERE " + BaseColumns._ID +"=?", new String[] {id + ""});
            
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();

                
                params.put("device_record_id", cursor.getInt(0));
				String animal = cursor.getString(1);
				Integer noOfAnimals = cursor.getInt(2);
				String actionTaken = cursor.getString(3);
				String extraNotes = cursor.getString(4);
				String latitude = cursor.getString(5);
				String longitude = cursor.getString(6);
				String imagePath = cursor.getString(7);
				String dateCreated = cursor.getString(8);
				Integer shiftID = cursor.getInt(9);
				
				params.put("lat", latitude);
				params.put("lon", longitude);
				params.put("animal", animal);
				params.put("no_of_animals", noOfAnimals);
				params.put("action_taken", actionTaken);
				params.put("extra_notes", extraNotes);
				
				try {
					File myFile = new File(imagePath);
				    params.put("image", myFile);
				    
				} catch(Exception e) {}
				
				                
				ShiftService service = new ShiftServiceImpl();
				params.put("shift_unique_record_id", service.getShiftUniqueRecordID(shiftID));
				
				try {
        			params.put("record_date_created", DateUtil.parse(dateCreated).getTime() + "");
        			params.put("unique_record_id", RangerApp.getUniqueDeviceID() + "-" + DateUtil.parse(dateCreated).getTime());
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
            }
        }finally {
            cursor.close();
        }
		
		AsyncHttpClient client = new AsyncHttpClient();
		
		client.post(url, params, handler);
	}
}
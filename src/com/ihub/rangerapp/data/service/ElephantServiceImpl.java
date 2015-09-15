package com.ihub.rangerapp.data.service;

import java.io.File;
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

public class ElephantServiceImpl extends DatabaseService implements ElephantService {
	
	@Override
	public Map<String, Object> save(
			Integer id,
			String toolUsed,
			Integer noOfAnimals,
			Integer maleCount,
			Integer femaleCount,
			Integer adultsCount,
			Integer semiAdultsCount,
			Integer juvenileCount,
			String ivoryPresence,
			String actionTaken,
			String extraNotes,
			String imagePath,
			String waypoint,
			String ranch) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
		ShiftService service = new ShiftServiceImpl();
		Long shiftID = service.getCurrentShiftID();
		
 		ContentValues values = new ContentValues();
 		if(id == -1)
 			values.put(Schemas.SHIFT_ID, shiftID);
 		
 		values.put(Schemas.ElephantPoaching.TOOLS_USED, toolUsed);
 		values.put(Schemas.ElephantPoaching.NO_OF_ANIMALS, noOfAnimals);
 		values.put(Schemas.ElephantPoaching.MALE_COUNT, maleCount);
 		values.put(Schemas.ElephantPoaching.FEMALE_COUNT, femaleCount);
 		
 		values.put(Schemas.ElephantPoaching.ADULTS_COUNT, adultsCount);
 		values.put(Schemas.ElephantPoaching.SEMI_ADULTS_COUNT, semiAdultsCount);
 		values.put(Schemas.ElephantPoaching.JUVENILE_COUNT, juvenileCount);
 		
 		values.put(Schemas.ElephantPoaching.IVORY_PRESENCE, ivoryPresence);
 		values.put(Schemas.ElephantPoaching.ACTION_TAKEN, actionTaken);
 		values.put(Schemas.ElephantPoaching.EXTRA_NOTES, extraNotes);
 		values.put(Schemas.ElephantPoaching.IMAGE_PATH, imagePath);
 		values.put(Schemas.ElephantPoaching.WAYPOINT, waypoint);
 		values.put(Schemas.RANCH, ranch);
 		
 		Boolean isValid = true;
 		
 		if(TextUtils.isEmpty(toolUsed))
 			isValid = false;
 		
 		if(noOfAnimals == null)
 			isValid = false;
 		
 		if(noOfAnimals != null && noOfAnimals <=0)
 			isValid = false;
 		
 		if(TextUtils.isEmpty(waypoint))
 			isValid = false;
 		
 		values.put(Schemas.REQUIRES_SYNC, isValid ? 1 : 0); //TODO check data changes
		values.put(Schemas.CAN_SYNC, isValid ? 1 : 0);
 		
 		try {
 			
 			if(id == -1) {
 				db.insert(Schemas.ELEPHANT_POACHING_TABLE, null, values);
 	 			result.put("status", "success");
 			} else {
 				db.update(Schemas.ELEPHANT_POACHING_TABLE, values, BaseColumns._ID + "=" + id, null);
 				result.put("status", "success");
 			}
 			
 		} catch (Exception e) {
 			result.put("status", "error");
 			result.put("message", e.getMessage());
 		}
 		
		return result;
	}
	
	@Override
	public void sync(Integer id, AsyncHttpResponseHandler handler) {

		String url = UrlUtils.ELEPHANT_POACHING_URL;
		
		Cursor cursor = null;
		
		RequestParams params = new RequestParams();

		try{
        	
        	SQLiteDatabase db = getWritableDatabase(RangerApp.get());
            cursor = db.rawQuery("SELECT * FROM " + Schemas.ELEPHANT_POACHING_TABLE +" WHERE " + BaseColumns._ID +"=?", new String[] {id + ""});
            
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();

                
                params.put("device_record_id", cursor.getInt(0));
                Integer noOfAnimals = cursor.getInt(1);
				String toolsUsed = cursor.getString(2);
				Integer maleCount = cursor.getInt(3);
				Integer femaleCount = cursor.getInt(4);
				Integer adultsCount = cursor.getInt(5);
				Integer semiAdultsCount = cursor.getInt(6);
				Integer juvenileCount = cursor.getInt(7);
				String ivoryPresence = cursor.getString(8);
				
				String actionTaken = cursor.getString(9);
				String extraNotes = cursor.getString(10);
				String waypoint = cursor.getString(11);
				String imagePath = cursor.getString(12);
				String dateCreated = cursor.getString(13);
				Integer shiftID = cursor.getInt(14);
				String ranch = cursor.getString(19);
				
				params.put("no_of_animals", noOfAnimals);
				params.put("tools_used", toolsUsed);
				params.put("adults_count", adultsCount);
				params.put("semi_adults_count", semiAdultsCount);
				params.put("juvenile_count", juvenileCount);
				params.put("male_count", maleCount);
				params.put("female_count", femaleCount);
				params.put("ivory_presence", ivoryPresence);
				
				params.put("waypoint", waypoint);
				params.put("action_taken", actionTaken);
				params.put("extra_notes", extraNotes);
				params.put("ranch", ranch);
				
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
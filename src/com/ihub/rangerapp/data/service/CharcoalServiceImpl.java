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

public class CharcoalServiceImpl extends DatabaseService implements CharcoalService {

	@Override
	public Map<String, Object> saveBagsData(Integer id, Integer noOfBags, String mode,
		String actionTaken, String extraNotes, String imagePath, String waypoint, String ranch) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
		ShiftService service = new ShiftServiceImpl();
		Long shiftID = service.getCurrentShiftID();
		
 		ContentValues values = new ContentValues();
 		
 		if(id == -1)
 			values.put(Schemas.SHIFT_ID, shiftID);
 		
 		values.put(Schemas.CharcoalBags.NO_OF_BAGS, noOfBags);
 		values.put(Schemas.CharcoalBags.MODE_OF_TRANSPORT, mode);
 		values.put(Schemas.CharcoalBags.ACTION_TAKEN, actionTaken);
 		values.put(Schemas.CharcoalBags.EXTRA_NOTES, extraNotes);
 		values.put(Schemas.CharcoalBags.IMAGE_PATH, imagePath);
 		values.put(Schemas.CharcoalBags.WAYPOINT, waypoint);
 		values.put(Schemas.RANCH, ranch);
 		
 		Boolean isValid = true;
 		
 		if(noOfBags == null)
 			isValid = false;
 		
 		if(noOfBags != null && noOfBags <= 0)
 			isValid = false;
 		
 		if(TextUtils.isEmpty(waypoint))
 			isValid = false; 		
 		
 		values.put(Schemas.REQUIRES_SYNC, isValid ? 1 : 0); //TODO check data changes
		values.put(Schemas.CAN_SYNC, isValid ? 1 : 0);
 		
 		try {
 			
 			if(id == -1) {
 				db.insert(Schemas.CHARCOAL_BAGS_TABLE, null, values);
 	 			result.put("status", "success");
 			} else {
 				db.update(Schemas.CHARCOAL_BAGS_TABLE, values, BaseColumns._ID + "=" + id, null);
 				result.put("status", "success");
 			}
 			
 		} catch (Exception e) {
 			result.put("status", "error");
 			result.put("message", e.getMessage());
 		}
 		
		return result;
	}

	@Override
	public Map<String, Object> saveKilns(Integer id, Integer noOfKilns,
		String freshnessLevels, String treeUsed, String actionTaken,
		String extraNotes, String imagePath, String waypoint, String ranch) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		SQLiteDatabase db = getWritableDatabase(RangerApp.get());
		
		ShiftService service = new ShiftServiceImpl();
		Long shiftID = service.getCurrentShiftID();
		
 		ContentValues values = new ContentValues();
 		if(id == -1)
 			values.put(Schemas.SHIFT_ID, shiftID);
 		
 		values.put(Schemas.CharcoalKilns.NO_OF_KILNS, noOfKilns);
 		values.put(Schemas.CharcoalKilns.FRESHNESS_LEVELS, freshnessLevels);
 		values.put(Schemas.CharcoalKilns.TREE_USED, treeUsed);
 		values.put(Schemas.CharcoalKilns.ACTION_TAKEN, actionTaken);
 		values.put(Schemas.CharcoalKilns.EXTRA_NOTES, extraNotes);
 		values.put(Schemas.CharcoalKilns.IMAGE_PATH, imagePath);
 		values.put(Schemas.CharcoalKilns.WAYPOINT, waypoint);
 		values.put(Schemas.RANCH, ranch);
 		
 		Boolean isValid = true;
 		
 		if(noOfKilns == null)
 			isValid = false;
 		
 		if(noOfKilns != null && noOfKilns <= 0)
 			isValid = false;
 		
 		if(TextUtils.isEmpty(waypoint))
 			isValid = false;
 		
 		values.put(Schemas.REQUIRES_SYNC, isValid ? 1 : 0); //TODO check data changes
		values.put(Schemas.CAN_SYNC, isValid ? 1 : 0);
 		
 		try {
 			
 			if(id == -1) {
 				db.insert(Schemas.CHARCOAL_KILN_TABLE, null, values);
 	 			result.put("status", "success");
 			} else {
 				db.update(Schemas.CHARCOAL_KILN_TABLE, values, BaseColumns._ID + "=" + id, null);
 				result.put("status", "success");
 			}
 			
 		} catch (Exception e) {
 			result.put("status", "error");
 			result.put("message", e.getMessage());
 		}
 		
		return result;
	}

	@Override
	public void syncBags(Integer id, AsyncHttpResponseHandler handler) {
		
		String url = UrlUtils.CHARCOAL_BAGS_URL;
		
		Cursor cursor = null;
		
		RequestParams params = new RequestParams();
		
		try{
        	
        	SQLiteDatabase db = getWritableDatabase(RangerApp.get());
            cursor = db.rawQuery("SELECT * FROM " + Schemas.CHARCOAL_BAGS_TABLE +" WHERE " + BaseColumns._ID +"=?", new String[] {id + ""});
            
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                
                params.put("device_record_id", cursor.getInt(0));
               
                Integer noOfBags = cursor.getInt(1);
				String modeOfTransport = cursor.getString(2);
				
				String actionTaken = cursor.getString(3);
				String extraNotes = cursor.getString(4);
				String waypoint = cursor.getString(5);
				String imagePath = cursor.getString(6);
				String dateCreated = cursor.getString(7);
				Integer shiftID = cursor.getInt(8);
				String ranch = cursor.getString(13);
				
				params.put("mode_of_transport", modeOfTransport);
				params.put("no_of_bags", noOfBags + "");
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

	@Override
	public void syncKilns(Integer id, AsyncHttpResponseHandler handler) {

		String url = UrlUtils.CHARCOAL_KILNS_URL;
		
		Cursor cursor = null;
		
		RequestParams params = new RequestParams();

		try{
        	
        	SQLiteDatabase db = getWritableDatabase(RangerApp.get());
            cursor = db.rawQuery("SELECT * FROM " + Schemas.CHARCOAL_KILN_TABLE +" WHERE " + BaseColumns._ID +"=?", new String[] {id + ""});
            
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();

                
                params.put("device_record_id", cursor.getInt(0));
                Integer noOfKilns = cursor.getInt(1);
				String freshnessLevels = cursor.getString(2);
				String treeUsed = cursor.getString(3);
				
				String actionTaken = cursor.getString(4);
				String extraNotes = cursor.getString(5);
				String waypoint = cursor.getString(6);
				String imagePath = cursor.getString(7);
				String dateCreated = cursor.getString(8);
				Integer shiftID = cursor.getInt(9);
				String ranch = cursor.getString(14);
				
				
				params.put("no_of_kilns", noOfKilns + "");
				params.put("freshness_levels", freshnessLevels);
				params.put("tree_used", treeUsed);
				
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
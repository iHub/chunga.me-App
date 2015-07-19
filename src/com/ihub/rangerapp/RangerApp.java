package com.ihub.rangerapp;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.ihub.rangerapp.loader.CharcoalBagsLoader;
import com.ihub.rangerapp.loader.CharcoalKilnsLoader;
import com.ihub.rangerapp.loader.DataLoader;
import com.ihub.rangerapp.loader.ElephantPoachingLoader;
import com.ihub.rangerapp.loader.GameMeatLoader;
import com.ihub.rangerapp.loader.HerdsLoader;
import com.ihub.rangerapp.loader.IndividualAnimalsLoader;
import com.ihub.rangerapp.loader.SuspiciousActivitiesLoader;
import com.ihub.rangerapp.loader.WaterholesLoader;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;

public class RangerApp extends Application {
	
	static RangerApp instance;
	
	Map<String, DataLoader> dataLoaders = new HashMap<String, DataLoader>();
	
	String password;
	
	public RangerApp(){}
	
	public static RangerApp get() {
		return instance;
	}
	
	@Override
	public void onCreate() {
		instance = this;
		initLoaders();
	}
	
	public static String getUniqueDeviceID() {
		
		final TelephonyManager tm = (TelephonyManager) RangerApp.get().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);

	    final String tmDevice, tmSerial, androidId;
	    tmDevice = "" + tm.getDeviceId();
	    tmSerial = "" + tm.getSimSerialNumber();
	    androidId = "" + android.provider.Settings.Secure.getString(RangerApp.get().getApplicationContext().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

	    UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
	    return deviceUuid.toString();
	}
	
	private void initLoaders() {
		dataLoaders.put(GameMeatLoader.class.getSimpleName(), new GameMeatLoader());
		
		dataLoaders.put(CharcoalBagsLoader.class.getSimpleName(), new CharcoalBagsLoader());
		dataLoaders.put(CharcoalKilnsLoader.class.getSimpleName(), new CharcoalKilnsLoader());
		dataLoaders.put(ElephantPoachingLoader.class.getSimpleName(), new ElephantPoachingLoader());
		dataLoaders.put(HerdsLoader.class.getSimpleName(), new HerdsLoader());
		dataLoaders.put(IndividualAnimalsLoader.class.getSimpleName(), new IndividualAnimalsLoader());
		dataLoaders.put(SuspiciousActivitiesLoader.class.getSimpleName(), new SuspiciousActivitiesLoader());
		dataLoaders.put(WaterholesLoader.class.getSimpleName(), new WaterholesLoader());
	}
	
	public DataLoader getLoader(String name) {
		return dataLoaders.get(name);
	}
}

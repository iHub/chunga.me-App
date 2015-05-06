package com.ihub.rangerapp;

import java.util.HashMap;
import java.util.Map;

import com.ihub.rangerapp.loader.DataLoader;
import com.ihub.rangerapp.loader.GameMeatLoader;

import android.app.Application;

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
	
	private void initLoaders() {
		dataLoaders.put(GameMeatLoader.class.getSimpleName(), new GameMeatLoader());
	}
	
	public DataLoader getLoader(String name) {
		return dataLoaders.get(name);
	}
}

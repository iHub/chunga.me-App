package com.ihub.rangerapp;

import android.app.Application;

public class RangerApp extends Application {
	
	static RangerApp instance;
	
	String password;
	
	public RangerApp(){}
	
	public static RangerApp get() {
		return instance;
	}
	
	@Override
	public void onCreate() {
		instance = this;
	}
}

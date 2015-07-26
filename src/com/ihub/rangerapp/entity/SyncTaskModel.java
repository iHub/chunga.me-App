package com.ihub.rangerapp.entity;

public class SyncTaskModel {
	
	String name;
	
	String progress;
	
	Integer errorCount = 0;
	
	public SyncTaskModel() {}
	
	public SyncTaskModel(String name, String progress) {
		this.name = name;
		this.progress = progress;
	}
	
	public SyncTaskModel(String name, String progress, Integer errorCount) {
		this.name = name;
		this.progress = progress;
		this.errorCount = errorCount;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getProgress() {
		return progress;
	}
	
	public void setProgress(String progress) {
		this.progress = progress;
	}
}
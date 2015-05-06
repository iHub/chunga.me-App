package com.ihub.rangerapp.model;

import android.content.Intent;

public class ElephantPoachingModel extends Model {

	Integer id;
	String toolsUsed;
	Integer noOfAnimals;
	String age;
	String sex;
	String ivoryPresence;
	String actionTaken;
	String extraNotes;
	String wp;
	String imagePath;
	String dateCreated;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getToolsUsed() {
		return toolsUsed;
	}
	public void setToolsUsed(String toolsUsed) {
		this.toolsUsed = toolsUsed;
	}
	public Integer getNoOfAnimals() {
		return noOfAnimals;
	}
	public void setNoOfAnimals(Integer noOfAnimals) {
		this.noOfAnimals = noOfAnimals;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getIvoryPresence() {
		return ivoryPresence;
	}
	public void setIvoryPresence(String ivoryPresence) {
		this.ivoryPresence = ivoryPresence;
	}
	public String getActionTaken() {
		return actionTaken;
	}
	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}
	public String getExtraNotes() {
		return extraNotes;
	}
	public void setExtraNotes(String extraNotes) {
		this.extraNotes = extraNotes;
	}
	public String getWp() {
		return wp;
	}
	public void setWp(String wp) {
		this.wp = wp;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	@Override
	public Intent getExtras() {
		
		Intent intent = new Intent();
		intent.putExtra("id", id);
		intent.putExtra("toolsUsed", toolsUsed);
		intent.putExtra("noOfAnimals", noOfAnimals);
		intent.putExtra("age", age);
		intent.putExtra("sex", sex);
		intent.putExtra("ivoryPresence", ivoryPresence);
		intent.putExtra("actionTaken", actionTaken);
		intent.putExtra("extraNotes", extraNotes);
		intent.putExtra("wp", wp);
		intent.putExtra("imagePath", imagePath);
		intent.putExtra("dateCreated", dateCreated);
		
		return intent;
	}
}
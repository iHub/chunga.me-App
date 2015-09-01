package com.ihub.rangerapp.model;

import android.content.Intent;

public class ElephantPoachingModel extends Model {

	String toolsUsed;
	Integer noOfAnimals;
	Integer maleCount;
	Integer femaleCount;
	Integer adultsCount;
	Integer semiAdultsCount;
	Integer juvenileCount;
	String ivoryPresence;
	String actionTaken;
	String extraNotes;
	String imagePath;
	String dateCreated;
	
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
	public Integer getMaleCount() {
		return maleCount;
	}
	public void setMaleCount(Integer maleCount) {
		this.maleCount = maleCount;
	}
	public Integer getFemaleCount() {
		return femaleCount;
	}
	public void setFemaleCount(Integer femaleCount) {
		this.femaleCount = femaleCount;
	}
	public Integer getAdultsCount() {
		return adultsCount;
	}
	public void setAdultsCount(Integer adultsCount) {
		this.adultsCount = adultsCount;
	}
	public Integer getSemiAdultsCount() {
		return semiAdultsCount;
	}
	public void setSemiAdultsCount(Integer semiAdultsCount) {
		this.semiAdultsCount = semiAdultsCount;
	}
	public Integer getJuvenileCount() {
		return juvenileCount;
	}
	public void setJuvenileCount(Integer juvenileCount) {
		this.juvenileCount = juvenileCount;
	}
	@Override
	public Intent getExtras() {
		
		Intent intent = new Intent();
		intent.putExtra("id", id);
		intent.putExtra("toolsUsed", toolsUsed);
		intent.putExtra("noOfAnimals", noOfAnimals);
		intent.putExtra("maleCount", maleCount);
		intent.putExtra("femaleCount", femaleCount);
		intent.putExtra("adultsCount", adultsCount);
		intent.putExtra("semiAdultsCount", semiAdultsCount);
		intent.putExtra("juvenileCount", juvenileCount);
		intent.putExtra("ivoryPresence", ivoryPresence);
		intent.putExtra("actionTaken", actionTaken);
		intent.putExtra("extraNotes", extraNotes);
		intent.putExtra("waypoint", waypoint);
		intent.putExtra("imagePath", imagePath);
		intent.putExtra("dateCreated", dateCreated);
		
		return intent;
	}
}
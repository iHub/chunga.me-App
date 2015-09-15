package com.ihub.rangerapp.model;

import android.content.Intent;

public class WaterholeModel extends Model {

	String name;
	String levelOfWater;
	String extraNotes;
	String imagePath;
	String dateCreated;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevelOfWater() {
		return levelOfWater;
	}

	public void setLevelOfWater(String levelOfWater) {
		this.levelOfWater = levelOfWater;
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

	@Override
	public Intent getExtras() {
		
		Intent intent = new Intent();
		intent.putExtra("id", id);
		intent.putExtra("name", name);
		intent.putExtra("levelOfWater", levelOfWater);
		intent.putExtra("extraNotes", extraNotes);
		intent.putExtra("waypoint", waypoint);
		intent.putExtra("imagePath", imagePath);
		intent.putExtra("dateCreated", dateCreated);
		intent.putExtra("ranch", ranch);
		return intent;
	}

}

package com.ihub.rangerapp.model;

import android.content.Intent;

public class WaterholeModel extends Model {

	Integer id;
	String name;
	String levelOfWater;
	Integer numberOfAnimals;
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

	public Integer getNumberOfAnimals() {
		return numberOfAnimals;
	}

	public void setNumberOfAnimals(Integer numberOfAnimals) {
		this.numberOfAnimals = numberOfAnimals;
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
		intent.putExtra("name", name);
		intent.putExtra("levelOfWater", levelOfWater);
		intent.putExtra("numberOfAnimals", numberOfAnimals);
		intent.putExtra("extraNotes", extraNotes);
		intent.putExtra("wp", wp);
		intent.putExtra("imagePath", imagePath);
		intent.putExtra("dateCreated", dateCreated);
		return intent;
	}

}

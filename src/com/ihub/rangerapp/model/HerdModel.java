package com.ihub.rangerapp.model;

import android.content.Intent;

public class HerdModel extends Model {

	String name;
	String type;
	Integer noOfAnimals;
	Integer adultsCount;
	Integer semiAdultsCount;
	Integer juvenileCount;
	Integer distanceSeen;
	String extraNotes;
	String imagePath;
	String dateCreated;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getNoOfAnimals() {
		return noOfAnimals;
	}

	public void setNoOfAnimals(Integer noOfAnimals) {
		this.noOfAnimals = noOfAnimals;
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

	public Integer getDistanceSeen() {
		return distanceSeen;
	}

	public void setDistanceSeen(Integer distanceSeen) {
		this.distanceSeen = distanceSeen;
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
		intent.putExtra("type", type);
		intent.putExtra("noOfAnimals", noOfAnimals);
		intent.putExtra("adultsCount", adultsCount);
		intent.putExtra("semiAdultsCount", semiAdultsCount);
		intent.putExtra("juvenileCount", juvenileCount);
		intent.putExtra("distanceSeen", distanceSeen);
		intent.putExtra("extraNotes", extraNotes);
		intent.putExtra("waypoint", waypoint);
		intent.putExtra("imagePath", imagePath);
		intent.putExtra("dateCreated", dateCreated);
		return intent;
	}
}
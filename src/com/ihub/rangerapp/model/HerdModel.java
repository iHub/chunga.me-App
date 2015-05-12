package com.ihub.rangerapp.model;

import android.content.Intent;

public class HerdModel extends Model {

	Integer id;
	String name;
	String type;
	Integer noOfAnimals;
	String age;
	Integer distanceSeen;
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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
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
		intent.putExtra("type", type);
		intent.putExtra("noOfAnimals", noOfAnimals);
		intent.putExtra("age", age);
		intent.putExtra("distanceSeen", distanceSeen);
		intent.putExtra("extraNotes", extraNotes);
		intent.putExtra("wp", wp);
		intent.putExtra("imagePath", imagePath);
		intent.putExtra("dateCreated", dateCreated);
		return intent;
	}
}
package com.ihub.rangerapp.model;

import android.content.Intent;

public class IndividualAnimalModel extends Model {

	String animal;
	String gender;
	String age;
	Integer distanceSeen;
	String extraNotes;
	String imagePath;
	String dateCreated;

	public String getAnimal() {
		return animal;
	}

	public void setAnimal(String animal) {
		this.animal = animal;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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
		intent.putExtra("animal", animal);
		intent.putExtra("gender", gender);
		intent.putExtra("age", age);
		intent.putExtra("distanceSeen", distanceSeen);
		intent.putExtra("extraNotes", extraNotes);
		intent.putExtra("lat", latitude);
		intent.putExtra("lon", longitude);
		intent.putExtra("imagePath", imagePath);
		intent.putExtra("dateCreated", dateCreated);
		
		return intent;
	}
}
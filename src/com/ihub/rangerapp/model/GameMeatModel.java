package com.ihub.rangerapp.model;

import android.content.Intent;

public class GameMeatModel extends Model {
	
	Integer id;
	String animal;
	Integer noOfAnimals;
	String actionTaken;
	String extraNotes;
	String wp;
	String imagePath;
	String dateCreated;
	
	public GameMeatModel() {}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getAnimal() {
		return animal;
	}
	
	public void setAnimal(String animal) {
		this.animal = animal;
	}
	public Integer getNoOfAnimals() {
		return noOfAnimals;
	}
	public void setNoOfAnimals(Integer noOfAnimals) {
		this.noOfAnimals = noOfAnimals;
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

	public Intent getExtras() {
		Intent intent = new Intent();
		intent.putExtra("id", id);
		intent.putExtra("animal", animal);
		intent.putExtra("noOfAnimals", noOfAnimals);
		intent.putExtra("actionTaken", actionTaken);
		intent.putExtra("extraNotes", extraNotes);
		intent.putExtra("wp", wp);
		intent.putExtra("imagePath", imagePath);
		intent.putExtra("dateCreated", dateCreated);
		
		return intent;
	}
}
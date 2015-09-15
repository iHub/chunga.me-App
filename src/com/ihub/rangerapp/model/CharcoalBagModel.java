package com.ihub.rangerapp.model;

import android.content.Intent;

public class CharcoalBagModel extends Model {
	
	Integer noOfBags;
	String modeOfTransport;
	String actionTaken;
	String extraNotes;
	String imagePath;
	String dateCreated;

	public Integer getNoOfBags() {
		return noOfBags;
	}

	public void setNoOfBags(Integer noOfBags) {
		this.noOfBags = noOfBags;
	}

	public String getModeOfTransport() {
		return modeOfTransport;
	}

	public void setModeOfTransport(String modeOfTransport) {
		this.modeOfTransport = modeOfTransport;
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

	@Override
	public Intent getExtras() {
		Intent intent = new Intent();
		intent.putExtra("id", id);
		intent.putExtra("noOfBags", noOfBags);
		intent.putExtra("modeOfTransport", modeOfTransport);
		intent.putExtra("actionTaken", actionTaken);
		intent.putExtra("extraNotes", extraNotes);
		intent.putExtra("waypoint", waypoint);
		intent.putExtra("imagePath", imagePath);
		intent.putExtra("dateCreated", dateCreated);
		intent.putExtra("ranch", ranch);
		return intent;
	}

}

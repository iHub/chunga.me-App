package com.ihub.rangerapp.model;

import android.content.Intent;

public class CharcoalKilnModel extends Model {

	Integer noOfKilns;
	String freshnessLevels;
	String treeUsed;
	String actionTaken;
	String extraNotes;
	String imagePath;
	String dateCreated;

	public Integer getNoOfKilns() {
		return noOfKilns;
	}

	public void setNoOfKilns(Integer noOfKilns) {
		this.noOfKilns = noOfKilns;
	}

	public String getFreshnessLevels() {
		return freshnessLevels;
	}

	public void setFreshnessLevels(String freshnessLevels) {
		this.freshnessLevels = freshnessLevels;
	}

	public String getTreeUsed() {
		return treeUsed;
	}

	public void setTreeUsed(String treeUsed) {
		this.treeUsed = treeUsed;
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
		intent.putExtra("noOfKilns", noOfKilns);
		intent.putExtra("freshnessLevels", freshnessLevels);
		intent.putExtra("treeUsed", treeUsed);
		intent.putExtra("actionTaken", actionTaken);
		intent.putExtra("extraNotes", extraNotes);
		intent.putExtra("lat", latitude);
		intent.putExtra("lon", longitude);
		intent.putExtra("imagePath", imagePath);
		intent.putExtra("dateCreated", dateCreated);
		return intent;
	}
}

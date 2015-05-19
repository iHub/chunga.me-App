package com.ihub.rangerapp.model;

import java.io.Serializable;
import android.content.Intent;

public abstract class Model implements Serializable {
	
	Integer id;
	
	String latitude = "";
	
	String longitude = "";

	public Model() {
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public abstract Intent getExtras();

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
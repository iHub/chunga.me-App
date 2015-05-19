package com.ihub.rangerapp.model;

import java.io.Serializable;
import android.content.Intent;

public abstract class Model implements Serializable {
	
	String latitude = "";
	
	String longitude = "";

	public Model() {
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
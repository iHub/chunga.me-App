package com.ihub.rangerapp.model;

import java.io.Serializable;
import android.content.Intent;

public abstract class Model implements Serializable {
	
	Integer id;
	
	String waypoint = "";
	
	String ranch= "";

	public Model() {
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public abstract Intent getExtras();

	public String getWaypoint() {
		return waypoint;
	}
	
	public void setWaypoint(String waypoint) {
		this.waypoint = waypoint;
	}

	public String getRanch() {
		return ranch;
	}

	public void setRanch(String ranch) {
		this.ranch = ranch;
	}
}
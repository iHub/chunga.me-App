package com.ihub.rangerapp.entity;

import java.io.Serializable;

public class IvoryTypeModel implements Serializable {

	String name;
	
	Integer image;
	
	public IvoryTypeModel() {}
	
	public IvoryTypeModel(String name, Integer image) {
		this.name = name;
		this.image = image;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getImage() {
		return image;
	}

	public void setImage(Integer image) {
		this.image = image;
	}
}
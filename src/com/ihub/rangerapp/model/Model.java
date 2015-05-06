package com.ihub.rangerapp.model;

import java.io.Serializable;
import android.content.Intent;

public abstract class Model implements Serializable {

	public Model() {
	}
	
	public abstract Intent getExtras();
}
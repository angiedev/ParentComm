package org.angiedev.parentcomm.service.impl.json.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Geometry {

	private Location location;
	
	public Geometry(@JsonProperty("location") Location location) {
		this.location = location;	
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}

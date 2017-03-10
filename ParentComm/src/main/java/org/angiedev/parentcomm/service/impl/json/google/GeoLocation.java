package org.angiedev.parentcomm.service.impl.json.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown=true)
class GeoLocation extends org.angiedev.parentcomm.model.GeoLocation {

	private Geometry geometry; 
	
	public GeoLocation(@JsonProperty("geometry") Geometry geometry) {
		this.geometry = geometry;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
	
	@Override
	public double getLatitude() {
		return geometry.getLocation().getLatitude();
	}
	
	@Override 
	public double getLongitude() {
		return geometry.getLocation().getLongitude();
	}
	
}


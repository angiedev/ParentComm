package org.angiedev.parentcomm.service.impl.json.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * GeoLocation is a domain class used to consume the data from the Google Geocoding API.  
 * The data returned from the API is in JSON.  Spring's rest template uses Jackson 
 * JSON processing library to translate this JSON data into a Java object.
 * <p>
 * We are interested in the geo location (latitude and longitude) data returned 
 * by the API.
 * @author Angela Gordon 
 *
 */

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


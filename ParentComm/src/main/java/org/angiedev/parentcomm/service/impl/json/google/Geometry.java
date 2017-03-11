package org.angiedev.parentcomm.service.impl.json.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Geometry is a domain class used to consume the data from the Google Geocoding API.  
 * The data returned from the API is in JSON.  Spring's rest template uses Jackson 
 * JSON processing library to translate this JSON data into a Java object.
 * <p>
 * We are interested in the geo location (latitude and longitude) data returned 
 * by the API.
 * @author Angela Gordon 
 *
 */
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

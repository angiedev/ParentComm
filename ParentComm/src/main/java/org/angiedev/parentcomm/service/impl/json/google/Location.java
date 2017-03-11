package org.angiedev.parentcomm.service.impl.json.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Location is a domain class used to consume the data from the Google Geocoding API.  
 * The data returned from the API is in JSON.  Spring's rest template uses Jackson 
 * JSON processing library to translate this JSON data into a Java object.
 * <p>
 * We are interested in the geo location (latitude and longitude) data returned 
 * by the API.
 * @author Angela Gordon 
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Location {
	private double latitude;
	private double longitude;
	
	public Location(@JsonProperty("lat") double latitude,
			@JsonProperty("lng") double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}


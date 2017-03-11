package org.angiedev.parentcomm.service.impl.json.google;
import java.util.List;

import org.angiedev.parentcomm.service.impl.json.google.GeoLocation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Result is a domain class used to consume the data from the Google Geocoding API.  
 * The data returned from the API is in JSON.  Spring's rest template uses Jackson 
 * JSON processing library to translate this JSON data into a Java object.
 * <p>
 * We are interested in the geo location (latitude and longitude) data returned 
 * by the API.
 * @author Angela Gordon 
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Result {

	private String status; 
	private GeoLocation geoLocation;
	
	public Result(@JsonProperty("status") String status,
			@JsonProperty("results") List<GeoLocation> geoLocationData) {
		this.setStatus(status);
	    if (geoLocationData.size() > 0) {
		   this.setGeoLocation(geoLocationData.get(0));
	    }
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public GeoLocation getGeoLocation() {
		return geoLocation;
	}

	public void setGeoLocation(GeoLocation geoLocation) {
		this.geoLocation = geoLocation;
	}
	
}







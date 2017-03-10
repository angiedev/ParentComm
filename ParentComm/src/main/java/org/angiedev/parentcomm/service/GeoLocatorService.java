package org.angiedev.parentcomm.service;

import java.io.IOException;

/**
 * GeoLocatorService defines the interface for a service used to retrieve Geolocation 
 * (latitude/longitude) data for addresses 
 * @author Angela Gordon
 */

import org.angiedev.parentcomm.model.GeoLocation;

public interface GeoLocatorService {
	
	/**
	 * Returns the GeoLocation (latitude/longitude) for the passed in address 
	 * @param address	address to find geolocation for
	 * @return 			GeoLocation of the address
	 * @throws IOException
	 */
	public GeoLocation getGeoLocationForAddress(String address) throws IOException;

}

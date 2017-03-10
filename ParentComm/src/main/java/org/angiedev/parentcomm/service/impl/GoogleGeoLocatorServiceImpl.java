package org.angiedev.parentcomm.service.impl;

import java.io.IOException;

import org.angiedev.parentcomm.model.GeoLocation;
import org.angiedev.parentcomm.service.GeoLocatorService;
import org.angiedev.parentcomm.service.impl.json.google.Result;
import org.angiedev.parentcomm.util.Props;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleGeoLocatorServiceImpl implements GeoLocatorService {
	
	private static final String GEOCODE_LOOKUP_URL = "https://maps.googleapis.com/maps/api/geocode/json?";
	private static final String ADDRESS_KEY_PARAM = "address=";
	private static final String GEOCODE_API_KEY_PARAM = "key=" + Props.getInstance().getGoogleGeoCodeAPIKey();

	/**
	  * Calls the google geo code service to look up the geo location for the passed in address
	  * @param address 		street address
	  * @param city 		city	
	  * @param stateCode 	two letter state code
	  * @return 			geo location of address
	  */
	 @Override
	 public GeoLocation getGeoLocationForAddress(String address) throws IOException {
		
		String query = GEOCODE_LOOKUP_URL + ADDRESS_KEY_PARAM + address +  "&" + GEOCODE_API_KEY_PARAM;
		
		RestTemplate restTemplate = new RestTemplate();
		Result result = 
				restTemplate.getForObject(query, Result.class);
		
		switch (result.getStatus()) {
			case "OK":
				return result.getGeoLocation();
			default:
				throw new IOException("Unable to get GeoLocation for address: " + address 
						+ ".  GeoCode API returned status: " + result.getStatus());
		}
	}
	


}

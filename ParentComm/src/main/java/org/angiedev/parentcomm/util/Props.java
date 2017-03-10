package org.angiedev.parentcomm.util;

import java.util.Properties;

import java.io.IOException;
import org.apache.log4j.Logger;
/**
 * Props is a utility class used to retrieve application properties.
 * 
 * @author Angela Gordon
 */
public class Props {

	private static Props instance;
	private static final Logger logger = Logger.getLogger(Props.class);
	
	private Properties properties;
	
	public static Props getInstance() {
		return (instance == null) ? instance = new Props() : instance;
	}
	
	private Props() {
		if (properties == null) {
			try {
				properties = new Properties();
				properties.load(this.getClass().getResourceAsStream("/ParentComm.properties"));
			} catch (IOException e) {
				logger.error("Unable to load Properties:" + e);
				properties = null;
			}
		}
	}
	
	public String getGoogleGeoCodeAPIKey() {
		return (properties == null) ? null : properties.getProperty("GOOGLE_GEOCODE_API_KEY");
	}
	
	public String getGoogleAutocompleteAPIKey() {
		return (properties == null) ? null : properties.getProperty("GOOGLE_AUTOCOMPLETE_API_KEY");
	}
	
	public int getSearchRadiusForNameSearch() {
		return (properties == null) ? 0 : Integer.parseInt(properties.getProperty("SEARCH_BY_NAME_RADIUS"));
	}
	public int getSearchRadiusForAddressSearch() {
		return (properties == null) ? 0 : Integer.parseInt(properties.getProperty("SEARCH_BY_ADDRESS_RADIUS"));
	}

}

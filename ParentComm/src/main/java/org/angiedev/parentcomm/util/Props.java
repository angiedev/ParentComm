package org.angiedev.parentcomm.util;

import java.util.Properties;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Props is a utility class used to retrieve application properties.
 * 
 * @author Angela Gordon
 */
public class Props {

	private static final Logger LOGGER = LogManager.getLogger();
	private static Props instance;

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
				LOGGER.error("Unable to load Properties:" + e);
				properties = null;
			}
		}
	}
	
	public String getGoogleAPIKey() {
		return (properties == null) ? null : properties.getProperty("GOOGLE_API_KEY");
	}
	
}

package org.angiedev.parentcomm.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.angiedev.parentcomm.model.School;
import org.angiedev.parentcomm.model.SchoolLevel;
import org.angiedev.parentcomm.service.impl.json.SchoolFinderSchool;
import org.angiedev.parentcomm.service.impl.json.GoogleGeocodingLookupResult;
import org.angiedev.parentcomm.model.GeoLocation;
import org.angiedev.parentcomm.util.Props;
import org.angiedev.parentcomm.service.SchoolLocatorService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * SchoolLocatorServiceImpl is a service which retrieves and filter schools 
 * based on school data retrieved using the SchoolFinder REST service.
 * 
 * @author Angela Gordon
 */
@Service
public class SchoolLocatorServiceImpl implements SchoolLocatorService {

	private final String SCHOOL_FINDER_SERVICE_URL = "http://localhost:8080/SchoolFinder/schools";
	private final String SEARCH_REQUEST = "search";
	private final String SEARCH_STRING_PARAM = "searchString=";
	private final String LAT_PARAM = "lat=";
	private final String LONG_PARAM = "long=";
	private final String RADIUS_PARAM = "searchRadius=";
	private final String MAX_RESULTS_PARAM = "maxNumResults=";
	private static final String GEOCODE_LOOKUP_URL = "https://maps.googleapis.com/maps/api/geocode/json?";
	private static final String ADDRESS_KEY_PARAM = "address=";
	private static final String GEOCODE_API_KEY_PARAM = "key=" + Props.getInstance().getGoogleGeoCodeAPIKey();

	
	@Override
	public List<School> findSchoolsByGeoLocation(double latitude, double longitude, int searchRadius,
			int maxNumResults) {
		
		String query = SCHOOL_FINDER_SERVICE_URL + "/" + SEARCH_REQUEST + "?" +
						LAT_PARAM  + latitude + "&" +
						LONG_PARAM + longitude + "&" + 
						RADIUS_PARAM +  searchRadius + "&" + 
						MAX_RESULTS_PARAM + maxNumResults; 
		return getSchools(query);
	}
	
	@Override
	public List<School> findSchoolsByNameAndGeoLocation(String name, double latitude, double longitude,
			int searchRadius, int maxNumResults) {
		
		String query = SCHOOL_FINDER_SERVICE_URL + "/" + SEARCH_REQUEST + "?" +
				SEARCH_STRING_PARAM + name + "&" + 
				LAT_PARAM  + latitude + "&" +
				LONG_PARAM + longitude + "&" + 
				RADIUS_PARAM +  searchRadius +"&" + 
				MAX_RESULTS_PARAM + maxNumResults; 
		return getSchools(query);
	}

	@Override
	public School getSchoolByNcesId(String ncesId) {

		String query = SCHOOL_FINDER_SERVICE_URL + "/" + ncesId;
		RestTemplate rest = new RestTemplate();
		SchoolFinderSchool school = rest.getForObject(query, SchoolFinderSchool.class);
		return school;
	}
	
	/* helper method to execute query against DB and return list of schools */
	private List<School> getSchools(String query) {
		List<School> resultList = new ArrayList<School>();
		RestTemplate rest = new RestTemplate();
		
		SchoolFinderSchool[] schools = rest.getForObject(query, SchoolFinderSchool[].class);
		for (SchoolFinderSchool s: schools) {
			resultList.add(s);
		}
		return resultList;
	}
	
	/**
	 * Filters a list of schools based on the passed in school level.
	 * @param schools		list of schools to filter
	 * @param schoolLevel	level of school to look for (ie. ELEMENTARY, MIDDLE, HIGH)
	 * @return				list of schools having grades in the passed in school level
	 */
	@Override
	public List<School> filterSchoolsByType(List<School> schools, SchoolLevel schoolLevel) {
		
		List<School> filteredList = new ArrayList<School>();
		
		for (School school: schools) {
			
			switch (schoolLevel) {
			  	case ELEMENTARY: 
			  		if (school.getLowGrade().isElementary()) {
			  			filteredList.add(school);
			  		}
			  		break;
			  	case INTERMEDIATE:
			  		if (school.getLowGrade().isIntermediate() ||
			  			school.getHighGrade().isIntermediate()) {
			  			filteredList.add(school);
			  		}
			  		break;
			  	case HIGH:  
			  		if (school.getHighGrade().isHigh()) {
			  			filteredList.add(school);
			  		}
			  		break;
			}
		}
				
		return filteredList;
	}	
	
	/**
	  * Calls the google geo code service to look up the geo location for the passed in address
	  * @param address 		street address
	  * @param city 		city	
	  * @param stateCode 	two letter state code
	  * @return 			geo location of address
	  */
	 public GeoLocation getGeoLocationForAddress(String address)
	 	throws IOException {
		
		String query = GEOCODE_LOOKUP_URL + ADDRESS_KEY_PARAM + address +  "&" + GEOCODE_API_KEY_PARAM;
		
		RestTemplate restTemplate = new RestTemplate();
		GoogleGeocodingLookupResult result = 
				restTemplate.getForObject(query, GoogleGeocodingLookupResult.class);
		
		switch (result.getStatus()) {
			case "OK":
				return result.getGeoLocation();
			case "ZERO_RESULTS":
				return null;
			default:
				throw new IOException("Unable to get GeoLocation for address: " + address 
						+ ".  GeoCode API returned status: " + result.getStatus());
		}
	}
	

	
}

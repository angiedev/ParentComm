package org.angiedev.parentcomm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.angiedev.parentcomm.model.School;
import org.angiedev.parentcomm.model.SchoolLevel;
import org.angiedev.parentcomm.service.SchoolLocatorService;
import org.angiedev.parentcomm.service.impl.json.schoolFinder.SchoolFinderSchool;
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
	
}

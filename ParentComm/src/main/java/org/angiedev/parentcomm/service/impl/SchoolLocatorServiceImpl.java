package org.angiedev.parentcomm.service.impl;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.angiedev.parentcomm.model.School;
import org.angiedev.parentcomm.model.SchoolLevel;
import org.angiedev.parentcomm.service.impl.json.SchoolFinderSchool;
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

	private final String SEARCH_URL = "http://localhost:8080/SchoolFinder/schools?";
	private final String SEARCH_STRING_PARAM = "searchString=";
	private final String LAT_PARAM = "lat=";
	private final String LONG_PARAM = "long=";
	private final String RADIUS_PARAM = "searchRadius=";
	private final String ORDER_BY_PARAM = "orderBy=";
	
	
	@Override
	public List<School> findSchoolsByGeoLocation(double latitude, double longitude, int searchRadius) {
		
		String query = SEARCH_URL + LAT_PARAM  + latitude + "&" +
						LONG_PARAM + longitude + "&" + RADIUS_PARAM +  searchRadius + 
						"&" + ORDER_BY_PARAM + "BY_DISTANCE"; 
		return getSchools(query);
	}
	
	@Override
	public List<School> findSchoolsByNameAndGeoLocation(String name, double latitude, double longitude,
			int searchRadius) {
		
		String query = SEARCH_URL + SEARCH_STRING_PARAM + name + "&" + LAT_PARAM  + latitude + "&" +
				LONG_PARAM + longitude + "&" + RADIUS_PARAM +  searchRadius +
				"&" + ORDER_BY_PARAM + "BY_DISTANCE"; 
		return getSchools(query);
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
	 * <p>
	 * If a user is filtering by ELEMENTARY then all schools with a grade of 
	 * PK, KG, 1, 2, 3, 4, or 5 it will be included in the results.
	 * <p>
	 * If a user is filtering by MIDDLE then all schools with a grade of 
	 * 6, 7, or 8 will be included in the results.
	 * <p>
	 * If a user is filtering by HIGH then all schools with a grade of 
	 * 9, 10, 11, or 12 will be included in the results.
	 *<p>
	 * @param schools		list of schools to filter
	 * @param schoolLevel	level of school to look for (ie. ELEMENTARY, MIDDLE, HIGH)
	 * @return				list of schools having grades in the passed in school level
	 */
	@Override
	public List<School> filterSchoolsByType(List<School> schools, SchoolLevel schoolLevel) {
		
		List<School> filteredList = new ArrayList<School>();
		
		for (School school: schools) {
			
			int lowGrade = getIntegerValueForGrade(school.getLowGrade());
			int highGrade = getIntegerValueForGrade(school.getHighGrade());
		
			// ignore schools that have a grade value were are not interested in
			if (( lowGrade == -1) || (highGrade == -1)) {
				break;
			}
			
			switch (schoolLevel) {
			  	case ELEMENTARY: 
			  		if (lowGrade <= 5) {
			  			filteredList.add(school);
			  		} 
			  		break;
			  	case INTERMEDIATE:
			  		if ( ((lowGrade >= 6) && (lowGrade < 9)) ||
			  			((highGrade >=6) && (highGrade < 9)) ) {
			  			filteredList.add(school);
			  		}
			  		break;
			  	case HIGH:  
			  		if (highGrade >= 9) {
			  			filteredList.add(school);
			  		}
			  		break;
			}
		}
				
		return filteredList;
	}	
		
		
		
	/* 
	 * Converts a string grade value to an integer value.
	 * Returns a -1 value for schools that should be ignored.
	 * (Schools that should be ignored either no students, are 
	 * ungraded or have another unexpected non-numerical value.
	 */
	private int getIntegerValueForGrade(String grade) {
		
		int gradeValue; 
		
		// If school has no students or is ungraded then we are not interested in 
		// this school 
		if (grade.equals("N") || grade.equals( "UG")) {
		    gradeValue =  -1;
		} else if (grade.equals("PK") || grade.equals("KG")) {
		    gradeValue = 0;
		} else {
			try{
		       gradeValue = Integer.parseInt(grade);
			} catch (NumberFormatException e) {
				// if its not a valid number grade then we will ignore it
				gradeValue = -1;
			}
		}   
		
		return gradeValue;
	}

	
}

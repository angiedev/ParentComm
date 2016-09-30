package org.angiedev.parentcomm.service;

import java.util.List;

import org.angiedev.parentcomm.model.School;
import org.angiedev.parentcomm.model.SchoolLevel;

/**
 * SchoolLocatorService is a interface for a service which enables a user to find 
 * and filter a collection of schools.
 * 
 * @author Angela Gordon
 */
public interface SchoolLocatorService {

	/**
	 * Retrieves a list of schools near a passed in geo-location.
	 * @param latitude		latitude of location to search near.
	 * @param longitude		longitude of location to search near.
	 * @param searchRadius	distance in miles to search withing
	 * @return				list of schools near the passed in address
	 */
	public List<School> findSchoolsByGeoLocation(double latitude, double longitude,
			int searchRadius);
	
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
	 * If a school has a low_grade/high_grade of N, UG or other unrecognized 
	 * value then the school will not be included in the filtered list
	 * @param schools		list of schools to filter
	 * @param schoolLevel	level of school to look for (ie. ELEMENTARY, MIDDLE, HIGH)
	 * @return				list of schools having grades in the passed in school level
	 */
	public List<School> filterSchoolsByType(List<School> schools, SchoolLevel schoolLevel);

}

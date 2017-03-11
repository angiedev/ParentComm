package org.angiedev.parentcomm.service;


import java.io.IOException;
import java.util.List;

import org.angiedev.parentcomm.model.School;
import org.angiedev.parentcomm.model.SchoolLevel;
import org.angiedev.parentcomm.model.SearchRadius;

/**
 * SchoolLocatorService defines the interface for a service which enables a user to find 
 * and filter a collection of schools.
 * 
 * @author Angela Gordon
 */
public interface SchoolLocatorService {

	/**
	 * Retrieves a list of schools near a passed in geo-location.
	 * @param latitude		latitude of location to search near.
	 * @param longitude		longitude of location to search near.
	 * @param searchRadius	distance in miles to search within
	 * @param maxNumResults	maximum number of schools to return
	 * @return				list of schools near the passed in geolocation
	 */
	public List<School> findSchoolsByGeoLocation(double latitude, double longitude,
			SearchRadius searchRadius, int maxNumResults);
	
	/**
	 * Retrieves a list of schools near a passed in address
	 * @param address		address to search near
	 * @param searchRadius	distance in miles to search within
	 * @param maxNumResults maximum number of schools to return
	 * @return				list of schools near the passed in address
	 * @throws IOException  thrown for invalid addresses
	 */
	public List<School> findSchoolsByAddress(String address, SearchRadius searchRadius,
			int maxNumResults) throws IOException;
		
		
	/**
	 * Retrieves a list of schools near a passed in geo-location with a matching name.
	 * @param name 			name to match school against
	 * @param latitude		latitude of location to search near.
	 * @param longitude		longitude of location to search near.
	 * @param searchRadius	distance in miles to search within
	 * @param maxNumResults	maximum number of schools to return
	 * @return				list of schools near the passed in address
	 */
	public List<School> findSchoolsByNameAndGeoLocation(String name, double latitude,
			double longitude, SearchRadius searchRadius, int maxNumResults);
	
	/**
	 * Retrieves the school identified by the passed in NCES id
	 * @param ncesId	unique NCES id identifying the school 
	 *                  (Id assigned by the National Center for Education Statistics)
	 * @return			school with the passed in NCES Id
     */
	public School getSchoolByNcesId(String ncesId);
	
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
	public List<School> filterSchoolsBySchoolLevel(List<School> schools, SchoolLevel schoolLevel);
	
	
}

package org.angiedev.parentcomm.web.form;

import org.angiedev.parentcomm.model.SchoolLevel;
import org.angiedev.parentcomm.model.SearchRadius;
import org.angiedev.parentcomm.model.SearchType;

/**
 * Form data used to support users requests to search for schools 
 * @author Angela Gordon
 *
 */
public class SchoolFinderForm {

	private String searchValue;
	private double latitude;
	private double longitude;
	private SearchRadius radius; 
	private SchoolLevel schoolLevel;
	private SearchType searchType;
	
	public SchoolFinderForm() {
		schoolLevel = SchoolLevel.ALL; // default to ALL schools
		radius = SearchRadius.SMALL;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public SearchRadius getRadius() {
		return radius;
	}

	public void setRadius(SearchRadius radius) {
		this.radius = radius;
	}

	public SchoolLevel getSchoolLevel() {
		return schoolLevel;
	}

	public void setSchoolLevel(SchoolLevel schoolLevel) {
		this.schoolLevel = schoolLevel;
	}

	public SearchType getSearchType() {
		return searchType;
	}

	public void setSearchType(SearchType searchType) {
		this.searchType = searchType;
	}
}

package org.angiedev.parentcomm.web.form;

import org.angiedev.parentcomm.model.SchoolLevel;

public class SchoolFinderForm {

	public static final int MAX_RADIUS = 1000000;
	private String searchValue;
	private double latitude;
	private double longitude;
	private int radius; 
	private SchoolLevel filterType;
	
	public SchoolFinderForm() {
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

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public void setMaxRadius() {
		radius = MAX_RADIUS;
	}

	public SchoolLevel getFilterType() {
		return filterType;
	}

	public void setFilterType(SchoolLevel filterType) {
		this.filterType = filterType;
	}
}

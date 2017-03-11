package org.angiedev.parentcomm.model;

/**
 * SearchRadius is a enumeration of radius values a user can choose when performing a search 
 * for schools
 * @author Angela Gordon
 */
public enum SearchRadius {
	XSMALL(2),
	SMALL(5), 
	MEDIUM(15),
	LARGE(30), 
	UNRESTRICTED(1000000);
	
	private final int value; 
	
	SearchRadius(int value) {
		this.value = value;
	}
	
	public int getValue() { 
		return value;
	}
}


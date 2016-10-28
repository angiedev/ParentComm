package org.angiedev.parentcomm.model;

/**
 * SchoolGrade is an enumeration which represents a grade of a school.
 * @author Angela Gordon	
 *
 */
public enum SchoolGrade {
	UNGRADED("UG", -2), // ungraded
	PREK("PK", -1),	// Pre-Kindergarden
	KINDER("KG", 0), // Kindergarden
	FIRST("01", 1),	// 1st grade
	SECOND("02", 2),	// 2nd grade
	THIRD("03", 3),	// 3rd grade
	FOURTH("04", 4),	// 4th grade
	FIFTH("05", 5),	// 5th grade
	SIXTH("06", 6),	// 6th grade
	SEVENTH("07", 7),	// 7th grade
	EIGHTH("08", 8),	// 8th grade
	NINETH("09", 9),	// 9th grade
	TENTH("10", 10),   // 10th grade
	ELEVENTH("11", 11), // 11th grade
	TWELFTH("12", 12); // 12th grade
	
	
	private final String gradeCode;
	private final int value;
	
	
	SchoolGrade(String gradeCode, int value) {
		this.gradeCode = gradeCode;
		this.value = value;
	}
	
	public static SchoolGrade getSchoolGrade(String gradeCode) {
		for (SchoolGrade s: values()) {
			if (s.gradeCode.equals(gradeCode)) {
				return s;
			}
		}
		return UNGRADED;
		
	}
	 
	public String getGradeCode() {
		return gradeCode;
	}
	
	public int getValue() {
		return value;
	}
	
	/** 
	 * Checks to see if school grade is an elementary grade 
	 * (PK, KG, 1st, 2nd, 3rd, 4th, or 5th grade)
	 * @return true if elementary grade, otherwise false
	 */
	public boolean isElementary() {
		if ( (value >= -1) && (value <= 5) ) 
			return true;
		else 
			return false;
	}
	 
	/** 
	 * Checks to see if school grade is an intermediate grade 
	 * (6th, 7th or 8th grade)
	 * @return true if intermediate grade, otherwise false
	 */
	public boolean isIntermediate() {
		if ( (value >= 6) && (value <= 8) ) 
			return true;
		else 
			return false;
	}
	
	/** 
	 * Checks to see if school grade is an high school grade 
	 * (9th, 10th, 11th, or 12th grade)
	 * @return true if intermediate grade, otherwise false
	 */
	public boolean isHigh() {
		if ( (value >= 9) && (value <= 12) ) 
			return true;
		else 
			return false;
	}
	
	
}

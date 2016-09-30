package org.angiedev.parentcomm.web.form;


public class SelectSchoolForm {

	private String filterType;
	private String selectedSchool;
	
	public SelectSchoolForm() {
		
	}
	public String getFilterType() {
		return filterType;
	}
	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}
	public String getSelectedSchool() {
		return selectedSchool;
	}
	public void setSelectedSchool(String selectedSchool) {
		this.selectedSchool = selectedSchool;
	}
	
}

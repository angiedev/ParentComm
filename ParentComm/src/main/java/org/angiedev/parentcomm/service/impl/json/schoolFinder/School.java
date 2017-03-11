package org.angiedev.parentcomm.service.impl.json.schoolFinder;

/**
 * School is a domain class used to consume the data from the 
 * SchoolFinder REST Service.  The data returned from the API is in JSON.  Spring's 
 * rest template uses Jackson JSON processing library to translate this JSON data 
 * into a Java object.
 * <p>
 * @author Angela Gordon 
 */

import org.angiedev.parentcomm.model.SchoolGrade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class School extends org.angiedev.parentcomm.model.School {

	
	@JsonCreator
	public School(@JsonProperty("name") String name,
			@JsonProperty("ncesId") String id,
			@JsonProperty("streetAddr") String streetAddress,
			@JsonProperty("city") String city,
			@JsonProperty("state") String state,
			@JsonProperty("zip") String zip,
			@JsonProperty("lowGrade") String lowGrade,
			@JsonProperty("highGrade") String highGrade) {
		
		this.name = name;
		this.id = id;
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.zip = zip;	
		this.lowGrade = SchoolGrade.getSchoolGrade(lowGrade);
		this.highGrade = SchoolGrade.getSchoolGrade(highGrade);
	}

}

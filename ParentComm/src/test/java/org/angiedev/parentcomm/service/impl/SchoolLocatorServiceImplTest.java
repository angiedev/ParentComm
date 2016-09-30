package org.angiedev.parentcomm.service.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.angiedev.parentcomm.model.School;
import org.angiedev.parentcomm.model.SchoolLevel;
import org.angiedev.parentcomm.service.SchoolLocatorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:config/ParentCommConfig.xml"})
public class SchoolLocatorServiceImplTest {

	@Autowired 
	SchoolLocatorService service;
	
	@Test
	public void testFindSchoolsByGeoLocationWithSmallRadius() throws Exception {
		
		// Address of Safeway parking lot in Almaden
		double latitude = 37.219836;
		double longitude = -121.861611;
		int searchRadius = 2;
		
		List<School> schools = service.findSchoolsByGeoLocation(latitude, longitude, searchRadius);
		
		List<String> schoolNames = new ArrayList<String>();
		
		for (School school: schools) {
			schoolNames.add(school.getName());
		}
		
		assertTrue("Simonds should be found", schoolNames.contains("SIMONDS ELEMENTARY"));
		assertTrue("Graystone should be found", schoolNames.contains("GRAYSTONE ELEMENTARY"));
		assertTrue("Williams should be found", schoolNames.contains("WILLIAMS ELEMENTARY"));
		assertTrue("Castillero should be found", schoolNames.contains("CASTILLERO MIDDLE"));
		assertTrue("Leland should be found", schoolNames.contains("LELAND HIGH"));
		assertFalse("Pioneer should not be found", schoolNames.contains("PIONEER HIGH"));
		assertFalse("Gunderson should not be found", schoolNames.contains("GUNDERSON HIGH"));
		assertFalse("Willow Glen should not be found", schoolNames.contains("WILLOW GLEN ELEMENTARY"));
		assertFalse("Reed should not be found", schoolNames.contains("REED ELEMENTARY"));
	
	}

	@Test
	public void testFindSchoolsByGeoLocationWithLargeRadius() throws Exception {
		
		// Address of Safeway parking lot in Almaden
		double latitude = 37.219836;
		double longitude = -121.861611;
		int searchRadius = 60;
		
		List<School> schools = service.findSchoolsByGeoLocation(latitude, longitude, searchRadius);
		
		List<String> schoolNames = new ArrayList<String>();
		
		for (School school: schools) {
			schoolNames.add(school.getName());
		}
		
		assertTrue("Simonds should be found", schoolNames.contains("SIMONDS ELEMENTARY"));
		assertTrue("Graystone should be found", schoolNames.contains("GRAYSTONE ELEMENTARY"));
		assertTrue("Williams should be found", schoolNames.contains("WILLIAMS ELEMENTARY"));
		assertTrue("Castillero should be found", schoolNames.contains("CASTILLERO MIDDLE"));
		assertTrue("Leland should be found", schoolNames.contains("LELAND HIGH"));
		assertTrue("Pioneer should be found", schoolNames.contains("PIONEER HIGH"));
		assertTrue("Gunderson should be found", schoolNames.contains("GUNDERSON HIGH"));
		assertTrue("Willow Glen should be found", schoolNames.contains("WILLOW GLEN ELEMENTARY"));
		assertTrue("Reed should  be found", schoolNames.contains("REED ELEMENTARY"));
		
	}
	
	
	@Test
	public void testFilterSchoolsByType() throws Exception {
		
		// TESTING SCHOOLS 
		// ALLEN AT STEINBECK                      | KG        | 08    
		// BRET HARTE MIDDLE                       | 06        | 08    
		// CASTILLERO MIDDLE                       | 06        | 08      
		// GRAYSTONE ELEMENTARY                    | KG        | 05   
		// HERMAN (LEONARD) INTERMEDIATE 		   | 05        | 08    
		// LELAND HIGH                             | 09        | 12    
		// LELAND PLUS (CONTINUATION)              | 09        | 12 
		// LOS ALAMITOS ELEMENTARY                 | KG        | 05  
		// LIBERTY HIGH (ALTERNATIVE)              | 06        | 12  
		// SAKAMOTO ELEMENTARY                     | KG        | 06 
		// SIMONDS ELEMENTARY                      | KG        | 05     
		// WILLIAMS ELEMENTARY                     | KG        | 05     
		
		// Geolocation of Safeway parking lot in Almaden
		double latitude = 37.219836;
		double longitude = -121.861611;
		int searchRadius = 2;
		
		List<School> schools = service.findSchoolsByGeoLocation(latitude, longitude, searchRadius);
		
		// Filter by Elementary
		List<School> filteredSchools = service.filterSchoolsByType(schools, SchoolLevel.ELEMENTARY);
		
		List<String> filteredSchoolNames = new ArrayList<String>();
		for (School school: filteredSchools) {
			filteredSchoolNames.add(school.getName());
		}
		
		
		assertTrue("Allen at Steinbeck should be found", filteredSchoolNames.contains("ALLEN AT STEINBECK"));
		assertFalse("Bret Harte should not be found", filteredSchoolNames.contains("BRET HARTE MIDDLE"));
		assertFalse("Castillero should not be found", filteredSchoolNames.contains("CASTILLERO MIDDLE"));
		assertTrue("Graystone should be found", filteredSchoolNames.contains("GRAYSTONE ELEMENTARY"));
		assertTrue("Herman Intermediate should be found", filteredSchoolNames.contains("HERMAN (LEONARD) INTERMEDIATE")); 
		assertFalse("Leland High should not be found", filteredSchoolNames.contains("LELAND HIGH"));
		assertFalse("Leland Plus should not be found", filteredSchoolNames.contains("LELAND PLUS (CONTINUATION)"));
		assertTrue("Los Alamitos should be found", filteredSchoolNames.contains("LOS ALAMITOS ELEMENTARY"));
		assertFalse("Liberty High should not be found", filteredSchoolNames.contains("LIBERTY HIGH (ALTERNATIVE)"));
		assertTrue("Sakmoto should be found", filteredSchoolNames.contains("SAKAMOTO ELEMENTARY"));
		assertTrue("Simonds should be found", filteredSchoolNames.contains("SIMONDS ELEMENTARY"));
		assertTrue("Williams should be found", filteredSchoolNames.contains("WILLIAMS ELEMENTARY"));
		
		
		// Filter by Intermediate
		filteredSchools = service.filterSchoolsByType(schools, SchoolLevel.INTERMEDIATE);
		
		filteredSchoolNames = new ArrayList<String>();
		for (School school: filteredSchools) {
			filteredSchoolNames.add(school.getName());
		}
		
		assertTrue("Allen at Steinbeck should be found", filteredSchoolNames.contains("ALLEN AT STEINBECK"));
		assertTrue("Bret Harte should be found", filteredSchoolNames.contains("BRET HARTE MIDDLE"));
		assertTrue("Castillero should be found", filteredSchoolNames.contains("CASTILLERO MIDDLE"));
		assertFalse("Graystone should not be found", filteredSchoolNames.contains("GRAYSTONE ELEMENTARY"));
		assertTrue("Herman Intermediate should be found", filteredSchoolNames.contains("HERMAN (LEONARD) INTERMEDIATE")); 
		assertFalse("Leland High should not be found", filteredSchoolNames.contains("LELAND HIGH"));
		assertFalse("Leland Plus should not be found", filteredSchoolNames.contains("LELAND PLUS (CONTINUATION)"));
		assertFalse("Los Alamitos should not be found", filteredSchoolNames.contains("LOS ALAMITOS ELEMENTARY"));
		assertTrue("Liberty High should be found", filteredSchoolNames.contains("LIBERTY HIGH (ALTERNATIVE)"));
		assertTrue("Sakmoto should be found", filteredSchoolNames.contains("SAKAMOTO ELEMENTARY"));
		assertFalse("Simonds should not be found", filteredSchoolNames.contains("SIMONDS ELEMENTARY"));
		assertFalse("Williams should not be found", filteredSchoolNames.contains("WILLIAMS ELEMENTARY"));
		

		// Filter by Intermediate
		filteredSchools = service.filterSchoolsByType(schools, SchoolLevel.HIGH);
		
		filteredSchoolNames = new ArrayList<String>();
		for (School school: filteredSchools) {
			filteredSchoolNames.add(school.getName());
		}
		
		assertFalse("Allen at Steinbeck should not be found", filteredSchoolNames.contains("ALLEN AT STEINBECK"));
		assertFalse("Bret Harte should not be found", filteredSchoolNames.contains("BRET HARTE MIDDLE"));
		assertFalse("Castillero should not be found", filteredSchoolNames.contains("CASTILLERO MIDDLE"));
		assertFalse("Graystone should not be found", filteredSchoolNames.contains("GRAYSTONE ELEMENTARY"));
		assertFalse("Herman Intermediate should not be found", filteredSchoolNames.contains("HERMAN (LEONARD) INTERMEDIATE")); 
		assertTrue("Leland High should be found", filteredSchoolNames.contains("LELAND HIGH"));
		assertTrue("Leland Plus should be found", filteredSchoolNames.contains("LELAND PLUS (CONTINUATION)"));
		assertFalse("Los Alamitos should not be found", filteredSchoolNames.contains("LOS ALAMITOS ELEMENTARY"));
		assertTrue("Liberty High should be found", filteredSchoolNames.contains("LIBERTY HIGH (ALTERNATIVE)"));
		assertFalse("Sakmoto should not be found", filteredSchoolNames.contains("SAKAMOTO ELEMENTARY"));
		assertFalse("Simonds should not be found", filteredSchoolNames.contains("SIMONDS ELEMENTARY"));
		assertFalse("Williams should not be found", filteredSchoolNames.contains("WILLIAMS ELEMENTARY"));
	
	}
	
	public void testFilterSchoolsByTypeWithInvalidGradeLevel() throws Exception {
		
		// Geo location of James McEntee Academy  
		// testing with this school since data shows low grade and high grade with invalid value "M"
		double latitude = 37.369450;
		double longitude =-121.836256;
		int searchRadius = 1;
		
		List<School> schools = service.findSchoolsByGeoLocation(latitude, longitude, searchRadius);
		
		boolean found = false;
		for (School school: schools) {
			if (school.getName().equals("JAMES MCENTEE ACADEMY")) {
				found = true; 
				break;
			}
		}
		
		assertTrue("James McEnteee Academy should be found in list of all schools", found);
		
		found = false;
		// Filter by Elementary
		List<School> filteredSchools = service.filterSchoolsByType(schools, SchoolLevel.ELEMENTARY);
		
		for (School school: filteredSchools) {
			if (school.getName().equals("JAMES MCENTEE ACADEMY")) {
				found = true; 
				break;
			}
		}
		assertFalse("James McEnteee Academy should not be found in list of elementary schools", found);
		
		found = false;
		// Filter by Intermediate
		filteredSchools = service.filterSchoolsByType(schools, SchoolLevel.INTERMEDIATE);
		
		for (School school: filteredSchools) {
			if (school.getName().equals("JAMES MCENTEE ACADEMY")) {
				found = true; 
				break;
			}
		}
		assertFalse("James McEnteee Academy should not be found in list of intermediate schools", found);
		
		found = false;
		// Filter by High
		filteredSchools = service.filterSchoolsByType(schools, SchoolLevel.HIGH);
		
		for (School school: filteredSchools) {
			if (school.getName().equals("JAMES MCENTEE ACADEMY")) {
				found = true; 
				break;
			}
		}
		assertFalse("James McEnteee Academy should not be found in list of high schools", found);
	}

	public void testFilterSchoolsByTypeWithN_GradeLevel() throws Exception {
		
		// Geo location of Big Pine Academy  
		// testing with this school since data shows low grade and high grade with value "N"
		double latitude = 37.161973; 
		double longitude = -118.289459;
		int searchRadius = 1;
		
		List<School> schools = service.findSchoolsByGeoLocation(latitude, longitude, searchRadius);
		
		boolean found = false;
		for (School school: schools) {
			if (school.getName().equals("BIG PINE ACADEMY")) {
				found = true; 
				break;
			}
		}
		
		assertTrue("Big Pine Academy should be found in list of all schools", found);
		
		found = false;
		// Filter by Elementary
		List<School> filteredSchools = service.filterSchoolsByType(schools, SchoolLevel.ELEMENTARY);
		
		for (School school: filteredSchools) {
			if (school.getName().equals("BIG PINE ACADEMY")) {
				found = true; 
				break;
			}
		}
		assertFalse("Big Pine Academy should not be found in list of elementary schools", found);
		
		found = false;
		// Filter by Intermediate
		filteredSchools = service.filterSchoolsByType(schools, SchoolLevel.INTERMEDIATE);
		
		for (School school: filteredSchools) {
			if (school.getName().equals("BIG PINE ACADEMY")) {
				found = true; 
				break;
			}
		}
		assertFalse("Big Pine Academy should not be found in list of intermediate schools", found);
		
		found = false;
		// Filter by High
		filteredSchools = service.filterSchoolsByType(schools, SchoolLevel.HIGH);
		
		for (School school: filteredSchools) {
			if (school.getName().equals("BIG PINE ACADEMY")) {
				found = true; 
				break;
			}
		}
		assertFalse("Big Pine Academy should not be found in list of high schools", found);
	}

}


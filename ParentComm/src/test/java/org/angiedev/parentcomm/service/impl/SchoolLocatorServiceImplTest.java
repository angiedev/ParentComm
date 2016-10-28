package org.angiedev.parentcomm.service.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.angiedev.parentcomm.model.School;
import org.angiedev.parentcomm.model.SchoolGrade;
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
		
		List<School> schools =  service.findSchoolsByGeoLocation(latitude, longitude, searchRadius, 100);
		
		List<String> schoolNames = new ArrayList<String>();
		
		for (School school: schools) {
			schoolNames.add(school.getName());
		}
		
		assertTrue("Simonds should be found", schoolNames.contains("Simonds Elementary"));
		assertTrue("Graystone should be found", schoolNames.contains("Graystone Elementary"));
		assertTrue("Williams should be found", schoolNames.contains("Williams Elementary"));
		assertTrue("Castillero should be found", schoolNames.contains("Castillero Middle"));
		assertTrue("Leland should be found", schoolNames.contains("Leland High"));
		assertFalse("Pioneer should not be found", schoolNames.contains("Pioneer High"));
		assertFalse("Gunderson should not be found", schoolNames.contains("Gunderson High"));
		assertFalse("Willow Glen should not be found", schoolNames.contains("Willow Glen Elementary"));
		assertFalse("Reed should not be found", schoolNames.contains("Reed Elementary"));
	
	}

	@Test
	public void testFindSchoolsByGeoLocationWithLargerRadius() throws Exception {
		
		// Address of Safeway parking lot in Almaden
		double latitude = 37.219836;
		double longitude = -121.861611;
		int searchRadius = 5;
		
		List<School> schools = service.findSchoolsByGeoLocation(latitude, longitude, searchRadius, 100);
		
		List<String> schoolNames = new ArrayList<String>();
		
		for (School school: schools) {
			schoolNames.add(school.getName());
		}
		
		assertTrue("Simonds should be found", schoolNames.contains("Simonds Elementary"));
		assertTrue("Graystone should be found", schoolNames.contains("Graystone Elementary"));
		assertTrue("Williams should be found", schoolNames.contains("Williams Elementary"));
		assertTrue("Castillero should be found", schoolNames.contains("Castillero Middle"));
		assertTrue("Leland should be found", schoolNames.contains("Leland High"));
		assertTrue("Pioneer should be found", schoolNames.contains("Pioneer High"));
		assertTrue("Gunderson should be found", schoolNames.contains("Gunderson High"));
		assertFalse("Willow Glen should not be found", schoolNames.contains("Willow Glen Elementary"));
		assertTrue("Reed should  be found", schoolNames.contains("Reed Elementary"));
		
	}
	
	
	@Test
	public void testFilterSchoolsByType() throws Exception {
		
		// TESTING SCHOOLS 
		// Allen At Steinbeck                      | KG        | 08    
		// Bret Harte Middle                       | 06        | 08    
		// Castillero MIDDLE                       | 06        | 08      
		// GRAYSTONE ELEMENTARY                    | KG        | 05   
		// Herma (Leonard) Intermediate 		   | 05        | 08    
		// Leland HIGH                             | 09        | 12    
		// Leland PLUS (Continuation)              | 09        | 12 
		// Los Alamitos ELEMENTARY                 | KG        | 05  
		// Liberty HIGH (Alternative)              | 06        | 12  
		// Sakamoto ELEMENTARY                     | KG        | 06 
		// SIMONDS ELEMENTARY                      | KG        | 05     
		// Williams ELEMENTARY                     | KG        | 05     
		
		// Geolocation of Safeway parking lot in Almaden
		double latitude = 37.219836;
		double longitude = -121.861611;
		int searchRadius = 2;
		
		List<School> schools = service.findSchoolsByGeoLocation(latitude, longitude, searchRadius, 100);
		
		// Filter by Elementary
		List<School> filteredSchools = service.filterSchoolsByType(schools, SchoolLevel.ELEMENTARY);
		
		List<String> filteredSchoolNames = new ArrayList<String>();
		for (School school: filteredSchools) {
			filteredSchoolNames.add(school.getName());
		}
		
		
		assertTrue("Allen At Steinbeck should be found", filteredSchoolNames.contains("Allen At Steinbeck"));
		assertFalse("Bret Harte should not be found", filteredSchoolNames.contains("Bret Harte Middle"));
		assertFalse("Castillero should not be found", filteredSchoolNames.contains("Castillero Middle"));
		assertTrue("Graystone should be found", filteredSchoolNames.contains("Graystone Elementary"));
		assertTrue("Herman Intermediate should be found", filteredSchoolNames.contains("Herman (Leonard) Intermediate")); 
		assertFalse("Leland High should not be found", filteredSchoolNames.contains("Leland High"));
		assertFalse("Leland Plus should not be found", filteredSchoolNames.contains("Leland Plus (Continuation)"));
		assertTrue("Los Alamitos should be found", filteredSchoolNames.contains("Los Alamitos Elementary"));
		assertFalse("Liberty High should not be found", filteredSchoolNames.contains("Liberty High (Alternative)"));
		assertTrue("Sakmoto should be found", filteredSchoolNames.contains("Sakamoto Elementary"));
		assertTrue("Simonds should be found", filteredSchoolNames.contains("Simonds Elementary"));
		assertTrue("Williams should be found", filteredSchoolNames.contains("Williams Elementary"));
		
		
		// Filter by Intermediate
		filteredSchools = service.filterSchoolsByType(schools, SchoolLevel.INTERMEDIATE);
		
		filteredSchoolNames = new ArrayList<String>();
		for (School school: filteredSchools) {
			filteredSchoolNames.add(school.getName());
		}
		
		assertTrue("Allen At Steinbeck should be found", filteredSchoolNames.contains("Allen At Steinbeck"));
		assertTrue("Bret Harte should be found", filteredSchoolNames.contains("Bret Harte Middle"));
		assertTrue("Castillero should be found", filteredSchoolNames.contains("Castillero Middle"));
		assertFalse("Graystone should not be found", filteredSchoolNames.contains("Graystone Elementary"));
		assertTrue("Herman Intermediate should be found", filteredSchoolNames.contains("Herman (Leonard) Intermediate")); 
		assertFalse("Leland High should not be found", filteredSchoolNames.contains("Leland High"));
		assertFalse("Leland Plus should not be found", filteredSchoolNames.contains("Leland Plus (Continuation)"));
		assertFalse("Los Alamitos should not be found", filteredSchoolNames.contains("Los Alamitos Elementary"));
		assertTrue("Liberty High should be found", filteredSchoolNames.contains("Liberty High (Alternative)"));
		assertTrue("Sakmoto should be found", filteredSchoolNames.contains("Sakamoto Elementary"));
		assertFalse("Simonds should not be found", filteredSchoolNames.contains("Simonds Elementary"));
		assertFalse("Williams should not be found", filteredSchoolNames.contains("Williams Elementary"));
		

		// Filter by Intermediate
		filteredSchools = service.filterSchoolsByType(schools, SchoolLevel.HIGH);
		
		filteredSchoolNames = new ArrayList<String>();
		for (School school: filteredSchools) {
			filteredSchoolNames.add(school.getName());
		}
		
		assertFalse("Allen At Steinbeck should not be found", filteredSchoolNames.contains("Allen At Steinbeck"));
		assertFalse("Bret Harte should not be found", filteredSchoolNames.contains("Bret Harte Middle"));
		assertFalse("Castillero should not be found", filteredSchoolNames.contains("Castillero MIDDLE"));
		assertFalse("Graystone should not be found", filteredSchoolNames.contains("Graystone Elementary"));
		assertFalse("Herman Intermediate should not be found", filteredSchoolNames.contains("Herman (Leonard) Intermediate")); 
		assertTrue("Leland High should be found", filteredSchoolNames.contains("Leland High"));
		assertTrue("Leland Plus should be found", filteredSchoolNames.contains("Leland Plus (Continuation)"));
		assertFalse("Los Alamitos should not be found", filteredSchoolNames.contains("Los Alamitos Elementary"));
		assertTrue("Liberty High should be found", filteredSchoolNames.contains("Liberty High (Alternative)"));
		assertFalse("Sakmoto should not be found", filteredSchoolNames.contains("Sakamoto Elementary"));
		assertFalse("Simonds should not be found", filteredSchoolNames.contains("Simonds Elementary"));
		assertFalse("Williams should not be found", filteredSchoolNames.contains("Williams Elementary"));
	
	}
	
	@Test
	public void testFilterSchoolsByTypeWithInvalidGradeLevel() throws Exception {
		
		// Geo location of James Mcentee Academy  
		// testing with this school since data shows low grade and high grade with invalid value "M"
		double latitude = 37.369450;
		double longitude =-121.836256;
		int searchRadius = 1;
		
		List<School> schools = service.findSchoolsByGeoLocation(latitude, longitude, searchRadius, 100);
		
		boolean found = false;
		for (School school: schools) {
			if (school.getName().equals("James Mcentee Academy")) {
				found = true; 
				break;
			}
		}
		
		assertTrue("James McEnteee Academy should be found in list of all schools", found);
		
		found = false;
		// Filter by Elementary
		List<School> filteredSchools = service.filterSchoolsByType(schools, SchoolLevel.ELEMENTARY);
		
		for (School school: filteredSchools) {
			if (school.getName().equals("James Mcentee Academy")) {
				found = true; 
				break;
			}
		}
		assertFalse("James McEnteee Academy should not be found in list of elementary schools", found);
		
		found = false;
		// Filter by Intermediate
		filteredSchools = service.filterSchoolsByType(schools, SchoolLevel.INTERMEDIATE);
		
		for (School school: filteredSchools) {
			if (school.getName().equals("James Mcentee Academy")) {
				found = true; 
				break;
			}
		}
		assertFalse("James McEnteee Academy should not be found in list of intermediate schools", found);
		
		found = false;
		// Filter by High
		filteredSchools = service.filterSchoolsByType(schools, SchoolLevel.HIGH);
		
		for (School school: filteredSchools) {
			if (school.getName().equals("James Mcentee Academy")) {
				found = true; 
				break;
			}
		}
		assertFalse("James McEnteee Academy should not be found in list of high schools", found);
	}

	@Test
	public void testFilterSchoolsByTypeWithN_GradeLevel() throws Exception {
		
		// Geo location of Big Pine Academy  
		// testing with this school since data shows low grade and high grade with value "N"
		double latitude = 37.161973; 
		double longitude = -118.289459;
		int searchRadius = 1;
		
		List<School> schools = service.findSchoolsByGeoLocation(latitude, longitude, searchRadius, 100);
		
		boolean found = false;
		for (School school: schools) {
			if (school.getName().equals("Big Pine Academy")) {
				found = true; 
				break;
			}
		}
		
		assertTrue("Big Pine Academy should be found in list of all schools", found);
		
		found = false;
		// Filter by Elementary
		List<School> filteredSchools = service.filterSchoolsByType(schools, SchoolLevel.ELEMENTARY);
		
		for (School school: filteredSchools) {
			if (school.getName().equals("Big Pine Academy")) {
				found = true; 
				break;
			}
		}
		assertFalse("Big Pine Academy should not be found in list of elementary schools", found);
		
		found = false;
		// Filter by Intermediate
		filteredSchools = service.filterSchoolsByType(schools, SchoolLevel.INTERMEDIATE);
		
		for (School school: filteredSchools) {
			if (school.getName().equals("Big Pine Academy")) {
				found = true; 
				break;
			}
		}
		assertFalse("Big Pine Academy should not be found in list of intermediate schools", found);
		
		found = false;
		// Filter by High
		filteredSchools = service.filterSchoolsByType(schools, SchoolLevel.HIGH);
		
		for (School school: filteredSchools) {
			if (school.getName().equals("Big Pine Academy")) {
				found = true; 
				break;
			}
		}
		assertFalse("Big Pine Academy should not be found in list of high schools", found);
	}

	@Test
	public void testGetSchoolByNcesIdWithValidId() throws Exception {
		String ncesId = "063459005738";  // NCES Id of Simonds Elementary
		School s = service.getSchoolByNcesId(ncesId);
		
		assertNotNull("School should not be null", s);
		assertTrue("School name should be Simonds Elementary", s.getName().equals("Simonds Elementary"));
		assertTrue("School city should be San Jose", s.getCity().equals("San Jose"));
		assertTrue("School address should be 6515 Grapevine Way", s.getStreetAddress().equals("6515 Grapevine Way"));
		assertTrue("School state should be CA", s.getState().equals("CA"));
		assertTrue("School zip should be 95120", s.getZip().equals("95120"));
		assertTrue("School low grade should be KG", s.getLowGrade().equals(SchoolGrade.KINDER));
		assertTrue("School high grade should be KG", s.getHighGrade().equals(SchoolGrade.FIFTH));
		assertTrue("School id should be 063459005738", s.getId().equals("063459005738"));
	}

}


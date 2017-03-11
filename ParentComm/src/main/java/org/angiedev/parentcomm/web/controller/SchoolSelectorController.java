package org.angiedev.parentcomm.web.controller;

import org.angiedev.parentcomm.model.GeoLocation;
import org.angiedev.parentcomm.model.School;
import org.angiedev.parentcomm.model.SchoolLevel;
import org.angiedev.parentcomm.model.SearchRadius;
import org.angiedev.parentcomm.model.SearchType;
import org.angiedev.parentcomm.service.SchoolLocatorService;
import org.angiedev.parentcomm.util.Props;
import org.angiedev.parentcomm.web.form.SchoolFinderForm;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * SchoolLocatorController handles requests to search for schools and filter the results.
 * @author Angela Gordon
 */

@Controller
@RequestMapping("/schools")
public class SchoolSelectorController {
	
	@Autowired 
	private SchoolLocatorService schoolLocatorService;
	
	private static final int MAX_NUM_RESULTS = 100; 

	// Session Keys to store user's search data
	private static final String SESSION_FULL_SEARCH_RESULT = "UserFullSearchResult";
	private static final String SESSION_FILTERED_SEARCH_RESULT ="UserFilteredSearchResult";
	private static final String SESSION_USER_GEO_LOCATION = "UserGeoLocation";
	
	// Model keys used to pass data to form 
	private static final String MODEL_FINDER_FORM = "finderForm";
	private static final String MODEL_SCHOOL_LEVEL = "schoolLevel";
	private static final String MODEL_SCHOOLS = "schools";
	private static final String MODEL_ERROR_STRING = "error";
	
	// JSPS  
	private static final String FIND_SCHOOLS_JSP = "findSchool";
	private static final String SELECT_SCHOOL_JSP = "selectSchool";
	

	private static final Logger logger = Logger.getLogger(SchoolSelectorController.class);
	
	/**
	 * Returns a page to the user which requests the user for either a school name or 
	 * an address to locate a school nearby 
	 * @return ModelAndView  findsSchools view and finder form data
	 */
	@RequestMapping (value="find", method=RequestMethod.GET)
	public ModelAndView getNameOrAddressPage(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.addObject(MODEL_FINDER_FORM, new SchoolFinderForm());
		mav.setViewName(FIND_SCHOOLS_JSP);
		clearSessionSearchParams(session);
		return mav;
	}
	
	/**
	 * Searches for the school based on the school name or the address the user entered 
	 * @param finderForm form values on which to base the search 
	 * @param session session object needed to retain the list of schools found 
	 *        so that the results can later be filtered by the user 
	 * @return ModelAndView select school view and form data and search results 
	 */
	@RequestMapping (value="find", method=RequestMethod.POST)
	public ModelAndView findSchools(@ModelAttribute("finderForm") SchoolFinderForm finderForm,
			HttpSession session) {
	
		ModelAndView mav = new ModelAndView();
		List<School> schools = null;
		String errorStr = null;	
		
		// Mark as address search if search value starts with number
		finderForm.setSearchType( (Character.isDigit(finderForm.getSearchValue().trim().charAt(0))) ?
				SearchType.BY_ADDRESS : SearchType.BY_NAME);
			
		switch (finderForm.getSearchType()) {
			
		case BY_ADDRESS: 
			try {
				schools = schoolLocatorService.findSchoolsByAddress(
						finderForm.getSearchValue(), 
						finderForm.getRadius(),
						MAX_NUM_RESULTS);
			
			} catch (IOException e) {
				errorStr = "INVALID_ADDRESS";
			}
			break;
			
		case BY_NAME: 
			if ((finderForm.getLatitude() == 0) || (finderForm.getLongitude() == 0)) {
				// require geo location to be sent to narrow down list of schools
				errorStr = "GEOLOCATION_DISABLED";
			} else {
				schools = schoolLocatorService.findSchoolsByNameAndGeoLocation(
					finderForm.getSearchValue(), 
					finderForm.getLatitude(),
					finderForm.getLongitude(),
					finderForm.getRadius(), 
					MAX_NUM_RESULTS);
			}
			break;
		}
		
		if (errorStr != null) {
			mav.addObject(MODEL_FINDER_FORM, new SchoolFinderForm());
			mav.addObject(MODEL_ERROR_STRING, errorStr);
			mav.setViewName(FIND_SCHOOLS_JSP);
		} else {
		 	// save set of schools and search params in session to support later filtering operations
			if (finderForm.getSearchType() == SearchType.BY_NAME) {
				session.setAttribute(SESSION_USER_GEO_LOCATION,
					new GeoLocation(finderForm.getLatitude(), finderForm.getLongitude()));
			}
			session.setAttribute(SESSION_FULL_SEARCH_RESULT, schools);
			session.setAttribute(SESSION_FILTERED_SEARCH_RESULT, schools);
			mav.addObject(MODEL_SCHOOLS,schools);
			mav.addObject(MODEL_FINDER_FORM, finderForm);
			mav.setViewName(SELECT_SCHOOL_JSP);
		}
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="filter", method=RequestMethod.POST, params={"newSchoolLevel"})
	public ModelAndView filterSchoolsBySchoolLevel(@RequestParam("newSchoolLevel") String newSchoolLevelString,
			@ModelAttribute("finderForm") SchoolFinderForm finderForm,
			HttpSession session) {
		
		ModelAndView mav = new ModelAndView();
		List<School> filteredSchools = null;
		
		SchoolLevel schoolLevel =  SchoolLevel.valueOf(newSchoolLevelString);
		finderForm.setSchoolLevel(schoolLevel);
		
		// get previous search results since user must want to filter results
		List<School> allSchools = 
			(List<School>)session.getAttribute(SESSION_FULL_SEARCH_RESULT);
		
		
		if (allSchools != null) {		
			if (schoolLevel == SchoolLevel.ALL) {
				filteredSchools = allSchools;
			} else {
				filteredSchools = schoolLocatorService.filterSchoolsBySchoolLevel(
					allSchools,  schoolLevel);
			}
		}
		session.setAttribute(SESSION_FILTERED_SEARCH_RESULT, filteredSchools);
		mav.setViewName(SELECT_SCHOOL_JSP);
		mav.addObject(MODEL_SCHOOLS,filteredSchools);
		mav.addObject(MODEL_FINDER_FORM, finderForm);
		return mav;		
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="filter", method=RequestMethod.POST)
	public ModelAndView filterSchoolsBySearchRadius(@ModelAttribute("finderForm") SchoolFinderForm finderForm,
			HttpSession session) {
		
		ModelAndView mav = new ModelAndView();
		List<School> schools = null;
		List<School> filteredSchools = null;
		
		if (finderForm.getSearchType() == SearchType.BY_NAME) {
			schools = schoolLocatorService.findSchoolsByNameAndGeoLocation(
				finderForm.getSearchValue(),
				((GeoLocation)session.getAttribute(SESSION_USER_GEO_LOCATION)).getLatitude(),
				((GeoLocation)session.getAttribute(SESSION_USER_GEO_LOCATION)).getLongitude(),
				finderForm.getRadius(), MAX_NUM_RESULTS);
		} else {
			try {
				schools = schoolLocatorService.findSchoolsByAddress(
						finderForm.getSearchValue(),
						finderForm.getRadius(),
						MAX_NUM_RESULTS);
			} catch (IOException e) {
				// should not reach here.. addresss should already be valid at this point
				logger.error("IOException thrown when calling findSchoolsByAddress to " + 
						"support change search radius operation: " + e);
				schools = (List<School>)session.getAttribute(SESSION_FULL_SEARCH_RESULT);
			}
		}
		if (finderForm.getSchoolLevel() != SchoolLevel.ALL) {
			filteredSchools = schoolLocatorService.filterSchoolsBySchoolLevel(
					schools, finderForm.getSchoolLevel());
		} else {
			filteredSchools = schools;
		}
		mav.setViewName(SELECT_SCHOOL_JSP);
		mav.addObject(MODEL_SCHOOLS, filteredSchools);
		mav.addObject(MODEL_FINDER_FORM, finderForm);
		session.setAttribute(SESSION_FULL_SEARCH_RESULT, schools);
		session.setAttribute(SESSION_FILTERED_SEARCH_RESULT, filteredSchools);
		return mav;		
	}
	
	/* Clears out search parameters from user's session so user can start a fresh search */
	private void clearSessionSearchParams(HttpSession session) {
		session.removeAttribute(SESSION_FULL_SEARCH_RESULT);
		session.removeAttribute(SESSION_FILTERED_SEARCH_RESULT);
		session.removeAttribute(SESSION_USER_GEO_LOCATION);
	}
}
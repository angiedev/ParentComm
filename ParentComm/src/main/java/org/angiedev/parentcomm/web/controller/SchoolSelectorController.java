package org.angiedev.parentcomm.web.controller;

import org.angiedev.parentcomm.model.GeoLocation;
import org.angiedev.parentcomm.model.School;
import org.angiedev.parentcomm.model.SchoolLevel;
import org.angiedev.parentcomm.service.GeoLocatorService;
import org.angiedev.parentcomm.service.SchoolLocatorService;
import org.angiedev.parentcomm.util.Props;
import org.angiedev.parentcomm.web.form.SchoolFinderForm;

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
 * SchoolLocatorController handles requests to search for schools and filters the results.
 * @author Angela Gordon
 */

@Controller
@RequestMapping("/schools")
public class SchoolSelectorController {
	
	@Autowired 
	private SchoolLocatorService schoolLocatorService;
	
	@Autowired
	private GeoLocatorService geoLocatorService;
	
	private final int MAX_NUM_RESULTS = 100; 
	
	/**
	 * Returns a page to the user which requests the user for either a school name or 
	 * an address to locate a school nearby 
	 * @return ModelAndView object for address view and supporting address object
	 */
	@RequestMapping (value="find", method=RequestMethod.GET)
	public ModelAndView nameOrAddress() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("finderForm", new SchoolFinderForm());
		mav.setViewName("findSchool");
		return mav;
	}
	
	/**
	 * Searches for the school based on the school name or the address the user entered 
	 * @param finderForm form values on which to base the search 
	 * @param session session object needed to retain the list of schools found 
	 *        so that the results can later be filtered by the user 
	 * @return ModelAndView object showing list of school choices for the user
	 */
	@RequestMapping (value="find", method=RequestMethod.POST)
	public ModelAndView findSchools(@ModelAttribute("finderForm") SchoolFinderForm finderForm,
			HttpSession session) {
	
		ModelAndView mav = new ModelAndView();
		List<School> schools = null;
		String errorStr = null;	
		
			
		if (Character.isDigit(finderForm.getSearchValue().trim().charAt(0))) {
			// Assume address search if search value starts with number
			try {
				// use default search radius if not specified by the user
				if (finderForm.getRadius() == 0) {
					finderForm.setRadius(Props.getInstance().getSearchRadiusForAddressSearch());
				}
				
				GeoLocation geoLocation = geoLocatorService.getGeoLocationForAddress(
						finderForm.getSearchValue());
				
				schools = schoolLocatorService.findSchoolsByGeoLocation(
						geoLocation.getLatitude(), geoLocation.getLongitude(), 
						finderForm.getRadius(), MAX_NUM_RESULTS);
				
			} catch (IOException e) {
				errorStr = "INVALID_ADDRESS";
			}
			
		} else { 
			// Assuming school name search 
			if ((finderForm.getLatitude() == 0) ||
				(finderForm.getLongitude() == 0)) {
				// require geo location to be sent to narrow down list of schools
				errorStr = "GEOLOCATION_DISABLED";

			} else {
				if (finderForm.getRadius() == 0) {
					finderForm.setRadius(Props.getInstance().getSearchRadiusForNameSearch());
				}
				schools = schoolLocatorService.findSchoolsByNameAndGeoLocation(
					finderForm.getSearchValue(), 
					finderForm.getLatitude(),
					finderForm.getLongitude(),
					finderForm.getRadius(), MAX_NUM_RESULTS);
				if (schools.size() == 0) {
					// If no schools were found then widen search across US
					finderForm.setMaxRadius();
					schools = schoolLocatorService.findSchoolsByNameAndGeoLocation(
							finderForm.getSearchValue(), 
							finderForm.getLatitude(),
							finderForm.getLongitude(),
							finderForm.getRadius(), MAX_NUM_RESULTS);
					
				}
			}
		}
		
		if (errorStr != null) {
			mav.addObject("school", new SchoolFinderForm());
			mav.addObject("error", errorStr);
			mav.setViewName("findSchool");
		} else {
		 	// save set of schools in session to support filtering operation
			session.setAttribute("schoolSearchResult", schools);
			mav.addObject("schools",schools);
			mav.addObject("finderForm", finderForm);
			mav.setViewName("selectSchool");
		}
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="select", method=RequestMethod.POST, params = {"filterType"})
	public ModelAndView filterSchools(@RequestParam("filterType") String filterType,
			@ModelAttribute("finderForm") SchoolFinderForm finderForm,
			HttpSession session) {
		
		ModelAndView mav = new ModelAndView();
	
		// get previous search results since user must want to filter results
		List<School> schools = 
			(List<School>)session.getAttribute("schoolSearchResult");
	
		// if no results available take user back to page to input school name
		if (schools == null) {
			mav.addObject("school", new SchoolFinderForm());
			mav.setViewName("findSchool");
		} else {
			mav.setViewName("selectSchool");
			mav.addObject("filterType", filterType);
			if (filterType.equals("ALL")) {
				mav.addObject("schools", schools);
			} else {
				mav.addObject("schools",
					schoolLocatorService.filterSchoolsByType(schools,  SchoolLevel.valueOf(filterType)));
			}
		}
		mav.addObject("finderForm", finderForm);
		return mav;		
	}
	
	/*
	@RequestMapping(value="select", method=RequestMethod.POST)
	public ModelAndView changeSearchRadiusSchools(@RequestParam("radius") int radius, HttpSession session) {
		
		ModelAndView mav = new ModelAndView();
	
		// get previous search results since user must want to filter results
		List<School> schools = 
			(List<School>)session.getAttribute("schoolSearchResult");
	
		return mav;		
	}
	*/
}
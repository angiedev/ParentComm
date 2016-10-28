package org.angiedev.parentcomm.web.controller;

import org.angiedev.parentcomm.model.GeoLocation;
import org.angiedev.parentcomm.model.School;
import org.angiedev.parentcomm.model.SchoolLevel;
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
	
	/**
	 * Directs the user to a page which enables a user to enter an address
	 * to locate a school near by 
	 * @return ModelAndView object for address view and supporting address object
	 */
	
	@RequestMapping (value="input", method=RequestMethod.GET)
	public ModelAndView nameOrAddress() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("finderForm", new SchoolFinderForm());
		mav.setViewName("inputSchool");
		return mav;
	}
	
	@RequestMapping (value="findSchool", method=RequestMethod.POST)
	public ModelAndView findSchools(@ModelAttribute("finderForm") SchoolFinderForm finderForm,
			HttpSession session) {
	
		ModelAndView mav = new ModelAndView();
		List<School> schools = null;
		int maxNumResults = 100;
		String errorStr = null;	
			
		
		if (Character.isDigit(finderForm.getSearchValue().trim().charAt(0))) {
			// Assuming address search if search value starts with number
			try {
				GeoLocation geoLocation = schoolLocatorService.
						getGeoLocationForAddress(finderForm.getSearchValue());
				schools = schoolLocatorService.findSchoolsByGeoLocation(
						geoLocation.getLatitude(), geoLocation.getLongitude(), 
						Props.getInstance().getSearchRadiusForAddressSearch(), maxNumResults);
			} catch (IOException e) {
				errorStr = "INVALID_ADDRESS";
			}
		} else { 
			// Assuming school name search 
			if (finderForm.getLatitude().equals("") ||
				finderForm.getLongitude().equals("")) {
				// require geo location to be sent to narrow down list of schools
				errorStr = "GEOLOCATION_DISABLED";
			} else {
				schools = schoolLocatorService.findSchoolsByNameAndGeoLocation(
					finderForm.getSearchValue(), 
					Double.parseDouble(finderForm.getLatitude()),
					Double.parseDouble(finderForm.getLongitude()),
					Props.getInstance().getSearchRadiusForNameSearch(), maxNumResults);
				if (schools.size() == 0) {
					// If no schools were found then widen search across US
					schools = schoolLocatorService.findSchoolsByNameAndGeoLocation(
							finderForm.getSearchValue(), 
							Double.parseDouble(finderForm.getLatitude()),
							Double.parseDouble(finderForm.getLongitude()),
							10000, maxNumResults);
				}
			}
		}
		
		if (errorStr != null) {
			mav.addObject("school", new SchoolFinderForm());
			mav.addObject("error", errorStr);
			mav.setViewName("getSchoolNameOrAddress");
		} else {
		 	// save set of schools in session to support filtering operation
			session.setAttribute("schoolSearchResult", schools);
			mav.addObject("schools",schools);
			mav.setViewName("selectSchool");
		}
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="selectSchool", method=RequestMethod.POST, params = {"filterType"} )
	public ModelAndView filterSchools(@RequestParam() String filterType, HttpSession session) {
		
		ModelAndView mav = new ModelAndView();
		
		// get previous search results since user must want to filter results
		List<School> schools = 
			(List<School>)session.getAttribute("schoolSearchResult");
	
		// if no results available take user back to page to input school name
		if (schools == null) {
			mav.addObject("school", new SchoolFinderForm());
			mav.setViewName("inputSchool");
		} else {
			mav.setViewName("selectSchool");
			mav.addObject("filterType", filterType);
			if (filterType.equals("ALL")) {
				mav.addObject("schools", schools);
			} else {
				mav.addObject("schools",
					schoolLocatorService.filterSchoolsByType(schools, SchoolLevel.valueOf(filterType) ));
			}
		}
		return mav;		
	}
}
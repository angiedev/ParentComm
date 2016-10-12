package org.angiedev.parentcomm.web.controller;

import org.angiedev.parentcomm.model.School;
import org.angiedev.parentcomm.model.SchoolLevel;
import org.angiedev.parentcomm.service.SchoolLocatorService;
import org.angiedev.parentcomm.web.form.AddressForm;
import org.angiedev.parentcomm.web.form.SchoolNameForm;

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
	 * Directs the user to a page which enables a user to enter a school name
	 * to locate a school with a matching name near by 
	 * @return ModelAndView object for address view and supporting address object
	 */
	@RequestMapping (value="/inputName", method=RequestMethod.GET)
	public ModelAndView getSchool() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("school", new SchoolNameForm());
		mav.setViewName("getSchoolName");
		return mav;
	}

	/**
	 * Directs the user to a page which enables a user to enter an address
	 * to locate a school near by 
	 * @return ModelAndView object for address view and supporting address object
	 */
	
	@RequestMapping (value="/inputAddress", method=RequestMethod.GET)
	public ModelAndView address() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("address", new AddressForm());
		mav.setViewName("getAddress");
		return mav;
	}
	

	/**
	 * Retrieves the schools located near the passed in address and redirects user
	 * to a page which enables a user to select their school
	 * @param address 	street address being searched near 
	 * @param session	http session required to store search results
	 * @return			ModelAndView object for selectSchool view and supporting 
	 * 					schoolForm object and list of schools
	 */
	@RequestMapping (value="findByAddress", method=RequestMethod.POST)
	public ModelAndView findSchoolsBasedOnAddress(@ModelAttribute("address") AddressForm address,
			HttpSession session) {
	
		ModelAndView mav = new ModelAndView();
		int searchRadius = 5; // hard code for now
		
		// if no geo location data found ask user to reenter an address
		if (address.getLatitude().equals("") || address.getLongitude().equals("")) {
			mav.addObject("address", new AddressForm());
			mav.setViewName("getAddress");
		} else {
			List<School> schools = schoolLocatorService.findSchoolsByGeoLocation(
					Double.parseDouble(address.getLatitude()), 
					Double.parseDouble(address.getLongitude()), 
					searchRadius);
			mav.addObject("schools",schools);
			mav.setViewName("selectSchool");
			// save set of schools in session to support filtering operation
			session.setAttribute("schoolSearchResult", schools);
		}
		return mav;
	}

	@RequestMapping (value="findByName", method=RequestMethod.POST)
	public ModelAndView findSchoolsBasedOnName(@ModelAttribute("name") SchoolNameForm nameForm,
			HttpSession session) {
	
		ModelAndView mav = new ModelAndView();
		int searchRadius = 15; // hard code for now
		
		// if no school name was entered or no geolocation data was obtained then 
		// ask user to enter school name again 
		if (nameForm.getSchoolName().equals("") ||
			nameForm.getLatitude().equals("") ||
			nameForm.getLongitude().equals("")) {
			mav.addObject("school", new SchoolNameForm());
			mav.setViewName("getSchoolName");
		} else {
			List<School> schools = schoolLocatorService.findSchoolsByNameAndGeoLocation(
					nameForm.getSchoolName(),
					Double.parseDouble(nameForm.getLatitude()), 
					Double.parseDouble(nameForm.getLongitude()), 
					searchRadius);
			mav.addObject("schools",schools);
			mav.setViewName("selectSchool");
			// save set of schools in session to support filtering operation
			session.setAttribute("schoolSearchResult", schools);
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
			mav.addObject("school", new SchoolNameForm());
			mav.setViewName("getSchoolName");
		} else {
			mav.setViewName("selectSchool");
			
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
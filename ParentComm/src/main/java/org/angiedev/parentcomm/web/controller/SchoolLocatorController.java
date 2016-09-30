package org.angiedev.parentcomm.web.controller;

import org.angiedev.parentcomm.model.School;
import org.angiedev.parentcomm.model.SchoolLevel;
import org.angiedev.parentcomm.service.SchoolLocatorService;
import org.angiedev.parentcomm.web.form.AddressForm;
import org.angiedev.parentcomm.web.form.SelectSchoolForm;

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
 * @author angie
 */
@Controller
public class SchoolLocatorController {
	
	@Autowired 
	private SchoolLocatorService schoolLocatorService;

	private final int SEARCH_RADIUS = 2; // Hard coded now; will be configuratble in future
	
	/**
	 * Redirects the user to a page which enables a user to enter an address
	 * @return ModelAndView object for address view and supporting address object
	 */
	@RequestMapping (value="/", method=RequestMethod.GET)
	public ModelAndView address() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("address", new AddressForm());
		mav.setViewName("getAddress");
		return mav;
	}

	/**
	 * Retrieves the schools located near the passed in address and redirects user
	 * to a page which enable a user to select a school
	 * @param address 	street address being searched near 
	 * @param session	http session required to store search results
	 * @return			ModelAndView object for selectSchool view and supporting 
	 * 					schoolForm object and list of schools
	 */
	@RequestMapping (value="findSchools", method=RequestMethod.POST)
	public ModelAndView findSchoolsBasedOnAddress(@ModelAttribute("address") AddressForm address,
			HttpSession session) {
	
		ModelAndView mav = null;
		
		if (address.getLatitude().equals("") | address.getLongitude().equals("")) {
			mav = new ModelAndView("redirect:/");
			mav.addObject("address", new AddressForm());
		} else {
			mav = new ModelAndView();
			List<School> schools = schoolLocatorService.findSchoolsByGeoLocation(
					Double.parseDouble(address.getLatitude()), 
					Double.parseDouble(address.getLongitude()), 
					SEARCH_RADIUS);
			mav.addObject("schools",schools);
			mav.addObject("selectSchoolForm", new SelectSchoolForm()); 
			mav.setViewName("selectSchool");
			
			session.setAttribute("schoolSearchResult", schools);
		}
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="selectSchool", method=RequestMethod.POST, params = {"filterType"} )
	public ModelAndView filterSchools(@RequestParam() String filterType, HttpSession session) {
		
		ModelAndView mav = null;
		
		// get previous search results since user must want to filter results
		List<School> schools = 
			(List<School>)session.getAttribute("schoolSearchResult");
	
		// if no results available take user back to page to input address
		if (schools == null) {
			mav = new ModelAndView("redirect:/");
			mav.addObject("address", new AddressForm());
		} else {
			SelectSchoolForm form = new SelectSchoolForm();
			form.setFilterType(filterType);
			
			mav = new ModelAndView();
			mav.addObject("selectSchoolForm",form);
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
	
	/*
	@RequestMapping (value="selectSchool", method=RequestMethod.POST)
	public ModelAndView selectSchool(@ModelAttribute("selectSchoolForm") SelectSchoolForm form) {
		System.out.println("forwarding to select class");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("selectClass");
		// TBD
		return mav;
	}
	*/
	
	
}
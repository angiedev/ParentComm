package org.angiedev.parentcomm.web.controller;

import javax.servlet.http.HttpSession;
import java.util.List; 

import org.angiedev.parentcomm.dao.ClassDAO;
import org.angiedev.parentcomm.model.Class;
import org.angiedev.parentcomm.model.School;
import org.angiedev.parentcomm.service.SchoolLocatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * ClassSelectorController handles requests to search for classes and filters the results.
 * 
 * @author Angela Gordon
 *
 */
@Controller
@RequestMapping(value="/classes")
public class ClassSelectorController {

	@Autowired
	SchoolLocatorService service;
	
	@Autowired 
	ClassDAO dao;
	
	@RequestMapping (method=RequestMethod.GET)
	public ModelAndView loadClasses(@RequestParam("schoolId") String schoolId, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		School school = (School)session.getAttribute(schoolId);
		if (school == null) {
			school = service.getSchoolByNcesId(schoolId);
		} 
		List<Class> classes = dao.getClassesBySchoolId(schoolId);
		mav.addObject("classes", classes);
		mav.addObject("school", school);
		mav.setViewName("selectClass");
		return mav;
	}
}

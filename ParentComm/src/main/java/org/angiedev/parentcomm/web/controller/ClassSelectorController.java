package org.angiedev.parentcomm.web.controller;

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

	@RequestMapping (method=RequestMethod.GET)
	public ModelAndView loadClasses(@RequestParam("schoolId") String schoolId) {
		ModelAndView mav = new ModelAndView();
		//mav.addObject("classes",form);
		mav.setViewName("selectClass");
		return mav;
	}
}

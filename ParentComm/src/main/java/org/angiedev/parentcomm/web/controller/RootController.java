package org.angiedev.parentcomm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Main root controller for application
 * @author Angela Gordon
 *
 */
@Controller
public class RootController {
	
	/**
	 * Redirects user to entry point of application
	 * @return
	 */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView root() {
    	return new ModelAndView("redirect:/schools/find");
    }
}
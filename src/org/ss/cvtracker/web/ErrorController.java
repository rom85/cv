package org.ss.cvtracker.web;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.ss.cvtracker.service.ErrorService;
import org.ss.cvtracker.service.LettersManager;
import org.ss.cvtracker.service.LocationService;
import org.ss.cvtracker.service.ResumeMailService;
import org.ss.cvtracker.service.ResumeService;
import org.ss.cvtracker.service.StatisticService;
import org.ss.cvtracker.service.TechnologyService;
import org.ss.cvtracker.service.mail.reader.MailSession;

@Controller
@RequestMapping(value = "/error")
public class ErrorController {
	@Autowired
	LocationService locationService;
	@Autowired
	TechnologyService technologyService;
	@Autowired
	ResumeService resumeService;
	@Autowired
	LettersManager lettersManager;
	@Autowired
	StatisticService statisticService;
	
	@RequestMapping(method = RequestMethod.GET)
	/**
	 * Returns error JSP page with error code from ErrorService 
	 * @return error.jsp
	 */
	
	public ModelAndView error() {
	    ModelAndView mav = new ModelAndView();
	    Map<String, String> message = new HashMap<String, String>();
	    message.put("message", ErrorService.getErrorStatus());
	    mav.setViewName("error");
	    mav.addObject("message", message);
	    return mav;
	}
	
	
}

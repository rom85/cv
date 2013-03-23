package org.ss.cvtracker.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.ss.cvtracker.service.ResumeService;

@Controller
public class HomeController {
	@Autowired
	ResumeService resumeService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "redirect:/inbox";
	}
}

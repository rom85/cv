package org.ss.cvtracker.web;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.ss.cvtracker.service.LettersManager;
import org.ss.cvtracker.service.LocationService;
import org.ss.cvtracker.service.ResumeService;
import org.ss.cvtracker.service.StatisticService;
import org.ss.cvtracker.service.TechnologyService;

@Controller
@RequestMapping(value = "/statistic")
public class StatisticController {
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
	public ModelAndView statistic() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("statistic");
		mav.addObject("dates", lettersManager.getAllLetters());
		return mav;
	}

	@RequestMapping(value = "/buildTable", method = RequestMethod.GET)
	public ModelAndView buildTable(@RequestParam("param") String param,
			@RequestParam("dateFrom") String dateFrom,
			@RequestParam("dateTo") String dateTo, ModelAndView mav) {
		Set<String> params = new HashSet<String>();

		mav.addObject("dateFrom", dateFrom);
		mav.addObject("dateTo", dateTo);
		mav.addObject("param", param);

		params.add(param);
		if (params.contains("Locations") || params.contains("Location")
				&& params.size() == 1) {
			mav.addObject("nameLocations", locationService.findLocations());
			//HERE CHANGED makeStatisticsForLocations to makeStatisticsForLocations
			mav.addObject(
					"locations",
					statisticService.makeFullStatisticsForLocations(
							locationService.findLocations(), dateFrom, dateTo));
		}
		if (params.contains("Locations and technologies")) {
			mav.addObject("nameLocations", locationService.findLocations());
			mav.addObject("nameTechnologies",
					technologyService.findTechnologies());
			//HERE CHANGED makeStatisticsTechnologiesLocations to makeFullStatisticsTechnologiesLocations
			mav.addObject("specres", statisticService
					.makeFullStatisticsTechnologiesLocations(
							technologyService.findTechnologies(),
							locationService.findLocations(), dateFrom, dateTo));
		}
		if (params.contains("Location and mails")) {
			mav.addObject("nameLocations", locationService.findLocations());
			mav.addObject("nameLetters", statisticService
					.getUniqueReceivedFroms(lettersManager.getAllLetters()));
			//HERE CHANGED makeStatisticsLocationsLetters to makeFullStatisticsLocationsLetters
			mav.addObject("specLetterLocRes", statisticService
					.makeFullStatisticsLocationsLetters(
							locationService.findLocations(),
							lettersManager.getAllLetters(), dateFrom, dateTo));
		}
		if (params.equals(null)) {
			mav.setViewName("statistic");
			return mav;
		}

		mav.setViewName("statistic");
		return mav;
	}
}

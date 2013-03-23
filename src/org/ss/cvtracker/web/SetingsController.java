package org.ss.cvtracker.web;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.ss.cvtracker.domain.Constant;
import org.ss.cvtracker.domain.Country;
import org.ss.cvtracker.domain.Location;
import org.ss.cvtracker.domain.MailAddress;

import org.ss.cvtracker.domain.Technology;
import org.ss.cvtracker.service.ConstantService;
import org.ss.cvtracker.service.CountryService;
import org.ss.cvtracker.service.ErrorService;
import org.ss.cvtracker.service.LocationService;
import org.ss.cvtracker.service.LookForService;
import org.ss.cvtracker.service.MailAdressService;
import org.ss.cvtracker.service.MailServerService;
import org.ss.cvtracker.service.Page;
import org.ss.cvtracker.service.ResumeMailService;
import org.ss.cvtracker.service.ResumePatternService;
import org.ss.cvtracker.service.ResumeService;
import org.ss.cvtracker.service.SchedulerService;
import org.ss.cvtracker.service.TechnologyService;
import org.ss.cvtracker.service.VacancyMailService;
import org.ss.cvtracker.service.VacancyPatternService;
import org.ss.cvtracker.service.VacancyService;

@Controller
/**
 * Manages all settings objects, that are used in settings.jsp.
 */
public class SetingsController {
	// Folder, where all resumes are saved.
	private static final int CONST_RESUME_FOLDER = 2;
	// Folder, where all vacancies are saved.
	private static final int CONST_VACANCY_FOLDER = 4;
	// Name of proxy, that is in use.
	private static final int CONST_PROXY = 3;
	// Name of current country(default value is - 'Ukraine').
	private static final int CONST_COUNTRY = 1;
	//
	private static final int CONST_CRON = 5;
	
	@Autowired
	LocationService locationService;
	@Autowired
	TechnologyService technologyService;
	@Autowired
	ResumeService resumeService;
	@Autowired
	VacancyService vacancyService;
	@Autowired
	MailAdressService mailAdressService;
	@Autowired
	CountryService countryService;
	@Autowired
	MailServerService serverService;
	@Autowired
	ResumeMailService resumeMailService;
	@Autowired
	VacancyMailService vacancyMailService;
	@Autowired
	ConstantService constantService;
	@Autowired
	ResumePatternService resPatternsService;
	@Autowired
	VacancyPatternService vacPatternsService;
	@Autowired
	LookForService lookForService;
	@Autowired
	SchedulerService schedulerService;
	/**
	 * Reacts on 'Settings' button clicking(that is found on the main page).
	 * Adds all necessary attributes to the model and calls the jsp page, that
	 * reflects all settings, that client can use while using CVTracker
	 * application.
	 * 
	 * @param technology
	 *            - represents instance of Technology class, that will be used
	 *            in settings actions.
	 * @param location
	 *            - represents instance of Location class, that will be used in
	 *            settings actions.
	 * @param mailAddress
	 *            - represents instance of MailAddresses class, that will be
	 *            used in settings actions.
	 * @return instance of ModelAndView with set attributes and name of
	 *         necessary view.
	 */
	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public ModelAndView settings(Technology technology, Location location, MailAddress mailAddress) {
		ErrorService.setErrorStatus("");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("settings");
		mav.addObject("locations", locationService.findLocations());
		mav.addObject("technologies", technologyService.findTechnologies());
		mav.addObject("mailAddresses", mailAdressService.findAllMailAdress());
		mav.addObject("servers", serverService.findAllMailServer());
		mav.addObject("mailAddress", mailAddress);
		mav.addObject("location", location);
		mav.addObject("technology", technology);
		mav.addObject("lookFors", lookForService.findAllLookFor());
		mav.addObject("constCron",(constantService.get(CONST_CRON)));
		mav.addObject("constCountry", (constantService.get(CONST_COUNTRY)));
		mav.addObject("constResumeFolder", constantService.get(CONST_RESUME_FOLDER));
		mav.addObject("constVacancyFolder", constantService.get(CONST_VACANCY_FOLDER));
		mav.addObject("constProxy", constantService.get(CONST_PROXY));
		mav.addObject("countries", countryService.findCountries());
		mav.addObject("resLetPatterns", resPatternsService.findPatterns());
		mav.addObject("vacLetPatterns", vacPatternsService.findPatterns());
		return mav;
	}

	/**
	 * Reacts on 'Add mail' button clicking(that is found in the settings.jsp at
	 * 'Mail' tab). Adds all necessary attributes to the model and save new
	 * email address and password for it.
	 * 
	 * @param mailAddress
	 *            - MailAddress object, that should be added to the database.
	 * @param result
	 *            - BindingResult object for mapping model attributes.
	 * @param technology
	 *            - represents instance of Technology class, that is used in
	 *            settings actions.
	 * @param location
	 *            - represents instance of Location class, is used in settings
	 *            actions.
	 * @param action
	 *            - represent 'Add mail' button's value
	 * @return instance of ModelAndView with set attributes and name of
	 *         necessary view.
	 */
	@RequestMapping(value = "/addMailAddress", method = RequestMethod.POST)
	public ModelAndView addMailAddress(@ModelAttribute("mailAddress") @Valid MailAddress mailAddress,
			BindingResult result, Location location, Technology technology, @RequestParam("addUpdateMail") String action) {
		ModelAndView mav = new ModelAndView();
		MailAddress mail = mailAdressService.getMailAdressById(mailAddress.getId());
		System.out.println(action);
		if (result.hasErrors()) {
			mav.setViewName("settings");
			mav.addObject("locations", locationService.findLocations());
			mav.addObject("technologies", technologyService.findTechnologies());
			mav.addObject("mailAddresses", mailAdressService.findAllMailAdress());
			mav.addObject("servers", serverService.findAllMailServer());
			mav.addObject("technology", technology);
			mav.addObject("location", location);
			mav.addObject("lookFors", lookForService.findAllLookFor());

			mav.addObject("constCountry", constantService.get(CONST_COUNTRY));
			mav.addObject("constResumeFolder", constantService.get(CONST_RESUME_FOLDER));
			mav.addObject("constVacancyFolder", constantService.get(CONST_VACANCY_FOLDER));
			mav.addObject("constProxy", constantService.get(CONST_PROXY));
			mav.addObject("countries", countryService.findCountries());
			return mav;
		} else if (action.equals("Add mail")) {
			mailAdressService.add(mailAddress);
			mav.setViewName("redirect:/settings");
			return mav;
		} else if (action.equals("Save changes") && mailAddress.getId() != 0) {
			mail.setAddress(mailAddress.getAddress());
			mail.setLogin(mailAddress.getLogin());
			mail.setMailServerId(mailAddress.getMailServerId());
			mail.setPassword(mailAddress.getPassword());
			mail.setLookFor(mailAddress.getLookFor());
			mailAdressService.update(mail);
			mav.setViewName("redirect:/settings");
			return mav;
		}
		mav.setViewName("redirect:/settings");
		return mav;
	}

	/**
	 * Reacts on 'Cancel' button clicking(that is found in the settings.jsp at
	 * 'Mail' tab). Adds all necessary attributes to the model and cancel
	 * current mail saving operation.
	 * 
	 * @param mailAddress
	 *            - MailAddress object, that should be added to the database.
	 * @param result
	 *            - BindingResult object for mapping model attributes.
	 * @param technology
	 *            - represents instance of Technology class, that is used in
	 *            settings actions.
	 * @param location
	 *            - represents instance of Location class, is used in settings
	 *            actions.
	 * @return instance of ModelAndView with set attributes and name of
	 *         necessary view.
	 */
	@RequestMapping(value = "/cancelMailAddress", method = RequestMethod.POST)
	public ModelAndView cancelMailAddress(@ModelAttribute("mailAddr") @Valid MailAddress mailAddress,
			BindingResult result, Location location, Technology technology) {
		ModelAndView mav = new ModelAndView();
		if (result.hasErrors()) {
			mav.setViewName("settings");
			mav.addObject("locations", locationService.findLocations());
			mav.addObject("technologies", technologyService.findTechnologies());
			mav.addObject("mailAddresses", mailAdressService.findAllMailAdress());
			mav.addObject("servers", serverService.findAllMailServer());
			mav.addObject("technology", technology);
			mav.addObject("location", location);
			mav.addObject("lookFors", lookForService.findAllLookFor());

			mav.addObject("constCountry", constantService.get(CONST_COUNTRY));
			mav.addObject("constResumeFolder", constantService.get(CONST_RESUME_FOLDER));
			mav.addObject("constVacancyFolder", constantService.get(CONST_VACANCY_FOLDER));
			mav.addObject("constProxy", constantService.get(CONST_PROXY));
			mav.addObject("countries", countryService.findCountries());
			return mav;
		} else {
			mav.setViewName("redirect:/settings");
			return mav;
		}
	}

	/**
	 * Reacts on 'Add technology' button clicking(that is found in the
	 * settings.jsp at 'Technology' tab). Adds all necessary attributes to the
	 * model and save new technology.
	 * 
	 * @param technology
	 *            - represents instance of Technology class, that is used in
	 *            settings actions.
	 * @param result
	 *            - BindingResult object for mapping model attributes.
	 * @param location
	 *            - represents instance of Location class, is used in settings
	 *            actions.
	 * @param mailAddress
	 *            - MailAddress object, that should be added to the database.
	 * @param action
	 *            - represents 'Add technology' button's value
	 * @return instance of ModelAndView with set attributes and name of
	 *         necessary view.
	 */
	@RequestMapping(value = "/addTechnology", method = RequestMethod.POST)
	public ModelAndView addTechnology(@ModelAttribute("technology") @Valid Technology technology, BindingResult result,
			Location location, MailAddress mailAddress, @RequestParam("addUpdateTechnology") String action) {
		ModelAndView mav = new ModelAndView();
		Technology techn = technologyService.get(technology.getId());
		if (result.hasErrors()) {
			mav.setViewName("settings");
			mav.addObject("locations", locationService.findLocations());
			mav.addObject("technologies", technologyService.findTechnologies());
			mav.addObject("mailAddresses", mailAdressService.findAllMailAdress());
			mav.addObject("location", location);
			mav.addObject("mailAddress", mailAddress);

			mav.addObject("constCountry", constantService.get(CONST_COUNTRY));
			mav.addObject("constResumeFolder", constantService.get(CONST_RESUME_FOLDER));
			mav.addObject("constVacancyFolder", constantService.get(CONST_VACANCY_FOLDER));
			mav.addObject("constProxy", constantService.get(CONST_PROXY));
			mav.addObject("countries", countryService.findCountries());
			return mav;
		} else if (action.equals("Add technology")) {
			technologyService.add(technology);
			mav.setViewName("redirect:/settings");
			return mav;
		} else if (action.equals("Save changes") && technology.getId() != 0) {
			techn.setTechnology(technology.getTechnology());
			technologyService.update(techn);
			mav.setViewName("redirect:/settings");
			return mav;
		}
		mav.setViewName("redirect:/settings");
		return mav;
	}

	/**
	 * Reacts on 'Cancel' button clicking(that is found in the settings.jsp at
	 * 'Technology' tab). Adds all necessary attributes to the model and cancel
	 * saving technology operation.
	 * 
	 * @param technology
	 *            - represents instance of Technology class, that is used in
	 *            settings actions.
	 * @param result
	 *            - BindingResult object for mapping model attributes.
	 * @param location
	 *            - represents instance of Location class, is used in settings
	 *            actions.
	 * @param mailAddress
	 *            - MailAddress object, that should be added to the database.
	 * @param action
	 *            - represents 'Cancel' button's value
	 * @return instance of ModelAndView with set attributes and name of
	 *         necessary view.
	 */
	@RequestMapping(value = "/cancelTechnology", method = RequestMethod.POST)
	public ModelAndView cancelTechnology(@ModelAttribute("technology") @Valid Technology technology,
			BindingResult result, Location location, MailAddress mailAddress,
			@RequestParam("addUpdateTechnology") String action) {
		ModelAndView mav = new ModelAndView();

		if (result.hasErrors()) {
			mav.setViewName("settings");
			mav.addObject("locations", locationService.findLocations());
			mav.addObject("technologies", technologyService.findTechnologies());
			mav.addObject("mailAddresses", mailAdressService.findAllMailAdress());
			mav.addObject("location", location);
			mav.addObject("mailAddress", mailAddress);

			mav.addObject("constCountry", constantService.get(CONST_COUNTRY));
			mav.addObject("constResumeFolder", constantService.get(CONST_RESUME_FOLDER));
			mav.addObject("constVacancyFolder", constantService.get(CONST_VACANCY_FOLDER));
			mav.addObject("constProxy", constantService.get(CONST_PROXY));
			mav.addObject("countries", countryService.findCountries());
			return mav;
		} else if (action.equals("Cancel")) {
			mav.setViewName("redirect:/settings");
			return mav;
		}
		mav.setViewName("redirect:/settings");
		return mav;
	}

	/**
	 * Reacts on 'Add location' button clicking(that is found in the
	 * settings.jsp at 'Location' tab). Adds all necessary attributes to the
	 * model and save new location.
	 * 
	 * @param location
	 *            - represents instance of Location class, is used in settings
	 *            actions.
	 * 
	 * @param result
	 *            - BindingResult object for mapping model attributes.
	 * 
	 * @param id
	 *            - id of the country, that should be added.
	 * 
	 * @param technology
	 *            - represents instance of Technology class, that is used in
	 *            settings actions.
	 * 
	 * @param mailAddress
	 *            - MailAddress object, that should be added to the database.
	 * @param action
	 *            - represents 'Add location' button's value
	 * @return instance of ModelAndView with set attributes and name of
	 *         necessary view.
	 */
	@RequestMapping(value = "/addLocation", method = RequestMethod.POST)
	public ModelAndView addLocation(@ModelAttribute("location") @Valid Location location, BindingResult result,
			@RequestParam("locations_country") Integer id, Technology technology, MailAddress mailAddress,
			@RequestParam("addUpdateLocation") String action) {
		Location loc = locationService.get(location.getId());
		ModelAndView mav = new ModelAndView();
		if (action.equals("Add location")) {
			Set<Country> locationCountry = new HashSet<Country>();
			locationCountry.add(countryService.get(id));
			location.setLocations_country(locationCountry);
			locationService.add(location);
		} else if (action.equals("Save changes") && location.getId() != 0) {
			loc.setLocation(location.getLocation());
			locationService.update(loc);
		}
		mav.setViewName("redirect:/settings");
		mav.addObject("locations", locationService.findLocations());
		mav.addObject("technologies", technologyService.findTechnologies());
		mav.addObject("mailAddresses", mailAdressService.findAllMailAdress());
		mav.addObject("location", location);
		mav.addObject("mailAddress", mailAddress);

		mav.addObject("constCountry", constantService.get(CONST_COUNTRY));
		mav.addObject("constResumeFolder", constantService.get(CONST_RESUME_FOLDER));
		mav.addObject("constVacancyFolder", constantService.get(CONST_VACANCY_FOLDER));
		mav.addObject("constProxy", constantService.get(CONST_PROXY));
		mav.addObject("countries", countryService.findCountries());
		return mav;
	}

	/**
	 * Reacts on 'Cancel' button clicking(that is found in the settings.jsp at
	 * 'Location' tab). Adds all necessary attributes to the model and cancel
	 * saving location operation.
	 * 
	 * @param action
	 *            - represents 'Cancel' button's value
	 * @return instance of ModelAndView with set attributes and name of
	 *         necessary view.
	 */
	@RequestMapping(value = "/cancelLocation", method = RequestMethod.POST)
	public ModelAndView cancelLocation(@RequestParam("cancelLocation") String action) {

		ModelAndView mav = new ModelAndView();
		if (action.equals("Cancel")) {
			mav.setViewName("redirect:/settings");
			return mav;
		}
		mav.setViewName("redirect:/settings");
		return mav;
	}

	/**
	 * Reacts on 'Save change' button clicking(that is found in the settings.jsp
	 * at 'Constant' tab). Adds all necessary attributes to the model and save
	 * new constants.
	 * 
	 * @param constContry
	 *            - country, that should be added to the applictation constants
	 * @param resArchiveFolder
	 *            - represents folder, where all archives with resumes go
	 * @param vacArchiveFolder
	 *            - represents folder, where all archives with vacancies go
	 * @param constProxy
	 *            - name of a proxy server
	 * @return name of the view or mapping for the controller
	 */
	@RequestMapping(value = "/settings/updateConstant", method = RequestMethod.GET)
	public String updateConstant(
			@RequestParam("selectConstCountry") String constContry,
			@RequestParam("resConstFolder") String resArchiveFolder,
			@RequestParam("vacConstFolder") String vacArchiveFolder,
			@RequestParam("constProxy") String constProxy,
			@RequestParam("constCron") String constCron) {
		ErrorService.setErrorStatus("");
		Constant country = constantService.get(CONST_COUNTRY);
		country.setConstant(constContry);
		constantService.update(country);
		Constant resArchFolder = constantService.get(CONST_RESUME_FOLDER);
		resArchFolder.setConstant(resArchiveFolder);
		constantService.update(resArchFolder);
		Constant vacArchFolder = constantService.get(CONST_VACANCY_FOLDER);
		vacArchFolder.setConstant(vacArchiveFolder);
		constantService.update(vacArchFolder);
		Constant proxy = constantService.get(CONST_PROXY);
		proxy.setConstant(constProxy);
		constantService.update(proxy);
		schedulerService.changeCron(constCron);
		return "redirect:/settings";
	}

	/**
	 * Reacts on 'Edit' or 'Delete' buttons clicking(that are found in the
	 * settings.jsp at 'Mail' tab). Adds all necessary attributes to the model
	 * and edits or deletes existing constants.
	 * 
	 * @param id
	 *            - id number of email address that should be deleted or edited
	 * @param action
	 *            - represents 'Edit' or 'Detele' buttons's value
	 * @param mailAddress
	 *            - MailAddress object, that should be added to the database.
	 * @param mav
	 *            - Model object
	 * @return name of the view or mapping for the controller
	 */
	@RequestMapping(value = "/settings/mailAddress/{id}/{action}", method = { RequestMethod.GET, RequestMethod.POST })
	public String actionMailAddress(@PathVariable String id, @PathVariable String action,
			@ModelAttribute("settings") MailAddress editMailAddress, Model mav) {
		MailAddress mailAddress = mailAdressService.getMailAdressById(Integer.parseInt(id));
		if (action.equals("delete")) {
			mailAdressService.delete(mailAddress);
			return "redirect:/settings";
		} else if (action.equals("edit")) {
			mav.addAttribute("address", mailAddress.getAddress());
			mav.addAttribute("id", mailAddress.getId());
			return "redirect:/settings";
		}
		if (action.equals("addMailAddress")) {
			mailAdressService.update(editMailAddress);
			return "redirect:/settings";
		}
		return "/settings";
	}

	/**
	 * Reacts on 'Edit' or 'Delete' buttons clicking(that are found in the
	 * settings.jsp at 'Location' tab). Adds all necessary attributes to the
	 * model and edits or deletes existing locations.
	 * 
	 * @param id
	 *            - id number of location that should be deleted or edited
	 * @param action
	 *            - represents 'Edit' or 'Detele' buttons's value
	 * @param model
	 *            - Model object
	 * @return name of the view or mapping for the controller
	 */
	@RequestMapping(value = "/settings/location/{id}/{action}", method = RequestMethod.GET)
	public String deleteLocation(@PathVariable String id, @PathVariable String action, Model model) {
		Location location = locationService.get(Integer.parseInt(id));
		if (action.equals("delete")) {
			locationService.delete(location);
			return "redirect:/settings";
		} else if (action.equals("edit")) {
			model.addAttribute("location", location.getLocation());
			model.addAttribute("id", location.getId());
			return "redirect:/settings";
		}
		return "/settings";
	}

	/**
	 * Reacts on 'Edit' or 'Delete' buttons clicking(that are found in the
	 * settings.jsp at 'Technology' tab). Adds all necessary attributes to the
	 * model and edits or deletes existing technologies.
	 * 
	 * @param id
	 *            - id number of technology that should be deleted or edited
	 * @param action
	 *            - represents 'Edit' or 'Detele' buttons's value
	 * @param model
	 *            - Model object
	 * @return name of the view or mapping for the controller
	 */
	@RequestMapping(value = "/settings/technology/{id}/{action}", method = RequestMethod.GET)
	public String deleteTechnology(@PathVariable String id, @PathVariable String action, Model model) {
		Technology technology = technologyService.get(Integer.parseInt(id));
		if (action.equals("delete")) {
			technologyService.delete(technology);
			return "redirect:/settings";
		} else if (action.equals("edit")) {
			model.addAttribute("technology", technology.getTechnology());
			model.addAttribute("id", technology.getId());
			return "redirect:/settings";
		}
		return "/settings";
	}

	/**
	 * Retrieves resumes and vacancies from existing archieve folders. Saves
	 * gotten resumes and vacancies into database.
	 * 
	 * @param dateFrom
	 *            - start date for searching appropriate archieves.
	 * @param dateTo
	 *            - end date for searching appropriate archieves.
	 * @return name of view or request mapping
	 */
	@RequestMapping(value = "/settings/getArchive", method = RequestMethod.GET)
	public String getArchive(@RequestParam("dateFrom") String dateFrom, @RequestParam("dateTo") String dateTo) {

		if ((!dateFrom.isEmpty()) | (!dateTo.isEmpty())) {
			resumeMailService.getLettersFromArchive(dateFrom, dateTo);
			vacancyMailService.getLettersFromArchive(dateFrom, dateTo);
		}
		return "redirect:/settings";
	}

	/**
	 * Gets user's input as letter's and resume's patterns. Saves or updates
	 * patterns in database.
	 * 
	 * @param request
	 *            - current http request
	 * @return request mapping for controller
	 */
	@RequestMapping(value = "/settings/configResPatterns", method = RequestMethod.POST)
	public String configResPatterns(HttpServletRequest request) {
		Map<String, String[]> parameters = request.getParameterMap();
		resPatternsService.updatePatterns(parameters);
		return "redirect:/settings";
	}

	/**
	 * Gets user's input as letter's and vacancy patterns. Saves or updates
	 * patterns in database.
	 * 
	 * @param request
	 *            - current http request
	 * @return request mapping for controller
	 */
	@RequestMapping(value = "/settings/configVacPatterns", method = RequestMethod.POST)
	public String configVacPatterns(HttpServletRequest request) {
		Map<String, String[]> parameters = request.getParameterMap();
		vacPatternsService.updatePatterns(parameters);
		return "redirect:/settings";
	}

	/**
	 * Reacts on 'Check resumes' button clicking, that is found in settings.jsp.
	 * Application reads all new letters with resumes from appropriate email
	 * address, then updates current page.
	 * 
	 * @return request mapping to the next event, that should be done.
	 */
	@RequestMapping(value = "/settings/updateResumes", method = RequestMethod.GET)
	public String checkResumes() {
		resumeMailService.performBatchUpdate();
		// this will force update of inbox page
		Page currentPage = resumeService.getCurrentPage();
		currentPage.setContent(null);
		currentPage.setHasContent(false);
		currentPage.setNumberOfResults(-1l);
		return "redirect:/inbox";
	}

	/**
	 * Reacts on 'Check vacancies' button clicking, that is found in
	 * settings.jsp. Application reads all new letters with vacancies from
	 * appropriate email address, then updates current page.
	 * 
	 * @return request mapping to the next event, that should be done.
	 */
	@RequestMapping(value = "/settings/updateVacancies", method = RequestMethod.GET)
	public String checkVacancies() {
		vacancyMailService.performBatchUpdate();
		Page currentPage = vacancyService.getCurrentPage();
		currentPage.setContent(null);
		currentPage.setHasContent(false);
		currentPage.setNumberOfResults(-1l);
		return "redirect:/vacancy";
	}

	// to add patterns for resume into a database
	@RequestMapping(value = "/settings/configPatAdd", method = RequestMethod.POST)
	public String configPatAdd(HttpServletRequest request) {
		Map<String, String[]> parameters = request.getParameterMap();
		resPatternsService.addPatterns(parameters);
		return "redirect:/settings";
	}

	// to check if resume patterns are right
	@RequestMapping(value = "/settings/checkPatterns", method = RequestMethod.POST)
	public String checkPatterns(HttpServletRequest request, Model model) {
		Map<String, String[]> parameters = request.getParameterMap();
		String validateResult = null;
		validateResult = resPatternsService.checkPatterns(parameters);
		model.addAttribute("validateResult", validateResult);
		return "/parameters";
	}

	// to add patterns for vacancy into database
	@RequestMapping(value = "/settings/addVacPatterns", method = RequestMethod.POST)
	public String addVacPatterns(HttpServletRequest request) {
		Map<String, String[]> parameters = request.getParameterMap();
		vacPatternsService.addVacPat(parameters);
		return "redirect:/settings";
	}

	// to check if vacancy patterns are right
	@RequestMapping(value = "/settings/checkVacPatterns", method = RequestMethod.POST)
	public String checkVacPatterns(HttpServletRequest request, Model model) {
		Map<String, String[]> parameters = request.getParameterMap();
		String validateResult = null;
		validateResult = vacPatternsService.checkVacPatterns(parameters);
		model.addAttribute("validateResult", validateResult);
		return "/parameters";
	}
}

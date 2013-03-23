package org.ss.cvtracker.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.ss.cvtracker.domain.Vacancy;
import org.ss.cvtracker.service.CountryService;
import org.ss.cvtracker.service.ErrorService;
import org.ss.cvtracker.service.LettersManager;
import org.ss.cvtracker.service.Page;
import org.ss.cvtracker.service.VacancyPage;
import org.ss.cvtracker.service.LocationService;
import org.ss.cvtracker.service.VacancyService;
import org.ss.cvtracker.service.TechnologyService;
import static org.ss.cvtracker.service.VacancyPage.*;

/**
 * Manages Vacancy objects()
 * @author IF-023
 */
@Controller
@RequestMapping(value = "/vacancy")
public class VacancyController {
	private static final String THIRTY = "30";
	private static final String TWENTY = "20";
	private static final String TEN = "10";
	@Autowired
	VacancyService vacancyService;
	@Autowired
	LocationService locationService;
	@Autowired
	TechnologyService technologyService;
	@Autowired
	CountryService countryService;
	@Autowired
	LettersManager lettersManager;

	/**
	 * Method provides data for vacancy.jsp. Most of requests mapped to this
	 * controller are redirected to this method
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView vacancy() {
		Page nextPage = vacancyService.getCurrentPage();

		if (!nextPage.hasContent()) {
			nextPage = vacancyService.updatePageContent(nextPage);
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("vacancy");
		mav.addObject("vCurrentPage", nextPage);
		mav.addObject("mailAddresses", lettersManager.findeMail());
		mav.addObject("locations", locationService.findLocationsByCountry(countryService.get(Integer.parseInt(nextPage.getCountry()))));
				mav.addObject("countries", countryService.findCountries());
		return mav;
	}

	/**
	 * Method handles page switching on inbox
	 */
	@RequestMapping(value = "/page/{direction}", method = RequestMethod.GET)
	public String changePage(@PathVariable("direction") String direction) {
		Page nextPage = vacancyService.getCurrentPage();
		direction = direction.intern();
		if (direction == "next") {
			nextPage.setPageNumber(nextPage.getPageNumber() + 1);
		} else if (direction == "previous") {
			nextPage.setPageNumber(nextPage.getPageNumber() - 1);
		} else if (direction == "first") {
			nextPage.setPageNumber(1);
		} else if (direction == "last") {
			int last = (int) (nextPage.getNumberOfResults() / nextPage
					.getPageSize());
			float lastL = nextPage.getNumberOfResults()
					% nextPage.getPageSize();
			if (lastL != 0) {
				last = last + 1;
			}
			nextPage.setPageNumber(last);
		}
		vacancyService.updatePageContent(nextPage);
		return "redirect:/vacancy";
	}

	/**
	 * Method handles requests related to sorting of the list on inbox
	 */
	@RequestMapping(value = "/sort/{sortColumn}", method = RequestMethod.GET)
	public String sort(@PathVariable("sortColumn") String sortColumn) {
		sortColumn = sortColumn.intern();
		Page vCurrentPage = vacancyService.getCurrentPage();
		String currentSortOrder;
		String newSortOrder;
		// if new sort column is the same as current - then we change sort order
		// else - we change sort column and set ascending sort order
		if (vCurrentPage.getSortColumn() == sortColumn) {
			currentSortOrder = vCurrentPage.getSortOrder();
			newSortOrder = currentSortOrder == SORT_ORDER_ASC ? SORT_ORDER_DESC
					: SORT_ORDER_ASC;
		} else {
			newSortOrder = SORT_ORDER_ASC;
			vCurrentPage.setSortColumn(sortColumn);
		}
		vCurrentPage.setPageNumber(1);
		vCurrentPage.setSortOrder(newSortOrder);
		vacancyService.updatePageContent(vCurrentPage);
		return "redirect:/vacancy";
	}

	/**
	 * Method handles request for removal of one element from the vacancy list on
	 * inbox
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable String id) {
		Vacancy vacancy = vacancyService.get(Integer.parseInt(id));
		vacancyService.delete(vacancy);
		// this will force update of inbox content at next request
		Page vCurrentPage = vacancyService.getCurrentPage();
		vCurrentPage.setHasContent(false);
		vCurrentPage.setNumberOfResults(-1l);
		return "redirect:/vacancy";
	}

	/**
	 * Method handles request for country
	 */
	@RequestMapping(value = "/country/{id}", method = RequestMethod.GET)
	public String country(@PathVariable String id) {
		Page vCurrentPage = vacancyService.getCurrentPage();
		vCurrentPage.setCountry(id);
		return "redirect:/vacancy";
	}

	/**
	 * Method handles request for opening of a single element of vacancy list
	 * Method provides data for vacancy.jsp
	 */
	@RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
	public ModelAndView show(@PathVariable String id, ModelAndView mav) {
		Vacancy vacancy = vacancyService.get(Integer.parseInt(id));
		mav.setViewName("vacancy");
		mav.addObject("vacancy", vacancy);
		mav.addObject("locations", locationService.findLocations());
		mav.addObject("technologies", technologyService.findTechnologies());
		return mav;
	}

	@RequestMapping(value = "/letter/{id}", method = RequestMethod.GET)
	public ModelAndView letter(@PathVariable String id, ModelAndView mav) {
		Vacancy vacancy = vacancyService.get(Integer.parseInt(id));
		mav.setViewName("letter");
		mav.addObject("vacancy", vacancy);
		return mav;
	}

	/**
	 * Method handles request for applying new filter settings (when displaying
	 * CV list on inbox)
	 */
	@RequestMapping(value = "/filter", method = RequestMethod.POST)
	public String filter(@ModelAttribute("vCurrentPage") VacancyPage pageFromForm) {
		Page nextPage = vacancyService.getCurrentPage();
		nextPage.setLocationIDs(pageFromForm.getLocationIDs());
		nextPage.setShowEmptyLocations(pageFromForm.getShowEmptyLocations());
		nextPage.setDateFrom(pageFromForm.getDateFrom());
		nextPage.setDateTo(pageFromForm.getDateTo());
		nextPage.seteMails(pageFromForm.geteMails());

		nextPage.setSetLocation(pageFromForm.isSetLocation());
		nextPage.setSetMail(pageFromForm.isSetMail());

		// force recount of results
		nextPage.setNumberOfResults(-1l);
		nextPage.setPageNumber(1);
		vacancyService.updatePageContent(nextPage);
		return "redirect:/vacancy";
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	public String refreshInbox() {
		ErrorService.setErrorStatus("");
		vacancyService.getCurrentPage().setNumberOfResults(-1l);
		vacancyService.updatePageContent(vacancyService.getCurrentPage());
		return "redirect:/vacancy";
	}

	@RequestMapping(value = "/pagesize/{size}", method = RequestMethod.GET)
	public String setPageSize(@PathVariable String size) {
		int pageSize;
		size = size.intern();
		if (size.equals(TEN)) {
			pageSize = 10;
		} else if (size.equals(TWENTY)) {
			pageSize = 20;
		} else if (size.equals(THIRTY)) {
			pageSize = 30;
		} else {
			pageSize = 10;
		}
		Page page = vacancyService.getCurrentPage();
		page.setPageSize(pageSize);
		page.setPageNumber(1);
		vacancyService.updatePageContent(page);
		return "redirect:/vacancy";
	}

}
package org.ss.cvtracker.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.ss.cvtracker.domain.Resume;
import org.ss.cvtracker.service.CountryService;
import org.ss.cvtracker.service.ErrorService;
import org.ss.cvtracker.service.LettersManager;
import org.ss.cvtracker.service.Page;
import org.ss.cvtracker.service.ResumePage;
import org.ss.cvtracker.service.LocationService;
import org.ss.cvtracker.service.ResumeService;
import org.ss.cvtracker.service.TechnologyService;
import static org.ss.cvtracker.service.ResumePage.*;

@Controller
@RequestMapping(value = "/inbox")
public class ResumeController {
	private static final String THIRTY = "30";
	private static final String TWENTY = "20";
	private static final String TEN = "10";
	@Autowired
	ResumeService resumeService;
	@Autowired
	LocationService locationService;
	@Autowired
	TechnologyService technologyService;
	@Autowired
	CountryService countryService;
	@Autowired
	LettersManager lettersManager;

	/**
	 * Method provides data for inbox.jsp. Most of requests mapped to this
	 * controller are redirected to this method
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView inbox() {
		Page nextPage = resumeService.getCurrentPage();

		if (!nextPage.hasContent()) {
			nextPage = resumeService.updatePageContent(nextPage);
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("inbox");
		mav.addObject("currentPage", nextPage);
		mav.addObject("mailAddresses", lettersManager.findeMail());
		mav.addObject("locations", locationService
				.findLocationsByCountry(countryService.get(Integer
						.parseInt(nextPage.getCountry()))));
		mav.addObject("technologies", technologyService.findTechnologies());
		mav.addObject("countries", countryService.findCountries());
		return mav;
	}

	/**
	 * Method handles page switching on inbox
	 */
	@RequestMapping(value = "/page/{direction}", method = RequestMethod.GET)
	public String changePage(@PathVariable("direction") String direction) {
		Page nextPage = resumeService.getCurrentPage();
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
		resumeService.updatePageContent(nextPage);
		return "redirect:/inbox";
	}

	/**
	 * Method handles requests related to sorting of the list on inbox
	 */
	@RequestMapping(value = "/sort/{sortColumn}", method = RequestMethod.GET)
	public String sort(@PathVariable("sortColumn") String sortColumn) {
		sortColumn = sortColumn.intern();
		Page currentPage = resumeService.getCurrentPage();
		String currentSortOrder;
		String newSortOrder;
		// if new sort column is the same as current - then we change sort order
		// else - we change sort column and set ascending sort order
		if (currentPage.getSortColumn() == sortColumn) {
			currentSortOrder = currentPage.getSortOrder();
			newSortOrder = currentSortOrder == SORT_ORDER_ASC ? SORT_ORDER_DESC
					: SORT_ORDER_ASC;
		} else {
			newSortOrder = SORT_ORDER_ASC;
			currentPage.setSortColumn(sortColumn);
		}
		currentPage.setPageNumber(1);
		currentPage.setSortOrder(newSortOrder);
		resumeService.updatePageContent(currentPage);
		return "redirect:/inbox";
	}

	/**
	 * Method handles request for removal of one element from the resume list on
	 * inbox
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable String id) {
		Resume resume = resumeService.get(Integer.parseInt(id));
		resumeService.delete(resume);
		// this will force update of inbox content at next request
		Page currentPage = resumeService.getCurrentPage();
		currentPage.setHasContent(false);
		currentPage.setNumberOfResults(-1l);
		return "redirect:/inbox";
	}

	/**
	 * Method handles request for country
	 */
	@RequestMapping(value = "/country/{id}", method = RequestMethod.GET)
	public String country(@PathVariable String id) {
		Page currentPage = resumeService.getCurrentPage();
		currentPage.setCountry(id);
		return "redirect:/inbox";
	}

	/**
	 * Method handles request for opening of a single element of resume list
	 * Method provides data for resume.jsp
	 */
	@RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
	public ModelAndView show(@PathVariable String id, ModelAndView mav) {
		Resume resume = resumeService.get(Integer.parseInt(id));
		mav.setViewName("resume");
		mav.addObject("resume", resume);
		mav.addObject("locations", locationService.findLocations());
		mav.addObject("technologies", technologyService.findTechnologies());
		return mav;
	}

	@RequestMapping(value = "/letter/{id}", method = RequestMethod.GET)
	public ModelAndView letter(@PathVariable String id, ModelAndView mav) {
		Resume resume = resumeService.get(Integer.parseInt(id));
		mav.setViewName("letter");
		mav.addObject("resume", resume);
		return mav;
	}

	/**
	 * Method handles request for applying new filter settings (when displaying
	 * CV list on inbox)
	 */
	@RequestMapping(value = "/filter", method = RequestMethod.POST)
	public String filter(@ModelAttribute("currentPage") ResumePage pageFromForm) {
		Page nextPage = resumeService.getCurrentPage();
		nextPage.setTechnologyIDs(pageFromForm.getTechnologyIDs());
		nextPage.setLocationIDs(pageFromForm.getLocationIDs());
		nextPage.setShowEmptyLocations(pageFromForm.getShowEmptyLocations());
		nextPage.setConcerningTechonology(pageFromForm.isConcerningTechonology());
		nextPage.setDateFrom(pageFromForm.getDateFrom());
		nextPage.setDateTo(pageFromForm.getDateTo());
		nextPage.seteMails(pageFromForm.geteMails());

		nextPage.setSetLocation(pageFromForm.isSetLocation());
		nextPage.setSetMail(pageFromForm.isSetMail());
		nextPage.setSetTechnology(pageFromForm.isSetTechnology());

		// force recount of results
		nextPage.setNumberOfResults(-1l);
		nextPage.setPageNumber(1);
		resumeService.updatePageContent(nextPage);
		return "redirect:/inbox";
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	public String refreshInbox() {
		ErrorService.setErrorStatus("");
		resumeService.getCurrentPage().setNumberOfResults(-1l);
		resumeService.updatePageContent(resumeService.getCurrentPage());
		return "redirect:/inbox";
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
		Page page = resumeService.getCurrentPage();
		page.setPageSize(pageSize);
		page.setPageNumber(1);
		resumeService.updatePageContent(page);
		return "redirect:/inbox";
	}
	
	@RequestMapping(value = "/annotation/{id}/{action}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView viewAnottation(@PathVariable String id, @PathVariable String action,
			ModelAndView mav, HttpServletRequest request) {
		{
			Resume resume = resumeService.get(Integer.parseInt(id));
			mav.setViewName("annotation");
			mav.addObject("ourValue", new Date());
			mav.addObject("annoId", id);
			String oldAnno = resume.getAnnotation();
			Integer oldAnnoP = resume.getPriority();
			mav.addObject("anno", oldAnno);
			mav.addObject("annop", oldAnnoP);
			String newAnno = request.getParameter("anno");
			String newAnnoP = request.getParameter("annop");
			mav.addObject("annonew", newAnno);
			mav.addObject("annonewp", newAnnoP);
			if(newAnnoP == null){ newAnnoP = oldAnnoP.toString();}
			if(newAnno == null){ newAnno = oldAnno;}
			resume.setPriority(Integer.parseInt(newAnnoP));
			resume.setAnnotation(newAnno);
			resumeService.update(resume);
			return mav;
		}
	}
	@RequestMapping(value = "/getannotation/{id}/data", method = RequestMethod.GET)
	public ModelAndView getAnottation(@PathVariable String id, ModelAndView mav) {
		mav.setViewName("getannotation");
		Resume resume = resumeService.get(Integer.parseInt(id));
		String Anno = resume.getAnnotation();
		Integer AnnoP = resume.getPriority();
		mav.addObject("anno", Anno);
		mav.addObject("annop", AnnoP);
		return mav;
	}

}
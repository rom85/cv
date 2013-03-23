package org.ss.cvtracker.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.cvtracker.dao.ConstantDAO;
import org.ss.cvtracker.dao.CountryDAO;
import org.ss.cvtracker.dao.LetterDAO;
import org.ss.cvtracker.dao.LocationDAO;
import org.ss.cvtracker.dao.ResumeDAO;
import org.ss.cvtracker.dao.TechnologyDAO;
import org.ss.cvtracker.domain.Location;
import org.ss.cvtracker.domain.Resume;
import org.ss.cvtracker.domain.Technology;

@Service
public class ResumeService {
	private static final String FORMAT_DATE = "dd.MM.yyyy";
	public static final int COUNTRY = 1;

	@Autowired
	ResumeDAO resumeDAO;
	@Autowired
	TechnologyDAO technologyDAO;
	@Autowired
	LocationDAO locationDAO;
	@Autowired
	CountryDAO countryDAO;
	@Autowired
	LetterDAO letterDAO;
	@Autowired
	Page resumePage;
	@Autowired
	CountryService countryService;
	@Autowired
	ConstantDAO constant;

	public void add(Resume resume) {
		resumeDAO.add(resume);
	}

	public void update(Resume resume) {
		resumeDAO.update(resume);
	}

	public void delete(Resume resume) {
		resumeDAO.delete(resume);
	}

	public Resume get(int resumeID) {
		return resumeDAO.getResumeById(resumeID);
	}

	public Collection<Location> getLocations(Resume resume) {
		Collection<Location> list = resume.getLocations();
		return list;
	}

	public Collection<Technology> getTechnologies(Resume resume) {
		Collection<Technology> list = resume.getTechnologies();
		return list;
	}

	public Page getCurrentPage() {
		if (!resumePage.isInitialized()) {
			initializeCurrentPage();
			resumePage.setInitialized(true);
		}
		return resumePage;
	}

	public void setConstant() {
		resumePage.setConstInbox(false);
		resumePage.setCountry(constant.getConstantById(COUNTRY).getConstant());
	}

	/**
	 * Method generates content for the page passed as the parameter. If some
	 * necessary properties of the page are modified according to the content
	 * retrieved from database
	 * 
	 * @param page
	 * @return
	 */
	public Page updatePageContent(Page page) {
		Date[] dateLimit = getDateRestriction(page.getDateFrom(), page.getDateTo());
		List<?> content = resumeDAO.getResumes(page.getTechnologyIDs(), page.getLocationIDs(),
				page.getShowEmptyLocations(), page.isConcerningTechonology(), page.geteMails(), dateLimit,
				page.getSortColumn(), page.getSortOrder(), (page.getPageNumber() - 1) * page.getPageSize(),
				page.getPageSize());

		// here we set the hasPrevious property of the page
		if (page.getPageNumber() > 1) {
			page.setHasPrevious(true);
			page.setFirst(true);
		} else {
			page.setHasPrevious(false);
			page.setFirst(false);
		}
		if (resumePage.getNumberOfResults() == -1L) {
			long newNumberOfResults = resumeDAO.countResultsNumber(page.getTechnologyIDs(), page.getLocationIDs(),
					page.getShowEmptyLocations(), page.isConcerningTechonology(), page.geteMails(), dateLimit);
			resumePage.setNumberOfResults(newNumberOfResults);
		}
		// here we set the hasNext property of the page
		if (page.getPageNumber() * page.getPageSize() < resumePage.getNumberOfResults()) {
			page.setHasNext(true);
			page.setLast(true);
		} else {
			page.setHasNext(false);
			page.setLast(false);
		}

		// here we get total resumes
		resumePage.setTotalResumes(resumeDAO.getAllResumes());
				
		// here we get total Letters
		resumePage.setTotalLetters(letterDAO.totalLetters());
		
		// here we get Last Update Date
		resumePage.setLastUpdateDate(letterDAO.lastUpdateDate());
		
		resumePage = (ResumePage) page;
		resumePage.setContent(content);
		resumePage.setHasContent(true);
		return resumePage;
	}

	/**
	 * Method sets filters of the <code>currentPage</code> property into
	 * "show all" mode
	 */
	private void initializeCurrentPage() {

		List<Integer> technologyIDs = new ArrayList<Integer>();
		List<Integer> locationIDs = new ArrayList<Integer>();
		Set<String> eMails = new TreeSet<String>();
		List<String> mails = new ArrayList<String>();
		for (Technology technology : technologyDAO.findAllTechnologies()) {
			technologyIDs.add(technology.getId());
		}
		if (resumePage.isConstInbox()) {
			setConstant();
		}
		String country = resumePage.getCountry();
		for (Location location : countryDAO.getLocationsByCountry(countryService.get(Integer.parseInt(country)))) {
			locationIDs.add(location.getId());
		}
		for (String letter : letterDAO.findAlleMail()) {
			eMails.add(letter);
		}

		mails.addAll(eMails);

		resumePage.setLocationIDs(locationIDs);
		resumePage.setTechnologyIDs(technologyIDs);
		resumePage.seteMails(mails);
		resumePage.setSetTechnology(true);
		resumePage.setSetLocation(true);
		resumePage.setSetMail(true);
	}

	/**
	 * Method transforms String representation of date restriction into Date
	 * format used by DAO
	 * 
	 */
	private Date[] getDateRestriction(String dateFrom, String dateTo) {

		Date[] dateLimit = new Date[2];
		SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DATE);
		Calendar dateCalendar = GregorianCalendar.getInstance();

		if ((!dateFrom.isEmpty()) && (!dateTo.isEmpty())) {
			try {
				dateLimit[0] = formatter.parse(dateFrom);
				Date date = formatter.parse(dateTo);
				dateCalendar.setTime(date);
				dateCalendar.add(Calendar.DAY_OF_MONTH, 1);
				dateLimit[1] = dateCalendar.getTime();
				return dateLimit;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else if ((!dateFrom.isEmpty()) && (dateTo.isEmpty())) {
			try {
				dateLimit[0] = formatter.parse(dateFrom);
				dateCalendar.setTime(dateLimit[0]);
				dateCalendar.add(Calendar.DAY_OF_MONTH, 1);
				dateLimit[1] = dateCalendar.getTime();
				return dateLimit;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else if ((dateFrom.isEmpty()) && (!dateTo.isEmpty())) {
			try {
				dateLimit[0] = formatter.parse(dateTo);
				dateCalendar.setTime(dateLimit[0]);
				dateCalendar.add(Calendar.DAY_OF_MONTH, 1);
				dateLimit[1] = dateCalendar.getTime();
				return dateLimit;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
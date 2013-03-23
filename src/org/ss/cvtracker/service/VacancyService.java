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
import org.ss.cvtracker.dao.VacancyDAO;
import org.ss.cvtracker.dao.VacancyLetterDAO;
import org.ss.cvtracker.domain.Location;
import org.ss.cvtracker.domain.Vacancy;

@Service
public class VacancyService {
	private static final String FORMAT_DATE = "dd.MM.yyyy";
	public static final int COUNTRY = 1;

	@Autowired
	VacancyDAO vacancyDAO;
	@Autowired
	LocationDAO locationDAO;
	@Autowired
	CountryDAO countryDAO;
	@Autowired
	VacancyLetterDAO letterDAO;
	@Autowired
	Page vacancyPage;
	@Autowired
	CountryService countryService;
	@Autowired
	ConstantDAO constant;

	public void add(Vacancy vacancy) {
		vacancyDAO.add(vacancy);
	}

	public void update(Vacancy vacancy) {
		vacancyDAO.update(vacancy);
	}

	public void delete(Vacancy vacancy) {
		vacancyDAO.delete(vacancy);
	}

	public Vacancy get(int vacancyID) {
		return vacancyDAO.getVacancyById(vacancyID);
	}

	public Collection<Location> getLocations(Vacancy vacancy) {
		Collection<Location> list = vacancy.getLocations();
		return list;
	}

	public Page getCurrentPage() {
		if (!vacancyPage.isInitialized()) {
			initializeCurrentPage();
			vacancyPage.setInitialized(true);
		}
		return vacancyPage;
	}

	public void setConstant() {
		vacancyPage.setConstInbox(false);
		vacancyPage.setCountry(constant.getConstantById(COUNTRY).getConstant());
	}

	/**
	 * Method generates content for the page passed as the parameter. If some
	 * necessary properties of the page are modified according to the content
	 * retrieved from database
	 * 
	 * @param page
	 * @return
	 */
	public Page updatePageContent(Page vPage) {
		Date[] dateLimit = getDateRestriction(vPage.getDateFrom(), vPage.getDateTo());
		List<?> content = vacancyDAO.getVacancies(vPage.getLocationIDs(), vPage.getShowEmptyLocations(),
				vPage.geteMails(), dateLimit, vPage.getSortColumn(), vPage.getSortOrder(), (vPage.getPageNumber() - 1)
						* vPage.getPageSize(), vPage.getPageSize());

		// here we set the hasPrevious property of the page
		if (vPage.getPageNumber() > 1) {
			vPage.setHasPrevious(true);
			vPage.setFirst(true);
		} else {
			vPage.setHasPrevious(false);
			vPage.setFirst(false);
		}
		if (vacancyPage.getNumberOfResults() == -1L) {
			long newNumberOfResults = vacancyDAO.countResultsNumber(vPage.getLocationIDs(),
					vPage.getShowEmptyLocations(), vPage.geteMails(), dateLimit);
			vacancyPage.setNumberOfResults(newNumberOfResults);
		}
		// here we set the hasNext property of the page
		if (vPage.getPageNumber() * vPage.getPageSize() < vacancyPage.getNumberOfResults()) {
			vPage.setHasNext(true);
			vPage.setLast(true);
		} else {
			vPage.setHasNext(false);
			vPage.setLast(false);
		}

		// here we get total resumes
		vacancyPage.setTotalVacancies(vacancyDAO.getAllVacancies());

		// here we get total Letters
		vacancyPage.setTotalVacancyLetters(letterDAO.totalLetters());

		// here we get Last Update Date
		vacancyPage.setLastUpdateDate(letterDAO.lastUpdateDate());

		vacancyPage = (VacancyPage) vPage;
		vacancyPage.setContent(content);
		vacancyPage.setHasContent(true);
		return vacancyPage;
	}

	/**
	 * Method sets filters of the <code>currentPage</code> property into
	 * "show all" mode
	 */
	private void initializeCurrentPage() {

		List<Integer> locationIDs = new ArrayList<Integer>();
		Set<String> eMails = new TreeSet<String>();
		List<String> mails = new ArrayList<String>();

		if (vacancyPage.isConstInbox()) {
			setConstant();
		}
		String country = vacancyPage.getCountry();
		for (Location location : countryDAO.getLocationsByCountry(countryService.get(Integer.parseInt(country)))) {
			locationIDs.add(location.getId());
		}
		for (String letter : letterDAO.findAlleMail()) {
			eMails.add(letter);
		}

		mails.addAll(eMails);

		vacancyPage.setLocationIDs(locationIDs);
		vacancyPage.seteMails(mails);
		vacancyPage.setSetLocation(true);
		vacancyPage.setSetMail(true);
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
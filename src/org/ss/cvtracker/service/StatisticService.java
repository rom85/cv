package org.ss.cvtracker.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.cvtracker.dao.LetterDAO;
import org.ss.cvtracker.dao.LocationDAO;
import org.ss.cvtracker.dao.ResumeDAO;
import org.ss.cvtracker.dao.TechnologyDAO;
import org.ss.cvtracker.domain.Letter;
import org.ss.cvtracker.domain.Location;
import org.ss.cvtracker.domain.Resume;
import org.ss.cvtracker.domain.Technology;

@Service
public class StatisticService {

	private static final String FORMAT_DATE = "dd.MM.yyyy";
	@Autowired
	ResumeDAO resumeDao;
	@Autowired
	TechnologyDAO technologyDao;
	@Autowired
	LetterDAO letterDao;
	@Autowired
	LocationDAO locationDao;

	/**
	 * 
	 * Generate statistics for Locations as a table
	 * 
	 * @param locations - List of available locations  
	 * @param dateFrom - start date of statistic gathering
	 * @param dateTo - end date of statistic gathering
	 * @return - a String List-of-ArrayLists, containing following information:
	 * element[0] - represents the string value of location name, and
	 * element[1] - represents total resume quantity for selected location
	 * 
	 * @author V.Ostapiv
	 */
	public List<List<String>> makeFullStatisticsForLocations(List<Location> locations,
			String dateFrom, String dateTo) {
		Date[] date = getDateRestriction(dateFrom, dateTo);
		List<List<String>> res = new ArrayList<List<String>>();//2-dimensional list-of-lists
		for (Location loc : locations) {
			List<String> singleList = new ArrayList<String>();//list of location name and resume quantity strings
			singleList.add(loc.getLocation());//add location name
			singleList.add(String.valueOf(locationDao.findResumes(loc, date).size()));//add number of resumes
			res.add(singleList);//add 1D list into 2D
		}
		return res;
	}
	
	/**
	 * 
	 * Old one version. For compatibility reasons. 
	 * 
	 */
	public List<Integer> makeStatisticsForLocations(List<Location> locations,
			String dateFrom, String dateTo) {
		Date[] date = getDateRestriction(dateFrom, dateTo);
		List<Integer> res = new LinkedList<Integer>();
		for (Location loc : locations) {
			res.add(locationDao.findResumes(loc, date).size());
			
		}
		return res;
	}

	public Set<String> getUniqueReceivedFroms(List<Letter> letters) {
		Set<String> receivedFroms = new LinkedHashSet<String>();
		for (Letter letter : letters) {
			receivedFroms.add(letter.getReceivedFrom());
		}
		return receivedFroms;
	}

	public List<Integer> makeStatisticsForLetters(List<Letter> letters,
			String dateFrom, String dateTo) {
		Date[] date = getDateRestriction(dateFrom, dateTo);
		List<Integer> res = new LinkedList<Integer>();
		for (String receivedFrom : getUniqueReceivedFroms(letters)) {
			res.add(letterDao.findResumes(receivedFrom, date[0], date[1])
					.size());
		}
		return res;
	}

	public List<Integer> makeStatisticsForTechnologies(
			List<Technology> technologies, String dateFrom, String dateTo) {
		Date date[] = getDateRestriction(dateFrom, dateTo);
		List<Integer> res = new LinkedList<Integer>();
		for (Technology techn : technologies) {
			res.add(technologyDao.findResumes(techn, date[0], date[1]).size());
		}
		return res;
	}

	public List<Resume> makeStatisticsTechnologyLetter(Technology technology,
			String receivedFrom, String dateFrom, String dateTo) {
		Date[] date = getDateRestriction(dateFrom, dateTo);
		List<Resume> res = resumeDao.getResumesByTechnologyFrom(technology,
				receivedFrom, date);
		return res;
	}

	public List<Resume> makeStatisticsLocationLetter(Location location,
			String receivedFrom, String dateFrom, String dateTo) {
		Date[] date = getDateRestriction(dateFrom, dateTo);
		List<Resume> res = resumeDao.getResumesByLocationFrom(location,
				receivedFrom, date);
		return res;
	}

	public List<Resume> makeStatisticsTechnologyLocation(Technology technology,
			Location location, String dateFrom, String dateTo) {
		Date[] date = getDateRestriction(dateFrom, dateTo);
		List<Resume> res = resumeDao.getResumesByTechnologyLocation(technology,
				location, date);
		return res;
	}
	
	/**
	 * 
	 * @param technologies - - List of available technologies
	 * @param locations - List of available locations  
	 * @param dateFrom - start date of statistic gathering
	 * @param dateTo - end date of statistic gathering
	 * @return - a String List-of-ArrayLists, containing following information:
	 *  - first column - Location names
	 *  - other columns - Technologies names
	 *  
	 * @author edited by V.Ostapiv 
	 */
	public List<List<String>> makeFullStatisticsTechnologiesLocations(
			List<Technology> technologies, List<Location> locations,
			String dateFrom, String dateTo) {
		List<List<String>> res = new LinkedList<List<String>>();
		for (Location location : locations) {
			List<String> resRow = new LinkedList<String>();
			resRow.add(location.getLocation());
			for (Technology technology : technologies) {
				resRow.add(String.valueOf(makeStatisticsTechnologyLocation(technology,
						location, dateFrom, dateTo).size()));
			}
			res.add(resRow);
		}
		return res;
	}
	
	/**
	 * 
	 * Old one version. For compatibility reasons.
	 *  
	 */
	public List<List<Integer>> makeStatisticsTechnologiesLocations(
			List<Technology> technologies, List<Location> locations,
			String dateFrom, String dateTo) {
		List<List<Integer>> res = new LinkedList<List<Integer>>();
		for (Location location : locations) {
			List<Integer> resRow = new LinkedList<Integer>();
			for (Technology technology : technologies) {
				resRow.add(makeStatisticsTechnologyLocation(technology,
						location, dateFrom, dateTo).size());
			}
			res.add(resRow);
		}
		return res;
	}
	
	/**
	 * 
	 * @param technologies - - List of available technologies
	 * @param letters - List of available mail addresses to receive resumes from  
	 * @param dateFrom - start date of statistic gathering
	 * @param dateTo - end date of statistic gathering
	 * @return - a String List-of-ArrayLists, containing following information:
	 *  - first column - Location names
	 *  - other columns - Technologies names
	 *  
	 * @author edited by V.Ostapiv 
	 */
	public List<List<String>> makeFullStatisticsTechnologiesLetters(
			List<Technology> technologies, List<Letter> letters,
			String dateFrom, String dateTo) {
		List<List<String>> res = new LinkedList<List<String>>();
		for (Technology technology : technologies) {
			List<String> resRow = new LinkedList<String>();
			resRow.add(technology.getTechnology());//add technology name as a 1st row
			for (String receivedFrom : getUniqueReceivedFroms(letters)) {
				resRow.add(String.valueOf(makeStatisticsTechnologyLetter(technology,
						receivedFrom, dateFrom, dateTo).size()));//add other rows (number of letters from every mail)
			}
			res.add(resRow);
		}
		return res;
	}
	
	/**
	 * 
	 * Old one version. For compatibility reasons.
	 *  
	 */
	public List<List<Integer>> makeStatisticsTechnologiesLetters(
			List<Technology> technologies, List<Letter> letters,
			String dateFrom, String dateTo) {
		List<List<Integer>> res = new LinkedList<List<Integer>>();
		for (Technology technology : technologies) {
			List<Integer> resRow = new LinkedList<Integer>();
			for (String receivedFrom : getUniqueReceivedFroms(letters)) {
				resRow.add(makeStatisticsTechnologyLetter(technology,
						receivedFrom, dateFrom, dateTo).size());
			}
			res.add(resRow);
		}
		return res;
	}
	
	/**
	 * 
	 * @param locations - - List of available locations
	 * @param letters - List of available mail addresses to receive resumes from  
	 * @param dateFrom - start date of statistic gathering
	 * @param dateTo - end date of statistic gathering
	 * @return - a String List-of-ArrayLists, containing following information:
	 *  - first column - Location names
	 *  - other columns - Technologies names
	 *  
	 * @author edited by V.Ostapiv 
	 */
	
	public List<List<String>> makeFullStatisticsLocationsLetters(
			List<Location> locations, List<Letter> letters, String dateFrom,
			String dateTo) {
		List<List<String>> res = new LinkedList<List<String>>();
		for (Location location : locations) {
			List<String> resRow = new LinkedList<String>();
			resRow.add(location.getLocation());//add location name as a 1st row
			for (String receivedFrom : getUniqueReceivedFroms(letters)) {
				resRow.add(String.valueOf(makeStatisticsLocationLetter(location, receivedFrom,
						dateFrom, dateTo).size()));
			}
			res.add(resRow);
		}
		return res;
	}
	
	/**
	 * 
	 * Old one version. For compatibility reasons.
	 *  
	 */
	
	public List<List<Integer>> makeStatisticsLocationsLetters(
			List<Location> locations, List<Letter> letters, String dateFrom,
			String dateTo) {
		List<List<Integer>> res = new LinkedList<List<Integer>>();
		for (Location location : locations) {
			List<Integer> resRow = new LinkedList<Integer>();
			for (String receivedFrom : getUniqueReceivedFroms(letters)) {
				resRow.add(makeStatisticsLocationLetter(location, receivedFrom,
						dateFrom, dateTo).size());
			}
			res.add(resRow);
		}
		return res;
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
		;

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

package org.ss.cvtracker.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.ss.cvtracker.domain.Location;
import org.ss.cvtracker.domain.Vacancy;

/**
 * Common interaface for management vacancy instances.
 * @author IF-023
 */
public interface VacancyDAO {
	/**
	 * Adds new vacancy object to 'vacancy' table in cvtracker database.
	 */
	void add(Vacancy vacancy);

	/**
	 * Adds collection of vacancy objects to 'vacancy' table in 'cvtracker'
	 * database.
	 * 
	 * @param vacancies
	 *            - collection of vacancy instances.
	 */
	void addAll(Collection<Vacancy> vacancies);

	/**
	 * Updates available vacancies in database with a new information.
	 * 
	 * @param vacancy
	 *            - vacancy instance that should be updated.
	 */
	void update(Vacancy vacancy);

	/**
	 * Delete necessary vacancy object from database.
	 * 
	 * @param vacancy
	 *            - vacancy instance that should be deleted.
	 */
	void delete(Vacancy vacancy);

	/**
	 * Returns vacancy instance by appropriate vacancy id.
	 * 
	 * @param vacancyID
	 *            - vacancy's id
	 * @return instance of vacancy class
	 */
	Vacancy getVacancyById(int vacancyID);

	/**
	 * Gets all vacancies from database and returns them.
	 * 
	 * @return List of vacancies.
	 */
	List<Vacancy> findAllVacancies();

	/**
	 * Method searches for vacancies which correspond to search criteria passed
	 * in parameters. Result includes those vacancies which contain AT LEAST ONE
	 * location from locations list and ALL technologies from technologies list
	 * 
	 * @param locations
	 *            - list of location_ids
	 * @param includeEmptyLocations
	 *            - boolean parameter indicating whether result should contain
	 *            vacancies without any locations specified
	 * @param eMails
	 *            - email addresses, by which vacancies will be found
	 * @param dateLimit
	 *            - start and end date for searching vacancies
	 * @param sortColumn
	 *            - column, by which sorting will be made
	 * @param sortOrder
	 *            - sorting order(ascending or descending)
	 * @param firstResult
	 *            - the first row to retrieve
	 * @param maxResults
	 *            - number of results to be retrieved
	 * @return the list with vacancies corresponding search criteria
	 */
	public List<Vacancy> getVacancies(List<Integer> locations, boolean includeEmptyLocations, List<String> eMails,
			Date[] dateLimit, String sortColumn, String sortOrder, int firstResult, int maxResults);

	/**
	 * Method counts the number of vacancies which correspond search criteria
	 * defined by parameters
	 * 
	 * @param locations
	 *            - list of location id's
	 * @param includeEmptyLocations
	 *            - boolean parameter indicating whether result should contain
	 *            vacancies without any location specified
	 * @param eMails
	 *            - collection of email, by which search will be done
	 * @param dateLimit
	 *            - first and last date, by which search will be done
	 * @return number of rows corresponding to search criteria
	 */
	Long countResultsNumber(List<Integer> locations, boolean includeEmptyLocations, List<String> eMails,
			Date[] dateLimit);

	/**
	 * Searchs vacancies according to appropriate location and email addresses.
	 * 
	 * @param location
	 *            - location by which vacancy will be found
	 * @param receivedFrom
	 *            - email addresses, from which application receives
	 *            letters(F.e.: website@work.ua, no-reaply@jobs.ua, etc.)
	 * @param dateLimit
	 *            - date limits(first and last date) by which vacancy will be
	 *            found
	 * @return List of vacancies.
	 */
	public List<Vacancy> getVacanciesByLocationFrom(Location location, String receivedFrom, Date[] dateLimit);
	
	public Long  getAllVacancies();
}
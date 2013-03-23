package org.ss.cvtracker.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.ss.cvtracker.domain.Location;
import org.ss.cvtracker.domain.Resume;
import org.ss.cvtracker.domain.Technology;

/**
 * Common interface for management Resume instances.
 */
public interface ResumeDAO {
	
	public Long  getAllResumes();
	
	/**
	 * Adds new Resume object to cvs table in cvtracker database.
	 */
	void add(Resume resume);

	/**
	 * Adds collection of Resume objects to 'cvs' table in 'cvtracker' database.
	 * 
	 * @param resumes
	 *            - collection of Resume instances.
	 */
	void addAll(Collection<Resume> resumes);

	/**
	 * Updates available resumes in database with a new information.
	 * 
	 * @param resume
	 *            - Resume instance that should be updated.
	 */
	void update(Resume resume);

	/**
	 * Delete necessary Resume object from database.
	 * 
	 * @param resume
	 *            - Resume instance that should be deleted.
	 */
	void delete(Resume resume);

	/**
	 * Returns Resume instance by appropriate resume id.
	 * 
	 * @param resumeID
	 *            - resume's id
	 * @return instance of Resume class
	 */
	Resume getResumeById(int resumeID);

	/**
	 * Gets all resumes from database and returns them.
	 * 
	 * @return List of resumes.
	 */
	List<Resume> findAllResumes();

	/**
	 * Method searches for CVs which correspond to search criteria passed in
	 * parameters. Result includes those CVs which contain AT LEAST ONE location
	 * from locations list and ALL technologies from technologies list
	 * 
	 * @param technologies
	 *            - list of technology_ids which should be present is search
	 *            result
	 * @param locations
	 *            - list of location_ids
	 * @param includeEmptyLocations
	 *            - boolean parameter indicating whether result should contain
	 *            CVs without any locations specified
	 * @param sortColumn
	 *            - sorting column
	 * @param sortOrder
	 *            - sorting order(ascending or descending)
	 * @param firstResult
	 *            - the first row to retrieve
	 * @param maxResults
	 *            - number of results to be retrieved
	 * @return the list with CVs corresponding search criteria
	 */
	public List<Resume> getResumes(List<Integer> technologies, List<Integer> locations, boolean includeEmptyLocations,
			boolean concerningTechonology, List<String> eMails, Date[] dateLimit, String sortColumn, String sortOrder,
			int firstResult, int maxResults);

	/**
	 * Method counts the number of CVs which correspond search criteria defined
	 * by parameters
	 * 
	 * @param technologies
	 *            - list of technology_ids which should be present is search
	 *            result
	 * @param locations
	 *            - list of location_ids
	 * @param includeEmptyLocations
	 *            - boolean parameter indicating whether result should contain
	 *            CVs without any location specified
	 * @param concerningTechonology
	 *            - boolean parameter indicating whether all technology
	 *            conditions should relate to one resume.
	 * @param eMails
	 *            - collection of email, by which search will be done
	 * @param dateLimit
	 *            - first and last date, by which search will be done
	 * @return number of rows corresponding to search criteria
	 */
	Long countResultsNumber(List<Integer> technologies, List<Integer> locations, boolean includeEmptyLocations,
			boolean concerningTechonology, List<String> eMails, Date[] dateLimit);

	/**
	 * Searchs resumes according to appropriate technology and location.
	 * 
	 * @param technology
	 *            - technology by which resume will be found
	 * @param location
	 *            - location by which resume will be found
	 * @param dateLimit
	 *            - date limits(first and last date) by which resume will be
	 *            found
	 * @return List of resumes.
	 */
	public List<Resume> getResumesByTechnologyLocation(Technology technology, Location location, Date[] dateLimit);

	/**
	 * Searchs resumes according to appropriate technology and email addresses.
	 * 
	 * @param technology
	 *            - technology by which resume will be found
	 * @param receivedFrom
	 *            - email addresses, from which application receives
	 *            letters(F.e.: website@work.ua, noreply@rabota.ua, etc.)
	 * @param dateLimit
	 *            - date limits(first and last date) by which resume will be
	 *            found
	 * @return List of resumes.
	 */
	public List<Resume> getResumesByTechnologyFrom(Technology technology, String receivedFrom, Date[] dateLimit);

	/**
	 * Searchs resumes according to appropriate location and email addresses.
	 * 
	 * @param location
	 *            - location by which resume will be found
	 * @param receivedFrom
	 *            - email addresses, from which application receives
	 *            letters(F.e.: website@work.ua, noreply@rabota.ua, etc.)
	 * @param dateLimit
	 *            - date limits(first and last date) by which resume will be
	 *            found
	 * @return List of resumes.
	 */
	public List<Resume> getResumesByLocationFrom(Location location, String receivedFrom, Date[] dateLimit);
}
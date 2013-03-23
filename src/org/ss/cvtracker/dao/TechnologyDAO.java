package org.ss.cvtracker.dao;

import java.util.Date;
import java.util.List;

import org.ss.cvtracker.domain.Resume;
import org.ss.cvtracker.domain.Technology;

/**
 * Common interface for managing Technology class objects;
 */
public interface TechnologyDAO {
	/**
	 * Adds Technology object into database('technologies' table)
	 * 
	 * @param technology
	 *            - technology, that should be added.
	 */
	public void add(Technology technology);

	/**
	 * Updates existing Technology object in database.
	 * 
	 * @param technology
	 *            - technology, that should be updated.
	 */
	public void update(Technology technology);

	/**
	 * Deletes existing Technology object in database.
	 * 
	 * @param technology
	 *            - technology, that should be deleted.
	 */
	public void delete(Technology technology);

	/**
	 * Gets technology from database by appropriate id.
	 * 
	 * @param technologyID
	 *            - id of technology, that should be found.
	 * @return found Technology object
	 */
	public Technology getTechnologyById(int technologyID);

	/**
	 * Gets all technologies from database.
	 * 
	 * @return List with Technology objects.
	 */
	public List<Technology> findAllTechnologies();

	/**
	 * Finds Resume objects in database by appropriate Technology.
	 * 
	 * @param technology
	 *            - technology by which Resume will be searched.
	 * @return List with found Resume objects.
	 */
	public List<Resume> findResumes(Technology technology);

	/**
	 * Finds Resume objects in database by corresponding Technology object and
	 * date limits.
	 * 
	 * @param technology
	 *            - technology by which Resume will be searched.
	 * @param dateFrom
	 *            - start date for searching resumes.
	 * @param dateTo
	 *            - end date for searching resumes.
	 * @return List with found Resume objects.
	 */
	public List<Resume> findResumes(Technology technology, Date dateFrom, Date dateTo);
}

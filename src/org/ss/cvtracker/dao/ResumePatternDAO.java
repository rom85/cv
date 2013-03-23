package org.ss.cvtracker.dao;

import java.util.Map;

import org.ss.cvtracker.domain.ResumePattern;

/**
 * Common interface for managing ResumePattern instances.
 * 
 * @author IF-023
 */
public interface ResumePatternDAO {

	/**
	 * Adds new ResumePattern instance into database(into 'resume_patterns'
	 * table).
	 * 
	 * @param resPattern
	 *            - ResumePattern object, that should be added to database
	 */
	public void add(ResumePattern resPattern);

	/**
	 * Updates existing ResumePattern objects.
	 * 
	 * @param parameters
	 *            - Map, that contains new patterns that will update current
	 *            states of ResumePattern objects.
	 */
	public void update(Map<String, String[]> parameters);
}

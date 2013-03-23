package org.ss.cvtracker.dao;

import java.util.Map;

import org.ss.cvtracker.domain.VacancyPattern;

/**
 * Common interface for managing VacancyPattern instances.
 * 
 * @author IF-023
 */
public interface VacancyPatternDAO {
	/**
	 * Adds new VacancyPattern instance into database(into 'vacancy_patterns'
	 * table).
	 * 
	 * @param vacPattern
	 *            - VacancyPattern object, that should be added to database
	 */
	public void add(VacancyPattern vacPattern);

	/**
	 * Updates existing VacancyPattern objects.
	 * 
	 * @param parameters
	 *            - Map, that contains new patterns that will update current
	 *            states of VacancyPattern objects.
	 */
	public void update(Map<String, String[]> parameters);
}

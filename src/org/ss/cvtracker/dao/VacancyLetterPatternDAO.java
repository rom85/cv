package org.ss.cvtracker.dao;

import java.util.List;

import org.ss.cvtracker.domain.VacancyLetterPattern;

/**
* Common interface for managing VacamcyLetterPattern instances.
* 
* @author IF-023
*/
public interface VacancyLetterPatternDAO {
	/**
	 * Adds VacancyLetterPattern object into database(into 'vacancy_letter_patterns' table).
	 * 
	 * @param vacPattern
	 *            - VacancyLetterPattern that should be added
	 */
	public void add(VacancyLetterPattern vacPattern);

	/**
	 * Update existing VacancyLetterPattern objects by its id numbers.
	 * 
	 * @param vacPattern
	 *            - new pattern, that will be set to the existing VacancyLetterPattern
	 *            object.
	 * @param patternID
	 *            - id of VacancyLetterPattern object, that should be changed.
	 */
	public void update(String vacPattern, String patternID);

	/**
	 * Finds all VacancyLetterPattern objects in a database.
	 * 
	 * @return List of VacancyLetterPattern objects.
	 */
	public List<VacancyLetterPattern> findAllPatterns();
}

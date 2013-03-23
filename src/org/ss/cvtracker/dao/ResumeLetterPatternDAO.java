package org.ss.cvtracker.dao;

import java.util.List;
import org.ss.cvtracker.domain.ResumeLetterPattern;

/**
 * Common interface for managing ResumeLetterPattern instances.
 * 
 * @author IF-023
 */
public interface ResumeLetterPatternDAO {
	/**
	 * Adds ResumeLetterPattern object into database(into 'resume_letter_patterns' table).
	 * 
	 * @param letPattern
	 *            - ResumeLetterPattern that should be added
	 */
	public void add(ResumeLetterPattern letPattern);

	/**
	 * Update existing ResumeLetterPattern objects by its id numbers.
	 * 
	 * @param letPattern
	 *            - new pattern, that will be set to the existing ResumeLetterPattern
	 *            object.
	 * @param patternID
	 *            - id of ResumeLetterPattern object, that should be changed.
	 */
	public void update(String letPattern, String patternID);

	/**
	 * Finds all ResumeLetterPattern objects in a database.
	 * 
	 * @return List of ResumeLetterPattern objects.
	 */
	public List<ResumeLetterPattern> findAllPatterns();
}

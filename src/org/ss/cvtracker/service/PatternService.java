package org.ss.cvtracker.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.ss.cvtracker.domain.Pattern;

/**
 * Common interface for managing Pattern objects.
 * 
 * @author IF-023
 */
@Service
public interface PatternService {
	/**
	 * Update letter's patterns in database based on user's input.
	 * 
	 * @param parameters
	 *            - map of request parameters that represent letter's patterns.
	 */
	public void updatePatterns(Map<String, String[]> parameters);

	/**
	 * Finds all letter's patterns in database.
	 * 
	 * @return List with found patterns
	 */
	public List<? extends Pattern> findPatterns();
}

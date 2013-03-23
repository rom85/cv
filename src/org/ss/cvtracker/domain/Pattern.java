package org.ss.cvtracker.domain;

/**
 * Common interface for resume's and vacancy's patterns. IF-023
 */
public interface Pattern {
	/**
	 * Gets pattern of appropriate Pattern object.
	 * 
	 * @return pattern of current Pattern object
	 */
	String getPattern();

	/**
	 * Sets pattern to appropriate Pattern object.
	 * 
	 * @param pattern
	 *            - pattern, that should be added
	 */
	void setPattern(String pattern);

	/**
	 * Gets id of appropriate Pattern object.
	 * 
	 * @return id current Pattern object
	 */
	Integer getId();

	/**
	 * Sets id to appropriate Pattern object.
	 * 
	 * @param id
	 *            - id, that should be added
	 */
	void setId(Integer id);
}

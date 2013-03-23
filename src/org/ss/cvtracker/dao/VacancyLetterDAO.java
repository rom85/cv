package org.ss.cvtracker.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.ss.cvtracker.domain.VacancyLetter;

/**
 * Common interface for managing VacancyLetter objects.
 * 
 * @author IF-023
 */
public interface VacancyLetterDAO {
	/**
	 * Adds new VacancyLetter object into 'vacancy_letters' table in 'cvtracker'
	 * database.
	 * 
	 * @param letter
	 *            - VacancyLetter, that should be added.
	 * @return id of added element
	 */
	public int add(VacancyLetter letter);

	/**
	 * Updates state of appropriate VacancyLetter object.
	 * 
	 * @param letter
	 *            - VacancyLetter object, which state should be updated.
	 */
	public void update(VacancyLetter letter);

	/**
	 * Deletes current VacancyLetter object.
	 * 
	 * @param letter
	 *            - VacancyLetter object, that should be deleted.
	 */
	public void delete(VacancyLetter letter);

	/**
	 * Returns List of all VacancyLetter objects, that exist in database.
	 * 
	 * @return List with VacancyLetter objects
	 */
	public List<VacancyLetter> getLetters();

	/**
	 * Gets VacancyLetter objects by appropriate id.
	 * 
	 * @param id
	 *            - id number, by which VacancyLetter object will be found.
	 * @return found VacancyLetter object.
	 */
	public VacancyLetter getLetterById(int id);

	/**
	 * Add Collection of VacancyLetter objects into database
	 * 
	 * @param letters
	 *            - collection of VacancyLetter objects.
	 */
	public void addAll(Collection<VacancyLetter> letters);

	public Long totalLetters();

	public String lastUpdateDate();

	public List<String> findAlleMail();
}

package org.ss.cvtracker.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.ss.cvtracker.domain.Letter;
import org.ss.cvtracker.domain.Resume;

public interface LetterDAO {
	

    public String lastUpdateDate();

    public Long totalLetters();
    
	public int add(Letter letter);

	public void update(Letter letter);

	public void delete(Letter letter);

	public List<Letter> getLetters();

	public Letter getLetterById(int id);

	public void addAll(Collection<Letter> letters);

	/**
	 * Method removes from the collection of UIDs passed as the parameter those
	 * elements which are already present in the database
	 * 
	 * @param UIDs
	 *            - collection of UIDs to be cleaned
	 */
	public void findNewUIDs(Set<String> UIDs);

	public List<Resume> findResumes(String receivedFrom);

	public List<Resume> findResumes(String receivedFrom, Date dateFrom, Date dateTo);
	
	public List<String> findAlleMail();
}

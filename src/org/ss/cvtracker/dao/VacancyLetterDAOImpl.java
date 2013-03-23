package org.ss.cvtracker.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ss.cvtracker.domain.VacancyLetter;

/**
 * Class for managing VacancyLetter objects.
 * @author IF-023
 */
@Repository
public class VacancyLetterDAOImpl implements VacancyLetterDAO{
	
	@Autowired
	protected HibernateTemplate template;
	

	@Transactional
	@Override
	public int add(VacancyLetter letter) {
		int id = (Integer) template.save(letter);
		template.flush();
		return id;
	}

	@Transactional
	@Override
	public void update(VacancyLetter letter) {
		template.saveOrUpdate(letter);
		template.flush();
	}

	@Transactional
	@Override
	public void delete(VacancyLetter letter) {
		template.delete(letter);
		template.flush();
	}

	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public List<VacancyLetter> getLetters() {
		return template.find("from VacancyLetter");
	}

	@Transactional
	@Override
	public VacancyLetter getLetterById(int id) {
		return (VacancyLetter) template.load(VacancyLetter.class, id);
	}
	
	@Transactional
	@Override
	public void addAll(Collection<VacancyLetter> letters) {
		template.saveOrUpdateAll(letters);
	}
	
	@Override
	public Long totalLetters() {
		@SuppressWarnings("unchecked")
		List<Long> totalLetters = template.find("select count(*) from VacancyLetter");
		return totalLetters.get(0);
	}

	@Override
	public String lastUpdateDate() {
		return template.find("SELECT DATE_FORMAT( date,'%d/%m/%Y %T') FROM VacancyLetter ORDER BY date DESC limit 1").get(0)
				.toString();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<String> findAlleMail() {
		List<String> adress = template
				.find("select distinct let.receivedFrom from VacancyLetter let ORDER BY let.receivedFrom");
		return adress;

	}

}

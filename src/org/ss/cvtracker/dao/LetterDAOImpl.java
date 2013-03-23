package org.ss.cvtracker.dao;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ss.cvtracker.domain.Letter;
import org.ss.cvtracker.domain.Resume;

@Repository
public class LetterDAOImpl implements LetterDAO {

	@Autowired
	protected HibernateTemplate template;
	ResourceBundle queries = ResourceBundle.getBundle("org.ss.cvtracker.dao.query");

	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public List<Letter> getLetters() {
		return template.find("from Letter");
	}

	@Transactional
	@Override
	public Letter getLetterById(int id) {
		return (Letter) template.load(Letter.class, id);
	}

	@Transactional
	@Override
	public int add(Letter letter) {
		int id = (Integer) template.save(letter);
		template.flush();
		return id;
	}

	@Transactional
	@Override
	public void update(Letter letter) {
		template.saveOrUpdate(letter);
		template.flush();
	}

	@Transactional
	@Override
	public void delete(Letter letter) {
		template.delete(letter);
		template.flush();
	}

	@Transactional
	@Override
	public void findNewUIDs(Set<String> UIDs) {
		List<?> duplicates = template.findByNamedParam("select let.UID from Letter as let where let.UID in (:UIDList)",
				"UIDList", UIDs);
		UIDs.removeAll(duplicates);
	}

	@Transactional
	@Override
	public void addAll(Collection<Letter> letters) {
		template.saveOrUpdateAll(letters);
	}

	@Transactional
	@Override
	public List<Resume> findResumes(String receivedFrom) {
		return findResumes(receivedFrom, null, null);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Resume> findResumes(String receivedFrom, Date dateFrom, Date dateTo) {
		List<Resume> res = new LinkedList<Resume>();
		if ((dateFrom == null) | (dateTo == null)) {
			res.addAll(template.findByNamedParam("select distinct r from Resume r join r.letter l "
					+ "where l.receivedFrom = :let ", "let", receivedFrom));
		} else {
			res.addAll(template.findByNamedParam("select distinct r from Resume r join r.letter l "
					+ "where l.receivedFrom = :let and res.letter.date>=(:dateFrom) and res.letter.date<(:dateTo)",
					new String[] { "let", "dateFrom, dateTo" }, new Object[] { receivedFrom, dateFrom, dateTo }));
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<String> findAlleMail() {
		List<String> adress = template
				.find("select distinct let.receivedFrom from Letter let ORDER BY let.receivedFrom");
		return adress;

	}

	@Override
	public Long totalLetters() {
		@SuppressWarnings("unchecked")
		List<Long> totalLetters = template.find("select count(*) from Letter");
		return totalLetters.get(0);
	}

	@Override
	public String lastUpdateDate() {
		return template.find("SELECT DATE_FORMAT( date,'%d/%m/%Y %T') FROM Letter ORDER BY date DESC limit 1").get(0)
				.toString();
	}

}

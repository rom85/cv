package org.ss.cvtracker.dao;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ss.cvtracker.domain.Resume;
import org.ss.cvtracker.domain.Technology;

/**
 * DAO classs for managing Technology objects.
 * 
 * @author IF-023
 */
@Repository
public class TechnologyDAOImpl implements TechnologyDAO {
	private HibernateTemplate template;

	@Autowired
	public void setTemplate(HibernateTemplate template) {
		this.template = template;
	}

	@Transactional
	@Override
	public void add(Technology technology) {
		template.save(technology);
		template.flush();
	}

	@Transactional
	@Override
	public void update(Technology technology) {
		template.saveOrUpdate(technology);
		template.flush();
	}

	@Transactional
	@Override
	public void delete(Technology technology) {
		template.delete(technology);
		template.flush();
	}

	@Transactional
	@Override
	public Technology getTechnologyById(int technologyID) {
		return template.get(Technology.class, technologyID);
	}

	/**
	 * {@inheritDoc} Sorts Technology object by technology in ascending order
	 * before returning the result.
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Technology> findAllTechnologies() {
		return template.find("from Technology ORDER BY technology ASC");
	}

	@Transactional
	@Override
	public List<Resume> findResumes(Technology technology) {
		return findResumes(technology, null, null);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Resume> findResumes(Technology technology, Date dateFrom, Date dateTo) {
		List<Resume> res = new LinkedList<Resume>();
		if ((dateFrom == null) | (dateTo == null)) {
			res.addAll(template.findByNamedParam("select distinct r from Resume r join r.technologies t "
					+ "where t.technology = :techn ", "techn", technology.getTechnology()));
		} else {
			res.addAll(template.findByNamedParam("select distinct r from Resume r join r.technologies t "
					+ "where t.technology = :techn and res.letter.date>=(:dateFrom) and res.letter.date<(:dateTo)",
					new String[] { "techn", "dateFrom, dateTo" }, new Object[] { technology.getTechnology(), dateFrom,
							dateTo }));
		}
		return res;
	}
}

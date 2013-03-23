package org.ss.cvtracker.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ss.cvtracker.domain.LocationSynonyms;

@Repository
public class LocationSynonymsDAOImpl implements LocationSynonymsDAO {
	private HibernateTemplate template;

	@Autowired
	public void setTemplate(HibernateTemplate template) {
		this.template = template;
	}

	@Transactional
	@Override
	public void add(LocationSynonyms synonym) {
		template.save(synonym);
		template.flush();
	}

	@Transactional
	@Override
	public void update(LocationSynonyms synonym) {
		template.saveOrUpdate(synonym);
		template.flush();
	}

	@Transactional
	@Override
	public void delete(LocationSynonyms synonym) {
		template.delete(synonym);
		template.flush();
	}

	@Transactional
	@Override
	public LocationSynonyms getSynonymById(int synonymID) {
		return template.get(LocationSynonyms.class, synonymID);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<LocationSynonyms> findAllSynonyms() {
		return template.find("from LocationSynonyms ORDER BY synonym ASC");
	}
}

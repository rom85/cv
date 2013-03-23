package org.ss.cvtracker.dao;

import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.ss.cvtracker.domain.VacancyPattern;
import org.ss.cvtracker.service.Translator;

/**
 * DAO class for managing VacancyPattern objects(adding, updating, etc.).
 * 
 * @author IF-023
 */
@Repository
public class VacancyPatternDAOImp implements VacancyPatternDAO{
	private HibernateTemplate template;

	@Autowired
	public void setTemplate(HibernateTemplate template) {
		this.template = template;
	}
	
	@Override
	public void add(VacancyPattern vacPattern) {
		template.save(vacPattern);
		template.flush();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Retrieves appropriate patterns and VacancyPattern's ids from Map that
	 * contains request's parameters
	 */
	@Override
	public void update(Map<String, String[]> parameters) {
		String namePattern = parameters.get("name")[0];
		String namePatId = parameters.get("namePatId")[0];
		executeUpdate(namePattern, namePatId);

		String salaryPattern = parameters.get("salary")[0];
		String salPatId = parameters.get("salPatId")[0];
		executeUpdate(salaryPattern, salPatId);

		String techPattern = parameters.get("vacancy")[0];
		String techPatId = parameters.get("vacPatId")[0];
		executeUpdate(techPattern, techPatId);

		String locPattern = parameters.get("location")[0];
		String locPatId = parameters.get("locPatId")[0];
		executeUpdate(locPattern, locPatId);

		template.flush();
	}
	
	/**
	 * Update existing VacancyPattern objects with new patterns. Encode patterns
	 * before updating.
	 * 
	 * @param pattern
	 *            - pattern that will change current state of VacancyPattern
	 *            object
	 * @param id
	 *            - id of VacancyPattern object that will be updated
	 */
	private void executeUpdate(String pattern, String id) {
		String encodedPattern = Translator.encoder(pattern);
		String query = "update VacancyPattern vp set vp.pattern='" + encodedPattern + "' where vp.id=" + id;
		Query updateQuery = template.getSessionFactory().openSession().createQuery(query);
		updateQuery.executeUpdate();
	}
}

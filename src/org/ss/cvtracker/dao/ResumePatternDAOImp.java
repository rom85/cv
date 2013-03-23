package org.ss.cvtracker.dao;

import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.ss.cvtracker.domain.ResumePattern;
import org.ss.cvtracker.service.Translator;

/**
 * DAO class for managing ResumePattern objects(adding, updating, etc.).
 * 
 * @author IF-023
 */
@Repository
public class ResumePatternDAOImp implements ResumePatternDAO {
	private HibernateTemplate template;

	@Autowired
	public void setTemplate(HibernateTemplate template) {
		this.template = template;
	}

	@Override
	public void add(ResumePattern resPattern) {
		template.save(resPattern);
		template.flush();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Retrieves appropriate patterns and ResumePattern's ids from Map that
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

		String techPattern = parameters.get("tech")[0];
		String techPatId = parameters.get("techPatId")[0];
		executeUpdate(techPattern, techPatId);

		String locPattern = parameters.get("location")[0];
		String locPatId = parameters.get("locPatId")[0];
		executeUpdate(locPattern, locPatId);

		template.flush();
	}

	/**
	 * Update existing ResumePattern objects with new patterns. Encode patterns
	 * before updating.
	 * 
	 * @param pattern
	 *            - pattern that will change current state of ResumePattern
	 *            object
	 * @param id
	 *            - id of ResumePattern object that will be updated
	 */
	private void executeUpdate(String pattern, String id) {
		String encodedPattern = Translator.encoder(pattern);
		String query = "update ResumePattern rp set rp.pattern='" + encodedPattern + "' where rp.id=" + id;
		Query updateQuery = template.getSessionFactory().openSession().createQuery(query);
		updateQuery.executeUpdate();
	}
}

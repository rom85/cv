package org.ss.cvtracker.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ss.cvtracker.domain.VacancyLetterPattern;
import org.ss.cvtracker.service.Translator;

/**
* DAO class for managing VacancyLetterPattern objects(adding, updating, etc.).
* 
* @author IF-023
*/
@Repository
public class VacancyLetterPatternDAOImpl implements VacancyLetterPatternDAO{
	
	@Autowired
	private HibernateTemplate template = null;

	@Autowired
	public void setTemplate(HibernateTemplate template) {
		this.template = template;
	}


	@Override
	public void add(VacancyLetterPattern vacPattern) {
		template.save(vacPattern);
		template.flush();
		
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Encodes pattern before updating the current state of VacancyLetterPattern
	 * object.
	 */
	@Transactional
	@Override
	public void update(String vacPattern, String patternID) {
		String encodedLetPattern = Translator.encoder(vacPattern);
		String query = "update VacancyLetterPattern vp set vp.pattern=\'" + encodedLetPattern + "' where vp.id=" + patternID;
		Query updateQuery = template.getSessionFactory().openSession().createQuery(query);
		updateQuery.executeUpdate();
		template.flush();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Sorts patterns by ids in ascending order.
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<VacancyLetterPattern> findAllPatterns() {
		return template.find("from VacancyLetterPattern order by id asc");
	}

}

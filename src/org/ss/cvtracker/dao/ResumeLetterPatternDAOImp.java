package org.ss.cvtracker.dao;

import java.util.List;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ss.cvtracker.domain.ResumeLetterPattern;
import org.ss.cvtracker.service.Translator;

/**
 * DAO class for managing ResumeLetterPattern objects(adding, updating, etc.).
 * 
 * @author IF-023
 */
@Repository
public class ResumeLetterPatternDAOImp implements ResumeLetterPatternDAO {
	@Autowired
	private HibernateTemplate template = null;

	@Autowired
	public void setTemplate(HibernateTemplate template) {
		this.template = template;
	}

	@Transactional
	@Override
	public void add(ResumeLetterPattern letPattern) {
		template.save(letPattern);
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
	public List<ResumeLetterPattern> findAllPatterns() {
		return template.find("from ResumeLetterPattern order by id asc");
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Encodes pattern before updating the current state of ResumeLetterPattern
	 * object.
	 */
	@Transactional
	@Override
	public void update(String letterPattern, String patternId) {
		String encodedLetPattern = Translator.encoder(letterPattern);
		String query = "update ResumeLetterPattern lp set lp.pattern=\'" + encodedLetPattern + "' where lp.id=" + patternId;
		Query updateQuery = template.getSessionFactory().openSession().createQuery(query);
		updateQuery.executeUpdate();
		template.flush();
	}
}

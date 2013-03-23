package org.ss.cvtracker.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ss.cvtracker.domain.Vacancy;
import org.ss.cvtracker.domain.Location;

/**
 * Manages Vacancy instances. Adds new vacancies, deletes them, update,
 * retrieves all vacancies or by some conditions. All operation are done with
 * 'cvtracker' database.
 * 
 * @author IF-023
 */
@Repository
public class VacancyDAOImpl implements VacancyDAO {
	// Creation HibernateTemplate object for simplifying work with database.
	private HibernateTemplate template;

	// Getting connection with a query.properties file for future retrieving
	// 'order by' conditions for database queries.
	ResourceBundle queries = ResourceBundle.getBundle("org.ss.cvtracker.dao.query");

	@Autowired
	public void setTemplate(HibernateTemplate template) {
		this.template = template;
	}

	@Transactional
	@Override
	public void add(Vacancy vacancy) {
		template.save(vacancy);
		template.flush();
	}

	@Transactional
	@Override
	public void update(Vacancy vacancy) {
		template.saveOrUpdate(vacancy);
		template.flush();
	}

	@Transactional
	@Override
	public void delete(Vacancy vacancy) {
		template.delete(vacancy);
		template.flush();
	}

	@Transactional
	@Override
	public Vacancy getVacancyById(int vacancyID) {
		Vacancy result = template.get(Vacancy.class, vacancyID);
		template.initialize(result.getLocations());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Vacancy> findAllVacancies() {
		List<Vacancy> result = template.find("from Vacancy");
		for (Vacancy el : result) {
			template.initialize(el.getLocations());
		}
		return result;
	}

	@Transactional
	@Override
	public void addAll(Collection<Vacancy> vacancies) {
		template.saveOrUpdateAll(vacancies);
	}

	/**
	 * Transform List of emails into String instance.
	 * 
	 * @param emails
	 *            - List of emails, by which vacancies will be found
	 * @return String object with emails retrived from a List
	 */
	private String getReceivedEmails(List<String> emails) {
		StringBuffer buffer = new StringBuffer();
		Iterator<String> iterEmails = emails.iterator();
		while (iterEmails.hasNext()) {
			String email = iterEmails.next();
			buffer.append("'" + email + "', ");
		}
		String str = buffer.toString();
		str = str.substring(0, str.length() - 2);
		return str;
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * If 'includeEmptyLocations' variable is 'false' - vacancies with no
	 * location will be ignored, if 'true' - vacancies with no location will
	 * take part in calculation of total number of results. If
	 * 'concerningTechnology' will be set to 'true' - all jobs will relate to
	 * one vacancy(every vacancy should have the whole list of jobs).
	 */
	@Transactional
	@Override
	public Long countResultsNumber(List<Integer> locations, boolean includeEmptyLocations, List<String> eMails,
			Date[] dateLimit) {

		if (locations == null) {// if locations==null we create list with non
								// existing ID
			locations = new ArrayList<Integer>();
			locations.add(-1);
		}
		StringBuffer queryString = new StringBuffer();
		String emptyLocationsFilter = includeEmptyLocations ? "or  size(res.locations)=0" : "";
		String dateRestriction = (dateLimit == null) ? ""
				: " res.letter.date>=(:dateLimitFrom) and res.letter.date<(:dateLimitTo) and ";
		String recivedFrom = (eMails == null) ? "" : " res.letter.receivedFrom in (" + getReceivedEmails(eMails)
				+ ") and ";
		{
			queryString.append("select count(distinct res.id) from Vacancy res left join res.locations loc where ")
					.append(recivedFrom).append(dateRestriction).append(" (loc.location.id in (:locationsList) ")
					.append(emptyLocationsFilter + " ) ");
		}

		Query query = template.getSessionFactory().openSession().createQuery(queryString.toString())
				.setParameterList("locationsList", locations);

		if (dateLimit != null) {
			query.setParameter("dateLimitFrom", dateLimit[0]);
			query.setParameter("dateLimitTo", dateLimit[1]);
		}
		List<?> result = query.list();
		if (result.size() == 0) {
			return 0l;
		} else if (result.size() == 1) {
			return (Long) result.get(0);
		} else {
			return (long) result.size();
		}
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * If 'includeEmptyLocations' variable is 'false' - vacancies with no
	 * location will be ignored, if 'true' - vacancies with no location will be
	 * included into the List of vacancy objects. If 'concerningTechnology' will
	 * be set to 'true' - all jobs will relate to one vacancy(every vacancy
	 * should have the whole list of jobs).
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Vacancy> getVacancies(List<Integer> locations, boolean includeEmptyLocations, List<String> eMails,
			Date[] dateLimit, String sortColumn, String sortOrder, int firstResult, int maxResults) {

		if (locations == null) {
			// if locations==null we provide list with non-existent ID
			locations = new ArrayList<Integer>(); //
			locations.add(-1);
		}

		StringBuffer queryString = new StringBuffer();
		String emptyLocationsFilter = includeEmptyLocations ? "or  size(res.locations)=0" : "";
		String dateRestriction = (dateLimit == null) ? ""
				: "res.letter.date>=(:dateLimitFrom) and res.letter.date<(:dateLimitTo) and ";
		String recivedFrom = (eMails == null) ? "" : " res.letter.receivedFrom in (" + getReceivedEmails(eMails)
				+ ") and ";
		{
			queryString.append("select distinct res from Vacancy res left join res.locations loc where  ")
					.append(recivedFrom).append(dateRestriction).append(" (loc.location.id in (:locationsList) ")
					.append(emptyLocationsFilter + " )");
		}
		queryString.append(" " + queries.getString(sortColumn) + " " + sortOrder);
		Query query = template.getSessionFactory().openSession().createQuery(queryString.toString())
				.setMaxResults(maxResults).setFirstResult(firstResult).setParameterList("locationsList", locations);

		if (dateLimit != null) {
			query.setParameter("dateLimitFrom", dateLimit[0]);
			query.setParameter("dateLimitTo", dateLimit[1]);
		}
		List<Vacancy> result = query.list();
		for (Vacancy vacancy : result) {
			Hibernate.initialize(vacancy.getLocations());
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * If 'dateLimit' array is not specified - date limits restrictions are not
	 * defined. If 'dateLimit' array is not null, date limits apply to the
	 * 'select' query.
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Vacancy> getVacanciesByLocationFrom(Location location, String receivedFrom, Date[] dateLimit) {
		List<Vacancy> res = new LinkedList<Vacancy>();
		if (dateLimit == null) {
			res.addAll(template.findByNamedParam(
					"select distinct r from Vacancy r join r.locations l join r.letter letter "
							+ "where l.location = :loc and letter.receivedFrom = :lett",
					new String[] { "loc", "lett" }, new Object[] { location.getLocation(), receivedFrom }));

		} else {
			res.addAll(template
					.findByNamedParam(
							"select distinct r from Vacancy r join r.locations l join r.letter letter "
									+ "where l.location = :loc and letter.receivedFrom = :lett and letter.date>= :dateLimitFrom and r.letter.date<(:dateLimitTo)",
							new String[] { "loc", "lett", "dateLimitFrom", "dateLimitTo" },
							new Object[] { location.getLocation(), receivedFrom, dateLimit[0], dateLimit[1] }));
		}
		return res;
	}

	@Override
	public Long getAllVacancies() {
		@SuppressWarnings("unchecked")
		List<Long> vacanciesNumber = (List<Long>) template.find("select count(*) from Vacancy");
		return vacanciesNumber.get(0);
	}
}
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
import org.ss.cvtracker.domain.Resume;
import org.ss.cvtracker.domain.Technology;
import org.ss.cvtracker.domain.Location;

/**
 * Manages Resume instances. Adds new resumes, deletes them, update, retrieves
 * all resumes or by some conditions. All operation are done with 'cvtracker'
 * database.
 */
@Repository
public class ResumeDAOImpl implements ResumeDAO {
	// Creation HibernateTemplate object for simplifying wokr with database.
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
	public void add(Resume resume) {
		template.save(resume);
		template.flush();
	}

	@Transactional
	@Override
	public void update(Resume resume) {
		template.saveOrUpdate(resume);
		template.flush();
	}

	@Transactional
	@Override
	public void delete(Resume resume) {
		template.delete(resume);
		template.flush();
	}

	@Transactional
	@Override
	public Resume getResumeById(int resumeID) {
		Resume result = template.get(Resume.class, resumeID);
		template.initialize(result.getLocations());
		template.initialize(result.getTechnologies());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Resume> findAllResumes() {
		List<Resume> result = template.find("from Resume");
		for (Resume el : result) {
			template.initialize(el.getLocations());
			template.initialize(el.getTechnologies());
		}
		return result;
	}

	@Transactional
	@Override
	public void addAll(Collection<Resume> resumes) {
		template.saveOrUpdateAll(resumes);
	}

	/**
	 * Transform List of emails into String instance.
	 * 
	 * @param emails
	 *            - List of emails, by which resumes will be found
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
	 * If 'includeEmptyLocations' variable is 'false' - resumes with no location
	 * will be ignored, if 'true' - resumes with no location will take part in
	 * calculation of total number of results. If 'concerningTechnology' will be
	 * set to 'true' - all technologies will relate to one resume(every resume
	 * should have the whole list of technologies).
	 */
	@Transactional
	@Override
	public Long countResultsNumber(List<Integer> technologies, List<Integer> locations, boolean includeEmptyLocations,
			boolean concerningTechonology, List<String> eMails, Date[] dateLimit) {

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
		if (technologies != null) {
			queryString.append("select count(distinct res.id) from Resume res join res.technologies tech where ")
					.append(recivedFrom).append(dateRestriction).append(" tech.technology.id in (:technologiesList)")
					.append(" and res.id in (select res.id from Resume res left join res.locations loc ")
					.append("where loc.location.id in (:locationsList) ").append(emptyLocationsFilter);
			if (concerningTechonology == false) {
				queryString.append(") ");
			} else {
				queryString.append(") group by res.id having count(res.id)=").append(
						Integer.toString(technologies.size()));
			}
		} else {
			// if technologies list is empty - we don't apply any
			// restrictions on technologies
			queryString.append("select count(distinct res.id) from Resume res left join res.locations loc where ")
					.append(recivedFrom).append(dateRestriction).append(" (loc.location.id in (:locationsList) ")
					.append(emptyLocationsFilter + " ) ");
		}

		Query query = template.getSessionFactory().openSession().createQuery(queryString.toString())
				.setParameterList("locationsList", locations);

		if (dateLimit != null) {
			query.setParameter("dateLimitFrom", dateLimit[0]);
			query.setParameter("dateLimitTo", dateLimit[1]);
		}
		if (technologies != null) {
			query.setParameterList("technologiesList", technologies);

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
	 * If 'includeEmptyLocations' variable is 'false' - resumes with no location
	 * will be ignored, if 'true' - resumes with no location will be included
	 * into the List of Resume objects. If 'concerningTechnology' will be set to
	 * 'true' - all technologies will relate to one resume(every resume should
	 * have the whole list of technologies).
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Resume> getResumes(List<Integer> technologies, List<Integer> locations, boolean includeEmptyLocations,
			boolean concerningTechonology, List<String> eMails, Date[] dateLimit, String sortColumn, String sortOrder,
			int firstResult, int maxResults) {
		if (locations == null) {
			// if locations==null we provide list with non-existent ID
			locations = new ArrayList<Integer>(); //
			locations.add(-1);
		}

		StringBuffer queryString = new StringBuffer();
		String emptyLocationsFilter = includeEmptyLocations ? "or  size(res.locations)=0" : "";
		String dateRestriction = (dateLimit == null) ? ""
				: "res.letter.date>=(:dateLimitFrom) and res.letter.date<(:dateLimitTo) and ";
		String recivedFrom = (eMails == null || eMails.isEmpty()) ? "" : " res.letter.receivedFrom in (" + getReceivedEmails(eMails)
				+ ") and ";
		if (technologies != null) {
			queryString.append("select distinct res from Resume res join res.technologies tech where ")
					.append(" tech.technology.id in (:technologiesList) and").append(recivedFrom)
					.append(dateRestriction)
					.append("res.id in (select res.id from Resume res left join res.locations loc ")
					.append("where loc.location.id in (:locationsList) ").append(emptyLocationsFilter);
			if (concerningTechonology == false) {
				queryString.append(") ");
			} else {
				queryString.append(") group by res.id having count(res.id)=").append(
						Integer.toString(technologies.size()));
			}
		} else {
			// if technologies list is empty - we don't apply any
			// restrictions on technologies
			queryString.append("select distinct res from Resume res left join res.locations loc where  ")
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
		if (technologies != null) {
			query.setParameterList("technologiesList", technologies);
		}
		List<Resume> result = query.list();
		for (Resume resume : result) {
			Hibernate.initialize(resume.getLocations());
			Hibernate.initialize(resume.getTechnologies());
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
	public List<Resume> getResumesByTechnologyLocation(Technology technology, Location location, Date[] dateLimit) {
		List<Resume> res = new LinkedList<Resume>();
		if (dateLimit == null) {
			res.addAll(template.findByNamedParam(
					"select distinct r from Resume r join r.locations l join r.technologies t "
							+ "where l.location = :loc and t.technology = :techn ", new String[] { "loc", "techn" },
					new Object[] { location.getLocation(), technology.getTechnology() }));

		} else {
			res.addAll(template
					.findByNamedParam(
							"select distinct r from Resume r join r.locations l join r.technologies t "
									+ "where l.location = :loc and t.technology = :techn and r.letter.date>= :dateLimitFrom and r.letter.date<(:dateLimitTo)",

							new String[] { "loc", "techn", "dateLimitFrom", "dateLimitTo" },
							new Object[] { location.getLocation(), technology.getTechnology(), dateLimit[0],
									dateLimit[1] }));
		}
		return res;
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
	public List<Resume> getResumesByTechnologyFrom(Technology technology, String receivedFrom, Date[] dateLimit) {
		List<Resume> res = new LinkedList<Resume>();
		if (dateLimit == null) {
			res.addAll(template.findByNamedParam(
					"select distinct r from Resume r join r.letter letter join r.technologies t "
							+ "where letter.receivedFrom = :lett and t.technology = :techn ", new String[] { "lett",
							"techn" }, new Object[] { receivedFrom, technology.getTechnology() }));

		} else {
			res.addAll(template
					.findByNamedParam(
							"select distinct r from Resume r join r.letter letter join r.technologies t "
									+ "where letter.receivedFrom = :lett and t.technology = :techn and r.letter.date>= :dateLimitFrom and r.letter.date<(:dateLimitTo)",
							new String[] { "lett", "techn", "dateLimitFrom", "dateLimitTo" }, new Object[] {
									receivedFrom, technology.getTechnology(), dateLimit[0], dateLimit[1] }));
		}
		return res;
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
	public List<Resume> getResumesByLocationFrom(Location location, String receivedFrom, Date[] dateLimit) {
		List<Resume> res = new LinkedList<Resume>();
		if (dateLimit == null) {
			res.addAll(template.findByNamedParam(
					"select distinct r from Resume r join r.locations l join r.letter letter "
							+ "where l.location = :loc and letter.receivedFrom = :lett",
					new String[] { "loc", "lett" }, new Object[] { location.getLocation(), receivedFrom }));

		} else {
			res.addAll(template
					.findByNamedParam(
							"select distinct r from Resume r join r.locations l join r.letter letter "
									+ "where l.location = :loc and letter.receivedFrom = :lett and letter.date>= :dateLimitFrom and r.letter.date<(:dateLimitTo)",
							new String[] { "loc", "lett", "dateLimitFrom", "dateLimitTo" },
							new Object[] { location.getLocation(), receivedFrom, dateLimit[0], dateLimit[1] }));
		}
		return res;
	}



	 @Override
		public Long getAllResumes() {
			@SuppressWarnings("unchecked")
			List<Long> ls=(List<Long>) template.find("select  count(*) from Resume");
			return ls.get(0);
		}

}
package org.ss.cvtracker.dao;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ss.cvtracker.domain.Location;
import org.ss.cvtracker.domain.Resume;

@Repository
public class LocationDAOImpl implements LocationDAO {
	private HibernateTemplate template;

	@Autowired
	public void setTemplate(HibernateTemplate template) {
		this.template = template;
	}

	@Transactional
	@Override
	public void add(Location location) {
		template.save(location);
		template.flush();
	}

	@Transactional
	@Override
	public void update(Location location) {
		template.saveOrUpdate(location);
		template.flush();
	}

	@Transactional
	@Override
	public void delete(Location location) {
		template.delete(location);
		template.flush();
	}

	@Transactional
	@Override
	public Location getLocationById(int locationID) {
		return template.get(Location.class, locationID);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Location> findAllLocations() {
		return template.find("from Location ORDER BY location ASC");
	}

	@Transactional
	@Override
	public List<Resume> findResumes(Location location) {
		return findResumes(location, null);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Resume> findResumes(Location location, Date[] date) {
		List<Resume> res = new LinkedList<Resume>();
		if (date == null) {
			res.addAll(template.findByNamedParam(
					"select distinct r from Resume r join r.locations l "
							+ "where l.location = :loc ", "loc",
					location.getLocation()));
		} else {
			res.addAll(template
					.findByNamedParam(
							"select distinct r from Resume r join r.locations l "
									+ "where l.location = :loc and r.letter.date>=(:dateFrom) and r.letter.date<(:dateTo)",
							new String[] { "loc", "dateFrom", "dateTo" },
							new Object[] { location.getLocation(), date[0],
									date[1] }));
		}
		return res;
	}
}

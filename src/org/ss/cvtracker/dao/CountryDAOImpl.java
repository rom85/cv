package org.ss.cvtracker.dao;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ss.cvtracker.domain.Country;
import org.ss.cvtracker.domain.Location;

@Repository
public class CountryDAOImpl implements CountryDAO {
	private HibernateTemplate template;

	@Autowired
	public void setTemplate(HibernateTemplate template) {
		this.template = template;
	}

	@Transactional
	@Override
	public void add(Country country) {
		template.save(country);
		template.flush();
	}

	@Transactional
	@Override
	public void update(Country country) {
		template.saveOrUpdate(country);
		template.flush();
	}

	@Transactional
	@Override
	public void delete(Country country) {
		template.delete(country);
		template.flush();
	}

	@Transactional
	@Override
	public Country getCountryById(int countryID) {
		return template.get(Country.class, countryID);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Country> findAllCountries() {
		return template.find("from Country ORDER BY country ASC");
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Location> getLocationsByCountry(Country country) {

		List<Location> location = new LinkedList<Location>();
		location.addAll(template.findByNamedParam(
				"select distinct l from Location l join l.locations_country c "
						+ "where c.country = :count ", "count",
				country.getCountry()));

		return location;
		// return null;
	}

}

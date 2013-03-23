package org.ss.cvtracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.cvtracker.dao.CountryDAO;
import org.ss.cvtracker.dao.LocationDAO;
import org.ss.cvtracker.domain.Country;

@Service
public class CountryService {

	@Autowired
	CountryDAO countryDao;
	@Autowired
	LocationDAO locationDAO;

	public void add(Country country) {
		if (!countryDao.findAllCountries().contains(country))
			countryDao.add(country);
	}

	public void update(Country country) {
		countryDao.update(country);
	}

	public void delete(Country country) {
		countryDao.delete(country);
	}

	public Country get(int countryID) {
		return countryDao.getCountryById(countryID);
	}

	public List<Country> findCountries() {
		return countryDao.findAllCountries();
	}

}

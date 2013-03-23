package org.ss.cvtracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.cvtracker.dao.CountryDAO;
import org.ss.cvtracker.dao.LocationDAO;
import org.ss.cvtracker.domain.Country;
import org.ss.cvtracker.domain.Location;
import org.ss.cvtracker.domain.Resume;

@Service
public class LocationService {

	@Autowired
	LocationDAO locationDao;
	@Autowired
	CountryDAO countryDao;

	public void add(Location location) {
		if (!locationDao.findAllLocations().contains(location))
			locationDao.add(location);
	}

	public void update(Location location) {
		locationDao.update(location);
	}

	public void delete(Location location) {
		locationDao.delete(location);
	}

	public Location get(int locationID) {
		return locationDao.getLocationById(locationID);
	}

	public List<Location> findLocations() {
		return locationDao.findAllLocations();
	}

	public List<Location> findLocationsByCountry(Country country) {
		return countryDao.getLocationsByCountry(country);
	}

	public List<Resume> findResumes(Location location) {
		return locationDao.findResumes(location);
	}
}

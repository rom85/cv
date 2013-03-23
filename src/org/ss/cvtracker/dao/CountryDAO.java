package org.ss.cvtracker.dao;

import java.util.List;
import org.ss.cvtracker.domain.Country;
import org.ss.cvtracker.domain.Location;



public interface CountryDAO {
	public void add(Country country);

	public void update(Country country);

	public void delete(Country country);

	public Country getCountryById(int countryID);

	public List<Country> findAllCountries();
	
	public List<Location> getLocationsByCountry(Country country);

}

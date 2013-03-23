package test.ss.cvtracker.dao;

import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.ss.cvtracker.dao.CountryDAO;
import org.ss.cvtracker.domain.Country;
import org.ss.cvtracker.domain.Location;

@SuppressWarnings("deprecation")
public class JdbcCountryDaoTests extends AbstractTransactionalDataSourceSpringContextTests {

    private CountryDAO countryDao;
	    
    public void setCountryDao(CountryDAO countryDao) {
        this.countryDao = countryDao;
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[] {"classpath:test-context.xml"};
    }

    public void testGetCountryData() {
        
        Country testCountry = countryDao.getCountryById(1);
        assertEquals("Afghanistan", testCountry.getCountry());
        
    }
    
    public void testGetCountries(){
    	List<Country> Countries = countryDao.findAllCountries();
        assertEquals("wrong number of countries?", 196, Countries.size());
    }
    
    public void testCountryLocations(){
    	Country testCountry = countryDao.getCountryById(184);//Ukraine
      	assertEquals("Ukraine", testCountry.getCountry());
      	Collection<Location> testLocations = testCountry.getLocations();//Get locations from country UA
      	assertEquals("wrong number of locations in Ukraine?", 25, testLocations.size());//26 regions in UA
      	
    }
    
 public void testUpdateAddCountryData() {
	 	Country testCountry = countryDao.getCountryById(1);
        testCountry.setCountry("Mordor");
        countryDao.update(testCountry);//Afghanistan -> Mordor
        testCountry = countryDao.getCountryById(1);
        assertEquals("Mordor", testCountry.getCountry());//Changed successfully
        Country cOz = new Country();
        cOz.setCountry("Oz");
        cOz.setId(197);
        countryDao.add(cOz);
        List<Country> Countries = countryDao.findAllCountries();
        assertEquals("not added?", 197, Countries.size());//196+1
        //Country added         
    }
 public void testDeleteCountry(){
	 Country testCountry = countryDao.getCountryById(196);
     countryDao.delete(testCountry);
     List<Country> Countries = countryDao.findAllCountries();
     assertEquals("not deleted?", 195, Countries.size());//Deleted
 }
}
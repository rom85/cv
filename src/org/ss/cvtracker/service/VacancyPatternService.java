package org.ss.cvtracker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.cvtracker.dao.LocationDAO;
import org.ss.cvtracker.dao.LocationSynonymsDAO;
import org.ss.cvtracker.dao.VacancyLetterPatternDAO;
import org.ss.cvtracker.dao.VacancyPatternDAO;
import org.ss.cvtracker.domain.LocationSynonyms;
import org.ss.cvtracker.domain.Pattern;
import org.ss.cvtracker.domain.ResumePattern;
import org.ss.cvtracker.domain.VacancyLetterPattern;
import org.ss.cvtracker.domain.VacancyPattern;
import org.ss.cvtracker.service.mail.LinkManager;
import org.ss.cvtracker.service.mail.TextParser;

/**
 * Service class that comunicate with vacancy's patterns DAO classes.
 * 
 * @author IF-023
 */
@Service
public class VacancyPatternService implements PatternService {
	/**
	 * Used to manage VacancyLetterPattern objects.
	 */
	@Autowired
	private VacancyLetterPatternDAO letterPatternDAO;

	/**
	 * Used to manage VacancyPattern objects.
	 */
	@Autowired
	private VacancyPatternDAO vacancyPatternDAO;
	
		
	@Autowired
	private LocationDAO locationDAO;

	@Autowired
	private LocationSynonymsDAO locationSynonymsDAO;
	
	@Autowired
	private LocationService locatServ;
	


	@Override
	public void updatePatterns(Map<String, String[]> parameters) {
		String letterPattern = parameters.get("letter")[0];
		String patternId = parameters.get("letPatId")[0];
		letterPatternDAO.update(letterPattern, patternId);
		vacancyPatternDAO.update(parameters);
	}

	@Override
	public List<? extends Pattern> findPatterns() {
		return letterPatternDAO.findAllPatterns();
	}
	public void addVacPat(Map<String, String[]> parameters) {
		VacancyLetterPattern vlp = new VacancyLetterPattern();

		vlp.setEmail(parameters.get("newSite")[0]);
		vlp.setPattern(parameters.get("letter")[0]);
		
		letterPatternDAO.add(vlp);
		
		VacancyPattern rp1=new VacancyPattern();
		VacancyPattern rp2=new VacancyPattern();
		VacancyPattern rp3=new VacancyPattern();
		VacancyPattern rp4=new VacancyPattern();
		
		rp1.setPattern(parameters.get("name")[0]);
		rp1.setVacancyLetterPattern(vlp);
		rp2.setPattern(parameters.get("salary")[0]);
		rp2.setVacancyLetterPattern(vlp);
		rp3.setPattern(parameters.get("vacancy")[0]);
		rp3.setVacancyLetterPattern(vlp);
		rp4.setPattern(parameters.get("location")[0]);
		rp4.setVacancyLetterPattern(vlp);
		vacancyPatternDAO.add(rp1);
		vacancyPatternDAO.add(rp2);
		vacancyPatternDAO.add(rp3);
		vacancyPatternDAO.add(rp4);
		}	
		

	// method to check if patterns are right
		
				public String checkVacPatterns(Map<String, String[]> parameters){
							
		// create new object to get content of web-page by URL of this page
					LinkManager lm=new LinkManager();
					String result = null;
					String content=lm.readURL(parameters.get("url")[0],"" );
					if (content==null) {result="not found";}
					System.out.println(content);
									
		//retrieve name from letter
					TextParser tp1=new TextParser();
					Pattern pat1=new VacancyPattern();
					pat1.setPattern(parameters.get("name")[0]);
					try {
						String name=tp1.searchName(content,pat1);
					
					
					if (name!=null){
					name = "Name:" + name+"\n";
					}else{
						name="Name: not found";
					}
								
					
		//retrieve salary from letter
					TextParser tp2 = new TextParser();
					Pattern pat2 = new VacancyPattern();
					pat2.setPattern(parameters.get("salary")[0]);
					String salary = tp2.searchSalary(content,pat2);
					
					if (salary!=null){
						salary = "Salary:" + salary+"\n";
					}else{
						salary = "Salary: not found";
					}
														
		//retrieve locations from letter
					TextParser tp3 = new TextParser();
					Pattern pat3 = new VacancyPattern();
					pat3.setPattern(parameters.get("location")[0]);
				
					List<org.ss.cvtracker.domain.Location> lisloc = new ArrayList<org.ss.cvtracker.domain.Location>();
					List<LocationSynonyms> lisLocSys=new ArrayList<LocationSynonyms>();
					lisloc = locationDAO.findAllLocations();
					lisLocSys = locationSynonymsDAO.findAllSynonyms();
							
					List<Integer> lint = new ArrayList<Integer>();

					lint = tp3.searchLocation(content, pat3, lisloc, lisLocSys);
					
					
					StringBuffer sb = new StringBuffer();
					String location=null;
					for (int i=0;i<lint.size();i++){
						int var = lint.get(i);
						location = (locatServ.get(var)).getLocation();
					sb.append(location);
					sb.append(" ");
					}
					
					String locations=null;
					if (sb!=null){
					locations ="Locations:" +sb.toString()+"\n";
					}
			// retrieve vacancy from letter			
						
					TextParser tp4=new TextParser();
					Pattern pat4=new VacancyPattern();
					pat4.setPattern(parameters.get("vacancy")[0]);
					try {
						String vacancy = tp4.searchVacancy(content,pat4);
					
					
					if (vacancy!=null){
					vacancy = "Vacancy:" + vacancy+"\n";
					}else{
						vacancy = "Vacancy: not found";
					}

		// result 
				result = name+"\n"+salary+"\n"+locations+"\n"+vacancy+"\n";
					} catch (Exception error){
						System.out.println("!!!error");
					}
					
					return result;
					}finally{}
		
	}

}

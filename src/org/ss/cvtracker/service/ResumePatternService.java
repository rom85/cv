package org.ss.cvtracker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.cvtracker.dao.LocationDAO;
import org.ss.cvtracker.dao.LocationSynonymsDAO;
import org.ss.cvtracker.dao.ResumeLetterPatternDAO;
import org.ss.cvtracker.dao.ResumePatternDAO;
import org.ss.cvtracker.dao.TechnologyDAO;
import org.ss.cvtracker.domain.LocationSynonyms;
import org.ss.cvtracker.domain.Pattern;
import org.ss.cvtracker.domain.ResumeLetterPattern;
import org.ss.cvtracker.domain.ResumePattern;
import org.ss.cvtracker.domain.Technology;
import org.ss.cvtracker.service.mail.LinkManager;
import org.ss.cvtracker.service.mail.TextParser;

/**
 * Service class that comunicate with resume's patterns DAO classes.
 * 
 * @author IF-023
 */
@Service
public class ResumePatternService implements PatternService {
	/**
	 * Used to manage ResumeLetterPattern objects.
	 */
	@Autowired
	private ResumeLetterPatternDAO letterPatternDAO;

	/**
	 * Used to manage ResumePattern objects.
	 */
	@Autowired
	private ResumePatternDAO resumePatternDAO;

	@Autowired
	private TechnologyDAO technologyDAO;
	
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
		resumePatternDAO.update(parameters);
	}

	@Override
	public List<? extends Pattern> findPatterns() {
		return letterPatternDAO.findAllPatterns();
	}
	
	// method to add new patterns to database
		public void addPatterns(Map<String, String[]> parameters) {
			ResumeLetterPattern rlp = new ResumeLetterPattern();

			rlp.setEmail(parameters.get("newSite")[0]);
			rlp.setPattern(parameters.get("letter")[0]);
			
			letterPatternDAO.add(rlp);
			
			ResumePattern rp1=new ResumePattern();
			ResumePattern rp2=new ResumePattern();
			ResumePattern rp3=new ResumePattern();
			ResumePattern rp4=new ResumePattern();
			
			rp1.setPattern(parameters.get("name")[0]);
			rp1.setResumeLetterPattern(rlp);
			rp2.setPattern(parameters.get("salary")[0]);
			rp2.setResumeLetterPattern(rlp);
			rp3.setPattern(parameters.get("tech")[0]);
			rp3.setResumeLetterPattern(rlp);
			rp4.setPattern(parameters.get("location")[0]);
			rp4.setResumeLetterPattern(rlp);
			resumePatternDAO.add(rp1);
			resumePatternDAO.add(rp2);
			resumePatternDAO.add(rp3);
			resumePatternDAO.add(rp4);
					
		}
		
		// method to check if patterns are right
		
		public String checkPatterns(Map<String, String[]> parameters){
					
// create new object to get content of web-page by URL of this page
			LinkManager lm=new LinkManager();
			String result = null;
			String content=lm.readURL(parameters.get("url")[0],"" );
			if (content==null) {result="not found";}
			System.out.println(content);
			
//retrieve name from CV
			TextParser tp1=new TextParser();
			Pattern pat1=new ResumePattern();
			pat1.setPattern(parameters.get("name")[0]);
			try {
				String name=tp1.searchName(content,pat1);
			
			
			if (name!=null){
			name = "Name:" + name;
			}else{
				name="Name: not found";
			}
						
			
//retrieve salary from CV
			TextParser tp2 = new TextParser();
			Pattern pat2 = new ResumePattern();
			pat2.setPattern(parameters.get("salary")[0]);
			String salary = tp2.searchSalary(content,pat2);
			
			if (salary!=null){
				salary = "Salary:" + salary;
			}else{
				salary = "Salary: not found";
			}
												
//retrieve locations from CV
			TextParser tp3 = new TextParser();
			Pattern pat3 = new ResumePattern();
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
			locations ="Locations:" +sb.toString();
			}
				
//retrieve technologies from CV
			TextParser tp4 = new TextParser();
			Pattern pat4 = new ResumePattern();
			pat4.setPattern(parameters.get("tech")[0]);
			List<Technology> techs = new ArrayList<Technology>();
			techs = tp4.searchTechnologies(content, technologyDAO.findAllTechnologies(), pat4);
									
			
			StringBuffer sb1 = new StringBuffer();
			String technologies = null;
			for (int i=0;i<techs.size();i++){
				Technology t = techs.get(i);
				technologies = t.getTechnology();
				sb1.append(technologies);
				sb1.append(" ");
			}
			String techResult = null;
			if (sb1!=null){
				techResult = "Technologies:"+sb1.toString();
								}
			
// result 
			result = name+"\n"+salary+"\n"+locations+"\n"+techResult+"\n";
			} catch (Exception error){
				System.out.println("!!!error");
			}
			
			return result;
		}
	
}

package org.ss.cvtracker.service.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ss.cvtracker.domain.LocationSynonyms;
import org.ss.cvtracker.domain.Location;
import org.ss.cvtracker.domain.Technology;
import org.ss.cvtracker.service.Translator;

public class TextParser {

	/**
	 * Searches locations and location synonyms in current resume or vacancy.
	 * Location are gotten from database(tables 'locations' and 'synonyms').
	 * 
	 * @param content
	 *            - content of current resume or vacancy
	 * @param locationPattern
	 *            - pattern for searching locations
	 * @param locations
	 *            - List of locations
	 * @param locationSynonyms
	 *            - list of locations synonyms(location representation in
	 *            cyrillic view)
	 * @return List of locations ids, that were found in current resume or
	 *         vacancy
	 */
	public List<Integer> searchLocation(String content, org.ss.cvtracker.domain.Pattern locationPattern,
			List<Location> locations, List<LocationSynonyms> locationSynonyms) {
		List<Integer> foundLocations = new ArrayList<Integer>();
		String commonPattern = Translator.decoder(locationPattern.getPattern());

		for (int i = 0; i < locations.size(); i++) {
			String pattern = commonPattern.replace("location", locations.get(i).getValue());
			Pattern locPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
			Matcher matcher = locPattern.matcher(content);
			if (matcher.find()) {
				foundLocations.add(locations.get(i).getId());
			}
		}

		if (foundLocations.isEmpty()) {
			for (int i = 0; i < locationSynonyms.size(); i++) {
				String pattern = commonPattern.replace("location", locationSynonyms.get(i).getValue());
				Pattern locPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
				Matcher matcher = locPattern.matcher(content);
				if (matcher.find()) {
					foundLocations.add(locationSynonyms.get(i).getId());
				}
			}
		}
		return foundLocations;
	}

	/**
	 * Searches salary, that employee wants to get or salary, that employer is
	 * ready to pay.
	 * 
	 * @param content
	 *            - content of resume or vacancy
	 * @param salaryPattern
	 *            - pattern for searching salary
	 * @return salary, retrieved from resume or vacancy
	 */
	public String searchSalary(String content, org.ss.cvtracker.domain.Pattern salaryPattern) {
		String salary = null;
		String pattern = Translator.decoder(salaryPattern.getPattern());
		Pattern namePattern = Pattern.compile(pattern);
		Matcher matcher = namePattern.matcher(content);

		if (matcher.find()) {
			salary = matcher.group(1).trim() + " " + matcher.group(2).trim();
		}

		return salary;
	}

	/**
	 * Searches name of employee in current resume or name of employer in
	 * current vacancy.
	 * 
	 * @param content
	 *            - content of resume or vacancy
	 * @param nPattern
	 *            - pattern for searching name
	 * @return name of employee or employer
	 */
	public String searchName(String content, org.ss.cvtracker.domain.Pattern nPattern) {
		String name = null;
		String pattern = Translator.decoder(nPattern.getPattern());
		Pattern namePattern = Pattern.compile(pattern);
		Matcher matcher = namePattern.matcher(content);

		if (matcher.find()) {
			name = matcher.group(1);
		}

		return name;
	}

	/**
	 * Searches technologies(gotten from database table 'technologies') in
	 * current resume.
	 * 
	 * @param content
	 *            - content of current resume
	 * @param technologies
	 *            - technologies that will be searched in letter
	 * @param technologyPattern
	 *            - pattern for searching technologies
	 * @return List of found technologies
	 */
	public List<Technology> searchTechnologies(String content, List<Technology> technologies,
			org.ss.cvtracker.domain.Pattern technologyPattern) {
		List<Technology> foundTechnologies = new ArrayList<Technology>();
		String commonPattern = Translator.decoder(technologyPattern.getPattern());

		for (int i = 0; i < technologies.size(); i++) {
			String technology = technologies.get(i).getValue();
			technology = technology.replace("+", "\\+");

			String pattern = commonPattern.replace("technology", technology);

			Pattern techPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
			Matcher matcher = techPattern.matcher(content);
			if (matcher.find()) {
				foundTechnologies.add(technologies.get(i));
			}
		}

		return foundTechnologies;
	}

	/**
	 * Search vacancy charackteristics in current vacancy page.
	 * 
	 * @param content
	 *            - vacancy content
	 * @param vacPattern
	 *            - pattern, that will be used for vacancy charackteristics
	 *            searching.
	 * @return vacancy charackteristics
	 */
	public String searchVacancy(String content, org.ss.cvtracker.domain.Pattern vacPattern) {
		String vacancy = null;
		String pattern = Translator.decoder(vacPattern.getPattern());
		Pattern namePattern = Pattern.compile(pattern);
		Matcher matcher = namePattern.matcher(content);

		if (matcher.find()) {
			vacancy = matcher.group(1);
		}

		return vacancy;
	}
}
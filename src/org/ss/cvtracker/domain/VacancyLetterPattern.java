package org.ss.cvtracker.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Represents vacancy letter's pattern essence.
 * 
 * @author IF-023
 */
@Entity
@Table(name = "vacancy_letter_patterns")
public class VacancyLetterPattern implements Pattern{

	/**
	 * Id of vacancy letter's pattern.
	 */
	@Id
	@GeneratedValue
	@Column(name = "lp_id")
	private Integer id = null;

	/**
	 * Email, from which letters with vacancy will come.
	 */
	@Column(name = "email")
	private String email = null;

	/**
	 * Pattern, which will be used for parsing letters from appropriate email
	 * address.
	 */
	@Column(name = "letter_pattern")
	private String pattern = null;

	/**
	 * Vacancy's patterns, which will belong to current vacancy letter's pattern.
	 */
	@OneToMany(mappedBy = "vacancyLetterPattern", fetch = FetchType.EAGER)
	private List<VacancyPattern> vacancyPatterns = null;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getPattern() {
		return pattern;
	}

	@Override
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public List<VacancyPattern> getVacancyPatterns() {
		return vacancyPatterns;
	}

	public void setVacancyPatterns(List<VacancyPattern> vacancyPatterns) {
		this.vacancyPatterns = vacancyPatterns;
	}
}

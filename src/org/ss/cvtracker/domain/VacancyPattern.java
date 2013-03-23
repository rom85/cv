package org.ss.cvtracker.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Represents vacancy's pattern essence.
 * 
 * @author IF-023
 */
@Entity
@Table(name = "vacancy_patterns")
public class VacancyPattern implements Pattern{

	/**
	 * Id of vacancy's pattern.
	 */
	@Id
	@GeneratedValue
	@Column(name = "vp_id")
	private Integer id = null;

	/**
	 * Pattern, that will be used in vacancy parsing.
	 */
	@Column(name = "vacancy_pattern")
	private String pattern = null;

	/**
	 * Letter's pattern, with which current vacancy's pattern will be joined.
	 */
	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "lp_id")
	private VacancyLetterPattern vacancyLetterPattern = null;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String getPattern() {
		return pattern;
	}

	@Override
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public VacancyLetterPattern getVacancyLetterPattern() {
		return vacancyLetterPattern;
	}

	public void setVacancyLetterPattern(VacancyLetterPattern vacancyLetterPattern) {
		this.vacancyLetterPattern = vacancyLetterPattern;
	}
}

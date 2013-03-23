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
 * Represents resume's pattern essence.
 * 
 * @author IF-023
 */
@Entity
@Table(name = "resume_patterns")
public class ResumePattern implements Pattern{

	/**
	 * Id of resume's pattern.
	 */
	@Id
	@GeneratedValue
	@Column(name = "rp_id")
	private Integer id = null;

	/**
	 * Pattern, that will be used in resume parsing.
	 */
	@Column(name = "resume_pattern")
	private String pattern = null;

	/**
	 * Letter's pattern, with which current resume's pattern will be joined.
	 */
	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "lp_id")
	private ResumeLetterPattern resumeLetterPattern = null;

	public ResumePattern() {
	}

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

	public ResumeLetterPattern getResumeLetterPattern() {
		return resumeLetterPattern;
	}

	public void setResumeLetterPattern(ResumeLetterPattern resumeLetterPattern) {
		this.resumeLetterPattern = resumeLetterPattern;
	}
}

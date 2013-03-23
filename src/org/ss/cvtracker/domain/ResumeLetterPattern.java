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
 * Represents resume letter's pattern essence.
 * 
 * @author IF-023
 */
@Entity
@Table(name = "resume_letter_patterns")
public class ResumeLetterPattern implements Pattern{
	/**
	 * Id of resume letter's pattern.
	 */
	@Id
	@GeneratedValue
	@Column(name = "lp_id")
	private Integer id = null;

	/**
	 * Email, from which letters with resumes will come.
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
	 * Resume's patterns, which will belong to current resume letter's pattern.
	 */
	@OneToMany(mappedBy = "resumeLetterPattern", fetch = FetchType.EAGER)
	private List<ResumePattern> resumePatterns = null;

	public ResumeLetterPattern() {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<ResumePattern> getResumePatterns() {
		return resumePatterns;
	}

	public void setResumePatterns(List<ResumePattern> resumePatterns) {
		this.resumePatterns = resumePatterns;
	}
}

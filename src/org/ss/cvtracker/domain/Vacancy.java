package org.ss.cvtracker.domain;

import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
/*
 * Represents Vacancy information like ID, recruiting company NAME, SALARY, LOCATION of vacancy and associated LETTER_ID
 * 
 */
@Table(name = "vacancy")
public class Vacancy {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Vacancy ID
	@Column(name = "vac_id")
	private int id;

	@Column(name = "name") //Recruiting company name 
	private String name;

	@Column(name = "salary")
	private String salary;
	
	@Column(name = "job")
	private String job;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER) //VacancyLetter ID for this entry
	@JoinColumn(name = "letter_id")
	VacancyLetter letter = new VacancyLetter();

//	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
//	@JoinTable(name = "vacancy_jobs", joinColumns = @JoinColumn(name = "vac_id"), inverseJoinColumns = @JoinColumn(name = "job_id"))
//	private Collection<Job> jobs;

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "vacancy_locations", joinColumns = @JoinColumn(name = "vac_id"), inverseJoinColumns = @JoinColumn(name = "location_id"))
	private Collection<Location> locations;

	@Column(name = "link")
	private String link;

	public Vacancy() {
	}

	public Vacancy(VacancyLetter letter, Collection<Location> locations, String link,
			String name, String salary, String job) {
		super();
		this.letter = letter;
		this.job = job;
		this.locations = locations;
		this.link = link;
		this.name = name;
		this.salary = salary;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public VacancyLetter getLetter() {
		return letter;
	}

	public void setLetter(VacancyLetter letter) {
		this.letter = letter;
	}

	public Date getDate() {
		return letter.getDate();
	}

	public void setDate(Date date) {
		letter.setDate(date);
	}

	public String getFrom() {
		return letter.getReceivedFrom();
	}

	public void setFrom(String receivedFrom) {
		letter.setReceivedFrom(receivedFrom);
	}

	public String getDataLetter() {
		return letter.getDataLetter();
	}

	public void setDataLetter(String dataLetter) {
		letter.setDataLetter(dataLetter);
	}

	public String getJob() {
		return job;
	}

	public void setJobs(String job) {
		this.job = job;
	}

	public Collection<Location> getLocations() {
		return locations;
	}

	public void setLocations(Collection<Location> locations) {
		this.locations = locations;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vacancy other = (Vacancy) obj;
		if (id != other.id)
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		return true;
	}

}

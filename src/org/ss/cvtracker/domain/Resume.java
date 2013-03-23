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
@Table(name = "cvs")
public class Resume {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cv_id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "salary")
	private String salary;
	
	@Column(name = "annotation")
	private String annotation;
	
	@Column(name = "priority")
	private int  priority;
	

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "letter_id")
	Letter letter = new Letter();

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "cvs_technologies", joinColumns = @JoinColumn(name = "cv_id"), inverseJoinColumns = @JoinColumn(name = "technology_id"))
	private Collection<Technology> technologies;

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "cvs_locations", joinColumns = @JoinColumn(name = "cv_id"), inverseJoinColumns = @JoinColumn(name = "location_id"))
	private Collection<Location> locations;

	@Column(name = "link")
	private String link;

	public Resume() {
	}

	public Resume(Letter letter, Collection<Technology> technologies, Collection<Location> locations, String link,
			String name, String salary, int priority) {
		super();
		this.letter = letter;
		this.technologies = technologies;
		this.locations = locations;
		this.link = link;
		this.name = name;
		this.salary = salary;
		this.priority = priority;
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
	
	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
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

	public Letter getLetter() {
		return letter;
	}

	public void setLetter(Letter letter) {
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

	public Collection<Technology> getTechnologies() {
		return technologies;
	}

	public void setTechnologies(Collection<Technology> technologies) {
		this.technologies = technologies;
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
		Resume other = (Resume) obj;
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

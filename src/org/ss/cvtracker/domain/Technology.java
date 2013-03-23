package org.ss.cvtracker.domain;

import java.util.Set;

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
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "technologies")
public class Technology implements StringField {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "technology_id")
	private int id;

	@Column(name = "technology")
	@Size(min = 1, max = 30)
	private String technology;

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "cvs_technologies", joinColumns = @JoinColumn(name = "technology_id"), inverseJoinColumns = @JoinColumn(name = "cv_id"))
	private Set<Resume> resumes;

	public Set<Resume> getResumes() {
		return resumes;
	}

	public void setResumes(Set<Resume> resumes) {
		this.resumes = resumes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	@Override
	public String getValue() {
		return technology;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((technology == null) ? 0 : technology.hashCode());
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
		Technology other = (Technology) obj;
		if (technology == null) {
			if (other.technology != null)
				return false;
		} else if (!technology.equalsIgnoreCase(other.technology))
			return false;
		return true;
	}

}

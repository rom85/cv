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
@Table(name = "locations")
public class Location implements StringField {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "location_id")
	private int id;

	@Column(name = "location")
	@Size(min = 1, max = 30)
	private String location;

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "cvs_locations", joinColumns = @JoinColumn(name = "location_id"), inverseJoinColumns = @JoinColumn(name = "cv_id"))
	private Set<Resume> resumes;

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "country_locations", joinColumns = @JoinColumn(name = "location_id"), inverseJoinColumns = @JoinColumn(name = "country_id"))
	private Set<Country> locations_country;

	public Set<Resume> getResumes() {
		return resumes;
	}

	public void setResumes(Set<Resume> resumes) {
		this.resumes = resumes;
	}

	public Set<Country> getLocations_country() {
		return locations_country;
	}

	public void setLocations_country(Set<Country> locations_country) {
		this.locations_country = locations_country;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String getValue() {
		return location;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
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
		Location other = (Location) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equalsIgnoreCase(other.location))
			return false;
		return true;
	}

}

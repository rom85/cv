package org.ss.cvtracker.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "synonyms")
public class LocationSynonyms implements StringField {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "synonym_id")
	private int synonym_id;

	@Column(name = "synonym")
	private String synonym;

	@Column(name = "location_id")
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSynonym() {
		return synonym;
	}

	public void setSynonym(String synonym) {
		this.synonym = synonym;
	}

	@Override
	public String getValue() {
		return synonym;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((synonym == null) ? 0 : synonym.hashCode());
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
		LocationSynonyms other = (LocationSynonyms) obj;
		if (synonym == null) {
			if (other.synonym != null)
				return false;
		} else if (!synonym.equalsIgnoreCase(other.synonym))
			return false;
		return true;
	}

	public int getSynonym_id() {
		return synonym_id;
	}

	public void setSynonym_id(int synonym_id) {
		this.synonym_id = synonym_id;
	}

}

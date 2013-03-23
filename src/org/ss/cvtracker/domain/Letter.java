package org.ss.cvtracker.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "letters")
public class Letter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "letter_id")
	private int id;
	@Column(name = "date")
	private Date date;
	@Column(name = "receivedFrom")
	private String receivedFrom;
	@Column(name = "dataLetter")
	private String dataLetter;
	

	public Letter() {
	}

	public Letter(Date date, String receivedFrom) {
		super();
		this.date = date;
		this.receivedFrom = receivedFrom;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getReceivedFrom() {
		return receivedFrom;
	}

	public void setReceivedFrom(String receivedFrom) {
		this.receivedFrom = receivedFrom;
	}
	
	public String getDataLetter() {
		return dataLetter;
	}

	public void setDataLetter(String dataLetter) {
		this.dataLetter = dataLetter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((receivedFrom == null) ? 0 : receivedFrom.hashCode());
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
		Letter other = (Letter) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (receivedFrom == null) {
			if (other.receivedFrom != null)
				return false;
		} else if (!receivedFrom.equalsIgnoreCase(other.receivedFrom))
			return false;
		return true;
	}
	
}

package org.ss.cvtracker.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mailaddresses")
public class MailAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mailadress_id")
	private int id;

	@Column(name = "address")
	private String address;

	@Column(name = "login")
	private String login;

	@Column(name = "password")
	private String password;

	@Column(name = "mailserver_id")
	private int mailServerId;
	
	@Column(name = "look_for_id")
	private String lookFor;

	public MailAddress() {
	}
	
	public void setLookFor(String lookFor) {
		this.lookFor = lookFor;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public MailAddress(String address, String login, String password,
			int mailServerId, String lookFor) {

		this.address = address;
		this.login = login;
		this.password = password;
		this.mailServerId = mailServerId;
		this.lookFor = lookFor;
	}

	public synchronized String getAddress() {
		return address;
	}

	public synchronized String getLogin() {
		return login;
	}

	public synchronized String getPassword() {
		return password;
	}

	public int getId() {
		return id;
	}
	
	public String getLookFor() {
		return lookFor;
	}

	public int getMailServerId() {
		return mailServerId;
	}

	public void setMailServerId(int mailServerId) {
		this.mailServerId = mailServerId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
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
		MailAddress other = (MailAddress) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equalsIgnoreCase(other.address))
			return false;
		return true;
	}

}

package org.ss.cvtracker.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mailserver")
public class MailServer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mailserver_id")
	private int id;

	@Column(name = "smtpHost")
	private String smtpHost;

	@Column(name = "smtpPort")
	private String smtpPort;

	@Column(name = "pop3Host")
	private String pop3Host;

	@Column(name = "name")
	private String name;

	public MailServer() {
	}

	public MailServer(String smtpHost, String address, String login,
			String password, String smtpPort, String pop3Host) {
		this.smtpHost = smtpHost;
		this.smtpPort = smtpPort;
		this.pop3Host = pop3Host;
	}

	public synchronized String getSmtpHost() {
		return smtpHost;
	}

	public synchronized String getSmtpPort() {
		return smtpPort;
	}

	public synchronized String getPop3Host() {
		return pop3Host;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		MailServer other = (MailServer) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equalsIgnoreCase(other.name))
			return false;
		return true;
	}

}

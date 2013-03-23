package org.ss.cvtracker.service.mail.reader;

import java.util.Date;
import java.util.Set;

public class MessageBean {
	private String from;
	private Date dateSent;
	private Set<String> textContent;

	public MessageBean(String from, Date dateSent, Set<String> textContent) {
		this.from = from;
		this.dateSent = dateSent;
		this.textContent = textContent;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Date getDateSent() {
		return dateSent;
	}

	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

	public Set<String> getTextContent() {
		return textContent;
	}

	public void setTextContent(Set<String> textContent) {
		this.textContent = textContent;
	}

}

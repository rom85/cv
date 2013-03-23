package org.ss.cvtracker.service.mail.reader;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MsgAuthenticator extends Authenticator {
	private String user;
	private String password;

	MsgAuthenticator(String user, String password) {
		this.user = user;
		this.password = password;
	}

	public PasswordAuthentication getPasswordAuthentication() {
		String user = this.user;
		String password = this.password;
		return new PasswordAuthentication(user, password);
	}
}

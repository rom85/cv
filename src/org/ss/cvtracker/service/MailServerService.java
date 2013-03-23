package org.ss.cvtracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.cvtracker.dao.MailServerDAO;
import org.ss.cvtracker.domain.MailServer;

@Service
public class MailServerService {
	@Autowired
	MailServerDAO mailServerDAO;

	public void add(MailServer mailServer) {
		mailServerDAO.add(mailServer);
	}

	public void update(MailServer mailServer) {
		mailServerDAO.update(mailServer);
	}

	public void delete(MailServer mailServer) {
		mailServerDAO.delete(mailServer);
	}

	public MailServer getMailServerById(int mailServerID) {
		return mailServerDAO.getMailServerById(mailServerID);
	}

	public List<MailServer> findAllMailServer() {
		return mailServerDAO.findAllMailServer();
	}
}

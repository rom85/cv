package org.ss.cvtracker.dao;

import java.util.List;

import org.ss.cvtracker.domain.MailServer;

public interface MailServerDAO {
	public void add(MailServer mailServer);

	public void update(MailServer mailServer);

	public void delete(MailServer mailServer);

	public MailServer getMailServerById(int mailServerID);

	public List<MailServer> findAllMailServer();
}

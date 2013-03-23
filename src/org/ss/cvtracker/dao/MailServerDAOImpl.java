package org.ss.cvtracker.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ss.cvtracker.domain.MailServer;

@Repository
public class MailServerDAOImpl implements MailServerDAO {
	private HibernateTemplate template;

	@Autowired
	public void setTemplate(HibernateTemplate template) {
		this.template = template;
	}

	@Transactional
	@Override
	public void add(MailServer mailServer) {
		template.save(mailServer);
		template.flush();
	}

	@Transactional
	@Override
	public void update(MailServer mailServer) {
		template.saveOrUpdate(mailServer);
		template.flush();
	}

	@Transactional
	@Override
	public void delete(MailServer mailServer) {
		template.delete(mailServer);
		template.flush();
	}

	@Transactional
	@Override
	public MailServer getMailServerById(int mailServerID) {
		return template.get(MailServer.class, mailServerID);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<MailServer> findAllMailServer() {
		List<MailServer> result = template.find("from MailServer");
		return result;
	}

}

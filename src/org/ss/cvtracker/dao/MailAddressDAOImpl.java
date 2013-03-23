package org.ss.cvtracker.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ss.cvtracker.domain.MailAddress;

@Repository
public class MailAddressDAOImpl implements MailAddressDAO {
	private HibernateTemplate template;

	@Autowired
	public void setTemplate(HibernateTemplate template) {
		this.template = template;
	}

	@Transactional
	@Override
	public void add(MailAddress mailAddress) {
		template.save(mailAddress);
		template.flush();
	}

	@Transactional
	@Override
	public void update(MailAddress mailAddress) {
		template.saveOrUpdate(mailAddress);
		template.flush();
	}

	@Transactional
	@Override
	public void delete(MailAddress mailAddress) {
		template.delete(mailAddress);
		template.flush();
	}

	@Transactional
	@Override
	public MailAddress getMailAdressById(int mailAdressID) {
		return template.get(MailAddress.class, mailAdressID);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<MailAddress> findAllMailAdress() {
		List<MailAddress> result = template.find("from MailAddress");
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<MailAddress> findAllMailByLookForId(int lookForId){
		List<MailAddress> result = template.find("from MailAddress where look_for_id=" + lookForId);
		return result;
		
	}
}

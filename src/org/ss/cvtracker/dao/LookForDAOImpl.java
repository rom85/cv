package org.ss.cvtracker.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ss.cvtracker.domain.LookFor;
import org.ss.cvtracker.domain.MailServer;



@Repository
public class LookForDAOImpl implements LookForDAO {
	
	private HibernateTemplate template;

	@Autowired
	public void setTemplate(HibernateTemplate template) {
		this.template = template;
	}
	
	@Transactional
	@Override
	public void add(LookFor lookFor) {
		template.save(lookFor);
		template.flush();
		
	}

	@Transactional
	@Override
	public void update(LookFor lookFor) {
		template.saveOrUpdate(lookFor);
		template.flush();
		
	}

	@Transactional
	@Override
	public void delete(LookFor lookFor) {
		template.delete(lookFor);
		template.flush();
		
	}

	@Transactional
	@Override
	public LookFor getLookForById(int lookForID) {
		return template.get(LookFor.class, lookForID);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<LookFor> findAllLookFor() {
		List<LookFor> result = template.find("from LookFor");
		return result;
	}

}

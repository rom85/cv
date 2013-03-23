package org.ss.cvtracker.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ss.cvtracker.domain.Constant;

@Repository
public class ConstantDAOImpl implements ConstantDAO {
	private HibernateTemplate template;

	@Autowired
	public void setTemplate(HibernateTemplate template) {
		this.template = template;
	}

	@Transactional
	@Override
	public void add(Constant constant) {
		template.save(constant);
		template.flush();
	}

	@Transactional
	@Override
	public void update(Constant constant) {
		template.saveOrUpdate(constant);
		template.flush();
	}

	@Transactional
	@Override
	public void delete(Constant constant) {
		template.delete(constant);
		template.flush();
	}

	@Transactional
	@Override
	public Constant getConstantById(int constantID) {
		return template.get(Constant.class, constantID);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Constant> findAllConstant() {
		return template.find("from Constant ORDER BY name ASC");
	}
}

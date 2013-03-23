package org.ss.cvtracker.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ss.cvtracker.domain.User;
import javax.sql.DataSource;

@Repository
public class UserDAOImpl implements UserDAO {
	private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	private DataSource dataSource;
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	@Autowired
	public void setTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Transactional
	public void addUser(User user) {
		hibernateTemplate.save(user);
		hibernateTemplate.flush();
	}

	@Transactional
	public void updateUser(User user) {
		hibernateTemplate.saveOrUpdate(user);
		hibernateTemplate.flush();
	}

	@Transactional
	public void delete(User user) {
		hibernateTemplate.delete(user);
		hibernateTemplate.flush();
	}

	@Transactional
	public User getUserByName(String name) {
		return hibernateTemplate.get(User.class, name);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<User> findUsers() {
		return hibernateTemplate.find("from User order by username");
	}
	
	public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}

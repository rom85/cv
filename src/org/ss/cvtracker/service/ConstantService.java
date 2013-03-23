package org.ss.cvtracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.cvtracker.dao.ConstantDAO;
import org.ss.cvtracker.domain.Constant;

@Service
public class ConstantService {

	@Autowired
	ConstantDAO constantDao;

	public void add(Constant constant) {
		if (!constantDao.findAllConstant().contains(constant))
			constantDao.add(constant);
	}

	public void update(Constant constant) {
		constantDao.update(constant);
	}

	public void delete(Constant constant) {
		constantDao.delete(constant);
	}

	public Constant get(int constantID) {
		return constantDao.getConstantById(constantID);
	}

	public List<Constant> findCountries() {
		return constantDao.findAllConstant();
	}

}

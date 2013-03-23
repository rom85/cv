package org.ss.cvtracker.dao;

import java.util.List;

import org.ss.cvtracker.domain.Constant;

public interface ConstantDAO {
	public void add(Constant constant);

	public void update(Constant constant);

	public void delete(Constant constant);

	public Constant getConstantById(int constantID);

	public List<Constant> findAllConstant();
}

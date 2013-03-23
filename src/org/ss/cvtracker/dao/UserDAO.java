package org.ss.cvtracker.dao;

import java.util.List;
import org.ss.cvtracker.domain.User;


public interface UserDAO {
	public void addUser(User user);
	public void updateUser(User user);
	public void delete(User user);
	public User getUserByName(String username);
	public List<User> findUsers();
}
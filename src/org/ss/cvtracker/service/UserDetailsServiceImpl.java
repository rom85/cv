package org.ss.cvtracker.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ss.cvtracker.dao.UserDAO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	Logger logger = Logger.getLogger(UserDetailsServiceImpl.class);
	@Autowired
	private UserDAO userDAO;

	@Transactional
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException, DataAccessException {
		org.ss.cvtracker.domain.User userFromDAO = userDAO
				.getUserByName(userName);
		if (userFromDAO == null) {
			throw new BadCredentialsException("Bad credentials");
		}
		List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>(
				1);
		grantedAuthorityList.add(new GrantedAuthorityImpl("ROLE_USER"));
		User user = new User(userFromDAO.getUsername(),
				userFromDAO.getPassword(), true, true, true, true,
				grantedAuthorityList);
		return user;
	}
}
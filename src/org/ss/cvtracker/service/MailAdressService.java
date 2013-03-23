package org.ss.cvtracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.cvtracker.dao.MailAddressDAO;
import org.ss.cvtracker.domain.MailAddress;

@Service
public class MailAdressService {
	@Autowired
	MailAddressDAO mailAddressDAO;

	public void add(MailAddress mailAddress) {
		mailAddressDAO.add(mailAddress);
	}

	public void update(MailAddress mailAddress) {
		mailAddressDAO.update(mailAddress);
	}

	public void delete(MailAddress mailAddress) {
		mailAddressDAO.delete(mailAddress);
	}

	public MailAddress getMailAdressById(int mailAdressID) {
		return mailAddressDAO.getMailAdressById(mailAdressID);
	}

	public List<MailAddress> findAllMailAdress() {
		return mailAddressDAO.findAllMailAdress();
	}
}

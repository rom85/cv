package org.ss.cvtracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.cvtracker.dao.LookForDAO;
import org.ss.cvtracker.domain.LookFor;



@Service
public class LookForService {
	@Autowired
	LookForDAO lookForDAO;
	
	public void add(LookFor lookFor) {
		lookForDAO.add(lookFor);
	}

	public void update(LookFor lookFor) {
		lookForDAO.update(lookFor);
	}

	public void delete(LookFor lookFor) {
		lookForDAO.delete(lookFor);
	}

	public LookFor getMailServerById(int mailServerID) {
		return lookForDAO.getLookForById(mailServerID);
	}

	public List<LookFor> findAllLookFor() {
		return lookForDAO.findAllLookFor();
	}
}

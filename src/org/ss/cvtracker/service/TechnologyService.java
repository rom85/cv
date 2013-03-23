package org.ss.cvtracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.cvtracker.dao.TechnologyDAO;
import org.ss.cvtracker.domain.Resume;
import org.ss.cvtracker.domain.Technology;

@Service
public class TechnologyService {

	@Autowired
	TechnologyDAO technologyDao;

	public void add(Technology technology) {
		if (!technologyDao.findAllTechnologies().contains(technology))
			technologyDao.add(technology);
	}

	public void update(Technology technology) {
		technologyDao.update(technology);
	}

	public void delete(Technology technology) {
		technologyDao.delete(technology);
	}

	public Technology get(int technologyID) {
		return technologyDao.getTechnologyById(technologyID);
	}

	public List<Technology> findTechnologies() {
		return technologyDao.findAllTechnologies();
	}

	public List<Resume> findResumes(Technology technology) {
		return technologyDao.findResumes(technology);
	}
}

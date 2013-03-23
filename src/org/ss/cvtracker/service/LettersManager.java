package org.ss.cvtracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.cvtracker.dao.LetterDAO;
import org.ss.cvtracker.domain.Letter;
import org.ss.cvtracker.domain.Resume;

@Service
public class LettersManager {
	@Autowired
	LetterDAO letterDao;

	public List<Letter> getAllLetters() {
		return letterDao.getLetters();
	}

	public int add(Letter letter) {
		return letterDao.add(letter);
	}

	public void update(Letter letter) {
		letterDao.update(letter);
	}

	public void delete(Letter letter) {
		letterDao.delete(letter);
	}

	public Letter get(int letterID) {
		return letterDao.getLetterById(letterID);
	}

	public List<Resume> findResumes(String receivedFrom) {
		return letterDao.findResumes(receivedFrom);
	}

	public List<String> findeMail() {
		return letterDao.findAlleMail();
	}
}

package org.ss.cvtracker.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.ss.cvtracker.dao.ConstantDAO;
import org.ss.cvtracker.dao.LetterDAO;
import org.ss.cvtracker.dao.ResumeLetterPatternDAO;
import org.ss.cvtracker.dao.LocationDAO;
import org.ss.cvtracker.dao.LocationSynonymsDAO;
import org.ss.cvtracker.dao.MailAddressDAO;
import org.ss.cvtracker.dao.MailServerDAO;
import org.ss.cvtracker.dao.ResumeDAO;
import org.ss.cvtracker.dao.TechnologyDAO;
import org.ss.cvtracker.domain.Letter;
import org.ss.cvtracker.domain.Pattern;
import org.ss.cvtracker.domain.ResumeLetterPattern;
import org.ss.cvtracker.domain.Location;
import org.ss.cvtracker.domain.MailAddress;
import org.ss.cvtracker.domain.MailServer;
import org.ss.cvtracker.domain.Resume;
import org.ss.cvtracker.domain.ResumePattern;
import org.ss.cvtracker.domain.Technology;
import org.ss.cvtracker.service.mail.LinkManager;
import org.ss.cvtracker.service.mail.TextParser;
import org.ss.cvtracker.service.mail.reader.MailSession;
import org.ss.cvtracker.service.mail.reader.MessageBean;
import org.ss.cvtracker.service.reader.ArchiveReader;

@Service
public class ResumeMailService {
	private static final int CONST_PROXY = 3;
	private static final String FORMAT_DATE = "dd.MM.yyyy";
	private static final int RESUME_ARCHIV_FOLDER = 2;
	private static final int EMAIL = 1;
	private static final int DEFAULT_PRIORITY = 2;
	private String ukrNet = "subscribe-job@ukr.net";

	@Autowired
	LetterDAO letterDao;

	@Autowired
	MailAddressDAO mailAddressDAO;

	@Autowired
	ResumeDAO resumeDao;

	@Autowired
	TechnologyDAO technologyDao;

	@Autowired
	LocationDAO locationDao;

	@Autowired
	MailServerDAO mailServerDAO;

	@Autowired
	LocationSynonymsDAO synonymsDAO;

	@Autowired
	ConstantDAO constant;

	@Autowired
	ResumeLetterPatternDAO letterPatternDAO;

	private LinkManager linkManager;
	private TextParser textParser;

	public ResumeMailService() {
		linkManager = new LinkManager();
		textParser = new TextParser();
	}

	private boolean getFromArchive;

	private String saveFolder;

	public static Logger log = Logger.getLogger(ResumeMailService.class);

	public void performBatchUpdate() {
		List<MailAddress> addresses = mailAddressDAO.findAllMailByLookForId(EMAIL);
		for (MailAddress addr : addresses) {
			MailServer server = mailServerDAO.getMailServerById(addr.getMailServerId());
			performSingleUpdate(addr, server);
		}
	}

	/**
	 * Method performs the whole cycle of operations to retrieve new CVs from
	 * one mail account (by analyzing all incoming messages in that account).
	 * Method saves results in the DB.
	 * 
	 * @param address
	 * @param server
	 */
	private void performSingleUpdate(MailAddress address, MailServer server) {
		List<MessageBean> newMessageBeans;
		ErrorService.setErrorStatus("");
		MailSession session = new MailSession(address, server);
		try {
			try {
				session.openConnection();
				Set<Message> messages = session.getMessages();
				newMessageBeans = session.fetchMessages(messages);
			} finally {
				session.closeConnection();
			}
			// now process all new messages
			parseLetter(newMessageBeans);
		} catch (MessagingException e) {
			ErrorService.setErrorStatus("E-Mail: " + e.getMessage());
			log.error(e);
		}
	}

	public void getLettersFromArchive(String dateFrom, String dateTo) {
		getFromArchive = true;
		Date[] date = getDateRestriction(dateFrom, dateTo);
		getLetterFromArchive(date);
		getFromArchive = false;
	}

	private void getLetterFromArchive(Date[] date) {
		List<MessageBean> newMessageBeans = null;
		setSaveFolder(constant.getConstantById(RESUME_ARCHIV_FOLDER).getConstant());
		ArchiveReader reader = new ArchiveReader();
		try {
			Date toDay;
			Calendar dateFrom = Calendar.getInstance();
			Calendar dateTo = Calendar.getInstance();

			dateFrom.setTime(date[0]);
			dateTo.setTime(date[1]);
			while (!dateTo.equals(dateFrom)) {

				toDay = dateFrom.getTime();

				newMessageBeans = reader.readFile(toDay, saveFolder);
				parseLetter(newMessageBeans);
				dateFrom.add(Calendar.DAY_OF_MONTH, 1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void parseLetter(List<MessageBean> MessageBeans) {
		for (Iterator<MessageBean> it = MessageBeans.iterator(); it.hasNext();) {
			MessageBean newMessageBean = it.next();
			if (!getFromArchive) {
				saveMsgToFile(newMessageBean);
			}
			List<Resume> newResumes = analyzeMessageBean(newMessageBean, technologyDao.findAllTechnologies(),
					locationDao.findAllLocations());
			if (!newResumes.isEmpty()) {
				// all newResumes letter fields reference the same Letter
				// object
				Letter letter = newResumes.get(0).getLetter();
				letter.setId(letterDao.add(letter));
				try {
					resumeDao.addAll(newResumes);
				} catch (DataIntegrityViolationException exc) {
					log.error(exc);
				}
				it.remove();
			}
		}
		// if we are here - no errors occurred - so all new letters have
		// been processed and we may save them to avoid double processing in
		// the future
		List<Letter> letters = new ArrayList<Letter>();
		for (MessageBean messageBean : MessageBeans) {
			letters.add(new Letter(messageBean.getDateSent(), messageBean.getFrom()));
			letterDao.addAll(letters);
		}

	}

	/**
	 * Method analyzes MessageBean by searching links inside text content,
	 * opening them and searching keywords in their content. Links containing
	 * keywords are returned in a list of Resume objects
	 * 
	 * @return list of Resume objects (with resume.id=null and
	 *         resume.letter.id=null). All Resume object contain reference to
	 *         the same Letter object
	 */
	private List<Resume> analyzeMessageBean(MessageBean messageBean, List<Technology> technologiesToBeSearched,
			List<Location> locationsToBeSearched) {

		String proxy = constant.getConstantById(CONST_PROXY).getConstant();

		List<Resume> result = new ArrayList<Resume>();
		List<ResumeLetterPattern> letterPatterns = decodeAllPatterns(letterPatternDAO.findAllPatterns());
		Map<String, Pattern> patternsMap = retrievePatterns(letterPatterns);
		String sender = messageBean.getFrom();
		ResumeLetterPattern letterPattern = (ResumeLetterPattern) patternsMap.get(sender);
		List<ResumePattern> resumePatterns = null;

		if (letterPattern != null) {
			resumePatterns = letterPattern.getResumePatterns();

			for (String link : linkManager.findLinks(messageBean, patternsMap)) {

				String linkContent = linkManager.readURL(link, proxy);

				if (linkContent != null) {

					String name = null;
					String salary = null;
					List<Location> foundLocations = null;
					List<Technology> foundTechnologies = null;

					if (!sender.equalsIgnoreCase(ukrNet)) {
						name = textParser.searchName(linkContent, resumePatterns.get(0));
					}

					foundTechnologies = textParser.searchTechnologies(linkContent, technologiesToBeSearched,
							resumePatterns.get(2));

					if (!foundTechnologies.isEmpty()) {
						List<Integer> locationId = textParser.searchLocation(linkContent, resumePatterns.get(3),
								locationsToBeSearched, synonymsDAO.findAllSynonyms());
						salary = textParser.searchSalary(linkContent, resumePatterns.get(1));

						Iterator<Integer> iterLocation = locationId.iterator();
						while (iterLocation.hasNext()) {
							Integer location = iterLocation.next();
							foundLocations = new ArrayList<Location>();
							foundLocations.add(locationDao.getLocationById(location));
						}

						result.add(new Resume(null, foundTechnologies, foundLocations, link, name, salary,
								DEFAULT_PRIORITY));
					}
				}
			}
		}

		Letter currentLetter = new Letter();
		currentLetter.setDate(messageBean.getDateSent());
		currentLetter.setReceivedFrom(messageBean.getFrom());
		currentLetter.setDataLetter(getHTMLPage(messageBean.getTextContent()));

		for (Resume resume : result) {
			resume.setLetter(currentLetter);
		}

		return result;
	}

	private String getHTMLPage(Set<String> letter) {
		StringBuffer buffer = new StringBuffer();
		Iterator<String> iter = letter.iterator();
		while (iter.hasNext()) {
			String line = iter.next();
			buffer.append(line + "\n");
		}
		return buffer.toString();
	}

	public int saveMsgToFile(MessageBean mess) {
		setSaveFolder(constant.getConstantById(RESUME_ARCHIV_FOLDER).getConstant());

		SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
		SimpleDateFormat dd = new SimpleDateFormat("dd");
		SimpleDateFormat MM = new SimpleDateFormat("MM");
		SimpleDateFormat fm = new SimpleDateFormat("HH-mm-");
		try {
			Set<String> contents = mess.getTextContent();
			Date d = mess.getDateSent();

			File dirMsg = new File(saveFolder + (yyyy.format(d)) + "/" + (MM.format(d)) + "/" + (dd.format(d)));
			dirMsg.mkdirs();
			File msgFile = new File(saveFolder + (yyyy.format(d)) + "/" + (MM.format(d)) + "/" + (dd.format(d)) + "/"
					+ (fm.format(d)) + mess.getFrom() + ".html");
			log.info("Wrote MSG into file  " + msgFile.getAbsolutePath());

			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(msgFile), "CP1251"));

			for (String content : contents) {
				out.write(content);
			}
			out.close();
			log.info("Wrote MSG in file ");
		} catch (Exception e) {
			log.error("In write MSG in file operation: " + e.getMessage());
			return -1;
		}
		return 0;
	}

	public String getSaveFolder() {
		return saveFolder;
	}

	public void setSaveFolder(String saveFolder) {
		this.saveFolder = saveFolder;
	}

	/**
	 * Method transforms String representation of date restriction into Date
	 */
	private Date[] getDateRestriction(String dateFrom, String dateTo) {

		Date[] dateLimit = new Date[2];
		SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DATE);
		Calendar dateCalendar = GregorianCalendar.getInstance();

		if ((!dateFrom.isEmpty()) && (!dateTo.isEmpty())) {
			try {
				dateLimit[0] = formatter.parse(dateFrom);
				Date date = formatter.parse(dateTo);
				dateCalendar.setTime(date);
				dateCalendar.add(Calendar.DAY_OF_MONTH, 1);
				dateLimit[1] = dateCalendar.getTime();
				return dateLimit;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else if ((!dateFrom.isEmpty()) && (dateTo.isEmpty())) {
			try {
				dateLimit[0] = formatter.parse(dateFrom);
				dateCalendar.setTime(dateLimit[0]);
				dateCalendar.add(Calendar.DAY_OF_MONTH, 1);
				dateLimit[1] = dateCalendar.getTime();
				return dateLimit;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else if ((dateFrom.isEmpty()) && (!dateTo.isEmpty())) {
			try {
				dateLimit[0] = formatter.parse(dateTo);
				dateCalendar.setTime(dateLimit[0]);
				dateCalendar.add(Calendar.DAY_OF_MONTH, 1);
				dateLimit[1] = dateCalendar.getTime();
				return dateLimit;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private List<ResumeLetterPattern> decodeAllPatterns(List<ResumeLetterPattern> letterPatterns) {
		List<ResumeLetterPattern> decodedPatterns = new ArrayList<ResumeLetterPattern>();
		List<ResumeLetterPattern> nonDecodedPatterns = letterPatterns;
		for (int i = 0; i < nonDecodedPatterns.size(); i++) {
			ResumeLetterPattern letterPattern = nonDecodedPatterns.get(i);
			letterPattern.setPattern(Translator.decoder(letterPattern.getPattern()));
			decodedPatterns.add(letterPattern);
		}
		return decodedPatterns;
	}

	private Map<String, Pattern> retrievePatterns(List<ResumeLetterPattern> letterPatterns) {
		Map<String, Pattern> patterns = new HashMap<String, Pattern>();
		for (int i = 0; i < letterPatterns.size(); i++) {
			String sender = letterPatterns.get(i).getEmail();
			int leftParenthesis = sender.indexOf('(');
			int rightParenthesis = sender.indexOf(')');
			String email = sender.substring(leftParenthesis + 1, rightParenthesis);
			patterns.put(email.trim(), letterPatterns.get(i));
		}
		return patterns;
	}
}

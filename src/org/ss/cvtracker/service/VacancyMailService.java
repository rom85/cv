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
import org.ss.cvtracker.dao.LocationDAO;
import org.ss.cvtracker.dao.LocationSynonymsDAO;
import org.ss.cvtracker.dao.MailAddressDAO;
import org.ss.cvtracker.dao.MailServerDAO;
import org.ss.cvtracker.dao.TechnologyDAO;
import org.ss.cvtracker.dao.VacancyDAO;
import org.ss.cvtracker.dao.VacancyLetterDAO;
import org.ss.cvtracker.dao.VacancyLetterPatternDAO;
import org.ss.cvtracker.domain.Location;
import org.ss.cvtracker.domain.MailAddress;
import org.ss.cvtracker.domain.MailServer;
import org.ss.cvtracker.domain.Vacancy;
import org.ss.cvtracker.domain.VacancyLetter;
import org.ss.cvtracker.domain.VacancyLetterPattern;
import org.ss.cvtracker.domain.VacancyPattern;
import org.ss.cvtracker.service.mail.LinkManager;
import org.ss.cvtracker.service.mail.TextParser;
import org.ss.cvtracker.service.mail.reader.MailSession;
import org.ss.cvtracker.service.mail.reader.MessageBean;
import org.ss.cvtracker.service.reader.ArchiveReader;

/**
 * Class for managing operatins with Vacancy objects;
 * @author IF-023
 */
@Service
public class VacancyMailService {
	/**
	 * Id number of current proxy server.
	 */
	private static final int CONST_PROXY = 3;
	/**
	 * Date format.
	 */
	private static final String FORMAT_DATE = "dd.MM.yyyy";
	/**
	 * Archive folder, where
	 */
	private static final int VACANCY_ARCHIV_FOLDER = 4;
	private static final int EMAIL = 2;// must be 2 here if vacancy e-mail

	@Autowired
	VacancyLetterDAO letterDao;

	@Autowired
	MailAddressDAO mailAddressDAO;

	@Autowired
	VacancyDAO vacancyDao;

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
	VacancyLetterPatternDAO letterPatternDAO;

	private LinkManager linkManager;
	private TextParser textParser;

	public VacancyMailService() {
		linkManager = new LinkManager();
		textParser = new TextParser();
	}

	private boolean getFromArchive;

	private String saveFolder;

	public static Logger log = Logger.getLogger(VacancyMailService.class);

	public void performBatchUpdate() {
		List<MailAddress> addresses = mailAddressDAO.findAllMailByLookForId(EMAIL);
		for (MailAddress addr: addresses){
			MailServer server = mailServerDAO.getMailServerById(addr.getMailServerId());
			performSingleUpdate(addr, server);
		}
	}

	/**
	 * Method performs the whole cycle of operations to retrieve new vacancies
	 * from one mail account (by analyzing all incoming messages in that
	 * account). Method saves results in the DB.
	 * 
	 * @param address
	 *            - mail address, from which letters with vacancies will be
	 *            read.
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
		setSaveFolder(constant.getConstantById(VACANCY_ARCHIV_FOLDER).getConstant());
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
			List<Vacancy> newVacancies = analyzeMessageBean(newMessageBean, locationDao.findAllLocations());
			if (!newVacancies.isEmpty()) {
				// all newResumes letter fields reference the same Letter
				// object
				VacancyLetter letter = newVacancies.get(0).getLetter();
				letter.setId(letterDao.add(letter));
				try {
					vacancyDao.addAll(newVacancies);
				} catch(DataIntegrityViolationException exc) {
					log.error(exc);
				}
				it.remove();
			}
		}
		// if we are here - no errors occurred - so all new letters have
		// been processed and we may save them to avoid double processing in
		// the future
		List<VacancyLetter> letters = new ArrayList<VacancyLetter>();
		for (MessageBean messageBean : MessageBeans) {
			letters.add(new VacancyLetter(messageBean.getDateSent(), messageBean.getFrom()));
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
	private List<Vacancy> analyzeMessageBean(MessageBean messageBean, List<Location> locationsToBeSearched) {

		String proxy = constant.getConstantById(CONST_PROXY).getConstant();

		List<Vacancy> result = new ArrayList<Vacancy>();
		List<VacancyLetterPattern> letterPatterns = decodeAllPatterns(letterPatternDAO.findAllPatterns());
		Map<String, org.ss.cvtracker.domain.Pattern> patternsMap = retrievePatterns(letterPatterns);
		String sender = messageBean.getFrom();
		VacancyLetterPattern letterPattern = (VacancyLetterPattern) patternsMap.get(sender);
		List<VacancyPattern> vacancyPatterns = null;

		if (letterPattern != null) {
			vacancyPatterns = letterPattern.getVacancyPatterns();

			for (String link : linkManager.findLinks(messageBean, patternsMap)) {

				String linkContent = linkManager.readURL(link, proxy);

				if (linkContent != null) {

					String companyName = null;
					String salary = null;
					List<Location> foundLocations = null;
					String job = null;

					companyName = textParser.searchName(linkContent, vacancyPatterns.get(0));
					if(companyName != null) {
						salary = textParser.searchSalary(linkContent, vacancyPatterns.get(1));

						job = textParser.searchVacancy(linkContent, vacancyPatterns.get(2));

						List<Integer> locationId = textParser.searchLocation(linkContent, vacancyPatterns.get(3),
								locationsToBeSearched, synonymsDAO.findAllSynonyms());

						Iterator<Integer> iterLocation = locationId.iterator();
						while (iterLocation.hasNext()) {
							Integer location = iterLocation.next();
							foundLocations = new ArrayList<Location>();
							foundLocations.add(locationDao.getLocationById(location));
						}

						result.add(new Vacancy(null, foundLocations, link, companyName, salary, job));
					}
				}
			}
		}
		VacancyLetter currentLetter = new VacancyLetter();
		currentLetter.setDate(messageBean.getDateSent());
		currentLetter.setReceivedFrom(messageBean.getFrom());
		currentLetter.setDataLetter(getHTMLPage(messageBean.getTextContent()));

		for (Vacancy vacancy : result) {
			vacancy.setLetter(currentLetter);
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
		setSaveFolder(constant.getConstantById(VACANCY_ARCHIV_FOLDER).getConstant());

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
	 * 
	 * 
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

	private List<VacancyLetterPattern> decodeAllPatterns(List<VacancyLetterPattern> letterPatterns) {
		List<VacancyLetterPattern> decodedPatterns = new ArrayList<VacancyLetterPattern>();
		List<VacancyLetterPattern> nonDecodedPatterns = letterPatterns;
		for (int i = 0; i < nonDecodedPatterns.size(); i++) {
			VacancyLetterPattern letterPattern = nonDecodedPatterns.get(i);
			letterPattern.setPattern(Translator.decoder(letterPattern.getPattern()));
			decodedPatterns.add(letterPattern);
		}
		return decodedPatterns;
	}

	private Map<String, org.ss.cvtracker.domain.Pattern> retrievePatterns(List<VacancyLetterPattern> letterPatterns) {
		Map<String, org.ss.cvtracker.domain.Pattern> patterns = new HashMap<String, org.ss.cvtracker.domain.Pattern>();
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

package org.ss.cvtracker.service.mail.reader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import org.apache.log4j.Logger;
import org.ss.cvtracker.domain.MailAddress;
import org.ss.cvtracker.domain.MailServer;

import com.sun.mail.pop3.POP3Folder;

public class MailSession {

	public Logger log = Logger.getLogger(MailSession.class);
	private Session session;
	private Properties props = System.getProperties();
	private Authenticator auth;
	private Store store;
	private POP3Folder inbox;

	public MailSession(MailAddress addr, MailServer server) {
		auth = new MsgAuthenticator(addr.getLogin(), addr.getPassword());
		props.put("mail.user", addr.getLogin());
		props.put("mail.host", server.getSmtpHost());
		props.put("mail.debug", "true");
		props.put("mail.store.protocol", "pop3");
		props.put("mail.transport.protocol", "smtp");
	}

	public void openConnection() throws MessagingException {
		log.debug("opening connection for " + props.getProperty("mail.user") + " at " + props.getProperty("mail.host"));
		session = Session.getInstance(props, auth);
		store = session.getStore();
		store.connect();
		inbox = (POP3Folder) store.getFolder("INBOX");

		inbox.open(Folder.READ_WRITE);

	}

	public void closeConnection() throws MessagingException {
		if (inbox != null) {
			log.debug("closing inbox for " + props.getProperty("mail.user") + " at " + props.getProperty("mail.host"));
			inbox.close(true);
		}
		if (store != null) {
			log.debug("closing inbox for " + props.getProperty("mail.user") + " at " + props.getProperty("mail.host"));
			store.close();
		}
	}

	public Set<Message> getMessages() throws MessagingException {
		Message[] messages = (Message[]) inbox.getMessages();

		HashSet<Message> result = new HashSet<Message>();
		for (int i = 0; i < messages.length; i++) {
			result.add(messages[i]);
			messages[i].setFlag(Flags.Flag.DELETED, true);
		}
		return result;
	}

	/**
	 * Method fetches information from inbox and puts it into MessageBean
	 * objects
	 * 
	 * @param messages
	 *            - collection of Message objects
	 * @return - list of MessageBean objects containing message info
	 * @throws MessagingException
	 */
	public List<MessageBean> fetchMessages(Collection<Message> messages) throws MessagingException {
		Set<String> textContent;
		List<MessageBean> result = new ArrayList<MessageBean>();
		for (Message message : messages) {
			textContent = new LinkedHashSet<String>();
			handleMessage(message, textContent);
			result.add(new MessageBean(((InternetAddress) (message.getFrom()[0])).getAddress().toString(), message
					.getSentDate(), textContent));
		}
		return result;
	}

	/**
	 * Method searches text content in a Message object and adds it to the
	 * resultsList
	 * 
	 * @param message
	 *            - message to be searched for text content
	 * @param textContent
	 *            - found content will go to this Set
	 */
	public static void handleMessage(Message message, Set<String> textContent) {
		try {
			Object content = message.getContent();
			if (content instanceof String) {// two if blocks - in case of
											// possible future changes - should
											// be deleted if not used
				if (message.isMimeType("text/html")) {
					textContent.add((String) content);
				}
				if (message.isMimeType("text/plain")) {
					textContent.add((String) content);
				}
			} else if (content instanceof Multipart) {
				Multipart mp = (Multipart) content;
				handleMultipart(mp, textContent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method searches text content in a multipart message
	 * 
	 * @param multipart
	 *            - message to be searched for text content
	 * @param textContent
	 *            - found content will go to this Set
	 */
	public static void handleMultipart(Multipart multipart, Set<String> textContent) {
		try {
			int count = multipart.getCount();
			for (int i = 0; i < count; i++) {
				BodyPart bp = multipart.getBodyPart(i);
				Object content = bp.getContent();

				if (content instanceof String) {
					if (bp.isMimeType("text/html")) {
						textContent.add((String) content);

					} else if (bp.isMimeType("text/plain")) {
						textContent.add((String) content);
					}
				} else if (content instanceof Message) {
					Message message = (Message) content;
					handleMessage(message, textContent);
				} else if (content instanceof Multipart) {
					Multipart innerMultipart = (Multipart) content;
					handleMultipart(innerMultipart, textContent);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package org.ss.cvtracker.service.mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.ss.cvtracker.service.ErrorService;
import org.ss.cvtracker.service.mail.reader.MessageBean;

/**
 * Responsible for resume's and vacancy's links managing(finding links in
 * appropriate letters and reading content of that links)
 */
public class LinkManager {
	/**
	 * Instance of Logger class for writing logs into console.
	 */
	public static Logger log = Logger.getLogger(LinkManager.class);
	/**
	 * Default pattern for searching links, that is used when application can't
	 * find necessary pattern in database.
	 */
	private String defaultPattern = "a[^<>]+href=\"(http[^\"\\s<>]+)";
	/**
	 * Map with all letter patterns, that exist in database.
	 */
	private Map<String, org.ss.cvtracker.domain.Pattern> lettersMap = null;
	/**
	 * Current port.
	 */
	private int port = 8080;
	/**
	 * Represents 'utf-8' charset type.
	 */
	private String utfCodingType = "UTF-8";

	/**
	 * Initialize Map with letter parameters.
	 */
	public LinkManager() {
		lettersMap = new HashMap<String, org.ss.cvtracker.domain.Pattern>();
	}

	/**
	 * Finds resume or vacancy letter's pattern in accordance to gotten
	 * MessageBean instance.
	 * 
	 * @param bean
	 *            - MessageBean instance from which resume or vacancy link will
	 *            be retrieved.
	 * @return if pattern exists in database it will be returned, if not default
	 *         pattern will be returned.
	 */
	public String getLinkPattern(MessageBean bean) {
		String sender = bean.getFrom();
		org.ss.cvtracker.domain.Pattern letterPattern = lettersMap.get(sender);
		if (letterPattern != null) {
			return letterPattern.getPattern();
		} else {
			return defaultPattern;
		}
	}

	/**
	 * Finds links to resumes or vacancies in current letter(MessageBean
	 * instance) using appropriate pattern.
	 * 
	 * @param bean
	 *            - represents current letter, from which links will be gotten
	 * @param patterns
	 *            - map of patterns for retrieving resume's or vacancy's links
	 *            from letter.
	 * @return set of link, found in current letter
	 */
	public Set<String> findLinks(MessageBean bean, Map<String, org.ss.cvtracker.domain.Pattern> patterns) {
		lettersMap = patterns;
		Set<String> links = new HashSet<String>();
		Set<String> contents = bean.getTextContent();
		String patternString = getLinkPattern(bean);

		for (String content : contents) {
			Pattern pattern = Pattern.compile(patternString);
			Matcher matcher = pattern.matcher(content);
			while (matcher.find()) {
				links.add((matcher.group(1)).toString());
			}
		}
		return links;
	}

	/**
	 * Gets charset of current resume or vacancy and reads it using appropriate
	 * charset's type. Then encodes data into 'UTF-8' charset and returns
	 * resume's or vacancy's page.
	 * 
	 * @param urlString
	 *            - link to resume or vacancy
	 * @param constProxy
	 *            - host name
	 * @return line, that contains the whole resume or vacancy
	 */
	public String readURL(String urlString, String constProxy) {
		ErrorService.setErrorStatus("");
		HttpURLConnection urlConnection;
		InputStream is = null;
		BufferedReader reader = null;
		StringBuilder resume = new StringBuilder();
		String utf8String = null;
		String charset = getCharset(urlString, constProxy);

		try {
			URL url = new URL(urlString);
			if (constProxy.isEmpty()) {
				urlConnection = (HttpURLConnection) url.openConnection();
			} else {
				Proxy prox = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(constProxy, port));
				urlConnection = (HttpURLConnection) url.openConnection(prox);
			}
			urlConnection.connect();

			log.info("opening: " + urlString);
			InputStream inputStream = urlConnection.getInputStream();
			// Reads current file if one exists and charset was read.
			if (inputStream != null && charset != null) {
				reader = new BufferedReader(new InputStreamReader(inputStream, charset));
				String inputLine;
				while ((inputLine = reader.readLine()) != null) {
					resume.append(inputLine);
				}
				// Convert resume string from old charset into 'utf-8'.
				byte[] utf8Bytes = resume.toString().getBytes(utfCodingType);
				utf8String = new String(utf8Bytes, utfCodingType);
			}
		} catch (IllegalArgumentException e1) {
			ErrorService.setErrorStatus("Error: " + e1.getMessage());
			log.error(e1);

		} catch (MalformedURLException e2) {
			ErrorService.setErrorStatus("Error: " + e2.getMessage());
			log.error(e2);
		} catch (IOException e3) {
			ErrorService.setErrorStatus("Error: " + e3.getMessage());
			log.error("Cannot open URL: " + urlString, e3);
		} finally {
			try {
				if (reader != null)
					reader.close();
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return utf8String;
	}

	/**
	 * Reads current resume or vacancy file and finds there type of charset.
	 * 
	 * @param urlString
	 *            - link to current resume or vacancy
	 * @param constProxy
	 *            - host name
	 * @return type of retrieved charset
	 */
	public String getCharset(String urlString, String constProxy) {
		HttpURLConnection urlConnection;
		InputStream is = null;
		BufferedReader reader = null;
		StringBuilder resume = new StringBuilder();
		String charset = null;

		try {

			URL url = new URL(urlString);
			if (constProxy.isEmpty()) {
				urlConnection = (HttpURLConnection) url.openConnection();
			} else {
				Proxy prox = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(constProxy, port));
				urlConnection = (HttpURLConnection) url.openConnection(prox);
			}
			urlConnection.connect();

			log.info("opening: " + urlString);
			InputStream inputStream = urlConnection.getInputStream();
			if (inputStream != null) {
				// Reads current file, using default charset.
				reader = new BufferedReader(new InputStreamReader(inputStream));
				String inputLine;
				while ((inputLine = reader.readLine()) != null) {
					resume.append(inputLine);
				}
				// Finds charset type in current file.
				Pattern pattern = Pattern.compile("charset=(.+)\".*>.+");
				Matcher matcher = pattern.matcher(resume.toString());
				if (matcher.find()) {
					String interim = matcher.group(1);
					int quotes = interim.indexOf('\"');
					charset = interim.substring(0, quotes).trim();
				}
			}
		} catch (IllegalArgumentException e1) {
			ErrorService.setErrorStatus("Error: " + e1.getMessage());
			log.error(e1);

		} catch (MalformedURLException e2) {
			ErrorService.setErrorStatus("Error: " + e2.getMessage());
			log.error(e2);
		} catch (IOException e3) {
			ErrorService.setErrorStatus("Error: " + e3.getMessage());
			log.error("Cannot open URL: " + urlString, e3);
		} finally {
			try {
				if (reader != null)
					reader.close();
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return charset;
	}
}

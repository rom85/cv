package org.ss.cvtracker.service;

import org.springframework.scheduling.annotation.Scheduled;

/**
 * Simple stub class to represent static variable for error handling
 * Errors are being generated in the MailService.java (internet connection error, mailbox log-in error) 
 * and in the LinkManager.java (proxy-server connection errors and URL-parsing errors)
 * setter method is called from MailService and LinkManager classes and getter method used in ErrorController class
 * 
 * 
 * @author Akceptor
 *
 */
public class ErrorService {
	
	private static String ErrorStatus = "";

	public static String getErrorStatus() {
		return ErrorStatus;
	}

	public static void setErrorStatus(String errorStatus) {
		ErrorStatus = errorStatus;
	}


}

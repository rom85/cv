package org.ss.cvtracker.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class VacancyPage extends Page {
	public VacancyPage() {
		initialized = false;

		setMail = false;
		setLocation = false;

		pageNumber = 1;
		pageSize = 10;
		sortOrder = SORT_ORDER_DESC;
		sortColumn = "letterDate";
		first = false;
		last = false;
		hasNext = false;
		hasPrevious = false;
		hasContent = false;
		showEmptyLocations = false;
		numberOfResults = -1l;

		constInbox = true;

		toDateLimit();
	}
}

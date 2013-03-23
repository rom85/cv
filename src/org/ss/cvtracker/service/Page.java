package org.ss.cvtracker.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class Page {
	private static final String FORMAT_DATE = "dd.MM.yyyy";
	public final static String SORT_ORDER_ASC = "asc";
	public final static String SORT_ORDER_DESC = "desc";
	
	/**
	 * Filter setting - specifies the period of time for which CVs should be
	 * shown in inbox: If set to null then no date restrictions are applied
	 */
	
	private Long totalLetters;
	private Long totalResumes;
	private String lastUpdateDate;
	private Long totalVacancyLetters;
	private Long totalVacancies;
	
	public Long getTotalVacancyLetters() {
		return totalVacancyLetters;
	}

	public void setTotalVacancyLetters(Long totalVacancyLetters) {
		this.totalVacancyLetters = totalVacancyLetters;
	}

	public Long getTotalVacancies() {
		return totalVacancies;
	}

	public void setTotalVacancies(Long totalVacancies) {
		this.totalVacancies = totalVacancies;
	}

	public Long getTotalLetters() {
		return totalLetters;
	}

	public void setTotalLetters(Long totalLetters) {
		this.totalLetters = totalLetters;
	}

	public Long getTotalResumes() {
		return totalResumes;
	}

	public void setTotalResumes(Long totalResumes) {
		this.totalResumes = totalResumes;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String dateFrom;
	public String dateTo;
	public int pageNumber;
	public int pageSize;
	public String sortOrder;
	public String sortColumn;
	/**
	 * Shows total number of results found for a given page (at current filter
	 * settings). Other methods may force re-count this property by setting it
	 * to -1L. Re-count will be performed at content update
	 */
	public Long numberOfResults;
	/**
	 * Indicates whether this page has next page
	 */
	public boolean hasNext;
	/**
	 * indicates whether this page has previous page
	 */
	public boolean hasPrevious;
	/**
	 * indicates whether this page has first page
	 */
	public boolean first;
	/**
	 * indicates whether this page has last page
	 */
	public boolean last;
	/**
	 * selects all elements Mail
	 */
	public boolean setMail;
	/**
	 * selects all elements Technology
	 */
	public boolean setTechnology;
	/**
	 * selects all elements Location
	 */
	public boolean setLocation;

	/**
	 * List of CVs to be shown in inbox
	 */
	public List<?> content;
	/**
	 * Indicates whether content for this page has been generated
	 */
	public boolean hasContent;
	/**
	 * Filter setting - specifies IDs of technologies to be included into CV
	 * list shown in inbox
	 */
	public List<Integer> technologyIDs;
	/**
	 * Filter setting - specifies IDs of locations to be included into CV list
	 * shown in inbox
	 */
	public List<Integer> locationIDs;
	/**
	 * Filter setting - specifies IDs of aMails to be included into CV list
	 * shown in inbox
	 */
	public List<String> eMails;
	/**
	 * Filter setting - specifies whether to show in inbox CVs without any
	 * location specified
	 */
	public String country;
	/**
	 * Filter setting - specifies whether to show in inbox CVs without any
	 * location specified
	 */
	public boolean showEmptyLocations;
	/**
	 * Filter setting - specifies whether to show in inbox CVs
	 * 
	 */
	public boolean concerningTechonology;

	/**
	 * Indicates whether filter settings (that is thechnologyIDs, locationIDs
	 * and showEmptyLocations) have been set up
	 */
	public boolean initialized;

	public boolean constInbox;

	public void toDateLimit() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);

		Date yesterday = calendar.getTime();

		SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DATE);
		String formattedDate = formatter.format(yesterday);

		dateFrom = formattedDate;
		dateTo = formattedDate;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public List<?> getContent() {
		return content;
	}

	public void setContent(List<?> content) {
		this.content = content;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public boolean getHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public boolean getHasPrevious() {
		return hasPrevious;
	}

	public void setHasPrevious(boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}

	public boolean getFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public boolean getLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public boolean hasContent() {
		return hasContent;
	}

	public void setHasContent(boolean hasContent) {
		this.hasContent = hasContent;
	}

	public List<Integer> getTechnologyIDs() {
		return technologyIDs;
	}

	public void setTechnologyIDs(List<Integer> technologyIDs) {
		this.technologyIDs = technologyIDs;
	}

	public List<Integer> getLocationIDs() {
		return locationIDs;
	}

	public void setLocationIDs(List<Integer> locationIDs) {
		this.locationIDs = locationIDs;
	}

	public boolean getShowEmptyLocations() {
		return showEmptyLocations;
	}

	public void setShowEmptyLocations(boolean showEmptyLocations) {
		this.showEmptyLocations = showEmptyLocations;
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	public Long getNumberOfResults() {
		return numberOfResults;
	}

	public void setNumberOfResults(Long numberOfResults) {
		this.numberOfResults = numberOfResults;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public boolean isSetMail() {
		return setMail;
	}

	public void setSetMail(boolean setMail) {
		this.setMail = setMail;
	}

	public boolean isSetLocation() {
		return setLocation;
	}

	public void setSetLocation(boolean setLocation) {
		this.setLocation = setLocation;
	}

	public boolean isSetTechnology() {
		return setTechnology;
	}

	public void setSetTechnology(boolean setTechnology) {
		this.setTechnology = setTechnology;
	}

	public List<String> geteMails() {
		return eMails;
	}

	public void seteMails(List<String> eMails) {
		this.eMails = eMails;
	}

	public boolean isConstInbox() {
		return constInbox;
	}

	public void setConstInbox(boolean constInbox) {
		this.constInbox = constInbox;
	}

	public boolean isConcerningTechonology() {
		return concerningTechonology;
	}

	public void setConcerningTechonology(boolean concerningTechonology) {
		this.concerningTechonology = concerningTechonology;
	}
}

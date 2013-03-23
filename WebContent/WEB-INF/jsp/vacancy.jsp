<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix='security'
	uri='http://www.springframework.org/security/tags'%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<title>CVTracker</title>

<style type="text/css">
SELECT {
	width: 202px;
}
</style>

<link rel="stylesheet"
	href="<c:url value="/resources/cvtracker.css" context="/CVTracker"/>" />

<!-- datepicker -->
<link rel="stylesheet"
	href="<c:url value="/resources/datepicker.css" context="/CVTracker"/>" />
<script src="<c:url value="/resources/js/jquery-1.5.1.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/ui.datepicker.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/validForm.js"/>"
	type="text/javascript"></script>
<!-- datepicker -->
<!-- thumbnail -->
<script type="text/javascript">$(document).ready(function(){$('a.screenshot').hover(function(){var path = $(this).attr('href');$(this).append('<div style="position:fixed; top: 10px; left: 10px; border:2px dashed blue;"><img class="screenshot" src="http://mini.s-shot.ru/1024x768/600/jpeg/?'+path+'" align="right"/></div> ');},function(){$('img.screenshot').remove();})});</script>
<!-- thumbnail -->

<script type="text/javascript">
$(document).ready(function(){
	$(".leftba").css({"color":"#000659","background-color":"#c8c8c8"});
	$("#vacancykey").css({"color":"#FFFFFF","background-color":"#0000FF"});
	$("#check").css({"color":"#FFFFFF","background-color":"#FFFFFF"});
});
</script>

<script type="text/javascript">
function getCountry()
   {
	var e = document.getElementById("select");
	var strID = e.options[e.selectedIndex].value;
	var loc = '<c:url value="/vacancy/country/'+strID+'"/>';
	window.location = loc;
   }
   
</script>

<script type="text/javascript">
function buttonAll(all, none, nameElement)
   {
	all.disabled=true;
	document.getElementById(none).disabled=false;
	   var check = document.getElementsByName(nameElement);
	   for (var i=0; i<check.length; i++)
	      {
	      check[i].checked = true;
	      }	 
   }
   
</script>

<script type="text/javascript">
function buttonNone(none, all, nameElement)
   {
	none.disabled=true;
	document.getElementById(all).disabled=false;
	   var check = document.getElementsByName(nameElement);
	   for (var i=0; i<check.length; i++)
	      {
	      check[i].checked = false;
	      }	 
   }
   
</script>

<script type="text/javascript">
function checkboxClick(nameElement, all, none)
   {
		var checkAllDisable=true;
		var checkNoneDisable=true;
		var check = document.getElementsByName(nameElement);
		for (var i=0; i<check.length; i++)
		{
			if (check[i].checked) {
				checkNoneDisable=false;
				}
			else {
				checkAllDisable=false;
				}
		}
	   document.getElementById(all).disabled=checkAllDisable;
	   document.getElementById(none).disabled=checkNoneDisable;
	}
 
</script>

</head>

<body>

	<!--Top of the page, grey bar with a logo-->
	<c:import url="header.jsp" charEncoding="UTF-8" />

	<!--Left bar with navigation menu-->

	<c:import url="menu.jsp" charEncoding="UTF-8" />

	<!--Right bar with search criteria-->
	<div id="rightbar">
		<form:form commandName="vCurrentPage" action="vacancy/filter"
			onsubmit="return validDate(this)">
			<div class="bluebold">Date</div>


			<form:input type="text" path="dateFrom" value="" />
 to <form:input type="text" path="dateTo" value="" />
			<script>
	$(function() {
		$( "#dateFrom, #dateTo" ).attachDatepicker({
			showOn: "button",
			buttonImage: '<c:url value="/resources/images/calendar.gif"/>',
			buttonImageOnly: true,
			yearRange: '2011:2015',
			dateFormat: 'dd.mm.yy',
		  	firstDay: 1,
		    //onSelect: function( selectedDate ) {
			//	var option = this.id == "dateFrom" ? "minDate" : "maxDate",
			//		instance = $( this ).data( "attachDatepicker" ),
			//		date = $.Datepicker.parseDate(
			//			instance.settings.dateFormat ||
			//			$.datepicker._defaults.dateFormat,
			//			selectedDate, instance.settings );
			//	dates.not( this ).datepicker( "option", option, date );
			//}
		});
	});
	</script>



			<br />
			<div class="bluebold">From</div>

			<div id="content_from">
				<button id="buttonAllEMailes" type="button"
					onclick="buttonAll(this,'buttonNoneEMailes','eMails')">All</button>
				<button id="buttonNoneEMailes" type="button"
					onclick="buttonNone(this,'buttonAllEMailes','eMails')">None</button>
				<span id="eMails"></span> <br />
				<c:forEach items="${mailAddresses}" var="mail">
					<form:checkbox path="eMails" value="${mail}"
						onclick="checkboxClick('eMails', 'buttonAllEMailes', 'buttonNoneEMailes')" />
					<c:out value="${mail}" />
					<br />
				</c:forEach>
				<script type="text/javascript"> checkboxClick('eMails', 'buttonAllEMailes', 'buttonNoneEMailes')</script>
			</div>

			<div class="bluebold">Location</div>
			<select name="select" id="select" size="1" onChange="getCountry()">
				<c:forEach items="${countries}" var="country">
					<c:choose>
						<c:when test="${country.id==vCurrentPage.country}">
							<option value="${country.id}" selected>
								<c:out value="${country.country}" />
							</option>
						</c:when>
						<c:otherwise>
							<option value="${country.id}">
								<c:out value="${country.country}" />
							</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
			<p />
			<div id="content_location">
				<form:checkbox path="showEmptyLocations" />
				location not specified
				<hr WIDTH="50%" ALIGN="center" size="0">
				<button id="buttonAllLocationIDs" type="button"
					onclick="buttonAll(this,'buttonNoneLocationIDs','locationIDs')">All</button>
				<button id="buttonNoneLocationIDs" type="button"
					onclick="buttonNone(this,'buttonAllLocationIDs','locationIDs')">None</button>
				<span id="locationIDs"></span> <br />
				<c:forEach items="${locations}" var="loc">
					<form:checkbox path="locationIDs" value="${loc.id}"
						onclick="checkboxClick('locationIDs', 'buttonAllLocationIDs', 'buttonNoneLocationIDs')" />
					<c:out value="${loc.location}" />
					<br />
				</c:forEach>
				<script type="text/javascript">checkboxClick('locationIDs', 'buttonAllLocationIDs', 'buttonNoneLocationIDs')</script>
			</div>

			<div id="search_button">
				<input type="submit" name="submitname2" value="Search"
					class="forbuttons" onmouseover="this.style.cursor='pointer'" />
			</div>
		</form:form>
	</div>


	<!--The vacancy with search results-->

	<div id="inbox" class="hideable" style="margin-left: 0;">
		<div class="searchResult">
			Total number of results: ${vCurrentPage.numberOfResults} &nbsp &nbsp
			&nbsp Total number of vacancies: ${vCurrentPage.totalVacancies} &nbsp
			&nbsp &nbsp Total number of letters:
			${vCurrentPage.totalVacancyLetters} &nbsp &nbsp &nbsp Last update:
			${vCurrentPage.lastUpdateDate}
			<div class="refresh" align="right">
				<span title="Refresh table"><a href=""
					onClick='<c:url value="/vacancy/refresh"/>'> <img
						src="<c:url value="/resources/images/Refresh_resized.png"/>"
						alt="Refresh">
				</a></span>
			</div>
		</div>

		<table>
			<tr>
				<c:if test="${!vCurrentPage.content.isEmpty()}">
					<!-- Helpful property for debugging CVTracker application. When setting "debug" variable to "true", two columns with resume's id and with number of current row will be added to the main table. -->
					<c:set var="debug" value="false" />

					<th><a href='<c:url value="/vacancy/sort/letterDate"/>'><spring:message
								code="label.date" /></a></th>
					<th><a
						href='<c:url value="/vacancy/sort/letterReceivedFrom"/>'><spring:message
								code="label.email" /></a></th>
					<th><a href=''><spring:message code="label.firm" /></a></th>
					<th><a href=''><spring:message code="label.job" /></a></th>
					<!--потрібно доробити Cael, low priority-->
					<th><a href=''><spring:message code="label.locations" /></a></th>

					<th><a href=''><spring:message code="label.salary" /></a></th>
					<!--потрібно доробити Cael, low priority-->
					<th><a href='<c:url value="/vacancy/sort/link"/>'><spring:message
								code="label.link" /></a></th>
					<th><a href=''><spring:message code="label.functions" /></a></th>
				</c:if>
			</tr>
			<c:forEach items="${vCurrentPage.content}" var="res"
				varStatus="status">
				<tr>
					<c:if test="${debug}">
						<td>${status.count}</td>
						<td>${res.id}</td>
					</c:if>
					<td><fmt:formatDate value="${res.date}" pattern="dd/MM/yyyy" />
					</td>
					<td><c:out value="${res.from}" /></td>
					<td><c:choose>
							<c:when test="${res.name!=null}">
								<c:out value="${res.name}" />
							</c:when>
							<c:otherwise>
								<c:out value="---" />
							</c:otherwise>
						</c:choose></td>

					<td><c:choose>
							<c:when test="${res.job!=null}">
								<c:out value="${res.job}" />
							</c:when>
							<c:otherwise>
								<c:out value="---" />
							</c:otherwise>
						</c:choose></td>

					<td><c:choose>
							<c:when test="${res.locations!='[]'}">
								<c:forEach items="${res.locations}" var="location">
									<c:out value="${location.location}" />
									<br />
								</c:forEach>
							</c:when>
							<c:when test="${res.locations=='[]'}">
								<c:out value="---" />
							</c:when>
						</c:choose></td>
					<td><c:choose>
							<c:when test="${res.salary!=null}">
								<c:out value="${res.salary}" />
							</c:when>
							<c:otherwise>
								<c:out value="---" />
							</c:otherwise>
						</c:choose></td>


					<td><span title="Open information"><a
							href='<c:out value="${res.link}" />' target="_blank"
							class="screenshot"><img
								src="<c:url value="/resources/images/INFO.png"/>"
								alt="Information"></a></span></td>
					<td><span title="Open letter"><a
							href='<c:url value="/vacancy/letter/${res.id}"/>' target="_blank"><img
								src="<c:url value="/resources/images/ok_button.png"/>"
								alt="Letter"></a></span> <span title="Open resume"><a
							href='<c:url value="/vacancy/show/${res.id}"/>' target="_blank"><img
								src="<c:url value="/resources/images/print-resume.png"/>"
								alt="Print"></a></span> <span title="Delete resume"><a
							onclick="return confirm('Are You sure?')"
							href='<c:url value="/vacancy/delete/${res.id}"/>'><img
								src="<c:url value="/resources/images/delete_button.png"/>"
								alt="Delete"></a></span></td>
				</tr>
			</c:forEach>

			<c:if test="${vCurrentPage.content.isEmpty()}">
				<h2>
					<spring:message code="label.vnotfound" />
				</h2>
			</c:if>
		</table>

		<div class="searchResult">
			<li style="display: inline">
				<ul>
					<c:if test="${vCurrentPage.first}">
						<a href='<c:url value="vacancy/page/first"/>'>First</a>
					</c:if>
				</ul>
				<ul>
					<c:if test="${vCurrentPage.hasPrevious}">
						<a href='<c:url value="vacancy/page/previous"/>'>Previous</a>
					</c:if>
				</ul>
				<ul>
					<c:if
						test="${!vCurrentPage.content.isEmpty() and (vCurrentPage.content.size()>1)}">
						<c:out value="${vCurrentPage.pageNumber}"></c:out>
					</c:if>
				</ul>
				<ul>
					<c:if test="${vCurrentPage.hasNext}">
						<a href='<c:url value="vacancy/page/next"/>'>Next</a>
					</c:if>
				</ul>
				<ul>
					<c:if test="${vCurrentPage.last}">
						<a href='<c:url value="vacancy/page/last"/>'>Last</a>
					</c:if>
				</ul>
			</li>
			<p />
			<c:if test="${!vCurrentPage.content.isEmpty()}">
				<span>Results on page:</span>
				<a href='<c:url value="/vacancy/pagesize/10"/>'>10</a>
				<a href='<c:url value="/vacancy/pagesize/20"/>'>20</a>
				<a href='<c:url value="/vacancy/pagesize/30"/>'>30</a>
			</c:if>
		</div>
	</div>


	<!--Bottom of the page with "Copyright".-->

	<c:import url="footer.jsp" charEncoding="UTF-8" />

</body>
</html>

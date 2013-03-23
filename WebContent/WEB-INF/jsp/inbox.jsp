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
<script src="<c:url value="/resources/js/jquery.dataTables.min.js"/>"
	type="text/javascript"></script>
<!-- datepicker -->
<!-- thumbnail -->
<script type="text/javascript">$(document).ready(function(){$('a.screenshot').hover(function(){var path = $(this).attr('href');$(this).append('<div style="position:fixed; top: 10px; left: 10px; border:2px dashed blue;"><img class="screenshot" src="http://mini.s-shot.ru/1024x768/600/jpeg/?'+path+'" align="right"/></div> ');},function(){$('img.screenshot').remove();})});</script>
<!-- pop-up -->
<script src="<c:url value="/resources/js/pop.js"/>"
	type="text/javascript"></script>
<link rel="stylesheet"
	href="<c:url value="/resources/pop.css" context="/CVTracker"/>" />


<script type="text/javascript">

function validDate(form) {
	
	var dateFrom = form.elements["dateFrom"].value;
var dateTo = form.elements["dateTo"].value;

if ((dateFrom == "") || (dateTo == "")) {
	alert("Date field is empty");
	return false;
}
			
// dateFrom generator for object Date
var dateFromArr = dateFrom.split('.');
var dateFromStr = dateFromArr[1] + "/"+dateFromArr[0]+"/"+dateFromArr[2];
var dateFromObj = new Date();
dateFromObj.setTime(Date.parse(dateFromStr));

// dateTo generator for object Date
var dateToArr = dateTo.split('.');
var dateToStr = dateToArr[1] + "/"+dateToArr[0]+"/"+dateToArr[2];
var dateToObj = new Date();
dateToObj.setTime(Date.parse(dateToStr));

// Validation

// start date is bigger then finish one
if (dateFromObj > dateToObj) {
	alert("Incorrect input! You have entered start date "+dateFromStr+" bigger then finish date "+dateToStr+". Change it please!");
	return false;
}	
	
var s = "eMails";
var k = 0;
var i=1;

while (form.elements[s+i]){

i=i+1;
k=k+1;

}
var quontity = k;


var k1=0;
for (var j = 1;j < quontity+1;j++){

if (!form.elements[s+j].checked) k1++; 
}
var quontityChecked = k1;	
		
if (quontity == quontityChecked) {
alert("No e-mails are choosen. You should choose some e-mail address.");
return false;
}

return true;
}
</script>

<script type="text/javascript">
function getCountry()
   {
	var e = document.getElementById("select");
	var strID = e.options[e.selectedIndex].value;
	var loc = '<c:url value="/inbox/country/'+strID+'"/>';
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
		<form:form commandName="currentPage" action="inbox/filter"
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

			<div class="bluebold">Technology</div>
			<form:checkbox path="ConcerningTechonology" /> Concerning the one candidate<br />
			<div id="content_technology">
				<button id="buttonAllTechnologyIDs" type="button"
					onclick="buttonAll(this,'buttonNoneTechnologyIDs','technologyIDs')">All</button>
				<button id="buttonNoneTechnologyIDs" type="button"
					onclick="buttonNone(this,'buttonAllTechnologyIDs','technologyIDs')">None</button>
				<span id="technologyIDs"></span> <br />
				<c:forEach items="${technologies}" var="tech">
					<form:checkbox path="technologyIDs" value="${tech.id}"
						onclick="checkboxClick('technologyIDs', 'buttonAllTechnologyIDs', 'buttonNoneTechnologyIDs')" />
					<c:out value="${tech.technology}" />
					<br />
				</c:forEach>
				<script type="text/javascript">checkboxClick('technologyIDs', 'buttonAllTechnologyIDs', 'buttonNoneTechnologyIDs')</script>
			</div>
			<div class="bluebold">Location</div>
			<select name="select" id="select" size="1" onChange="getCountry()">
				<c:forEach items="${countries}" var="country">
					<c:choose>
						<c:when test="${country.id==currentPage.country}">
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


	<!--The inbox with search results-->

	<div id="inbox" class="hideable" style="margin-left: 0;">
		<div class="searchResult">
			Total number of results: ${currentPage.numberOfResults} &nbsp &nbsp
			&nbsp Total number of resumes: ${currentPage.totalResumes} &nbsp
			&nbsp &nbsp Total number of letters: ${currentPage.totalLetters}
			&nbsp &nbsp &nbsp Last update: ${currentPage.lastUpdateDate}
			<div class="refresh" align="right">
				<span title="Refresh table"><a href=""
					onClick='<c:url value="/inbox/refresh"/>'> <img
						src="<c:url value="/resources/images/Refresh_resized.png"/>"
						alt="Refresh">
				</a></span>
			</div>
		</div>
		<table>
			<tr>
				<c:if test="${!currentPage.content.isEmpty()}">
					<!-- Helpful property for debugging CVTracker application. When setting "debug" variable to "true", two columns with resume's id and with number of current row will be added to the main table. -->
					<c:set var="debug" value="false" />
					<c:if test="${debug}">
						<th>#</th>
						<th>cd_id</th>
					</c:if>
					<th><a href='<c:url value="/inbox/sort/letterDate"/>'><spring:message
								code="label.date" /></a></th>
					<th><a href='<c:url value="/inbox/sort/letterReceivedFrom"/>'><spring:message
								code="label.email" /></a></th>
					<th><a href=''><spring:message code="label.name" /></a></th>
					<!--потрібно доробити Cael, low priority-->
					<th><a href=''><spring:message code="label.technologies" /></a></th>
					<th><a href=''><spring:message code="label.locations" /></a></th>

					<th><a href=''><spring:message code="label.salary" /></a></th>
					<th><a href='<c:url value="/inbox/sort/priority"/>'><spring:message
								code="label.priority" /></a></th>
					<th><a href='<c:url value="/inbox/sort/link"/>'><spring:message
								code="label.link" /></a></th>
					<th><a href=''><spring:message code="label.functions" /></a></th>
				</c:if>
			</tr>
			<c:forEach items="${currentPage.content}" var="res"
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
					<td><c:forEach items="${res.technologies}" var="technology">
							<c:out value="${technology.technology}" />
							<br />
						</c:forEach></td>
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
					<td><c:choose>
							<c:when test="${res.priority!=null}">
								<span style="display: none">${res.priority}</span>
								<img class="dummy" id="imgAnno_${res.id}"
									src="<c:url value="/resources/images/priority_${res.priority}.png" />" />
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
					<td><span title="Annotation"><a
							href="javascript:ShowHidePopup(${res.id})" id="annoLink${res.id}"><img
								src="<c:url value="/resources/images/annotation.png"/>"
								alt="Annotation"></a></span><span title="Open letter"><a
							href='<c:url value="/inbox/letter/${res.id}"/>' target="_blank"><img
								src="<c:url value="/resources/images/ok_button.png"/>"
								alt="Letter"></a></span> <span title="Open resume"><a
							href='<c:url value="/inbox/show/${res.id}"/>' target="_blank"><img
								src="<c:url value="/resources/images/print-resume.png"/>"
								alt="Print"></a></span> <span title="Delete resume"><a
							onclick="return confirm('Are You sure?')"
							href='<c:url value="/inbox/delete/${res.id}"/>'><img
								src="<c:url value="/resources/images/delete_button.png"/>"
								alt="Delete"></a></span></td>

				</tr>
			</c:forEach>
			<c:if test="${currentPage.content.isEmpty()}">
				<h2>
					<spring:message code="label.notfound" />
				</h2>
			</c:if>
		</table>

		<div class="searchResult">
			<li style="display: inline">
				<ul>
					<c:if test="${currentPage.first}">
						<a href='<c:url value="inbox/page/first"/>'>First</a>
					</c:if>
				</ul>
				<ul>
					<c:if test="${currentPage.hasPrevious}">
						<a href='<c:url value="inbox/page/previous"/>'>Previous</a>
					</c:if>
				</ul>
				<ul>
					<c:if
						test="${!currentPage.content.isEmpty() and (currentPage.content.size()>1)}">
						<c:out value="${currentPage.pageNumber}"></c:out>
					</c:if>
				</ul>
				<ul>
					<c:if test="${currentPage.hasNext}">
						<a href='<c:url value="inbox/page/next"/>'>Next</a>
					</c:if>
				</ul>
				<ul>
					<c:if test="${currentPage.last}">
						<a href='<c:url value="inbox/page/last"/>'>Last</a>
					</c:if>
				</ul>
			</li>
			<p />
			<c:if test="${!currentPage.content.isEmpty()}">
				<span>Results on page:</span>
				<a href='<c:url value="/inbox/pagesize/10"/>'>10</a>
				<a href='<c:url value="/inbox/pagesize/20"/>'>20</a>
				<a href='<c:url value="/inbox/pagesize/30"/>'>30</a>
			</c:if>
		</div>
	</div>

	<!--  =================================================  -->

	<div id="viewAnno">Test</div>
	<div id="panepop">
		<form:form id="fmMyAnno">
			<input id="fmfAnnoId" type="hidden" />
			<p>
				<label>Priority</label>
				<!-- 			<input type="text" name="annop" id="fmfAnnoP" />  -->
				<select name="annop" id="fmfAnnoP">
					<option value="1">Low Priority</option>
					<option value="2">Middle Priority</option>
					<option value="3">High Priority</option>
				</select>
			</p>
			<p>
				<label>Annotation</label><br>
				<textarea rows="6" name="anno" id="fmfAnno" cols="35"></textarea>
			</p>
			<p>
				<input type="button" onclick="UpdAnno()" value="Update" /> <input
					type="button" onclick="ShowHidePopup()" value="Cancel" /> <input
					type="button" onclick="clearAnno()" value="Clear" />
			</p>
		</form:form>
	</div>


	<!--  =================================================  -->



	<!--Bottom of the page with "Copyright".-->

	<c:import url="footer.jsp" charEncoding="UTF-8" />

</body>
</html>

<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<title><spring:message code="label.settings" /></title>

<link rel="stylesheet"
	href="<c:url value="/resources/cvtracker.css" context="/CVTracker"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/tab.css" context="/CVTracker"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/jquery-ui-1.css" context="/CVTracker"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/jquery.ui.theme.css" context="/CVTracker"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/jquery.ui.base.css" context="/CVTracker"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/date.css" context="/CVTracker"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/jquery-ui.css" context="/CVTracker"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/jquery-cron.css" context="/CVTracker"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/jquery-gentleSelect.css" context="/CVTracker"/>" />

<script src="<c:url value="/resources/js/jquery-1.5.1.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/myjs.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/jquery.ui.core.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/jquery.ui.widget.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/jquery.ui.tabs.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/jquery.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/jquery.validate.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/jquery.cookies.2.2.0.min.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/jquery-ui.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/jquery-gentleSelect-min.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/jquery-cron-min.js"/>"
	type="text/javascript"></script>

<style type="text/css">
SELECT {
	width: 202px;
}

#tab_block {
	width: 70%;
}
</style>

<script type="text/javascript">
    $(document).ready(function() {
    	$('#selector').cron({
    	    initial: "${constCron.constant}".substring(2),
    	    onChange: function() {
    	    	$("#constCron").val("* "+$(this).cron("value"));    	    	
    	    }
    	});
    	});
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".leftba").css({
			"color" : "#000659",
			"background-color" : "#c8c8c8"
		});
		$("#settingskey").css({
			"color" : "#FFFFFF",
			"background-color" : "#0000FF"
		});
		$("#check").css({
			"color" : "#F8F8FF",
			"font-family" : "sans-serif;"
		});

	});
</script>
<script>
	$(function() {
		$("#accordion").accordion();
	});
</script>
<script>
	$(function() {
		$("#accordion2").accordion();
	});
</script>
<script type="text/javascript">
	function getCountry() {
		var e = document.getElementById("select");
		var strID = e.options[e.selectedIndex].value;
		var loc = '<c:url value="/inbox/country/'+strID+'"/>';
		window.location = loc;
	}
</script>
<script type="text/javascript">
	function showMail() {
		document.getElementById("tab-mail").hidden = false;
		document.getElementById("tab-local").hidden = true;
		document.getElementById("tab-tech").hidden = true;
		document.getElementById("tab-arch").hidden = true;
		document.getElementById("tab-const").hidden = true;
		document.getElementById("tab-resumeConfig").hidden = true;
		document.getElementById("tab-vacancyConfig").hidden = true;
		document.getElementById("addrId").value = "0";		
	};

	function showLocal() {
		document.getElementById("tab-mail").hidden = true;
		document.getElementById("tab-local").hidden = false;
		document.getElementById("tab-tech").hidden = true;
		document.getElementById("tab-arch").hidden = true;
		document.getElementById("tab-const").hidden = true;
		document.getElementById("tab-resumeConfig").hidden = true;
		document.getElementById("tab-vacancyConfig").hidden = true;
		document.getElementById("cityId").value = "0";
	};

	function showTech() {
		document.getElementById("tab-mail").hidden = true;
		document.getElementById("tab-local").hidden = true;
		document.getElementById("tab-tech").hidden = false;
		document.getElementById("tab-arch").hidden = true;
		document.getElementById("tab-const").hidden = true;
		document.getElementById("tab-resumeConfig").hidden = true;
		document.getElementById("tab-vacancyConfig").hidden = true;
		document.getElementById("techId").value = "0";
	};

	function showArch() {
		document.getElementById("tab-mail").hidden = true;
		document.getElementById("tab-local").hidden = true;
		document.getElementById("tab-tech").hidden = true;
		document.getElementById("tab-arch").hidden = false;
		document.getElementById("tab-const").hidden = true;
		document.getElementById("tab-resumeConfig").hidden = true;
		document.getElementById("tab-vacancyConfig").hidden = true;
	}

	function showConst() {
		document.getElementById("tab-mail").hidden = true;
		document.getElementById("tab-local").hidden = true;
		document.getElementById("tab-tech").hidden = true;
		document.getElementById("tab-arch").hidden = true;
		document.getElementById("tab-const").hidden = false;
		document.getElementById("tab-resumeConfig").hidden = true;
		document.getElementById("tab-vacancyConfig").hidden = true;
	};

	function showResPatConfig() {
		document.getElementById("tab-mail").hidden = true;
		document.getElementById("tab-local").hidden = true;
		document.getElementById("tab-tech").hidden = true;
		document.getElementById("tab-arch").hidden = true;
		document.getElementById("tab-const").hidden = true;
		document.getElementById("tab-resumeConfig").hidden = false;
		document.getElementById("tab-vacancyConfig").hidden = true;
	};
	
	function showVacPatConfig() {
		document.getElementById("tab-mail").hidden = true;
		document.getElementById("tab-local").hidden = true;
		document.getElementById("tab-tech").hidden = true;
		document.getElementById("tab-arch").hidden = true;
		document.getElementById("tab-const").hidden = true;
		document.getElementById("tab-resumeConfig").hidden = true;
		document.getElementById("tab-vacancyConfig").hidden = false;
	};
</script>
<script>
	$(function() {
		$("#tab_block").tabs({
			selected : ($.cookies.get(cookieName) || 0),
			select : function(e, ui) {
				$.cookies.set(cookieName, ui.index);
			}
		}
		/*{
		    event: "mouseover"
		}*/);
	});
</script>


<script>
	var cookieName = "mycookie";

	$("#tab_block").tabs({
		selected : ($.cookies.get(cookieName) || 0),
		select : function(e, ui) {
			$.cookies.set(cookieName, ui.index);
		}
	});
</script>

<script type="text/javascript">
	/*functions for search tabs visualization*/
	$(document).ready(function() {
		tab = 1;
		/*function for sliding search options*/
		$("#search_show").click(function() {
			if (tab == 1) {
				$("#location_options").slideToggle("slow");
			}
			if (tab == 2) {
				$("#technology_options").slideToggle("slow");
			}
			if (tab == 3) {
				$("#date_options").slideToggle("slow");
			}
		});

	});

	/*Sergi*/
	$(document).ready(function() {
		tab = 1;
		$("#search_loc").css("text-decoration", "underline");
		$("#search_loc").click(function() {
			$("#search_loc").css("text-decoration", "underline");
			tab = 1;
		});

		$("#search_sh").click(function() {
			if (tab == 1) {
				$("#loc_options").slideToggle("slow");
			}
		});
	});
	/*fuctions for "Technologies" slide menu in "Settings"*/
	$(document).ready(function() {
		tab = 1;
		$("#search_tech").css("text-decoration", "underline");
		$("#search_tech").click(function() {
			$("#search_tech").css("text-decoration", "underline");
			tab = 1;
		});

		$("#search_shtech").click(function() {
			if (tab == 1) {
				$("#tech_options").slideToggle("slow");
			}
		});
	});
	/*fuctions for "Mail" slide menu in "Settings"*/
	$(document).ready(function() {
		tab = 1;
		$("#search_mail").css("text-decoration", "underline");
		$("#search_mail").click(function() {
			$("#search_mail").css("text-decoration", "underline");
			tab = 1;
		});

		$("#search_shmail").click(function() {
			if (tab == 1) {
				$("#mail_options").slideToggle("slow");
			}
		});
	});
</script>

<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.validate.js"/>"></script>

<!-- email validation function -->
<script type="text/javascript">
	function validateEmail(email) {
		var re = /^[a-zA-Z0-9._]+[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[a-zA-Z]{2,4}$/;
		return re.test(email);
	}
</script>
<!-- Technology field validation function -->
<script type="text/javascript">
	function valTech() {
		var tech = document.getElementById("tech").value;
		var techReg = /^[a-zA-Z0-9+\\s#]+$/;
		var digits = /^\d+$/;
		if (tech == "" || !techReg.test(tech) || digits.test(tech)) {
			alert("technology field is empty or contains special characters");
			return false;
		}
	}
</script>
<!-- empty City field validation function -->
<script type="text/javascript">
	function valCity() {
		var city = document.getElementById("city").value;
		var cityReg = /^[a-zA-Z-\\s-]+$/;
		if (city == "" || !cityReg.test(city)) {
			alert(" field 'City' is empty or contains special characters");
			return false;
		} else {
			return true;
		}
	}
</script>
<!-- empty Password field validation function -->
<script type="text/javascript">
	function emptyValPass() {
		var pas = document.getElementById("pass").value;
		if (pas != "") {
			return true;
		} else {
			return false;
		}
	}
</script>
<!-- validate input and generate login in hidden Login field -->
<script type="text/javascript">
	function addLogin() {
		var addr = document.getElementById("addr").value;

		if (validateEmail(addr) && emptyValPass()) {
			document.getElementById("log").value = addr.substring(0, addr
					.indexOf("@"));
			return true;
		} else {
			alert("field is empty or contains special characters");
			return false;
		}
	};
</script>

<!-- datepicker -->
<link rel="stylesheet"
	href="<c:url value="/resources/datepicker.css" context="/CVTracker"/>" />

<script src="<c:url value="/resources/js/ui.datepicker.js"/>"
	type="text/javascript"></script>
<!-- datepicker -->
</head>
<body>
	<c:import url="header.jsp" charEncoding="UTF-8" />
	<!-- start page -->
	<div id="page">
		<c:import url="menu.jsp" charEncoding="UTF-8" />
		<!-- start content -->
		<!--First table for MAIL-->
		<div id="tab_block" style="float: left; margin-left: 10%">
			<ul>
				<li><a href="#tab-mail" onclick="showMail()">Mail</a></li>
				<li><a href="#tab-local" onclick="showLocal()">Location</a></li>
				<li><a href="#tab-tech" onclick="showTech()">Technology</a></li>
				<li><a href="#tab-arch" onclick="showArch()">Data loading</a></li>
				<li><a href="#tab-const" onclick="showConst()">Constant</a></li>
				<li><a href="#tab-resumeConfig" onclick="showResPatConfig()">ResumePatternsConfig</a></li>
				<li><a href="#tab-vacancyConfig" onclick="showVacPatConfig()">VacancyPatternsConfig</a></li>
			</ul>
			<div id="tab-mail">
				<!--form for validation-->
				<table border="0" class="blue">
					<tr>

						<form:form modelAttribute="mailAddress" action="addMailAddress"
							method="POST" onsubmit="return addLogin()">
							<tr>
								<td>Address</td>
								<td><form:input path="address" value="" id="addr"
										type="text" /> <form:errors path="address" /></td>
								<td rowspan="4"></td>
							</tr>

							<!-- we hide Login field because it just part of Address
								 and we leave it to autogenerator with addLogin()-->
							<tr style="display: none;">
								<td>Login</td>
								<td><form:input path="login" id="log" type="text" value="" />
									<form:errors path="login" /></td>
							</tr>
							<tr>
								<td>Password</td>
								<td><form:input id="pass" type="password" path="password" />
									<form:errors path="password" /></td>
							</tr>

							<tr>
								<td>MailServer</td>
								<td><form:select path="mailServerId" name="select"
										id="select" size="1" style="width: 100%;">
										<c:forEach items="${servers}" var="server">
											<option value="${server.id}">
												<c:out value="${server.name}" />
											</option>
										</c:forEach>
									</form:select></td>
							</tr>
							<tr>
								<td>Look for:</td>
								<td><form:select path="lookFor" name="select"
										id="select" size="1" style="width: 100%;">
										<c:forEach items="${lookFors}" var="lookFor">
											<option value="${lookFor.id}">
												<c:out value="${lookFor.name}" />
											</option>
										</c:forEach>											 									
									</form:select></td>
							</tr>
							<!-- Hiden field for id of MailAddress, we want to edit -->
							<tr style="display: none;">
								<td>AddressId</td>
								<td><form:input path="id" value="" id="addrId" type="text" /> </td>
								
							</tr>
							<!--Submit button -->
							<td><input id="addLog"
								onmouseover="this.style.cursor='pointer'" class="serbuttons"
								type="submit" value="Add mail" name="addUpdateMail" /></td>
							<td><input id="save"
								onmouseover="this.style.cursor='pointer'" class="serbuttons"
								type="submit" value="Save changes" name="addUpdateMail" /></td>
						</form:form>	
						<form:form modelAttribute="mailAddr" action="cancelMailAddress"
							method="POST" >	
							<td><input id="cancelLog"
								onmouseover="this.style.cursor='pointer'" class="serbuttons"
								type="submit" value="Cancel" name="Add mail" /></td>
						</form:form>
					</tr>
					<tr></tr>
				</table>
				<div>&nbsp;</div>
				<!--slide menu: we used it before if-023java  -->
				<!-- 
				<div id="search_shmail" class="show"
					onmouseover="this.style.cursor='pointer'">List of Addresses</div>
				<div id="mail_options" class="search_mail">
					<table class="blue" border="0">
						
						<c:forEach items="${mailAddresses}" var="mail">
							<tr>
								<td><c:out value="${mail.address}" /></td>
								<td><a onclick="return confirm('Are You sure?')"
									href='<c:url value="/settings/mailAddress/${mail.id}/delete"/>'>
										<img
										src="<c:url value="/resources/images/delete_button.png"/>"
										alt="delete" />
								</a></td>
							</tr>
						</c:forEach>
					</table>
				</div>  -->

				<div>&nbsp;</div>
				<div>
					<table class="table addresses" border="0" class="blue">
						<thead>
							<tr>
								<th>Address</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${mailAddresses}" var="mail">
								<tr>
									<td><c:out value="${mail.address}" /></td>
									<td><a onclick="return confirm('Are You sure?')"
										href='<c:url value="/settings/mailAddress/${mail.id}/delete"/>'>
											<img
											src="<c:url value="/resources/images/delete_button.png"/>"
											alt="delete" />
									</a></td>
									<td><a href='<c:url value="/settings/mailAddress/${mail.id}/edit"/>'>
											<img src="<c:url value="/resources/images/edit_button.png"/>"
											alt="delete" />
									</a></td>
									<!-- 
									<td><a onclick="return confirm('Are You sure?')"
										href='<c:url value="/settings/mailAddress/${mail.id}/delete"/>'>
											<img src="<c:url value="/resources/images/test_button.png"/>"
											alt="delete" />
									</a></td>
									 -->
								</tr>
							</c:forEach>

						</tbody>
					</table>

				</div>
			</div>



			<!--Second table for Locations-->
			<div id="tab-local">
				<table class="blue" border="0">
					
						<form:form modelAttribute="location" action="addLocation"
							class="required" onsubmit="return valCity()">
							<tr>
								<td>Country</td>
								<td><select name="locations_country">
										<c:forEach items="${countries}" var="country">
											<c:choose>
												<c:when test="${country.id==constCountry.constant}">
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
								</select></td>
							</tr>
							<tr >
							<td>City</td>
							<td><form:input id="city" path="location"
									style="width: 100%;" /> <form:errors path="location" /></td>
							</tr>
							<!-- Hiden field for id of Location, we want to edit -->
							<tr style="display: none;">
							<td>CityId</td>
							<td><form:input id="cityId" path="id"
									style="width: 100%;" /> <form:errors path="location" /></td>
							</tr>
							
								<td><input class="serbuttons2" type="submit"
									name="addUpdateLocation" value="Add location"
									onmouseover="this.style.cursor='pointer'" /></td>
								<td><input class="serbuttons2" type="submit"
									name="addUpdateLocation" value="Save changes"
									onmouseover="this.style.cursor='pointer'" /></td>
						</form:form>
					
						<form:form modelAttribute="loc" action="cancelLocation"
							class="required" >	
								<td><input class="serbuttons2" type="submit"
									name="cancelLocation" value="Cancel"
									onmouseover="this.style.cursor='pointer'" /></td>
							
							<tr></tr>
						</form:form>
					
					<tr>
						<td>&nbsp;</td>
					</tr>
				</table>

				<div>
					<table class="table addresses" border="0" class="blue">
						<thead>
							<tr>
								<th>Cities</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${locations}" var="loc">
								<tr>
									<td><c:out value="${loc.location}" /></td>
									<td><a onclick="return confirm('Are You sure?')"
										href='<c:url value="/settings/location/${loc.id}/delete"/>'>
											<img
											src="<c:url value="/resources/images/delete_button.png"/>"
											alt="delete" />
									</a></td>
									<td><a href='<c:url value="/settings/location/${loc.id}/edit"/>'>
											<img
											src="<c:url value="/resources/images/edit_button.png"/>"
											alt="delete" />
									</a></td>

								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>
			</div>


			<!--Third Technologies table-->
			<div id="tab-tech">
				<table class="blue" border="0">
					<tr>
						<form:form modelAttribute="technology" action="addTechnology"
							method="POST" onsubmit="return valTech()">
							<tr>
								<td>Techology</td>
								<td><form:input id="tech" path="technology" /> <form:errors
										path="technology" /></td>
							</tr>
							<!-- Hiden field for id of Technology, we want to edit -->
							<tr style="display: none;">
								<td>TechologyId</td>
								<td><form:input id="techId" path="id" /> </td>
							</tr>
							<td><input class="serbuttons2" type="submit"
								name="addUpdateTechnology" value="Add technology"
								onmouseover="this.style.cursor='pointer'" /></td>
							<td><input class="serbuttons2" type="submit"
								name="addUpdateTechnology" value="Save changes"
								onmouseover="this.style.cursor='pointer'" /></td>
						</form:form>
						<form:form modelAttribute="tech" action="cancelTechnology"
							method="POST" >
							<td><input class="serbuttons2" type="submit"
								name="addUpdateTechnology" value="Cancel"
								onmouseover="this.style.cursor='pointer'" /></td>
						</form:form>
					</tr>
					<tr></tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<td rowspan="1000"></td>
				</table>

				<div>
					<table class="table addresses" border="0" class="blue">
						<thead>
							<tr>
								<th>Technologies</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${technologies}" var="techn">
								<tr>
									<td><c:out value="${techn.technology}" /></td>
									<td><a onclick="return confirm('Are You sure?')"
										href='<c:url value="/settings/technology/${techn.id}/delete"/>'><img
											src="<c:url value="/resources/images/delete_button.png"/>"
											alt="delete" /> </a></td>
									<td><a href='<c:url value="/settings/technology/${techn.id}/edit"/>'><img
											src="<c:url value="/resources/images/edit_button.png"/>"
											alt="delete" /> </a></td>
								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>
			</div>

			<!--Forth Archive table-->
			<div id="tab-arch">
				<form name="getArchive"
					action="<c:url value="/settings/getArchive"/>">
					<table width="100%">
						<tr>
							<td>
								<fieldset style="display: inline;">
									<legend>Loading letters from archives</legend>
									<input type="text" id="dateFrom" name="dateFrom" value="" />
									to <input type="text" id="dateTo" name="dateTo" value="" />
									<script>
													$(function() {
														$("#dateFrom, #dateTo")
																.attachDatepicker(
																		{
																			showOn : "button",
																			buttonImage : '<c:url value="/resources/images/calendar.gif"/>',
																			buttonImageOnly : true,
																			yearRange : '2011:2015',
																			dateFormat : 'dd.mm.yy',
																			firstDay : 1
																		});
													});
												</script>
									<input class="serbuttons2" type="submit"
										value="Get from archive"
										onmouseover="this.style.cursor='pointer'" />
								</fieldset>
							</td>
							<td>
								<fieldset style="display: inline">
									<legend>Loading letters from emails</legend>
									<input class="serbuttons2" type="button" value="Check Resumes"
										onmouseover="this.style.cursor='pointer'"
										onclick="window.location.href='<c:url value="/settings/updateResumes"/>'" />
									<input class="serbuttons2" type="button"
										value="Check Vacancies"
										onmouseover="this.style.cursor='pointer'"
										onclick="window.location.href='<c:url value="/settings/updateVacancies"/>'" />
								</fieldset>
							</td>
						</tr>
					</table>


				</form>

			</div>
			<div id="tab-const">
				<form
					action="<c:url value="/settings/updateConstant"/>">
					<table border="0" class="blue">
						<tr>
						<td>Updating</td>						
						<td><div id='selector' ></div></td>
						<td><input type="text" id="constCron" name="constCron" value='<c:out value="${constCron.constant}" />' /></td>				
						</tr>
						<tr>
							<td>Country</td>
							<td><select id="selectConstCountry"
								name="selectConstCountry" size="1">
									<c:forEach items="${countries}" var="country">
										<c:choose>
											<c:when test="${country.id==constCountry.constant}">
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
						</tr>
						<tr>
							<td>Resume archive folder</td>
							<td><span title=""><input type="text" size="30"
									id="constFolder" name="resConstFolder"
									value='<c:out value="${constResumeFolder.constant}" />' /></span></td>
						</tr>
						<tr>
							<td>Vacancy archive folder</td>
							<td><span title=""><input type="text" size="30"
									id="constFolder" name="vacConstFolder"
									value='<c:out value="${constVacancyFolder.constant}" />' /></span></td>
						</tr>
						<tr>
							<td>URL Proxy</td>
							<td><span title=""><input type="text" id="constProxy"
									name="constProxy" size="30"
									value='<c:out value="${constProxy.constant}" />' /></span></td>
						</tr>
					</table>
					<input type="submit" name="submitname2" value="Save change"
						class="forbuttons" onmouseover="this.style.cursor='pointer'" />
				</form>
			</div>
			<!-- Tab for letter's and resume's patterns configuration -->
			<div id="tab-resumeConfig">
				<div id="accordion">
					<c:forEach items="${resLetPatterns}" var="letterPattern">
						<h3>Site: ${letterPattern.email}</h3>
						<div>
							<form action="<c:url value="/settings/configResPatterns"/>"
								method="post">
								<table border="1" width="100%">
									<tr></tr>
									<tr>
										<td width="20%">Letter pattern</td>
										<td width="80%"><input type="text" name="letter"
											style="width: 100%" value="${letterPattern.pattern}" /></td>
										<td><input type="hidden" name="letPatId"
											value="${letterPattern.id}" /></td>
									</tr>
									<tr>
										<td width="20%">Name pattern</td>
										<td width="80%"><input type="text" name="name"
											style="width: 100%"
											value="${letterPattern.resumePatterns[0].pattern}" /></td>
										<td><input type="hidden" name="namePatId"
											value="${letterPattern.resumePatterns[0].id}" /></td>
									</tr>
									<tr>
										<td width="20%">Salary pattern</td>
										<td width="80%"><input type="text" name="salary"
											style="width: 100%"
											value="${letterPattern.resumePatterns[1].pattern}" /></td>
										<td><input type="hidden" name="salPatId"
											value="${letterPattern.resumePatterns[1].id}" /></td>
									</tr>
									<tr>
										<td width="20%">Technologies pattern</td>
										<td width="80%"><input type="text" name="tech"
											style="width: 100%"
											value="${letterPattern.resumePatterns[2].pattern}" /></td>
										<td><input type="hidden" name="techPatId"
											value="${letterPattern.resumePatterns[2].id}" /></td>
									</tr>
									<tr>
										<td width="20%">Location pattern</td>
										<td width="80%"><input type="text" name="location"
											style="width: 100%"
											value="${letterPattern.resumePatterns[3].pattern}" /></td>
										<td><input type="hidden" name="locPatId"
											value="${letterPattern.resumePatterns[3].id}" /></td>
									</tr>
								</table>
								<div>
									<p>
										<input type="submit" value="Save changes">
									</p>
								</div>
							</form>
							
							<b>You can also check if patterns work right.</b> <br><br>
							<form  action="<c:url value="/settings/checkPatterns"/>" method="POST">
							Input URL of resume to check it:<br>
								<input type="text" name="url" style="width: 80%" />
								<input type="hidden" name="name" value="${letterPattern.resumePatterns[0].pattern}"/>
								<input type="hidden" name="salary" value="${letterPattern.resumePatterns[1].pattern}"/>
								<input type="hidden" name="tech" value="${letterPattern.resumePatterns[2].pattern}"/>
								<input type="hidden" name="location" value="${letterPattern.resumePatterns[3].pattern}"/>
							
							<input type="submit" value="Test patterns">
							
							</form>
							
						</div>
					</c:forEach>
				<h3><b>Add new site with patterns</b> </h3>
					
					
							
						<div>
							<form action="<c:url value="/settings/configPatAdd"/>"
								method="POST">
								
					
								<table border="3" width="100%">
									<tr></tr>
									<tr>
									<td> New e-mail address</td>
									<td> <input type="text" name="newSite" style="width: 100%"/> </td>
									</tr>
									<tr>
										<td width="20%">Letter pattern</td>
										<td width="80%"><input type="text" name="letter"
											style="width: 100%" value="" /></td>
									</tr>
									
									<tr>
										<td width="20%">Name pattern</td>
										<td width="80%"><input type="text" name="name"
											style="width: 100%"
											value="" /></td>
									</tr>
									
									<tr>
										<td width="20%">Salary pattern</td>
										<td width="80%"><input type="text" name="salary"
											style="width: 100%"
											value="" /></td>
									</tr>
									
									<tr>
										<td width="20%">Technologies pattern</td>
										<td width="80%"><input type="text" name="tech"
											style="width: 100%"
											value="" /></td>
									</tr>
									
									<tr>
										<td width="20%">Location pattern</td>
										<td width="80%"><input type="text" name="location"
											style="width: 100%"
											value="" /></td>
									</tr>
								</table>
								<div>
									<p>
										<input type="submit" value="Add new element">
									</p>
								</div>
							</form>
							
							</div>
				</div>
			</div>
			
			<!-- Tab for letter's and vacancy's patterns configuration -->
			<div id="tab-vacancyConfig">
				<div id="accordion2">
					<c:forEach items="${vacLetPatterns}" var="letterPattern">
						<h3>Site: ${letterPattern.email}</h3>
						<div>
							<form action="<c:url value="/settings/configVacPatterns"/>"
								method="post">
								<table border="1" width="100%">
									<tr></tr>
									<tr>
										<td width="20%">Letter pattern</td>
										<td width="80%"><input type="text" name="letter"
											style="width: 100%" value="${letterPattern.pattern}" /></td>
										<td><input type="hidden" name="letPatId"
											value="${letterPattern.id}" /></td>
									</tr>
									<tr>
										<td width="20%">Name pattern</td>
										<td width="80%"><input type="text" name="name"
											style="width: 100%"
											value="${letterPattern.vacancyPatterns[0].pattern}" /></td>
										<td><input type="hidden" name="namePatId"
											value="${letterPattern.vacancyPatterns[0].id}" /></td>
									</tr>
									<tr>
										<td width="20%">Salary pattern</td>
										<td width="80%"><input type="text" name="salary"
											style="width: 100%"
											value="${letterPattern.vacancyPatterns[1].pattern}" /></td>
										<td><input type="hidden" name="salPatId"
											value="${letterPattern.vacancyPatterns[1].id}" /></td>
									</tr>
									<tr>
										<td width="20%">Vacancy pattern</td>
										<td width="80%"><input type="text" name="vacancy"
											style="width: 100%"
											value="${letterPattern.vacancyPatterns[2].pattern}" /></td>
										<td><input type="hidden" name="vacPatId"
											value="${letterPattern.vacancyPatterns[2].id}" /></td>
									</tr>
									<tr>
										<td width="20%">Location pattern</td>
										<td width="80%"><input type="text" name="location"
											style="width: 100%"
											value="${letterPattern.vacancyPatterns[3].pattern}" /></td>
										<td><input type="hidden" name="locPatId"
											value="${letterPattern.vacancyPatterns[3].id}" /></td>
									</tr>
								</table>
								<div>
									<p>
										<input type="submit" value="Save changes">
									</p>
								</div>
							</form>
							
						<b> You can also check if the patterns for vacancy letters are right.</b>
							<br><br>
							<form action="<c:url value="/settings/checkVacPatterns"/>"
								method="post">
							Input URL of vacancy letter to check it:<br>
							<input type="text" name="url" style="width: 80%" />
							<input type="hidden" name="name" value="${letterPattern.vacancyPatterns[0].pattern}"/>
							<input type="hidden" name="salary" value="${letterPattern.vacancyPatterns[1].pattern}"/>
							<input type="hidden" name="vacancy" value="${letterPattern.vacancyPatterns[2].pattern}"/>
							<input type="hidden" name="location" value="${letterPattern.vacancyPatterns[3].pattern}"/>
							<input type="submit" value="Test patterns">
							</form>
							
						</div>
					</c:forEach>
				<h3><b>Add new site with patterns</b></h3>
					
					<div>
							<form action="<c:url value="/settings/addVacPatterns"/>"
								method="post">
								<table border="1" width="100%">
									<tr></tr>
									<tr>
										<td width="20%">New e-mail</td>
										<td width="80%"><input type="text" name="newSite"
											style="width: 100%" value="" /></td>
										
									</tr>
									<tr>
										<td width="20%">Letter pattern</td>
										<td width="80%"><input type="text" name="letter"
											style="width: 100%" value="" /></td>
										
									</tr>
									<tr>
										<td width="20%">Name pattern</td>
										<td width="80%"><input type="text" name="name"
											style="width: 100%"
											value="" /></td>
										
									</tr>
									<tr>
										<td width="20%">Salary pattern</td>
										<td width="80%"><input type="text" name="salary"
											style="width: 100%"
											value="" /></td>
										
									</tr>
									<tr>
										<td width="20%">Vacancy pattern</td>
										<td width="80%"><input type="text" name="vacancy"
											style="width: 100%"
											value="" /></td>
										
									</tr>
									<tr>
										<td width="20%">Location pattern</td>
										<td width="80%"><input type="text" name="location"
											style="width: 100%"
											value="" /></td>
										
									</tr>
								</table>
								<div>
									<p>
										<input type="submit" value="Add new element">
									</p>
								</div>
							</form>
						</div>
					
					
				</div>
			</div>
		</div>
		<!-- end content -->
		<div style="clear: both;">&nbsp;</div>

	</div>

	<!-- start footer -->
	<c:import url="footer.jsp" charEncoding="utf-8" />
	<!-- end footer -->

</body>
</html>
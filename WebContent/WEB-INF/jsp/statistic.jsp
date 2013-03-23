<%@ include file="/WEB-INF/jsp/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Statistics</title>

<script src="<c:url value="/resources/js/jquery-1.5.1.js"/>"
	type="text/javascript"></script>
<!-- <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>-->
	<script src="<c:url value="/resources/js/dataTables.js"/>"
	type="text/javascript"></script>
	
	<link rel="stylesheet"
	href="/CVTracker/resources/styles.css" />
	<link rel="stylesheet" href="<c:url value="/resources/table.css" context="/CVTracker"/>" />

<script type="text/javascript">
$(document).ready(function(){
	$('#stat1').dataTable();
	$('#stat2').dataTable();
	$('#stat3').dataTable();
	$(".leftba").css({"color":"#000659","background-color":"#c8c8c8"});
	$("#statisticskey").css({"color":"#FFFFFF","background-color":"#0000FF"});
	$("#check").css({"color":"#FFFFFF","background-color":"#FFFFFF"});
});
</script>

	<!-- datepicker -->	
<link rel="stylesheet" href="<c:url value="/resources/datepicker.css" context="/CVTracker"/>" />	
	
	<script src="<c:url value="/resources/js/ui.datepicker.js"/>" type="text/javascript"></script>	
<!-- datepicker -->	


</head>
<body>
	<c:import url="header.jsp" charEncoding="utf-8" />
	<!-- start page -->
	

		<c:import url="menu.jsp" charEncoding="utf-8" />
		<!-- start content -->

		<div class="post">
			<div class="bluebig">STATISTICS</div>

			<div class="entry">
				<form name="formFilter" action="<c:url value="/statistic/buildTable"/>">
					<p>
					<div class="bluebold">Select option for which you want to get
						statistics</div>
					<Br> <input type="radio" name="param" value="Locations and technologies" checked> Locations and technologies
					<Br> <input type="radio" name="param" value="Locations"> Locations
					<Br> <input type="radio" name="param" value="Location and mails"> Location and mails<Br> 
			
			<input type="text" id="dateFrom" name="dateFrom"  value="${dateFrom}" />
 			to 
 			<input type="text" id="dateTo" name="dateTo" value="${dateTo}" />
			<script>
				$(function() {
					$( "#dateFrom, #dateTo" ).attachDatepicker({
						showOn: "button",
						buttonImage: '<c:url value="/resources/images/calendar.gif"/>',
						buttonImageOnly: true,
						yearRange: '2011:2015',
						dateFormat: 'dd.mm.yy',
					  	firstDay: 1
					});
				});
			</script>

					<input type="submit" value="Build"  class="forbuttons"/>
					
				</form>
				<br>
			</div>
			<br>
		</div>
<!-- Technology / Locations -->		
	
				<c:if test="${specres!=null}">
					<!--  <h2>Table Location Technology</h2><br>-->
						<div id="lettersstatistics" style="width:80%; position: absolute; top: 400px; left: 20px;">							
						<br><table cellpadding="0" cellspacing="0" border="0" class="display" id="stat1">
								<thead>
									<tr class="even.gradeC">
										<th>Locations/Technologies</th>
										<c:forEach items="${nameTechnologies}" var="technology">
											<th><c:out value="${technology.technology}" /></th>
										</c:forEach>
									</tr>
								</thead>
								<tbody>				
									<c:forEach items="${specres}" var="cres">
										<tr>
											<c:forEach items="${cres}" var="cresume">
												<td class="stat">
													<c:out value="${cresume}" />
												</td>
											</c:forEach>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot>
									<tr class="even.gradeC">
										<th>Locations/Technologies</th>
										<c:forEach items="${nameTechnologies}" var="technology">
											<th><c:out value="${technology.technology}" /></th>
										</c:forEach>
									</tr>
								</tfoot>
							</table>
						</div>
					</c:if>
			
<!-- Locations -->	
				
				<c:if test="${locations!=null}">
				<!--  <h2>Table Locations</h2><br> -->
					<div id="lettersstatistics" style="width:80%; position: absolute; top: 400px; left: 20px;">
					<br><table cellpadding="0" cellspacing="0" border="0" class="display" id="stat2">
							<thead>
								<tr class="even.gradeC">
									<th>Locations</th>
									<th>Number of resumes</th>
								</tr>
							</thead>
								<tbody>
									<c:forEach items="${locations}" var="location">
										<tr>
											<td><c:out value="${location[0]}" /></td><td><c:out value="${location[1]}" /></td>
										</tr>
									</c:forEach>
								</tbody>
							<tfoot>
							<tr class="even.gradeC">
									<th>Locations</th>
									<th>Number of resumes</th>
							</tr>
							</tfoot>
					</table>
					</div>
				</c:if>
				
<!-- Locations / Letters -->	

				<c:if test="${specLetterLocRes!=null}">
					<!-- <h2>Table Letter Location</h2><br> -->
					<div id="lettersstatistics" style="width:80%; position: absolute; top: 400px; left: 20px;">
						<br><table cellpadding="0" cellspacing="0" border="0" class="display" id="stat3">
								<thead>
									<tr class="even.gradeC">
										<th>Locations/Letters</th>
										<c:forEach items="${nameLetters}" var="letter">
											<th><c:out value="${letter}" /></th>
										</c:forEach>
									</tr>
								</thead>
								<tbody>				
									<c:forEach items="${specLetterLocRes}" var="cres">
										<tr  class="even.gradeC">
											<c:forEach items="${cres}" var="cresume">
												<td class="stat">
													<c:out value="${cresume}" />
												</td>
											</c:forEach>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot>
									<tr class="even.gradeC">
										<th>Locations/Letters</th>
										<c:forEach items="${nameLetters}" var="letter">
											<th><c:out value="${letter}" /></th>
										</c:forEach>
									</tr>
								</tfoot>
							</table>
					</div>
				</c:if>
			
	<!-- end content -->
	<div style="clear: both;">&nbsp;</div>

	<!-- end page -->
	<!-- start footer -->

	<!-- end footer -->
</body>
</html>
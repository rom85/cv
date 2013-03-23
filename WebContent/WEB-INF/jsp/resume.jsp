<%@ include file="/WEB-INF/jsp/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Resume</title>

<link rel='stylesheet' type='text/css' href="<c:url value="/resources/cvtracker.css" context="/CVTracker"/>" />
</head>
<body>

	
	<!-- start page -->
	<div id="page">
		<!-- start content -->
		<div class="letter_resume">
			<div >
				<h1 class="title">
					<spring:message code="label.resume" />
				</h1>
				<div class="entry">
					<div align="right">
						<h1>
							<a onclick="return confirm('Are You sure?')" href='<c:url value="/inbox/delete/${resume.id}"/>'><img
								src="<c:url value="/resources/img/delete.gif"/>" alt="delete" /> </a>
						</h1>
					</div>
					<div class="resume_table">
						<h2>
							<c:out value="${resume.date}" />
						</h2>
						<spring:message code="label.email" />:
						<c:out value="${resume.from}" />
						<br /><spring:message code="label.technologies" />:
						<c:forEach items="${resume.technologies}" var="technology">
							<c:out value="${technology.technology}" />
						</c:forEach>
						<br /><spring:message code="label.locations" />:
						<c:forEach items="${resume.locations}" var="location">
							<c:out value="${location.location}" />
						</c:forEach>
						<h2>
							<a href='<c:url value="${resume.link}" />'><c:out
									value="${resume.link}" /> </a>
						</h2>
					</div>
				</div>
			</div>
		</div>
		<!-- end content -->
		<div style="clear: both;">&nbsp;</div>
	</div>
	<!-- end page -->
	<!-- start footer -->
	<c:import url="footer.jsp" charEncoding="utf-8" />
	<!-- end footer -->
</body>
</html>
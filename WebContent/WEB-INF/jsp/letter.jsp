<%@ include file="/WEB-INF/jsp/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Letter</title>

<link rel='stylesheet' type='text/css' href="<c:url value="/resources/cvtracker.css" context="/CVTracker"/>" />
</head>
<body>

	
	<!-- start page -->
	<div id="page">
		<!-- start content -->
		<div id="content_">
			<div  class="letter_resume" >
				<h1 class="title">
					<spring:message code="label.letter" />
				</h1>
				<div class="entry">
					<div class="resume_table">
						<h2>
							<c:out value="${resume.date}" />
						</h2>
						<spring:message code="label.email" />:
						<c:out value="${resume.from}" />

						<h2>
								${resume.dataLetter}
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
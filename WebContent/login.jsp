<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="image/x-icon" href="<c:url value="/resources/images/fav.png"/>"
	rel="shortcut icon">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="resources/style.css">

<title>Login</title>
</head>
<body onload="document.f.j_username.focus();">
<br>
<br>
<br>
<br>
<br>
<br>
<br>


<center>
<br><font size="4">
</font>
<form name="f" action="<c:url value='j_spring_security_check'/>" method="POST">
<table id="simple2" style="border: 3px solid rgb(0, 0, 255);" border="0" height="120" width="100px">
<tbody><tr>
<td><a><img src="resources/images/Title.jpg" alt="Title" border="0" height="67" vspace="1" width="316"></a></td>
</tr>
<tr>
<td style="text-align: right; font-family: sans-serif; font-size: 22px;">Login:
	<input name="j_username" size="21" type="text" value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>'/>&nbsp;</td>
</tr>
<tr>
<td style="text-align: right; font-family: sans-serif; font-size: 22px;">Password:
	<input name="j_password" size="21" type="password">&nbsp;</td>
</tr>
<tr>
<td style="text-align: right;">
<input value="Login" name="submit" class="button" style="font-weight: bold; color: rgb(0, 0, 255); background-color: rgb(200, 200, 200); border-style: solid; border-color: rgb(0, 0, 255); border-width: 1px; font-family: sans-serif; font-size: 16px;" type="submit">
&nbsp;

</td>
</tr>
</tbody></table></form>
<%-- this form-login-page form is also used as the form-error-page to ask for a login again.--%>
	<c:if test="${not empty param.login_error}">
		<font color="red"> Your login attempt was not successful, try
			again.<br /> <br /> Reason: <c:out
				value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />. </font>
	</c:if>
</center>

</body>
</html>
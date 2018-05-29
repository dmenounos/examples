<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pd" %>

<c:set value="${pageContext.request.contextPath}" var="contextPath" />
<c:set value="${pageContext.request.remoteUser}"  var="remoteUser" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Test Taglib Page</title>
	</head>
	<body>

		<!-- Main Area -->

		<pd:test pageContext="${pageContext}" label="aaa" />

		<!-- Footer Area -->

		<p>${pageContext.servletContext.serverInfo}</p>

	</body>
</html>

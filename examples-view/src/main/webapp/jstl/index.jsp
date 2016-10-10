<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="contextPath" />
<c:set value="${pageContext.request.remoteUser}"  var="remoteUser" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Test JSTL Page</title>
	</head>
	<body>

		<!-- Main Area -->

		<%@ page import="java.util.List" %>
		<%@ page import="java.util.ArrayList" %>
		<%@ page import="java.util.Arrays" %>

		<h3>JSTL - Not-Null list of strings</h3>
		<% List<String> testArray = Arrays.asList("foo", "bar"); %>
		<% request.setAttribute("testArray", testArray); %>
		<c:forEach items="${testArray}" var="item">${item} </c:forEach>

		<h3>JSTL - Null list of strings</h3>
		<% List<String> nullArray = null; %>
		<% request.setAttribute("nullArray", nullArray); %>
		<c:forEach items="${nullArray}" var="item">${item} </c:forEach>

		<h3>Scriptlet - Not-Null list of strings</h3>
		<% try { %>
			<% for (String item : testArray) { %>
				<%= item %>
			<% } %>
		<% } catch(Exception e) { %>
			<% log(e.getMessage(), e); %>
			<p>Exception happened. Look at the server log.</p>
		<% } %>

		<h3>Scriptlet - Null list of strings</h3>
		<% try { %>
			<% for (String item : nullArray) { %>
				<%= item %>
			<% } %>
		<% } catch(Exception e) { %>
			<% log(e.getMessage(), e); %>
			<p>Exception happened. Look at the server log.</p>
		<% } %>

		<!-- Footer Area -->

		<p>${pageContext.servletContext.serverInfo}</p>

	</body>
</html>
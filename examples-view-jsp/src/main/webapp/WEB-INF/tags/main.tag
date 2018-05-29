<%@tag pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
		<link href="${pageContext.request.contextPath}/resources/styles.css" type="text/css" rel="stylesheet" />
		<title>Test Template</title>
	</head>

	<body>
		<!-- include external file -->
		<jsp:include page="/WEB-INF/tags/navbar.jsp" />
		<!-- include child content -->
		<jsp:doBody />
		<script src="${pageContext.request.contextPath}/resources/jquery/jquery.min.js"></script>
		<script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
		<script src="${pageContext.request.contextPath}/resources/script.js"></script>
	</body>

</html>

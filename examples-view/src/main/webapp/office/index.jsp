<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="contextPath" />
<c:set value="${pageContext.request.remoteUser}"  var="remoteUser" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Test Document Page</title>
		<style type="text/css">
			a { margin-right: 1em; }
		</style>
	</head>
	<body>

		<!-- Main Area -->

		<h3>Direct Links</h3>
		<p>The URL goes through the webapp public resources.</p>
		<p>
			<a href="${contextPath}/office/Test Document.docx?foo=bar" class="document-link">Document Link</a>
		</p>

		<h3>Servlet Links</h3>
		<p>The URL goes through an intermediate download servlet.</p>
		<p>
			<a href="${contextPath}/document/office/Test Document.docx?foo=bar" class="document-link">Document Link</a>
		</p>

		<h3>Some Reminders</h3>
		<p>
			Mapping a servlet with a prefix, e.g. /document/*, works like a virtual directory.<br />
			Mapping a servlet with a suffix, e.g. *.xhtml, works like a filter.
		</p>

		<!-- Footer Area -->

		<p>${pageContext.servletContext.serverInfo}</p>

		<!-- Scripts Area -->

		<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
		<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>

		<script type="text/javascript">
			var BROWSER_INTERNET_EXPLORER = 1;
			var BROWSER_FIREFOX           = 2;
			var BROWSER_CHROME            = 3;
			var BROWSER_SAFARI            = 4;
			var BROWSER_OTHER             = 5;
			var browser = null;

			function detectBrowser() {
				if (-1 != navigator.userAgent.indexOf("MSIE") || 
				    -1 != navigator.userAgent.indexOf("Trident"))     return BROWSER_INTERNET_EXPLORER;
				if (-1 != navigator.userAgent.indexOf("Firefox"))     return BROWSER_FIREFOX;
				if (-1 != navigator.userAgent.indexOf("Chrome"))      return BROWSER_CHROME;
				if (-1 != navigator.userAgent.indexOf("AppleWebKit")) return BROWSER_SAFARI;
				return BROWSER_OTHER;
			}

			function createOpenLink($link) {
				// 1. Create links with the office prefix
				var href = "ms-word:ofv|u|" + $link.prop("href");
				$wrappedLink = $("<a href='" + href + "'>(open with url-protocol)</a>");
				$wrappedLink.insertAfter($link);

				// 2. Handle the click event differently on IE
				if (browser == BROWSER_INTERNET_EXPLORER) {
					$activexLink = $("<a href='" + $link.prop("href") + "'>(open with activex)</a>");
					$activexLink.insertAfter($link);
					$activexLink.on("click", function(evt) {
						evt.preventDefault();
						// var cmd = "winword.exe";
						var url = $(this).prop("href");
						alert("Handling click on IE with ActiveX!\n" + url);
						// var obj = new ActiveXObject("Shell.Application");
						// obj.ShellExecute(cmd, url, "", "open", "1");
						var obj = new ActiveXObject("SharePoint.OpenDocuments");
						obj.ViewDocument(url);
					});
				}
			}

			function createEditLink($link) {
				// 1. Create links with the office prefix
				var href = "ms-word:ofe|u|" + $link.prop("href");
				$wrappedLink = $("<a href='" + href + "'>(edit with url-protocol)</a>");
				$wrappedLink.insertAfter($link);

				// 2. Handle the click event differently on IE
				if (browser == BROWSER_INTERNET_EXPLORER) {
					$activexLink = $("<a href='" + $link.prop("href") + "'>(edit with activex)</a>");
					$activexLink.insertAfter($link);
					$activexLink.on("click", function(evt) {
						evt.preventDefault();
						// var cmd = "winword.exe";
						var url = $(this).prop("href");
						alert("Handling click on IE with ActiveX!\n" + url);
						// var obj = new ActiveXObject("Shell.Application");
						// obj.ShellExecute(cmd, url, "", "open", "1");
						var obj = new ActiveXObject("SharePoint.OpenDocuments");
						obj.EditDocument(url);
					});
				}
			}

			$(function() {
				browser = detectBrowser();
				$(".document-link").each(function() {
					createEditLink($(this));
					createOpenLink($(this));
				});
			});
		</script>

	</body>
</html>

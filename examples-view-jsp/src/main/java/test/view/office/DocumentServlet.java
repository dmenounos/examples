package test.view.office;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final Logger logger = LoggerFactory.getLogger(DocumentServlet.class);

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info(" ============================================================================================== ");

		logger.info("REQ METHOD " + request.getMethod() + " " + request.getRequestURI());
		logger.info("REQ CONTEXT PATH : " + request.getContextPath());
		logger.info("REQ SERVLET PATH : " + request.getServletPath());
		logger.info("REQ PATH INFO    : " + request.getPathInfo());
		logger.info("REQ QUERY STRING : " + request.getQueryString());

		for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
			String parameterName = e.nextElement();
			String parameterValue = request.getParameter(parameterName);
			logger.info("REQ PARAMETER " + parameterName + "=" + parameterValue);
		}

		for (Enumeration<String> e = request.getHeaderNames(); e.hasMoreElements();) {
			String headerName = e.nextElement();
			String headerValue = request.getHeader(headerName);
			logger.info("REQ HEADER " + headerName + "=" + headerValue);
		}

		super.service(request, response);

		for (String headerName : response.getHeaderNames()) {
			String headerValue = response.getHeader(headerName);
			logger.info("RES HEADER " + headerName + "=" + headerValue);
		}

		logger.info(" ---------------------------------------------------------------------------------------------- ");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		InputStream is = null;
		OutputStream os = null;

		try {
			String documentPath = req.getPathInfo();

			if (documentPath == null) {
				res.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			String documentRealPath = getServletContext().getRealPath(documentPath);

			if (documentRealPath == null) {
				res.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			File documentFile = new File(documentRealPath);

			if (!documentFile.isFile()) {
				res.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			String documentContentType = Files.probeContentType(documentFile.toPath());
			res.setContentType(documentContentType);

			is = getServletContext().getResourceAsStream(documentPath);
			os = res.getOutputStream();

			IOUtils.copy(is, os);
		}
		finally {
			IOUtils.closeQuietly(is);
		}
	}
}

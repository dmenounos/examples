package test.view.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Java EE Servlet Filter.<br />
 * Updates the current web context into the thread local storage.
 */
public class WebContextFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(WebContextFilter.class);

	@Override
	public void init(FilterConfig config) throws ServletException {
		logger.debug("init: ");
	}

	@Override
	public void destroy() {
		logger.debug("destroy: ");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		try {
			logger.debug("Creating thread-local context object for " + request.getRequestURI());
			WebContextObject ctx = new WebContextObject(request, response);
			WebContextHolder.setCurrentContext(ctx);
			chain.doFilter(req, res);
		}
		finally {
			logger.debug("Removing thread-local context object for " + request.getRequestURI());
			WebContextHolder.removeCurrentContext();
		}
	}
}

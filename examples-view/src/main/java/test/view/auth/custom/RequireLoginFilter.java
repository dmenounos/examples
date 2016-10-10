package test.view.auth.custom;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.view.core.WebContext;

public class RequireLoginFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(RequireLoginFilter.class);

	@Inject
	private WebContext webContext;

	private String loginForm;

	@Override
	public void init(FilterConfig config) throws ServletException {
		logger.debug("init: ");
		loginForm = config.getInitParameter("loginForm");
	}

	@Override
	public void destroy() {
		logger.debug("destroy: ");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		boolean isLoggedIn = webContext.getUser() != null;
		boolean isLoginForm = request.getRequestURI().equals(loginForm);

		if (!isLoggedIn && !isLoginForm) {
			if (loginForm != null) {
				RequestDispatcher loginFormResource = request.getRequestDispatcher(loginForm);

				if (loginFormResource != null) {
					logger.debug("No logged-in user; Forwarding to the login form!");
					loginFormResource.forward(req, res);
					return;
				}
			}

			logger.debug("No logged-in user; Sending forbiden error code!");
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

		chain.doFilter(req, res);
	}
}

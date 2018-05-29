package test.view.auth;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.view.core.WebContext;
import test.view.util.FacesUtils;
import test.view.util.MessagesFactory;

public abstract class AbstractLoginController implements Serializable {

	private static final long serialVersionUID = 1L;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	// init message bundle for the current locale
	protected final ResourceBundle bundle = MessagesFactory.getBundle("messages");

	// message keys for the authentication procedure
	protected static final String AUTH_INVALID_USERNAME_PASSWORD = "auth_invalid_username_password";
	protected static final String AUTH_LOGIN_ERROR               = "auth_login_error";

	// attribute keys with information about the initial requested resource
	protected static final String INITIAL_FORWARD_REQUEST_URI_ATTR  = "javax.servlet.forward.request_uri";
	protected static final String INITIAL_FORWARD_QUERY_STRING_ATTR = "javax.servlet.forward.query_string";

	@Inject	
	private WebContext webContext;

	/**
	 * The initial resource path that was requested, so that it can be used in
	 * order to redirect after a successful authentication.
	 */
	private String initialRequestURI;

	/**
	 * The initial query string that was requested, so that it can be used in
	 * order to redirect after a successful authentication.
	 */
	private String initialQueryString;

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLoggedIn() {
		return webContext.getUser() != null;
	}

	public void preRenderView() throws Exception {
		initInitialRequestUrl();
	}

	/**
	 * Is called in the following cases:
	 * 
	 * 1. preRenderView : Initial form render.
	 * 2. preRenderView : On form validation failure.
	 * 3. preRenderView : On form submission.
	 * 
	 * The useful case for setting the initial request url is 1.
	 */
	protected void initInitialRequestUrl() {
		HttpServletRequest httpRequest = FacesUtils.getHttpServletRequest();
		String requestURI = (String) httpRequest.getAttribute(INITIAL_FORWARD_REQUEST_URI_ATTR);
		String queryString = (String) httpRequest.getAttribute(INITIAL_FORWARD_QUERY_STRING_ATTR);
		String referer = httpRequest.getHeader("Referer"); // does not work as expected with links IIRC?

		if (initialRequestURI == null && requestURI != null) {
			logger.debug("initInitialRequestUrl: requestURI={}", requestURI);
			logger.debug("initInitialRequestUrl: queryString={}", queryString);
			logger.debug("initInitialRequestUrl: referer={}", referer);
			initialRequestURI = requestURI;
			initialQueryString = queryString;
		}
	}

	/**
	 * Recreates the full initial request url.
	 */
	protected String getInitialRequestUrl() {
		if (initialRequestURI != null && !initialRequestURI.isEmpty()) {
			StringBuilder initialUrl = new StringBuilder(initialRequestURI);
			if (initialQueryString != null && !initialQueryString.isEmpty()) {
				initialUrl.append("?").append(initialQueryString);
			}
			return initialUrl.toString();
		}
		logger.warn("Initial request url was not found. Returning default.");
		return getDefaultRequestUrl();
	}

	/**
	 * Returns the site root url.
	 */
	protected String getDefaultRequestUrl() {
		HttpServletRequest httpRequest = FacesUtils.getHttpServletRequest();
		String contextPath = httpRequest.getContextPath();
		return contextPath + "/";
	}

	/**
	 * Placeholder for subclasses to implement the actual authentication calls.
	 * Failure is indicated with a thrown exception.
	 */
	protected abstract void authenticate(String username, String password) throws Exception;

	public void onLogin() {
		HttpServletRequest httpRequest = FacesUtils.getHttpServletRequest();
		String login = null; // the user name as known to the server

		try {
			if (isLoggedIn()) {
				login = webContext.getUser().toString();
				logger.info("doLogin: User is already logged in as {}", login);
				httpRequest.logout();
				httpRequest.getSession().invalidate();
				logger.info("doLogin: User {} was logged out!", login);
			}

			login = username;

			logger.info("doLogin: Try to login as {}", login);
			authenticate(username, password);
			logger.info("doLogin: User {} was logged in!", login);

			// attach user information to session
			webContext.setUser(username);

			// Respond with redirect in order to prevent post-refreshes, such as by hitting F5 etc.
			HttpServletResponse httpResponse = FacesUtils.getHttpServletResponse();
			httpResponse.sendRedirect(getInitialRequestUrl());
		}
		catch (Exception e) {
			logger.warn("doLogin: Login for user {} FAILED! {}", login, e.getMessage());

			if (e.getMessage() != null && bundle.containsKey(e.getMessage())) {
				FacesUtils.addErrorMessage(bundle.getString(e.getMessage()), null);
			} else {
				FacesUtils.addErrorMessage(bundle.getString(AUTH_LOGIN_ERROR), null);
			}
		}
	}

	public void onLogout() {
		HttpServletRequest httpRequest = FacesUtils.getHttpServletRequest();
		String login = null; // the user name as known to the server

		try {
			if (isLoggedIn()) {
				login = webContext.getUser().toString();
				logger.info("doLogout: User is logged in as {}", login);
				httpRequest.logout();
				httpRequest.getSession().invalidate();
				logger.info("doLogout: User {} was logged out!", login);
			}

			// Respond with redirect in order to prevent post-refreshes, such as by hitting F5 etc.
			HttpServletResponse httpResponse = FacesUtils.getHttpServletResponse();
			httpResponse.sendRedirect(getDefaultRequestUrl());
		}
		catch (Exception e) {
			logger.warn("doLogout: Logout for user {} FAILED! {}", login, e.getMessage());

			if (e.getMessage() != null && bundle.containsKey(e.getMessage())) {
				FacesUtils.addErrorMessage(bundle.getString(e.getMessage()), null);
			} else {
				FacesUtils.addErrorMessage(bundle.getString(AUTH_LOGIN_ERROR), null);
			}
		}
	}

	public int getSessionTimeOut() {
		// Convert the, returned from jsf, seconds to minutes.
		return FacesUtils.getExternalContext().getSessionMaxInactiveInterval() / 60;
	}

	public String updateCountdown() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("UPDATE_TIMEOUT", "true");
		FacesContext.getCurrentInstance().renderResponse();
		return null;
	}
}

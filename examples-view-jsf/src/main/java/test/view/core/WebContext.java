package test.view.core;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.core.auth.AuthContext;

@SessionScoped
public class WebContext implements AuthContext, Serializable {

	private static final long serialVersionUID = 1L;

	private Object user;

	@Override
	public Object getUser() {
		return user;
	}

	public void setUser(Object user) {
		this.user = user;
	}

	@Override
	public String getRemoteUser() {
		return getCurrentRequest().getRemoteUser();
	}

	@Override
	public String getRemoteHost() {
		return getCurrentRequest().getRemoteHost();
	}

	/* Helper */
	protected HttpServletRequest getCurrentRequest() {
		return WebContextHolder.getCurrentContext().getRequest();
	}

	/* Helper */
	protected HttpServletResponse getCurrentResponse() {
		return WebContextHolder.getCurrentContext().getResponse();
	}
}

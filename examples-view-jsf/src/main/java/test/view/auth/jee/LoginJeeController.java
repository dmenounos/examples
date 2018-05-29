package test.view.auth.jee;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import test.view.auth.AbstractLoginController;
import test.view.util.FacesUtils;

/**
 * Controller for the login page.
 */
@Named
@SessionScoped
public class LoginJeeController extends AbstractLoginController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void authenticate(String username, String password) throws Exception {
		HttpServletRequest httpRequest = FacesUtils.getHttpServletRequest();
		httpRequest.login(username.toUpperCase(), password);
	}
}

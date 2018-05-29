package test.view.auth.custom;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import test.core.auth.UserService;
import test.view.auth.AbstractLoginController;

/**
 * Controller for the login page.
 */
@Named
@SessionScoped
public class LoginCustomController extends AbstractLoginController {

	private static final long serialVersionUID = 1L;

	@Inject
	private UserService userService;

	@Override
	protected void authenticate(String username, String password) throws Exception {
		if (!userService.authenticate(username.toUpperCase(), password)) {
			throw new Exception(AUTH_INVALID_USERNAME_PASSWORD);
		}
	}
}

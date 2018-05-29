package test.view.auth.jee;

import java.security.acl.Group;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.UsernamePasswordLoginModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.core.auth.UserService;

/**
 * Authentication module for JBoss.
 */
public class UserServiceJBossModule extends UsernamePasswordLoginModule {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceJBossModule.class);

	private static final String USER_SERVICE_KEY = "app.user.service";
	private static final String USER_SERVICE_NAME_DEFAULT = "java:module/UserServiceBean";

	// The UserService JNDI name may be provided (i.e., override the default) via a system property.
	private final String userServiceName = System.getProperty(USER_SERVICE_KEY, USER_SERVICE_NAME_DEFAULT);

	private UserService userService;

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		super.initialize(subject, callbackHandler, sharedState, options);

		InitialContext ctx;

		try {
			ctx = new InitialContext();
			userService = (UserService) ctx.lookup(userServiceName);
			LOGGER.info("Initialized userService={}", userServiceName);
		}
		catch (NamingException e) {
			LOGGER.error("Error while looking up userService={}" + userServiceName, e);
		}
	}

	@Override
	protected String getUsersPassword() throws LoginException {
		return null;
	}

	@Override
	protected boolean validatePassword(String inputPassword, String expectedPassword) {
		return userService.authenticate(getUsername(), inputPassword);
	}

	@Override
	protected Group[] getRoleSets() throws LoginException {
		Group[] groups = new SimpleGroup[1];
		groups[0] = new SimpleGroup("Roles");

		for (String role : userService.getRoles(getUsername())) {
			groups[0].addMember(new SimplePrincipal(role));
		}

		return groups;
	}
}

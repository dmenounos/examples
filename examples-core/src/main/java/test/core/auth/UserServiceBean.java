package test.core.auth;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Authentication implementation.
 */
@Stateless
public class UserServiceBean implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceBean.class);

	private static final Map<String, List<String>> users;

	static {
		users = new HashMap<String, List<String>>();
		users.put("ROOT", Arrays.asList("ADMINISTRATOR", "USER"));
		users.put("DMEN", Arrays.asList("USER"));
	}

	@Override
	public boolean authenticate(String username, String password) {
		LOGGER.info("authenticate: username={} password={}", username, password);
		return users.containsKey(username);
	}

	@Override
	public List<String> getRoles(String username) {
		LOGGER.info("getRoles: username={}", username);
		return users.get(username);
	}
}

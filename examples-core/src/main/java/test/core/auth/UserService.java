package test.core.auth;

import java.util.List;

/**
 * Authentication abstraction.
 */
public interface UserService {

	boolean authenticate(String username, String password);

	List<String> getRoles(String username);
}

package test.core.auth;

public interface AuthContext {

	/**
	 * The application user.
	 */
	Object getUser();

	/**
	 * The container provided user name.
	 */
	String getRemoteUser();

	/**
	 * The container provided user host.
	 */
	String getRemoteHost();
}

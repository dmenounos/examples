package test.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SQL convenience utilities.
 */
public class DBUtils {

	private static final Logger logger = LoggerFactory.getLogger(DBUtils.class);

	/**
	 * Creates a jdbc connection, mitigates the necessity of SQLException handling.
	 */
	public static Connection createConnection(String url, String username, String password) {
		try {
			return DriverManager.getConnection(url, username, password);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Creates a jdbc statement, mitigates the necessity of SQLException handling.
	 */
	public static Statement createStatement(Connection connection) {
		try {
			return connection.createStatement();
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Creates a jdbc prepared statement, mitigates the necessity of SQLException handling.
	 */
	public static PreparedStatement createPreparedStatement(Connection connection, String query) {
		try {
			return connection.prepareStatement(query);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Closes the jdbc object, mitigates the necessity of SQLException handling.
	 */
	public static void close(ResultSet closable) {
		try {
			if (closable != null) {
				closable.close();
			}
		}
		catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * Closes the jdbc object, mitigates the necessity of SQLException handling.
	 */
	public static void close(Statement closable) {
		try {
			if (closable != null) {
				closable.close();
			}
		}
		catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * Closes the jdbc object, mitigates the necessity of SQLException handling.
	 */
	public static void close(Connection closable) {
		try {
			if (closable != null) {
				closable.close();
			}
		}
		catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}
}

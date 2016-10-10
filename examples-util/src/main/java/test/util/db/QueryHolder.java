package test.util.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * SQL query container.
 */
public class QueryHolder {

	private String queryString;
	private Object[] parameters;

	/**
	 * Default constructor.
	 */
	public QueryHolder() {
	}

	/**
	 * Convenience constructor.
	 */
	public QueryHolder(String queryString, Object... parameters) {
		this.queryString = queryString;
		this.parameters = parameters;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	/**
	 * Initializes the statement parameters.
	 */
	public void mapParameters(PreparedStatement statement) throws SQLException {
		if (parameters == null) {
			return;
		}

		for (int i = 0; i < parameters.length; ++i) {
			statement.setObject(i + 1, parameters[i]);
		}
	}
}

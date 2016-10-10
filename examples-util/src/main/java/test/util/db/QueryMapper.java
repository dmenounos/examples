package test.util.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Maps records from / to jdbc objects.
 */
public class QueryMapper<K, R> extends QueryHolder {

	/**
	 * Returns the record primary key.
	 */
	public K getPrimaryKey(R record) {
		return null;
	}

	/**
	 * Initializes a record from the results.
	 */
	public R mapRecord(ResultSet results) throws SQLException {
		throw new UnsupportedOperationException();
	}
}

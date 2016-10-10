package test.util.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Uniform API for executing sql query statements. In conjunction with DBUtils,
 * mitigates the necessity of SQLException handling.
 */
public class QueryExecutor {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private Connection connection;

	/**
	 * Creates lazily and returns the sql connection object.
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Sets the sql connection object.
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Creates the sql statement object.
	 */
	protected PreparedStatement createStatement(String sql) {
		logger.debug("createStatement: sql={}", sql);
		return DBUtils.createPreparedStatement(getConnection(), sql);
	}

	public int update(QueryHolder holder) {
		String sql = holder.getQueryString();
		PreparedStatement statement = null;

		try {
			statement = createStatement(sql);
			holder.mapParameters(statement);
			return statement.executeUpdate();
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			DBUtils.close(statement);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T select(QueryHolder holder, SelectCallback callback) {
		String sql = holder.getQueryString();
		PreparedStatement statement = null;
		ResultSet results = null;

		try {
			statement = createStatement(sql);
			holder.mapParameters(statement);
			results = statement.executeQuery();
			return (T) callback.execute(results);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			DBUtils.close(results);
			DBUtils.close(statement);
		}
	}

	/**
	 * Helper method.
	 */
	public <T> List<T> getObjectList(final QueryMapper<?, T> mapper) {
		return select(mapper, new SelectCallback() {

			@Override
			public Object execute(ResultSet rs) throws SQLException {
				List<T> records = new ArrayList<T>();

				while (rs.next()) {
					T rec = mapper.mapRecord(rs);
					records.add(rec);
				}

				return records;
			}
		});
	}

	/**
	 * Helper method.
	 */
	public <K, R> Map<K, R> getObjectMap(final QueryMapper<K, R> mapper) {
		return select(mapper, new SelectCallback() {

			@Override
			public Object execute(ResultSet rs) throws SQLException {
				Map<K, R> records = new LinkedHashMap<K, R>();

				while (rs.next()) {
					R rec = mapper.mapRecord(rs);
					K key = mapper.getPrimaryKey(rec);
					records.put(key, rec);
				}

				return records;
			}
		});
	}

	/**
	 * Helper method.
	 */
	public Integer getCount(QueryHolder holder) {
		return select(holder, new SelectCallback() {

			@Override
			public Object execute(ResultSet rs) throws SQLException {
				return rs.next() ? rs.getInt(1) : 0;
			}
		});
	}

	/**
	 * ResultSet processor.
	 */
	public interface SelectCallback {

		/**
		 * Extracts the results into a collection object.
		 */
		Object execute(ResultSet rs) throws SQLException;
	}
}

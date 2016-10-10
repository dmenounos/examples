package test.util.db;

import java.sql.PreparedStatement;

/**
 * Convenience API for executing sql query statements in batches.
 */
public class QueryBatchExecutor<T> extends QueryExecutor {

	private PreparedStatement statement;
	private int batchCapacity;
	private int batchCount;
	private int totalCount;

	public QueryBatchExecutor(int batchCapacity) {
		this.batchCapacity = batchCapacity;
	}

	public void initSqlQuery(CharSequence sqlQuery) {
		statement = createStatement(sqlQuery.toString());
	}

	public boolean isFull() {
		return batchCount >= batchCapacity;
	}

	public boolean isEmpty() {
		return batchCount == 0;
	}

	public void addRecord(QueryHolder recordHolder) {
		try {
			recordHolder.mapParameters(statement);
			statement.addBatch();

			++batchCount;

			logger.trace("addRecord: batchCount={} totalCount={}", batchCount, totalCount);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void submit() {
		try {
			statement.executeBatch();

			totalCount += batchCount;
			batchCount = 0;

			logger.debug("submit: batchCount={} totalCount={}", batchCount, totalCount);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

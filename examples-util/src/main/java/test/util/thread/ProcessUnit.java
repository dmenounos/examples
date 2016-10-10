package test.util.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base structure for processing units.
 * Encapsulates thread life cycle management.
 */
public class ProcessUnit implements Runnable {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private boolean stopped;

	/**
	 * Override to implement execution logic.
	 */
	public void execute() throws Exception {
	}

	/**
	 * Implements life cycle management.
	 */
	@Override
	public void run() {
		try {
			logger.debug("THREAD ENTER");

			if (!isStopped()) {
				execute();
			}
		}
		catch (RuntimeException e) {
			throw e;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			stopped = true;
			logger.debug("THREAD EXIT");
		}
	}

	/**
	 * Starts the processing thread.
	 */
	public void start(boolean daemon) {
		Thread t = new Thread(this);
		t.setDaemon(daemon);
		t.start();
	}

	/**
	 * Stops the processing thread.
	 */
	public void stop() {
		stopped = true;
	}

	public boolean isStopped() {
		return stopped;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}
}

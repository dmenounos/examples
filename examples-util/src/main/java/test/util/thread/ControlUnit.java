package test.util.thread;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base structure for collaboration between processing units.
 */
public class ControlUnit extends BlockingCollection {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private final List<ProcessUnit> producers;
	private final List<ProcessUnit> consumers;

	public ControlUnit() {
		producers = new ArrayList<ProcessUnit>();
		consumers = new ArrayList<ProcessUnit>();
	}

	public List<ProcessUnit> getProducers() {
		return producers;
	}

	public List<ProcessUnit> getConsumers() {
		return consumers;
	}

	public boolean isAnyProducerRunning() {
		for (ProcessUnit producer : producers) {
			if (!producer.isStopped()) {
				return true;
			}
		}

		return false;
	}

	public boolean isAnyConsumerRunning() {
		for (ProcessUnit consumer : consumers) {
			if (!consumer.isStopped()) {
				return true;
			}
		}

		return false;
	}
}

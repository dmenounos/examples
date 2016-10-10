package test.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class Timer {

	private Time initTime;
	private Time currTime;
	private Map<String, Time> times;

	public Timer() {
		times = new LinkedHashMap<String, Time>();
		initTime = currTime = new Time(null);
		initTime.start();
	}

	public Timer go(String key) {
		assert key != null;

		currTime = times.get(key);

		if (currTime == null) {
			currTime = new Time(key);
		}

		currTime.start();
		return this;
	}

	public Timer ok() {
		currTime.stop();
		times.put(currTime.getKey(), currTime);
		return this;
	}

	public Timer done() {
		initTime.stop();
		times.put(initTime.getKey(), initTime);
		return this;
	}

	public long getTimeMs(String key) {
		Time time = times.get(key);
		return time != null ? time.getTotalTime() : 0L;
	}

	public int getTimePercent(String key) {
		long keytime = getTimeMs(key);
		long total = initTime.getTotalTime();

		if (total != 0) {
			return (int) ((keytime / (float) total) * 100);
		}

		return 0;
	}

	protected static class Time {

		private String key;
		private long checkTime;
		private long totalTime;

		public Time(String key) {
			this.key = key;
		}

		public String getKey() {
			return key;
		}

		public long getTotalTime() {
			return totalTime;
		}

		public void start() {
			long curr = System.currentTimeMillis();
			checkTime = curr;
		}

		public void stop() {
			long curr = System.currentTimeMillis();
			totalTime = totalTime + (curr - checkTime);
			checkTime = curr;
		}
	}
}

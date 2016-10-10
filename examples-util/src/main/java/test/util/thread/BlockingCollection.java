package test.util.thread;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingCollection {

	private BlockingQueue<Object> objects;

	public BlockingCollection() {
		objects = new LinkedBlockingQueue<Object>();
	}

	public void initObjects(int capacity) {
		objects = new LinkedBlockingQueue<Object>(capacity);
	}

	public Collection<Object> getObjects() {
		return objects;
	}

	public void insertLastObject(Object object) {
		try {
			objects.put(object);
		}
		catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public Object removeFirstObject() {
		try {
			return objects.take();
		}
		catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}

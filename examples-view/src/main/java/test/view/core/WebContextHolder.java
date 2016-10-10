package test.view.core;

/**
 * ThreadLocal web context storage.<br />
 * Works in conjunction with either WebContextFilter.
 */
public class WebContextHolder {

	private static final ThreadLocal<WebContextObject> context = new ThreadLocal<WebContextObject>();

	public static WebContextObject getCurrentContext() {
		return context.get();
	}

	public static void setCurrentContext(WebContextObject value) {
		context.set(value);
	}

	public static void removeCurrentContext() {
		context.remove();
	}
}

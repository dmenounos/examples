package test.view.core;

public class WebException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String key;
	private Object[] params;

	public WebException(String key, Object... params) {
		this.key = key;
		this.params = params;
	}

	public String getKey() {
		return key;
	}

	public Object[] getParams() {
		return params;
	}
}

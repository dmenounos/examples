package test.util.props;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Encapsulates property file loading.
 */
public class PropertiesHolder {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected final Properties properties = new Properties();

	public Properties getProperties() {
		return properties;
	}

	public int getInt(String key) {
		return Integer.parseInt(getString(key));
	}

	public String getString(String key) {
		return (String) properties.get(key);
	}

	public void loadProperties(String resource) {
		logger.debug("loadProperties: {}", resource);
		loadProperties(getClass().getResourceAsStream(resource));
	}

	public void loadProperties(InputStream is) {
		logger.debug("loadProperties: {}", is);

		try {
			properties.load(is);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

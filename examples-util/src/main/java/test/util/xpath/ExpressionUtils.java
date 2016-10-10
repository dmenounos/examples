package test.util.xpath;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * XPath Expression provider.
 */
public class ExpressionUtils {

	private Document document;
	private Namespaces namespaces;

	public ExpressionUtils() {
		namespaces = new Namespaces();
	}

	/**
	 * Initializes the XML document.
	 */
	public void initXmlDocument(InputStream is) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);

			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(is);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Initializes the HTML document.
	 */
	public void initHtmlDocument(InputStream is) {
		try {
			DOMParser parser = new DOMParser();
			parser.parse(new InputSource(is));
			document = parser.getDocument();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns the root document.
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * Provides XPath Expression objects.
	 */
	public Expression createExpression(String path) {
		Expression expression = new Expression();
		expression.initPath(path, namespaces);
		return expression;
	}

	/**
	 * Registers an xpath prefix / uri namespace pair.
	 */
	public void addNamespace(String prefix, String uri) {
		namespaces.map.put(prefix, uri);
	}

	private static class Namespaces implements NamespaceContext {

		private final Map<String, String> map;

		private Namespaces() {
			map = new HashMap<String, String>();
		}

		@Override
		public Iterator<String> getPrefixes(String namespaceURI) {
			return map.keySet().iterator();
		}

		@Override
		public String getNamespaceURI(String prefix) {
			return map.get(prefix);
		}

		@Override
		public String getPrefix(String namespaceURI) {
			return null;
		}
	};
}

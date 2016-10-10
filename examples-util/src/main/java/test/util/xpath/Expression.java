package test.util.xpath;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathVariableResolver;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XPath Expression wrapper.
 */
public class Expression {

	private Variables variables;
	private XPathExpression xpathExpression;

	public Expression() {
		variables = new Variables();
	}

	/**
	 * Initializes the XPathExpression object.
	 */
	public void initPath(String path, NamespaceContext namespaces) {
		try {
			XPath xpath = XPathFactory.newInstance().newXPath();

			if (namespaces != null) {
				xpath.setNamespaceContext(namespaces);
			}

			xpath.setXPathVariableResolver(variables);
			xpathExpression = xpath.compile(path);
		}
		catch (XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * Registers a name / value variable pair.
	 */
	public void addVariable(String name, String value) {
		variables.map.put(name, value);
	}

	/**
	 * Evaluates the path expression using the specified context.
	 */
	public String evaluateString(Node context) {
		try {
			return (String) getXpathExpression().evaluate(context, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * Evaluates the path expression using the specified context.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Node> T evaluateNode(Node context) {
		try {
			return (T) getXpathExpression().evaluate(context, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * Evaluates the path expression using the specified context.
	 */
	public NodeList evaluateNodeList(Node context) {
		try {
			return (NodeList) getXpathExpression().evaluate(context, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * Provides the XPathExpression object.
	 */
	private XPathExpression getXpathExpression() {
		return xpathExpression;
	}

	private static class Variables implements XPathVariableResolver {

		private final Map<String, String> map;

		private Variables() {
			map = new HashMap<String, String>();
		}

		@Override
		public Object resolveVariable(QName name) {
			return map.get(name.getLocalPart());
		}
	}
}

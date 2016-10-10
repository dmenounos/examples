package test.svhc.tree;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import test.svhc.pojo.CaseObject;
import test.svhc.pojo.RoundCase;
import test.svhc.pojo.SubstanceCase;

/**
 * Αλγόριθμος διαπέρασης δένδρου.
 */
public class TreeIterator implements Iterator<TreeNode> {

	protected final TraversalType traversalType;
	protected final List<TreeNode> treeNodes;

	private TreeNodeHandler treeNodeHandler;
	private TreeNodeFactory treeNodeFactory;

	public TreeIterator() {
		this(TraversalType.DEPTH_FIRST);
	}

	public TreeIterator(TraversalType traversalType) {
		assert traversalType != null;
		this.traversalType = traversalType;
		this.treeNodes = new ArrayList<>();
		this.treeNodeFactory = new DefaultTreeNodeFactory();
	}

	public TreeNodeHandler getTreeNodeHandler() {
		return treeNodeHandler;
	}

	public void setTreeNodeHandler(TreeNodeHandler treeNodeHandler) {
		this.treeNodeHandler = treeNodeHandler;
	}

	public TreeNodeFactory getTreeNodeFactory() {
		return treeNodeFactory;
	}

	public void setTreeNodeFactory(TreeNodeFactory treeNodeFactory) {
		this.treeNodeFactory = treeNodeFactory;
	}

	public void loadRootNode(String xmlFile) {
		try {
			InputStream xml = getClass().getResourceAsStream(xmlFile);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(xml));
			Element root = doc.getDocumentElement();
			treeNodes.add(getTreeNodeFactory().createTreeNode(null, root));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean hasNext() {
		return !treeNodes.isEmpty();
	}

	@Override
	public TreeNode next() {

		// 1. Αφαιρούμε την κεφαλή της λίστας.
		TreeNode treeNode = treeNodes.get(0);
		treeNodes.remove(treeNode);

		// 2. Τοποθετούμε τα παιδιά στη λίστα για επεξεργασία,
		// σύμφωνα με τον αλγόριθμο (κατα βάθος ή κατα πλάτος).
		switch (traversalType) {
		case DEPTH_FIRST:
			depthFirstTraversal(treeNode);
			break;
		case BREADTH_FIRST:
			breadthFirstTraversal(treeNode);
			break;
		}

		if (getTreeNodeHandler() != null) {
			getTreeNodeHandler().processTreeNode(treeNode);
		}

		return treeNode;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Depth First utilizes a LIFO (stack) type of collection.
	 */
	protected void depthFirstTraversal(TreeNode treeNode) {
		Element element = treeNode.getElement();
		NodeList childNodes = element.getChildNodes();

		for (int i = childNodes.getLength() - 1; i >= 0; --i) {
			Node childNode = childNodes.item(i);
			if (Node.ELEMENT_NODE == childNode.getNodeType()) {
				Element childElement = (Element) childNode;
				TreeNode childTreeNode = getTreeNodeFactory().createTreeNode(treeNode, childElement);
				if (childTreeNode != null) {
					treeNodes.add(0, childTreeNode);
				}
			}
		}

		treeNode.setExpanded(true);
	}

	/**
	 * Breadth First utilizes a FIFO (queue) type of collection.
	 */
	protected void breadthFirstTraversal(TreeNode treeNode) {
		Element element = treeNode.getElement();
		NodeList childNodes = element.getChildNodes();

		for (int i = 0; i < childNodes.getLength(); ++i) {
			Node childNode = childNodes.item(i);
			if (Node.ELEMENT_NODE == childNode.getNodeType()) {
				Element childElement = (Element) childNode;
				TreeNode childTreeNode = getTreeNodeFactory().createTreeNode(treeNode, childElement);
				if (childTreeNode != null) {
					treeNodes.add(childTreeNode);
				}
			}
		}

		treeNode.setExpanded(true);
	}

	protected static class DefaultTreeNodeFactory implements TreeNodeFactory {

		public TreeNode createTreeNode(TreeNode parentNode, Element element) {
			return new TreeNode(parentNode, element);
		}
	}
}

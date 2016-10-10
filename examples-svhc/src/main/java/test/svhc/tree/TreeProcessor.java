package test.svhc.tree;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Element;

import test.svhc.pojo.CaseObject;
import test.svhc.pojo.RoundCase;
import test.svhc.pojo.SubstanceCase;

public class TreeProcessor implements TreeNodeHandler, TreeNodeFactory {

	private TreeIteratorDFPPO1 treeIterator = new TreeIteratorDFPPO1();

	// process only these cases, if not empty
	private Set<String> caseNames = new LinkedHashSet<String>();

	public Set<String> getCaseNames() {
		return caseNames;
	}

	public void processXmlTree(String xmlFile) {
		treeIterator.setTreeNodeHandler(this);
		treeIterator.setTreeNodeFactory(this);
		treeIterator.loadRootNode(xmlFile);

		while (treeIterator.hasNext()) {
			treeIterator.next();
		}
	}

	@Override
	public void processTreeNode(TreeNode treeNode) {
		Element node = treeNode.getElement();
		String nodeName = node.getNodeName();
		String nameAttr = null; 

		if (node.hasAttribute("name")) {
			nameAttr = node.getAttribute("name");
		}

		System.out.println("--> " + nodeName + " " + nameAttr);

		switch (nodeName) {
		case "RoundCase":
			RoundCase rc = new RoundCase(nameAttr);
			treeNode.setCaseObject(rc);
			// roundCases.add(rc);
			break;
		case "SubstanceCase":
			SubstanceCase sc = new SubstanceCase(nameAttr);
			treeNode.setCaseObject(sc);
			// substanceCases.add(sc);
			break;
		case "Folder":
		case "Document":
			// Στους λοιπούς κόμβους, κληρονομούμε την υπόθεση από το γονέα.
			CaseObject caseObject = treeNode.getParentNode().getCaseObject();
			treeNode.setCaseObject(caseObject);
		}

		// Στους τερματικούς κόμβους ενημερώνουμε την υπόθεση.
		if ("Document".equals(nodeName)) {
			String documentPath = treeNode.getPath();
			treeNode.getCaseObject().addDocument(documentPath);
		}
	}

	@Override
	public void postProcessTreeNode(TreeNode treeNode) {
		Element node = treeNode.getElement();
		String nodeName = node.getNodeName();
		String nameAttr = null; 

		if (node.hasAttribute("name")) {
			nameAttr = node.getAttribute("name");
		}

		System.out.println("<-- " + nodeName + " " + nameAttr);

		switch (nodeName) {
		case "RoundCase":
		case "SubstanceCase":
			CaseObject caseObject = treeNode.getCaseObject();
			caseObject.finalizeDocuments();
			System.out.println(caseObject.toDebugString());
			break;
		}
	}

	@Override
	public TreeNode createTreeNode(TreeNode parentNode, Element element) {
		String nodeName = element.getNodeName();
		String nameAttr = null; 

		if (element.hasAttribute("name")) {
			nameAttr = element.getAttribute("name");
		}

		if (("RoundCase".equals(nodeName) || "SubstanceCase".equals(nodeName)) && 
			!getCaseNames().isEmpty() && !getCaseNames().contains(nameAttr)) {
			System.out.println("### Skipping " + nodeName + " " + nameAttr);
			return null;
		}

		return new TreeNode(parentNode, element);
	}
}

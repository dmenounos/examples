package test.svhc.tree;

import org.w3c.dom.Element;

import test.svhc.pojo.CaseObject;

public class TreeNode {

	private TreeNode parentNode;
	private Element element; // documentum object
	private CaseObject caseObject;
	private boolean expanded;
	private int level;

	public TreeNode(TreeNode parentNode, Element element) {
		this.parentNode = parentNode;
		this.element = element;

		if (parentNode != null) {
			this.level = parentNode.getLevel() + 1;
		}
	}

	public String getPath() {
		StringBuilder nodePath = new StringBuilder();
		TreeNode tn = this;

		// Χτίζουμε τη διαδρομή από κάτω προς τα επάνω.
		// Σταματάμε οταν βγούμε εκτός του πλαισίου υπόθεσης.
		while (tn != null && tn.getCaseObject() != null && 
			tn.getCaseObject().equals(this.getCaseObject()) && 
			tn.getParentNode() != null && // εξαιρούμε και το case node
			tn.getCaseObject().equals(tn.getParentNode().getCaseObject())) {

			// Η λογική είναι η εξης. Ξεκινάμε με έναν cursor από 
			// τον τερματικό κόμβο και συνεχίζουμε για οσο ο cursor 
			// έχει το ίδιο case object με τον αρχικό κόμβο καθώς επίσης 
			// έχει το ίδιο case object με τον επόμενο κόμβο στην ιεραρχία.

			if (tn.getElement().hasAttribute("name")) {
				nodePath.insert(0, "/" + tn.getElement().getAttribute("name"));
			}

			tn = tn.getParentNode();
		}

		String path = nodePath.toString();
		return path.length() != 0 ? path.substring(1) : path;
	}

	public TreeNode getParentNode() {
		return parentNode;
	}

	public void setParentNode(TreeNode parentNode) {
		this.parentNode = parentNode;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public CaseObject getCaseObject() {
		return caseObject;
	}

	public void setCaseObject(CaseObject caseObject) {
		this.caseObject = caseObject;
	}
}

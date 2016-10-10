package test.svhc.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Αλγόριθμος διαπέρασης δένδρου.
 */
public class TreeIteratorDFPPO2 implements Iterator<TreeNode> {

	private TreeIterator treeIterator = new TreeIterator();

	private List<TreeNode> treeNodes;
	private TreeNode prevNode;
	private TreeNode nextNode;

	public TreeIteratorDFPPO2() {
		this.treeNodes = new ArrayList<>();
	}

	public TreeNodeHandler getTreeNodeHandler() {
		return treeIterator.getTreeNodeHandler();
	}

	public void setTreeNodeHandler(TreeNodeHandler treeNodeHandler) {
		treeIterator.setTreeNodeHandler(treeNodeHandler);
	}

	public void loadRootNode(String xmlFile) {
		treeIterator.loadRootNode(xmlFile);
	}

	@Override
	public boolean hasNext() {
		return !treeNodes.isEmpty() || treeIterator.hasNext();
	}

	@Override
	public TreeNode next() {
		TreeNode currNode = null;

		// Εαν δεν υπάρχει nextNode, είτε δεν έχουμε ολοκληρώσει μια βουτιά σε 
		// ένα κλάδο του δένδρου, είτε είναι ο τελευταίος κλάδος.

		if (nextNode == null) {
			while (treeIterator.hasNext()) {
				currNode = treeIterator.next();

				if (getTreeNodeHandler() != null) {
					getTreeNodeHandler().processTreeNode(currNode);
				}

				if (prevNode == null || prevNode.getLevel() < currNode.getLevel()) {
					treeNodes.add(0, currNode);
					prevNode = currNode;
				} else {
					// Το currNode είναι είτε αδελφός είτε πρόγονος του prevNode.
					// Σε αυτό το σημείο σταματάμε τη "βουτιά" και κρατάμε το 
					// currNode σαν nextNode για μελλοντική χρήση.
					nextNode = currNode;
					break;
				}
			}
		}

		// Εαν υπάρχει prevNode έχουμε ολοκληρώσει μια βουτιά σε ένα κλάδο του 
		// δένδρου και πρέπει να καταναλώσουμε το buffer που έχουμε μαζέψει.

		if (prevNode != null) {
			currNode = prevNode;
			treeNodes.remove(prevNode);

			if (!treeNodes.isEmpty()) {
				prevNode = treeNodes.get(0);
			}

			if (nextNode != null && prevNode.getLevel() < nextNode.getLevel()) {
				treeNodes.add(0, nextNode);
				prevNode = nextNode;
				nextNode = null;
			}
		}

		if (getTreeNodeHandler() != null) {
			getTreeNodeHandler().postProcessTreeNode(currNode);
		}

		return currNode;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}

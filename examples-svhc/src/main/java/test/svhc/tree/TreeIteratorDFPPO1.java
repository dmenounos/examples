package test.svhc.tree;

/**
 * Αλγόριθμος διαπέρασης δένδρου.
 */
public class TreeIteratorDFPPO1 extends TreeIterator {

	@Override
	public TreeNode next() {
		TreeNode treeNode = treeNodes.get(0);

		while (!treeNode.isExpanded()) {
			if (getTreeNodeHandler() != null) {
				getTreeNodeHandler().processTreeNode(treeNode);
			}

			depthFirstTraversal(treeNode);
			treeNode = treeNodes.get(0);
		}

		treeNodes.remove(treeNode);

		if (getTreeNodeHandler() != null) {
			getTreeNodeHandler().postProcessTreeNode(treeNode);
		}

		return treeNode;
	}
}

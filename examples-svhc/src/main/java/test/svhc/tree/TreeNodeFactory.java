package test.svhc.tree;

import org.w3c.dom.Element;

public interface TreeNodeFactory {

	TreeNode createTreeNode(TreeNode parentNode, Element element);
}

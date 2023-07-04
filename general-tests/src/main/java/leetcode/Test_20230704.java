package leetcode;

/**
 * @author dewu.de
 * @date 2023-07-04 9:52 上午
 */
public class Test_20230704 {

    public TreeNode searchBST(TreeNode root, int val) {
        return doSearchBST(root, val);
    }

    public TreeNode doSearchBST(TreeNode node, int val) {
        if (node != null) {
            if (node.val == val) {
                return node;
            } else if (node.val > val) {
                return doSearchBST(node.left, val);
            } else {
                return doSearchBST(node.right, val);
            }
        }
        return null;
    }
}

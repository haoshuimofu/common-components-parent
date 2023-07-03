package leetcode;

/**
 * @author dewu.de
 * @date 2023-07-03 1:33 下午
 */
public class Test_20230703 {

    public static void main(String[] args) {
    }

    /**
     * 验证二叉树是BST
     * [120,70,140,50,100,130,160,20,55,75,110,119,135,150,200]
     *
     * @param root
     * @return
     */
    public static boolean isValidBST(TreeNode root) {
        if (root == null) {
            return false;
        }
        return validate(root.left, root, true) && validate(root.right, root, false);
    }

    public static TreeNode validateByInorder(TreeNode node, TreeNode prev) {
        if (node.left == null) {
            if (prev == null) {
                prev = node;
            }
        } else {
            prev = validateByInorder(node, prev);

        }
        prev = validateByInorder(node, prev);
        validateByInorder(node.right, prev);
        return null;
    }

    /**
     * 参考 Tree_BST.puml
     *
     * @param node
     * @param parent
     * @param isLeft
     * @return
     */
    private static boolean validate(TreeNode node, TreeNode parent, boolean isLeft) {
        if (node == null) {
            return true;
        }
        if (isLeft) {
            if (node.val >= parent.val) {
                return false;
            }
            if (node.left != null) {
                if (node.left.val >= node.val) {
                    return false;
                }
            }
            if (node.right != null) {
                if (node.right.val <= node.val || node.right.val >= parent.val) {
                    return false;
                }
            }
        } else {
            if (node.val <= parent.val) {
                return false;
            }
            if (node.left != null) {
                if (node.left.val >= node.val || node.left.val <= parent.val) {
                    return false;
                }
            }
            if (node.right != null) {
                if (node.right.val <= node.val) {
                    return false;
                }
            }
        }
        return validate(node.left, node, true) && validate(node.right, node, false);
    }
}

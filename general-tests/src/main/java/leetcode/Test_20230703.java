package leetcode;

/**
 * @author dewu.de
 * @date 2023-07-03 1:33 下午
 */
public class Test_20230703 {

    static TreeNode prev = null;

    public static void main(String[] args) {
        TreeNode root = TreeNode_Init.init();
        System.out.println(isValidBST(root));
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
            return true;
        }
        if (!isValidBST(root.left)) {
            return false;
        }
        if (prev != null) {
            if (root.val >= prev.val) {
                return false;
            }
        } else {
            prev = root;
        }
        return isValidBST(root.right);

    }


}

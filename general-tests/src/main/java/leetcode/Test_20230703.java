package leetcode;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dewu.de
 * @date 2023-07-03 1:33 下午
 */
public class Test_20230703 {

    static TreeNode prev = null;

    public static void main(String[] args) {
        TreeNode root = TreeNode_Init.init();
        System.out.println(isValidBST(root));

        BSTIterator iterator = new BSTIterator(root);
        System.out.println(JSON.toJSONString(iterator));
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
        if (prev != null && root.val <= prev.val) {
            return false;
        }
        prev = root;
        return isValidBST(root.right);
    }

    // BST迭代器实现
    @Getter
    @Setter
    static class BSTIterator {

        private List<Integer> iterator;
        private int index = -1;

        public BSTIterator(TreeNode root) {
            iterator = new ArrayList<>();
            inorder(root);
        }

        public int next() {
            if (hasNext()) {
                return iterator.get(++index);
            }
            return 0;
        }

        public boolean hasNext() {
            return iterator.size() > 0 && index < iterator.size() - 1;
        }

        private void inorder(TreeNode node) {
            if (node == null) {
                return;
            }
            inorder(node.left);
            iterator.add(node.val);
            inorder(node.right);
        }
    }

}

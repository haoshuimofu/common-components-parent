package leetcode;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import java.util.Arrays;

public class Test_20230804 {


    public static void main(String[] args) {
        int[] preorder = new int[]{3, 9, 20, 15, 7};
        int[] inorder = new int[]{9, 3, 15, 20, 7};
        TreeNode root = buildTree(preorder, inorder);
        List<Integer> vals = new ArrayList<>();
        printTree(root, vals);
        System.out.println(JSON.toJSONString(vals));

    }

    /**
     * 剑指 Offer 07. 重建二叉树
     *       1
     *    2    3
     * 4          5
     *         6    7
     * 前序遍历: [1, 2, 4, 3, 5, 6, 7]
     * 中序遍历: [4, 2, 1, 3, 6, 5, 7]
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        // 前序和中序遍历长度一致
        if (preorder == null || preorder.length == 0) {
            return null;
        }
        // 前序遍历index=0就是root节点
        int rootIndexPreorder = 0;
        TreeNode root = new TreeNode(preorder[rootIndexPreorder]);
        if (preorder.length == 1) {
            return root;
        }
        // 找root在中序遍历的位置, 左边就是root.left, 右边就是root.right
        int rootIndexInorder = -1;
        for (int i = 0; i < inorder.length; i++) {
            if (inorder[i] == root.val) {
                rootIndexInorder = i;
                break;
            }
        }
        root.left = buildTree(
                Arrays.copyOfRange(preorder, rootIndexPreorder + 1, rootIndexPreorder + rootIndexInorder + 1),
                Arrays.copyOfRange(inorder, 0, rootIndexInorder));

        root.right = buildTree(
                Arrays.copyOfRange(preorder, rootIndexPreorder + rootIndexInorder+1, preorder.length),
                Arrays.copyOfRange(inorder, rootIndexInorder + 1, inorder.length));
        return root;
    }

    private static void printTree(TreeNode root, List<Integer> output) {
        if (root == null) {
            output.add(null);
        } else {
            output.add(root.val);
            printTree(root.left, output);
            printTree(root.right, output);
        }
    }


}

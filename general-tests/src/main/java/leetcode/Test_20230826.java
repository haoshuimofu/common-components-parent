package leetcode;

import java.util.*;
import java.util.function.IntFunction;

public class Test_20230826 {


    /**
     * 剑指 Offer 32 - I. 从上到下打印二叉树
     *
     * @param root
     * @return
     */
    public int[] levelOrder(TreeNode root) {
        if (root == null) {
            return new int[0];
        }
        List<Integer> values = new ArrayList<>();
        ArrayDeque<TreeNode> nodes = new ArrayDeque<>();
        nodes.addLast(root);
        while (!nodes.isEmpty()) {
            TreeNode node = nodes.removeFirst();
            values.add(node.val);
            if (node.left != null) {
                nodes.addLast(node.left);
            }
            if (node.right != null) {
                nodes.addLast(node.right);
            }
        }
        int[] result = new int[values.size()];
        for (int i = 0; i < values.size(); i++) {
            result[i] = values.get(i);
        }
        return result;
    }

    /**
     * 剑指 Offer 32 - II. 从上到下打印二叉树 II
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder2(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<List<Integer>> values = new ArrayList<>();
        ArrayDeque<TreeNode> nodes = new ArrayDeque<>();
        nodes.addLast(root);
        while (!nodes.isEmpty()) {
            int size = nodes.size();
            List<Integer> levelValues = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                TreeNode node = nodes.removeFirst();
                levelValues.add(node.val);
                if (node.left != null) {
                    nodes.addLast(node.left);
                }
                if (node.right != null) {
                    nodes.addLast(node.right);
                }
            }
            values.add(levelValues);
        }
        return values;
    }

    /**
     * 剑指 Offer 32 - III. 从上到下打印二叉树 III
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder3(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<List<Integer>> values = new ArrayList<>();
        ArrayDeque<TreeNode> nodes = new ArrayDeque<>();
        nodes.addLast(root);
        int level = 1;
        while (!nodes.isEmpty()) {
            boolean reverse = ++level % 2 == 0;
            int size = nodes.size();
            LinkedList<Integer> levelValues = new LinkedList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = nodes.removeFirst();
                if (reverse) {
                    levelValues.add(node.val);
                } else {
                    levelValues.addFirst(node.val);
                }
                if (node.left != null) {
                    nodes.addLast(node.left);
                }
                if (node.right != null) {
                    nodes.addLast(node.right);
                }
            }
            values.add(levelValues);
        }
        return values;
    }

    /**
     * 剑指 Offer 33. 二叉搜索树的后序遍历序列
     *
     * @param postorder
     * @return
     */
    public boolean verifyPostorder(int[] postorder) {
        if (postorder == null || postorder.length == 0) {
            return true; // 空也认为是的!!!
        } else if (postorder.length == 1) {
            return true; // 数组长度1显然是
        }
        // 既然是二叉树问题, 自然想到递归
        return doVerifyPostorder(postorder, 0, postorder.length - 1);
    }

    /**
     * 搜索二叉树判断
     *
     * @param postorder 后续遍历数组
     * @param from      其实下标
     * @param to        对应root在后续遍历中的下标, 很关键!!!
     * @return
     */
    public boolean doVerifyPostorder(int[] postorder, int from, int to) {
        if (from == to) {
            return true; // 长度1很显然是
        }
        // to代表root, 从root向前找第一个小于root值的下标
        int index = to - 1;
        while (index >= from && postorder[index] > postorder[to]) {
            index--;
        }
        if (index >= from) {
            // 说明[from, index]是root节点的left部分, 看left部分是否有大于root节点值的元素, 如果直接返回false
            for (int i = from; i <= index; i++) {
                if (postorder[i] > postorder[to]) {
                    return false;
                }
            }
            if (index < to - 1) {
                // 递归判断root左右子节点
                return doVerifyPostorder(postorder, from, index) && doVerifyPostorder(postorder, index + 1, to - 1);
            } else {
                // root只有left
                return doVerifyPostorder(postorder, from, index);
            }
        } else {
            // left只有right
            return doVerifyPostorder(postorder, from, to - 1);
        }
    }

    public static void main(String[] args) {
        Test_20230826 test = new Test_20230826();
        System.out.println(test.verifyPostorder(new int[]{1, 2, 5, 10, 6, 9, 4, 3}));
    }
}

package leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author dewu.de
 * @date 2023-06-30 10:22 上午
 */
public class Test_20230630 {

    /**
     * 前序遍历: 根 -> 左子节点 -> 右子节点
     *
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> vals = new ArrayList<>();
        preorder(root, vals);
        return vals;
    }

    private void preorder(TreeNode node, List<Integer> vals) {
        if (node != null) {
            vals.add(node.val);
            preorder(node.left, vals);
            preorder(node.right, vals);
        }
    }

    /**
     * 中序遍历: 左子节点 -> 根 -> 右子节点
     *
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> vals = new ArrayList<>();
        inorder(root, vals);
        return vals;
    }

    private void inorder(TreeNode node, List<Integer> vals) {
        if (node != null) {
            preorder(node.left, vals);
            vals.add(node.val);
            preorder(node.right, vals);
        }
    }

    /**
     * 后序遍历: 右子节点 -> 左子节点 -> 根
     *
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> vals = new ArrayList<>();
        postorder(root, vals);
        return vals;
    }

    private void postorder(TreeNode node, List<Integer> vals) {
        if (node != null) {
            preorder(node.right, vals);
            preorder(node.left, vals);
            vals.add(node.val);
        }
    }

    /**
     * 层序遍历, 借助于队列实现
     * <p>
     * [3,9,20,null,null,15,7]
     * [[3],[9,20],[15,7]]
     * [[3]]
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<List<Integer>> vals = new ArrayList<>();
        LinkedBlockingDeque<TreeNode> queue = new LinkedBlockingDeque<>();
        // 将root首次添加到队列
        queue.offer(root);
        vals.add(Collections.singletonList(root.val));
        while (!queue.isEmpty()) {
            // 获取并删除队列首节点, 如果队列为空则返回null
            TreeNode node = queue.pollFirst();
            List<Integer> tempVals = new ArrayList<>();
            // 如果左子节点存在, 把左子节点加到队列尾
            if (node.left != null) {
                queue.offerLast(node.left);
                tempVals.add(node.left.val);
            }
            // 如果右子节点存在, 把右子节点加到队列尾
            if (node.right != null) {
                queue.offerLast(node.right);
                tempVals.add(node.right.val);
            }
            // 当前节点的左右子节点都已经连续加到队列尾, 把当前节点从队首删除
            // 继续循环，下个节点要么是兄弟节点, 要么是子节点
            //queue.pollFirst();
            if (!tempVals.isEmpty()) {
                vals.add(tempVals);
            }
        }
        return vals;
    }


}

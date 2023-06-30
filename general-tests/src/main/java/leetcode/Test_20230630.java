package leetcode;

import java.util.ArrayDeque;
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
     * [1,2,3,4,5,6,7]
     *      1
     *   2     3
     * 4  5  6   7
     *
     *
     * 队列首部逐一弹出, 追加其子节点到尾部
     * -> 1
     * -> 2 3
     * -> 3 4 5
     * -> 4 5 6 7
     *
     * -> 1
     * -> 2 3 -> 3 4 5 -> 4 5 6 7
     * -> 4 5 6 7
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<List<Integer>> vals = new ArrayList<>();

//        LinkedBlockingDeque<TreeNode> queue = new LinkedBlockingDeque<>();
        // ArrayDeque比LinkedDeque/LinkedBlockingDeque高效很多
        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        // 将root首次添加到队列
        queue.offer(root);
        vals.add(Collections.singletonList(root.val));
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> levelVals = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                // 获取并删除队列首节点, 如果队列为空则返回null
                TreeNode node = queue.poll();
                // 如果左子节点存在, 把左子节点加到队列尾
                if (node.left != null) {
                    queue.offerLast(node.left);
                    levelVals.add(node.left.val);
                }
                // 如果右子节点存在, 把右子节点加到队列尾
                if (node.right != null) {
                    queue.offerLast(node.right);
                    levelVals.add(node.right.val);
                }
            }
            vals.add(levelVals);
        }
        return vals;
    }

}

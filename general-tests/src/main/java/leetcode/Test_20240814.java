package leetcode;

import com.demo.algorithm.Tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test_20240814 {

    public static void main(String[] args) {
        TreeNode node_3 = new TreeNode(3);
        node_3.left = new TreeNode(5);
        node_3.left.left = new TreeNode(6);
        node_3.left.right = new TreeNode(2);
        node_3.left.right.left = new TreeNode(7);
        node_3.left.right.right = new TreeNode(4);

        node_3.right = new TreeNode(1);
        node_3.right.left = new TreeNode(0);
        node_3.right.right = new TreeNode(8);

        TreeNode node = new Test_20240814().lowestCommonAncestor(node_3, node_3.left, node_3.left.right.right);
//        System.out.println(node.val);

        int[] nums = new int[]{1, 3, 6, 7, 9, 4, 10, 5, 6};
        System.out.println(new Test_20240814().lengthOfLIS(nums));
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        List<Integer> pPath = new ArrayList<>();
        getPath(root, p, pPath);
        List<Integer> qPath = new ArrayList<>();
        getPath(root, q, qPath);
//        System.out.println(Arrays.toString(pPath.toArray()));
//        System.out.println(Arrays.toString(qPath.toArray()));
        qPath.retainAll(pPath);
        return getNode(root, qPath.get(qPath.size() - 1));
    }

    public void getPath(TreeNode node, TreeNode target, List<Integer> path) {
        path.add(node.val);
        if (node.val == target.val) {
            return;
        } else {
            if (node.left != null) {
                getPath(node.left, target, path);
            }
            if (path.get(path.size() - 1) == target.val) {
                return;
            }
            if (node.right != null) {
                getPath(node.right, target, path);
            }
            if (path.get(path.size() - 1) == target.val) {
                return;
            }
            path.remove(path.size() - 1);
        }
    }

    public TreeNode getNode(TreeNode root, int value) {
        if (root.val == value) {
            return root;
        } else {
            if (root.left != null) {
                TreeNode result = getNode(root.left, value);
                if (result == null && root.right != null) {
                    result = getNode(root.right, value);
                }
                return result;
            } else {
                return getNode(root.right, value);
            }
        }
    }


    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int[] max = new int[1];
        max[0] = 1;
        for (int i = 0; i < nums.length - 1; i++) {
            List<Integer> path = new ArrayList<>();
            path.add(nums[i]);
            doLengthOfLIS(nums, i + 1, path, max);
        }
        return max[0];
    }

    private void doLengthOfLIS(int[] nums, int index, List<Integer> path, int[] max) {
        for (int i = index; i < nums.length; i++) {
            if (nums[i] > path.get(path.size() - 1)) {
                path.add(nums[i]);
                max[0] = Math.max(max[0], path.size());
                doLengthOfLIS(nums, i + 1, path, max);
                path.remove(path.size() - 1);
            }
        }
    }
}

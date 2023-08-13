package leetcode;

import java.util.*;

public class Test_20230813 {

    public static void main(String[] args) {

        Test_20230813 test = new Test_20230813();
        System.out.println(test.containsNearbyDuplicate(new int[]{1, 2, 3, 1, 2, 3}, 2));

    }

    /**
     * 226. 翻转二叉树
     *
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {

        if (root == null) {
            return root;
        }
        TreeNode temp = root.right;
        root.right = root.left;
        root.left = temp;
        invertTree(root.left);
        invertTree(root.left);
        return root;

    }

    /**
     * 257. 二叉树的所有路径
     *
     * @param root
     * @return
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        doBinaryTreePaths(root, new ArrayList<>(), paths);
        return paths;
    }

    private void doBinaryTreePaths(TreeNode node, List<Integer> nodeValues, List<String> paths) {
        if (node == null) {
            return;
        }
        nodeValues.add(node.val);
        if (node.left == null && node.right == null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < nodeValues.size(); i++) {
                sb.append(nodeValues.get(i));
                if (i != nodeValues.size() - 1) {
                    sb.append("->");
                }
            }
            paths.add(sb.toString());
        } else {
            doBinaryTreePaths(node.left, nodeValues, paths);
            doBinaryTreePaths(node.right, nodeValues, paths);
        }
        nodeValues.remove(nodeValues.size() - 1);
    }

    /**
     * 219. 存在重复元素 II
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
//        for (int i = 0; i < nums.length - 1; i++) {
//            for (int j = i + 1; j < nums.length; j++) {
//                if (nums[j] == nums[i] && j - k <= k) {
//                    return true;
//                }
//            }
//        }
//        return false;
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            List<Integer> indexList = map.get(nums[i]);
            if (indexList == null) {
                indexList = new ArrayList<>();
                indexList.add(i);
                map.put(nums[i], indexList);
            } else {
                for (Integer idx : indexList) {
                    if (i - idx <= k) {
                        return true;
                    }
                }
                indexList.add(i);
            }
        }
        return false;
    }

}

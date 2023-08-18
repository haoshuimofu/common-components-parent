package leetcode;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dewu.de
 * @date 2023-08-18 11:25 上午
 */
public class Test_20230818 {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);

        root.right = new TreeNode(7);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(8);

        Test_20230818 test = new Test_20230818();
        System.out.println(test.pathSum(root, 7));

    }

    /**
     * * [3,9,20, 15,7]
     * * [9,3,15,20, 7]
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return doBuildTree(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    private TreeNode doBuildTree(int[] preorder, int preFrom, int preTo,
                                 int[] inorder, int inFrom, int inTo) {
        if (preFrom > preTo) {
            return null;
        }
        TreeNode node = new TreeNode(preorder[preFrom]);
        if (preFrom < preTo) {
            // 找node.val在inorder中的下标, 找出left节点数
            int leftCount = 0;
            for (int i = inFrom; i <= inTo; i++) {
                if (inorder[i] == node.val) {
                    leftCount = i - inFrom;
                    break;
                }
            }
            if (leftCount > 0) {
                node.left = doBuildTree(preorder, preFrom + 1, preFrom + leftCount, inorder, inFrom, inFrom + leftCount);
            }
            if (inFrom + leftCount < inTo) {
                node.right = doBuildTree(preorder, preFrom + leftCount + 1, preTo, inorder, inFrom + leftCount + 1, inTo);
            }
        }
        return node;
    }

    // ========
    public int pathSum(TreeNode node, int targetSum) {
        int[] count = new int[1];
        List<Integer> leftPathVals = new ArrayList<>();
        leftPathVals.add(node.val);
        doPathSum(node.left, targetSum, true, leftPathVals, count);
        List<Integer> rightPathVals = new ArrayList<>();
        rightPathVals.add(node.val);
        doPathSum(node.right, targetSum, false, rightPathVals, count);
        return count[0];
    }

    public void doPathSum(TreeNode node, int targetNum, boolean left, List<Integer> pathVals, int[] count) {
        if (node == null) {
            if (pathVals.size() > 0) {
                System.out.println(JSON.toJSONString(pathVals));
                countPath(pathVals, targetNum, count);
            }
        } else {
            pathVals.add(node.val);
            if (left) {
                doPathSum(node.left, targetNum, true, pathVals, count);
                if (node.right != null) {
                    List<Integer> newPathVals = new ArrayList<>();
                    newPathVals.add(node.val);
                    doPathSum(node.right, targetNum, false, newPathVals, count);
                }
            } else {
                doPathSum(node.right, targetNum, false, pathVals, count);
                if (node.left != null) {
                    List<Integer> newPathVals = new ArrayList<>();
                    newPathVals.add(node.val);
                    doPathSum(node.left, targetNum, true, newPathVals, count);
                }
            }
        }
    }

    private void countPath(List<Integer> path, int targetSum, int[] count) {
        for (int i = 0; i < path.size(); i++) {
            doCountPath(path, targetSum, i, 0, 0, count);
        }
    }

    private void doCountPath(List<Integer> path, int targetNum, int currIndex, int steps, int sum, int[] count) {
        if (currIndex >= path.size()) {
            return;
        }
        steps++;
        sum += path.get(currIndex);
        if (steps > 1 && sum == targetNum) {
            count[0]++;
        }
        // 既然循环只有一个值, 就没必要循环
//        for (int i = currIndex + 1; i <= currIndex + 1; i++) {
//            doCountPath(path, targetNum, i, sum, count);
//        }
        doCountPath(path, targetNum, currIndex + 1, steps, sum, count);
    }

}

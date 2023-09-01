package leetcode;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test_20230824 {

    public static void main(String[] args) {
        Test_20230824 test = new Test_20230824();

        int[] nums = {10, 1, 2, 7, 6, 1, 5};
        System.out.println(JSON.toJSONString(test.combinationSum2(nums, 8)));

        nums = new int[]{2, 5, 2, 1, 2};
        System.out.println(JSON.toJSONString(test.combinationSum2(nums, 5)));

        nums = new int[]{14, 6, 25, 9, 30, 20, 33, 34, 28, 30, 16, 12, 31, 9, 9, 12, 34, 16, 25, 32, 8, 7, 30, 12, 33, 20, 21, 29, 24, 17, 27, 34, 11, 17, 30, 6, 32, 21, 27, 17, 16, 8, 24, 12, 12, 28, 11, 33, 10, 32, 22, 13, 34, 18, 12};
        System.out.println(JSON.toJSONString(test.combinationSum2(nums, 27)));

    }

    /**
     * LCR 050. 路径总和 III
     * 437. 路径总和 III
     *
     * @param root
     * @param targetSum
     * @return
     */
    public int pathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return 0;
        }
        int[] count = new int[1];
        doPathSum(root, targetSum, new ArrayList<>(), count);
        return count[0];
    }

    public void doPathSum(TreeNode node, int targetSum, List<Integer> path, int[] count) {
        if (node == null) {
            return;
        }
        path.add(node.val);
        // 如果有叶子节点, 先处理叶子节点
        doPathSum(node.left, targetSum, path, count);
        doPathSum(node.right, targetSum, path, count);
        // 叶子节点处理完了, 回溯时叶子节点也移除了
        count[0] += countPath(path, targetSum);
        path.remove(path.size() - 1);
    }

    public int countPath(List<Integer> path, int targetNum) {
        if (path == null || path.isEmpty()) {
            return 0;
        }
        int count = 0;
        long value = targetNum; // long 方式int运算溢出
        for (int i = path.size() - 1; i >= 0; i--) {
            value -= path.get(i);
            if (value == 0) {
                count++;
            }
        }
        return count;
    }

    // ============

    /**
     * 40. 组合总和 II
     * TODO 待求最优解
     *
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        int[] sum = new int[1];
        for (int i = 0; i < candidates.length; i++) {
            if (candidates[i] > target) {
                break;
            }
            if (i == 0 || candidates[i] != candidates[i - 1]) {
                doCombinationSum2(candidates, target, i, path, sum, result);
            }
        }
        return result;
    }

    public void doCombinationSum2(int[] candidates, int target, int currIndex, List<Integer> path, int[] sum, List<List<Integer>> result) {
        if (sum[0] >= target) {
            return;
        }
        int currValue = candidates[currIndex];
        path.add(currValue);
        sum[0] += currValue;
        if (sum[0] == target) {
            result.add(new ArrayList<>(path));
        } else if (sum[0] < target) {
            for (int i = currIndex + 1; i < candidates.length; i++) {
                if (i == currIndex + 1 || candidates[i] != candidates[i - 1]) {
                    doCombinationSum2(candidates, target, i, path, sum, result);
                }
            }
        }
        path.remove(path.size() - 1);
        sum[0] -= currValue;
    }

}

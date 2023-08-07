package leetcode;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author dewu.de
 * @date 2023-08-07 1:09 下午
 */
public class Test_20230807 {

    public static void main(String[] args) {
        Test_20230807 test = new Test_20230807();

        int[] nums = new int[]{1, 2, 3};
        List<List<Integer>> subsets = new ArrayList<>();
        subsets.add(Collections.emptyList());
        for (int i = 1; i <= nums.length; i++) {
            test.subsets(nums, i, subsets);
        }
        System.out.println(JSON.toJSONString(subsets));
    }

    /**
     * LCR 079. 子集
     * 输入：nums = [1,2,3]
     * 输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
     *
     * @param nums
     * @return
     */
    public void subsets(int[] nums, int subsetLen, List<List<Integer>> subsets) {
        boolean[] visit = new boolean[nums.length];
        findSubset(nums, subsetLen, new ArrayList<>(), new ArrayList<>(), visit, subsets);
    }

    private void findSubset(int[] nums, int subsetLen, List<Integer> subset, List<Integer> visitIndexes, boolean[] visit, List<List<Integer>> subsets) {
        if (subset.size() == subsetLen) {
            subsets.add(new ArrayList<>(subset));
        }
        for (int i = 0; i < nums.length; i++) {
            if (!visit[i] && (visitIndexes.isEmpty() || i > visitIndexes.get(visitIndexes.size() - 1))) {
                subset.add(nums[i]);
                visitIndexes.add(i);
                visit[i] = true;
                findSubset(nums, subsetLen, subset, visitIndexes, visit, subsets);
                visit[i] = false;
                subset.remove(subset.size() - 1);
                visitIndexes.remove(visitIndexes.size() - 1);
            }
        }
    }

    /**
     * 剑指 Offer 63. 股票的最大利润
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int min = Integer.MAX_VALUE;
        int profit = 0;
        for (int i = 0; i < prices.length; i++) {
            if (i > 0) {
                profit = Math.max(profit, prices[i] - min);
            }
            min = Math.min(min, prices[i]);
        }
        return profit;
    }

    /**
     * 剑指 Offer 55 - I. 二叉树的深度
     *
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return dfs(root, 1);
    }

    public int dfs(TreeNode node, int depth) {
        if (node == null) {
            return depth - 1;
        }
        return Math.max(dfs(node.left, depth + 1), dfs(node.right, depth + 1));
    }

    /**
     * LCR 038. 每日温度
     *
     * @param temperatures
     * @return
     */
    public int[] dailyTemperatures(int[] temperatures) {
        if (temperatures == null) {
            return null;
        }
        int[] res = new int[temperatures.length];
        res[0] = 0;
        int slow = 0;
        int fast = 1;
        while (fast < temperatures.length) {
            if (slow == fast) {
                fast++;
            } else {
                if (temperatures[fast] > temperatures[fast - 1]) {
                    res[fast - 1] = 1;
                }
                if (temperatures[fast] > temperatures[slow]) {
                    if (temperatures[slow] == 0) {
                        res[slow] = fast - slow;
                    }
                    slow++;
                } else {
                    fast++;
                }
            }
        }
        return res;
    }

}

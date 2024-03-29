package leetcode;

import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 * @author dewu.de
 * @date 2023-08-07 1:09 下午
 */
public class Test_20230807 {

    public static void main(String[] args) {
        Test_20230807 test = new Test_20230807();

        int[] nums = new int[]{1, 2, 3};
        List<List<Integer>> subsets = test.subsets(nums);
        System.out.println(JSON.toJSONString(subsets));


        int[] temperatures = new int[]{73, 74, 75, 71, 69, 72, 76, 73};
        System.out.println(JSON.toJSONString(test.dailyTemperatures(temperatures)));
    }

    /**
     * LCR 079. 子集
     * 输入：nums = [1,2,3]
     * 输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> subsets = new ArrayList<>();
        subsets.add(Collections.emptyList());
        for (int i = 0; i < nums.length; i++) {
            findSubset(nums, i, i, new ArrayList<>(), subsets);
        }
        return subsets;
    }

    private void findSubset(int[] nums, int fromIndex, int currIndex, List<Integer> path, List<List<Integer>> subsets) {
        if (fromIndex >= nums.length) {
            return;
        }
        path.add(nums[currIndex]);
        subsets.add(new ArrayList<>(path));
        for (int i = fromIndex; i < nums.length; i++) {
            if (i > currIndex) {
                findSubset(nums, fromIndex, i, path, subsets);
            }
        }
        path.remove(path.size() - 1);
    }


    /**
     * 90. 子集 II
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> subsets = new ArrayList<>();
        subsets.add(Collections.emptyList());
        for (int i = 0; i < nums.length; i++) {
            findSubsetWithDup(nums, i, i, new ArrayList<>(), subsets);
        }
        return subsets;
    }

    private void findSubsetWithDup(int[] nums, int fromIndex, int currIndex, List<Integer> path, List<List<Integer>> subsets) {
        if (fromIndex >= nums.length) {
            return;
        }
        path.add(nums[currIndex]);
        subsets.add(new ArrayList<>(path));
        for (int i = fromIndex; i < nums.length; i++) {
            if (i > currIndex) {
                if (i - currIndex == 1 || nums[i] != nums[i - 1]) {
                    findSubset(nums, fromIndex, i, path, subsets);
                }
            }
        }
        path.remove(path.size() - 1);
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
        Stack<Integer> stack = new Stack<>();
        stack.add(0);
        for (int i = 1; i < temperatures.length; i++) {
            if (temperatures[i] > temperatures[i - 1]) {
                res[i - 1] = 1;
                int size = stack.size();
                for (int j = 0; j < size; j++) {
                    int target = stack.peek();
                    if (temperatures[i] > temperatures[target]) {
                        res[stack.pop()] = i - target;
                    } else {
                        // 当遇到当前值小栈顶元素则中断
                        break;
                    }
                }
            } else {
                stack.push(i - 1);
            }
        }
        return res;
    }

}

package leetcode;

import java.util.ArrayDeque;

/**
 * @author dewu.de
 * @date 2023-07-19 7:49 下午
 */
public class Test_20230719 {

    public static void main(String[] args) {

        int[] nums = new int[]{1, 1, 1, 1, 1};
        System.out.println(findTargetSumWays(nums, 3));
        System.out.println(findTargetSumWays1(nums, 3));
    }

    /**
     * 494.目标和
     * 基于队列的暴力解法
     *
     * @param nums
     * @param target
     * @return
     */
    public static int findTargetSumWays(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int count = 0;
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        for (int i = 0; i < nums.length; i++) {
            int size = queue.size();
            for (int j = 0; j < size; j++) {
                int factor = queue.removeFirst();
                int value = factor + nums[i];
                if (i == nums.length - 1) {
                    if (value == target) {
                        count++;
                    }
                } else {
                    queue.addLast(value);
                }
                value = factor - nums[i];
                if (i == nums.length - 1) {
                    if (value == target) {
                        count++;
                    }
                } else {
                    queue.addLast(value);
                }
            }
        }
        return count;
    }

    /**
     * 递归-暴力解法
     * 可看做root=0的二叉树,前序递归遍历
     *
     * @param nums
     * @param target
     * @return
     */
    public static int findTargetSumWays1(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int[] count = new int[1];
        doSum(0, nums, 0, target, count);
        return count[0];
    }

    private static void doSum(int value, int[] nums, int index, int target, int[] count) {
        if (index < nums.length - 1) {
            doSum(value + nums[index], nums, index + 1, target, count);
            doSum(value - nums[index], nums, index + 1, target, count);
        } else if (index == nums.length - 1) {
            if (value + nums[index] == target) {
                count[0] += 1;
            }
            if (value - nums[index] == target) {
                count[0] += 1;
            }
        }
    }
}

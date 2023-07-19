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
    }

    public static int findTargetSumWays(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        ArrayDeque<Integer> queue = new ArrayDeque<>();

        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            int size = i == 0 ? 1 : queue.size();
            for (int j = 0; j < size; j++) {
                int factor = i == 0 ? 0 : queue.removeFirst();
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
}

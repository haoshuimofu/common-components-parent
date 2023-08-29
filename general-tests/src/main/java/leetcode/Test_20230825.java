package leetcode;

import uitls.NumberUtils;

import java.util.Arrays;

public class Test_20230825 {

    /**
     * 560. 和为 K 的子数组
     * TODO 待求最优解 - 超时
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraySum(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int[] count = new int[1];
        for (int i = 0; i < nums.length; i++) {
            dfs(nums, k, i, 0, count);
        }
        return count[0];
    }

    public void dfs(int[] nums, int k, int currIndex, int sum, int[] count) {
        if (currIndex >= nums.length) {
            return;
        }
        sum += nums[currIndex];
        if (sum == k) {
            count[0]++;
        }
        dfs(nums, k, currIndex + 1, sum, count);
    }

    /**
     * 勉强过
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraySum1(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int from = 0;
        int index = 0;
        int sum = 0;
        int count = 0;
        while (index < nums.length) {
            sum += nums[index];
            int lastFrom = from;
            int lastSum = sum;
            while (from <= index) {
                if (sum == k) {
                    count++;
                }
                sum -= nums[from];
                from++;
            }
            from = lastFrom;
            sum = lastSum;
            index++;
        }
        return count;
    }

    // ==================

    /**
     * 43. 字符串相乘
     *
     * @param num1
     * @param num2
     * @return
     */
    public String multiply(String num1, String num2) {
        return String.valueOf(convertStrToNum(num1) * convertStrToNum(num2));
    }

    private int convertStrToNum(String str) {
        int factor = 1;
        int sum = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            int chInt = (int) str.charAt(i) - 48;
            sum += chInt * factor;
            factor *= 10;
        }
        return sum;
    }

    /**
     * 215. 数组中的第K个最大元素
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        if (k == 1) {
            return nums[nums.length - 1];
        }
        int count = 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] <= nums[i + 1] && ++count == k) {
                return nums[i];
            }
        }
        return Integer.MIN_VALUE;
    }

    public static void main(String[] args) {
        System.out.println((int) '0');
        System.out.println((int) '9');
        Test_20230825 test = new Test_20230825();
        System.out.println(test.convertStrToNum("123"));

        System.out.println(test.findKthLargest(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4));
    }

}

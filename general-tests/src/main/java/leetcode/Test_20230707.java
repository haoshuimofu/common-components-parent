package leetcode;

import java.util.Arrays;

/**
 * @author dewu.de
 * @date 2023-07-07 9:59 上午
 */
public class Test_20230707 {

    public static void main(String[] args) {
        int[] nums = new int[]{-1, 2, 1, -4};
        System.out.println(threeSumClosest1(nums, 1));

    }

    /**
     * 最接近的三数之和 - 暴力解法
     *
     * @param nums
     * @param target
     * @return
     */
    public static int threeSumClosest1(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            throw new RuntimeException("不存在三数");
        }
        int delta = Integer.MAX_VALUE;
        int sum = Integer.MIN_VALUE;
        for (int x = 0; x <= nums.length - 3; x++) {
            for (int y = x + 1; y <= nums.length - 2; y++) {
                for (int z = y + 1; z <= nums.length - 1; z++) {
                    int tempSum = nums[x] + nums[y] + nums[z];
                    int diff = Math.abs(tempSum - target);
                    if (diff == 0) {
                        return tempSum;
                    } else if (delta > diff) {
                        delta = diff;
                        sum = tempSum;
                    }
                }
            }
        }
        return sum;
    }

    /**
     * 最接近的三数之和
     *
     * @param nums
     * @param target
     * @return
     */
    public static int threeSumClosest(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            throw new RuntimeException("不存在三数");
        }
        Arrays.sort(nums);
        int n = nums.length;
        int d = Integer.MAX_VALUE;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            int a = nums[i];
            int b = i + 1;
            int c = n - 1;
            while (b < c) {
                int sum = a + nums[b] + nums[c];
                if (Math.abs(sum - target) < d) {
                    d = Math.abs(sum - target);
                    ans = sum;
                }
                if (sum > target) {
                    c--;
                } else {
                    b++;
                }
            }
        }
        return ans;
    }

    /**
     * 斐波那契数 - 递归 - 自顶而下
     * F(0) = 0，F(1) = 1
     * F(n) = F(n - 1) + F(n - 2)，其中 n > 1
     *
     * @param n
     * @return
     */
    public int fib(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n - 1) + fib(n - 2);
    }

    public int fib1(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n - 1) + fib(n - 2);
    }

}

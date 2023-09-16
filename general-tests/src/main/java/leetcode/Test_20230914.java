package leetcode;

import java.util.Arrays;

public class Test_20230914 {

    public static void main(String[] args) {
        Test_20230914 test = new Test_20230914();
        int[] nums = new int[]{2, 3, -2, 4};
        System.out.println(test.maxProduct(nums));

        int[] coins = new int[]{411, 412, 413, 414, 415, 416, 417, 418, 419, 420, 421, 422};
        System.out.println(test.coinChange(coins, 9864));

        coins = new int[]{284, 260, 393, 494};
        System.out.println(test.coinChange(coins, 7066));
    }

    /**
     * 152. 乘积最大子数组
     *
     * @param nums
     * @return
     */
    public int maxProduct(int[] nums) {
        int max = nums[0];
        int lastMax = max;
        int lastMin = max;
        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
            // 因为有正负数, 拿之前的max & min乘以当前数值
            int tempMax = Math.max(lastMax * num, lastMin * num);
            int tempMin = Math.min(lastMax * num, lastMin * num);
            lastMax = Math.max(tempMax, num);
            lastMin = Math.min(tempMin, num);
            max = Math.max(max, lastMax);
        }
        return max;
    }

    /**
     * 322. 零钱兑换
     *
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange(int[] coins, int amount) {
        if (amount <= 0) {
            return 0;
        }
        long start = System.currentTimeMillis();
        Arrays.sort(coins);
        int[] min = new int[1];
        min[0] = Integer.MAX_VALUE;
        for (int i = coins.length - 1; i >= 0; i--) {
            doCoinChange(coins, i, amount, 0, 0, min);
        }
        System.out.println("cost=" + (System.currentTimeMillis() - start));
        return min[0] == Integer.MAX_VALUE ? -1 : min[0];
    }

    public void doCoinChange(int[] coins, int to, int amount, int total, int count, int[] min) {
        if (to < 0 || total >= amount) {
            return;
        }
        int num = (amount - total) / coins[to];
        total += coins[to] * num;
        count += num;
        if (total == amount) {
            min[0] = Math.min(min[0], count);
            return;
        }
        while (num >= 0) {
            doCoinChange(coins, to - 1, amount, total, count, min);
            total -= coins[to];
            count--;
            num--;
        }
    }

}

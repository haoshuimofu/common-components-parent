package leetcode;

import java.util.Arrays;

public class Test_20230914 {

    public static void main(String[] args) {
        Test_20230914 test = new Test_20230914();
        int[] nums = new int[]{2, 3, -2, 4};
        System.out.println(test.maxProduct(nums));

        int[] coins = new int[]{411, 412, 413, 414, 415, 416, 417, 418, 419, 420, 421, 422};
        System.out.println(test.coinChange(coins, 9864));
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
        Arrays.sort(coins);
        int[] min = new int[1];
        min[0] = Integer.MAX_VALUE;
        doCoinChange(coins, coins.length - 1, amount, 0, 0, min);
        return min[0] == Integer.MAX_VALUE ? -1 : min[0];
    }

    public void doCoinChange(int[] coins, int to, int amount, int total, int count, int[] min) {
        if (to < 0 || total >= amount) {
            return;
        }
        int num = (amount - total) / coins[to];
        if (num == 0) {
            doCoinChange(coins, to - 1, amount, total, count, min);
        } else {
            count += num;
            total += coins[to] * num;
            if (amount == total) {
                min[0] = Math.min(min[0], count);
            } else {
                int diff = amount - total;
                while ()


                int left = 0;
                int right = to - 1;
                int position = -1;

                while (left <= right) {
                    int mid = (left + right) / 2;
                    int midVal = coins[mid];
                    if (midVal == diff) {
                        position = mid;
                        break;
                    } else if (midVal > diff) {
                        right = mid - 1;
                    } else {
                        left = mid + 1;
                    }
                }
                if (position >= 0) {

                }


                while (num > 0) {
                    doCoinChange(coins, to - 1, amount, total, count, min);
                    total -= coins[to];
                    count--;
                    num--;
                }
            }
        }
    }

}

package leetcode;

import com.alibaba.fastjson.JSON;

/**
 * @author dewu.de
 * @date 2023-07-11 10:24 上午
 */
public class Test_20230711 {

    /**
     * 买卖股票的最佳时机
     * 因为是单次买卖
     * 第i天的收益 = prices[i] - 前n-1天中的最低价
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int min = prices[0];
        int max = 0;
        for (int i = 1; i < prices.length; i++) {
            max = Math.max(max, prices[i] - min);
            min = Math.min(min, prices[i]);
        }
        return max;
    }

    /**
     * 买卖股票的最佳时机 II
     *
     * @param prices
     * @return
     */
    public static int maxProfit2(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                profit += (prices[i] - prices[i - 1]);
            }
        }
        return profit;
    }

    public static int maxProfit2_2(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int low = 0;
        int high = 1;
        int profit = 0;
        while (high < prices.length) {
            if (prices[high] <= prices[high - 1]) {
                profit += (prices[high - 1] - prices[low]);
                low = high;
                high = low + 1;
            } else {
                if (high == prices.length - 1) {
                    profit += (prices[high] - prices[low]);
                }
                high++;
            }
        }
        return profit;
    }

    /**
     * 面试题 17.16. 按摩师
     * [1, 2, 3, 4, 5]看这个数组，首先预约中间要有休息时间，说明不能连续预约。推理如下：
     * 可以直接从1 -> 3，这个时候走2步；
     * 可以直接从1 -> 4，这个时候走3步；
     * 可以直接从1 -> 5吗？不可以，1和5之间可以带上3，单纯只有1和5的话，预约时间显然不会是最长！
     * 由此可见，状态转移方程为：dp[n] = Max(dp[n-2], dp[n-3]) + time[n]。
     * 需要注意，倒数后两位都是选项值，因为不连续！！！
     * 初始化值呢？很显然如下：
     * dp[0] = time[0]
     * dp[1] = time[1]
     * dp[2] = time[0] + time[2]，如果有的话！
     * 接下来就是DP了，见代码。
     * <p>
     * 注意，最后取dp后两位最大值即可！！！
     * <p>
     * 作者：i3usy-paynekjn
     * 链接：https://leetcode.cn/problems/the-masseuse-lcci/solution/dong-tai-gui-hua-chang-gui-jie-ti-by-i3u-ya1l/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     *
     * @param nums
     * @return
     */
    public static int massage(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = nums[1];
        if (nums.length >= 3) {
            dp[2] = nums[0] + nums[2];
            for (int i = 3; i < nums.length; i++) {
                dp[i] = Math.max(dp[i - 2], dp[i - 3]) + nums[i];
            }
        }
        System.out.println(JSON.toJSONString(dp));
        return Math.max(dp[dp.length - 1], dp[dp.length - 2]);

    }

    public static void main(String[] args) {
        int[] prices = new int[]{7, 1, 5, 3, 6, 4};
        System.out.println(maxProfit2(prices));
        System.out.println(maxProfit2_2(prices));

        int[] msgs = new int[]{2, 7, 9, 3, 1};
        System.out.println(massage(msgs));
    }
}

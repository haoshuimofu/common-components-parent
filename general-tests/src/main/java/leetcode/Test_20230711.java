package leetcode;

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
                if (low == prices.length - 1) {
                    profit += (prices[high] - prices[low]);
                }
                high++;
            }
        }
        return profit;
    }

    public static void main(String[] args) {
        int[] prices = new int[]{7, 1, 5, 3, 6, 4};
        System.out.println(maxProfit2(prices));
        System.out.println(maxProfit2_2(prices));
    }
}

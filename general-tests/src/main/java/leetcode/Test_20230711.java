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
}

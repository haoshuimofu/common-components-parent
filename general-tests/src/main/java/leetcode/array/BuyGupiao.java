package leetcode.array;

public class BuyGupiao {

    private static int getMax(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int low = 0;
        int max = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[low] < prices[i]) {
                i++;
            } else {
                max += (prices[i] - prices[low]);
                low++;
            }
        }
        return max;
    }

}

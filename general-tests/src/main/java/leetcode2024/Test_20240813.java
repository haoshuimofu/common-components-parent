package leetcode2024;

public class Test_20240813 {


    public static void main(String[] args) {
        int[] height = new int[]{3,6,2,9,8,5};
        System.out.println(new Test_20240813().bestTiming(height));
    }

    public int bestTiming(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int minPrice = prices[0];
        int profile = Integer.MIN_VALUE;
        for (int i = 1; i < prices.length; i++) {
            profile = Math.max(prices[i] - minPrice, prices[i]);
            minPrice = Math.min(minPrice, prices[i]);
        }
        return Math.max(0, profile);
    }
}

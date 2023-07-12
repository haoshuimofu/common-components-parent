package leetcode;

/**
 * @author dewu.de
 * @date 2023-07-12 9:47 上午
 */
public class Test_20230712 {

    public static void main(String[] args) {
        int[] cost = new int[]{10, 15, 20};
        System.out.println(minCostClimbingStairs(cost));
        cost = new int[]{1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
        System.out.println(minCostClimbingStairs(cost));
        cost = new int[]{1, 100};
        System.out.println(minCostClimbingStairs1(cost));
        System.out.println(minCostClimbingStairs1(cost));
    }

    /**
     * 746. 使用最小花费爬楼梯
     * 剑指 Offer II 088. 爬楼梯的最少成本
     */
    public static int minCostClimbingStairs(int[] cost) {
        if (cost == null || cost.length == 0) {
            return 0;
        }
        if (cost.length == 1) {
            return cost[0];
        }
        int[] dp = new int[cost.length + 1];
        dp[0] = 0;
        dp[1] = 0;
        for (int i = 2; i <= cost.length; i++) {
            dp[i] = Math.min(dp[i - 2] + cost[i - 2], dp[i - 1] + cost[i - 1]);
        }
        return dp[dp.length - 1];
    }

    /**
     * 剑指 Offer II 088. 爬楼梯的最少成本
     */
    public static int minCostClimbingStairs1(int[] cost) {
        //  创建一个动态规划数组
        int[] dp = new int[cost.length + 1];
        // 初始动态规划数组
        dp[0] = 0;
        dp[1] = 0;
        for (int i = 2; i <= cost.length; i++) {
            dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
        }
        return dp[cost.length];
    }
}

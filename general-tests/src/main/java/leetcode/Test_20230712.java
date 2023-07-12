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

        // 面试题 05.03. 翻转数位
        System.out.println(reverseBits(0));
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


    /**
     * 面试题 05.03. 翻转数位
     *
     * @param num
     * @return
     */
    public static int reverseBits(int num) {
        String bStr = Integer.toBinaryString(num);
        System.out.println(bStr + ".length() = " + bStr.length());
//        if (bStr.length() < 32) {
//            bStr = "0" + bStr;
//        }
        int start = -2;
        int zeroIndex = -2;
        // 正数不满32位首位补0, 特别设置-1, 后面计算相当于+1
        if (bStr.length() < 32) {
            start = -1;
            zeroIndex = -1;
        }
        int max = 0;
        for (int i = 0; i < bStr.length(); i++) {
            if (start == -2) {
                start = i;
            }
            if (bStr.charAt(i) == '0') {
                if (zeroIndex != -2) {
                    max = Math.max(max, i - start);
                    start = zeroIndex + 1;
                } else {
                    if (i == bStr.length() - 1) {
                        max = Math.max(max, i - start + 1);
                    }
                }
                zeroIndex = i;
            } else if (i == bStr.length() - 1) {
                max = Math.max(max, i - start + 1);
            }
        }
        return max;
    }
}

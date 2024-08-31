package leetcode2024;

public class Test_20240827 {

    /**
     * 718. 最长重复子数组
     * @param nums1
     * @param nums2
     * @return
     */
    public int findLength(int[] nums1, int[] nums2) {
        int maxLen = 0;
        for (int i = 0; i < nums1.length; i++) {
            int k = 0;
            for (int j = 0; j < nums2.length; j++) {
                while (i + k < nums1.length  && j + k < nums2.length && nums1[i + k] == nums2[j + k]) {
                    k++;
                }
                maxLen = Math.max(maxLen, k);
                k = 0;
            }
        }
        return maxLen;
    }

    public int findLength_dp(int[] nums1, int[] nums2) {
        int maxLen = 0;
        int[][] dp = new int[nums1.length][nums2.length];
        for (int i = nums1.length - 1; i >= 0; i--) {
            for (int j = nums2.length - 1; j >= 0; j--) {
                if (i == nums1.length - 1 || j == nums2.length - 1) {
                    dp[i][j] = nums1[i] == nums2[j] ? 1 : 0;
                } else {
                    dp[i][j] = nums1[i] == nums2[j] ? dp[i + 1][j + 1] + 1 : 0;
                }
                maxLen = Math.max(maxLen, dp[i][j]);
            }
        }
        return maxLen;
    }

    public int findLength_dp_1(int[] nums1, int[] nums2) {
        int maxLen = 0;
        int[][] dp = new int[nums1.length + 1][nums2.length + 1];
        for (int i = nums1.length - 1; i >= 0; i--) {
            for (int j = nums2.length - 1; j >= 0; j--) {
                dp[i][j] = nums1[i] == nums2[j] ? dp[i + 1][j + 1] + 1 : 0;
                maxLen = Math.max(maxLen, dp[i][j]);
            }
        }
        return maxLen;
    }


    /**
     * 121. 买卖股票的最佳时机
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int profit = 0;
        int minPrice = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] <= minPrice) {
                minPrice = prices[i];
            } else {
                profit = Math.max(prices[i] - minPrice, profit);
            }
        }
        return profit;
    }

    /**
     * 55. 跳跃游戏
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {
        if (nums.length == 1) {
            return true;
        }
        int maxIndex = nums[0];
        for (int i = 0; i <= maxIndex; i++) {
            maxIndex = Math.max(maxIndex, nums[i] + i);
            if (maxIndex >= nums.length - 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 53. 最大子数组和
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        int maxSub = nums[0];
        int sub = maxSub;
        int left = 0;
        int right = 1;
        while (right < nums.length) {
            int tempMaxSub = sub + nums[right];
            if (tempMaxSub < nums[right]) {
                sub = nums[right];
                left = right;
                right = left + 1;
            } else {
                sub = tempMaxSub;
                right++;
            }
            maxSub = Math.max(maxSub, tempMaxSub);
        }
        return maxSub;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{5,4,-1,7,8};
        System.out.println(new Test_20240827().maxSubArray(nums));
    }

}

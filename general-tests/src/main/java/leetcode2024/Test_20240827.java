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

}

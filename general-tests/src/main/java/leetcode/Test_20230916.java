package leetcode;

import java.util.Arrays;

public class Test_20230916 {

    /**
     * 334. 递增的三元子序列
     *
     * @param nums
     * @return
     */
    public boolean increasingTriplet(int[] nums) {
        if (nums == null || nums.length < 3) {
            return false;
        }
        int[] dp = new int[nums.length];
        dp[0] = 1;
        dp[1] = nums[1] > nums[0] ? 2 : 1;
        for (int i = 2; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                dp[i] = dp[i - 1];
                continue;
            }
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                    if (dp[i] >= 3) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean increasingTriplet1(int[] nums) {
        if (nums == null || nums.length < 3) {
            return false;
        }
        int[] seq = new int[nums.length];
        int index = 0;
        seq[index] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > seq[index]) {
                seq[++index] = nums[i];
                if (index >= 2) {
                    return true;
                }
            } else {
                int low = 0;
                int high = index;
                while (low <= high) {
                    int mid = (low + high) / 2;
                    int midVal = seq[mid];
                    if (midVal == nums[i]) {
                        break;
                    } else if (midVal > nums[i]) {
                        if (mid == 0 || seq[mid - 1] < nums[i]) {
                            seq[mid] = nums[i];
                            break;
                        } else {
                            high = mid - 1;
                        }
                    } else {
                        low = mid + 1;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 392. 判断子序列
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isSubsequence(String s, String t) {
        if (s == null || t == null) {
            return false;
        }
        if (s.length() == 0) {
            return true;
        }
        for (int i = 0; i < t.length(); i++) {
            if (s.charAt(0) == t.charAt(i)) {
                if (dfs(s, t, 0, i)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean dfs(String s, String t, int sIndex, int tIndex) {
        if (sIndex >= s.length() || tIndex >= t.length()) {
            return false;
        }
        if (s.charAt(sIndex) == t.charAt(tIndex)) {
            if (sIndex == s.length() - 1) {
                return true;
            } else {
                return dfs(s, t, sIndex + 1, tIndex + 1);
            }
        }
        return dfs(s, t, sIndex + 1, tIndex) || dfs(s, t, sIndex, tIndex + 1);
    }

    /**
     * 1679. K 和数对的最大数目
     *
     * @param nums
     * @param k
     * @return
     */
    public int maxOperations(int[] nums, int k) {
        Arrays.sort(nums);
        int max = 0;
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int sum = nums[left] + nums[right];
            if (sum == k) {
                max++;
                left++;
                right++;
            } else if (sum > k) {
                right--;
            } else {
                left++;
            }
        }
        return max;
    }

    /**
     * 1732. 找到最高海拔
     *
     * @param gain
     * @return
     */
    public int largestAltitude(int[] gain) {
        int sum = 0;
        int max = 0;
        for (int i = 0; i < gain.length; i++) {
            sum += gain[i];
            max = Math.max(max, sum);
        }
        return max;
    }

}

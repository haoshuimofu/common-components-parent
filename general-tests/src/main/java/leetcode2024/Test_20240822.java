package leetcode2024;

public class Test_20240822 {

    public int minSubArrayLen(int target, int[] nums) {
        if (nums.length == 1) {
            return nums[0] >= target ? 1 : 0;
        }
        int sum = nums[0];
        if (sum >= target) {
            return 1;
        }
        int minLen = Integer.MAX_VALUE;
        int left = 0;
        int right = 1;
        while (left < right && right < nums.length) {
            if (nums[right] >= target) {
                return 1;
            }
            sum += nums[right];
            if (sum >= target) {
                minLen = Math.min(minLen, right - left + 1);
                sum -= nums[right];
                sum -= nums[left];
                left++;
            } else {
                right++;
            }
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

}

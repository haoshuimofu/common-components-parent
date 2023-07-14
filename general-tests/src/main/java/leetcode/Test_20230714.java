package leetcode;

public class Test_20230714 {

    /**
     * 209. 长度最小的子数组
     *
     * @param target
     * @param nums
     * @return
     */
    public int minSubArrayLen(int target, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int sum = 0;
        int low = 0;
        int high = 0;
        int min = 0;
        while (high < nums.length) {
            if (nums[high] >= target) {
                min = 1;
                break;
            }
            sum += nums[high];
            if (sum >= target) {
                if (min == 0) {
                    min = high - low + 1;
                } else {
                    min = Math.min(min, high - low + 1);
                }
                sum -= (nums[low] + nums[high]);
                low++;
            } else {
                high++;
            }
        }
        return min;
    }

}

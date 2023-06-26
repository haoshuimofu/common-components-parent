package leetcode;

public class Test_20230626_2 {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4, 5};
        System.out.println(minSubArrayLen(12, nums));

    }

    public static int minSubArrayLen(int target, int[] nums) {
        int slow = 0;
        int fast = 0;
        int sum = 0;
        int min = 0;
        while (fast <= nums.length - 1) {
            sum += nums[fast];
            if (sum == target) {
                if (min == 0) {
                    min = fast - slow + 1;
                } else {
                    min = Math.min(min, fast - slow + 1);
                }
                sum -= nums[slow];
                slow++;
                fast++;
            } else if (sum > target) {
                sum -= (nums[slow] + nums[fast]);
                slow++;
            } else {
                fast++;
            }
        }
        return min;
    }
}

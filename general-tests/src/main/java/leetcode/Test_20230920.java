package leetcode;

import java.util.Arrays;

/**
 * @author dewu.de
 * @date 2023-09-20 1:15 下午
 */
public class Test_20230920 {

    public static void main(String[] args) {
        Test_20230920 test = new Test_20230920();

        int[] nums = new int[]{1, 3, 2};
        System.out.println(Arrays.toString(nums));

        test.nextPermutation(nums);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 31. 下一个排列
     *
     * @param nums
     */
    public void nextPermutation(int[] nums) {
        int right = nums.length - 1;
        boolean ok = false;
        while (right >= 1 && !ok) {
            if (nums[right] <= nums[right - 1]) {
                right--;
                continue;
            }
            int left = right - 1;
            while (left >= 0) {
                if (nums[left] < nums[right]) {
                    int temp = nums[left];
                    nums[left] = nums[right];
                    nums[right] = temp;
                    Arrays.sort(nums, left + 1, nums.length);
                    ok = true;
                    break;
                }
                left--;
            }
            right--;
        }
        if (!ok) {
            Arrays.sort(nums);
        }
    }
}

package leetcode;

import java.util.Arrays;

/**
 * @author dewu.de
 * @date 2023-06-26 1:40 下午
 */
public class Test_20230626 {

    public static void main(String[] args) {
        int[] nums = new int[]{0, 1, 2, 2, 3, 0, 4, 2};
        System.out.println(removeElement(nums, 2));
        int[] a = new int[]{1, 2, 3, 4, 4, 4, 4, 4, 5, 6};
        System.out.println(Arrays.binarySearch(a, 4));

    }

    public static int arrayPairSum(int[] nums) {
        Arrays.sort(nums);
        int res = 0;
        for (int i = 0; i < nums.length; ) {
            res += nums[i];
            res += 2;
        }
        return res;
    }

    public int[] twoSum(int[] numbers, int target) {
        int low = 0;
        int high = numbers.length - 1;
        while (low < high) {
            int temp = numbers[low] + numbers[high];
            if (temp == target) {
                return new int[]{low, high};
            } else if (temp < target) {
                low++;
            } else {
                high--;
            }
        }
        return null;
    }


    public static int removeElement(int[] nums, int val) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == val) {
                int end = i + 1;
                while (end < nums.length && nums[end] == val) {
                    end++;
                }
                end = nums.length - 1;
                if (end == nums.length - 1) {
                    return i;
                }
                int count = end - i + 1;
                if (count > 0) {
                    for (int j = 0; j < nums.length - count; j++) {
                        nums[i + j] = nums[i + j + count];
                    }
                }
                return nums.length - count;
            }
        }
        return nums.length;
    }


}

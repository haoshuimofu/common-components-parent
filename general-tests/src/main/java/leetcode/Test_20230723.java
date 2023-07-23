package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Test_20230723 {

    public static void main(String[] args) {
        System.out.println((int) 'a');
        System.out.println((int) 'z');
        System.out.println(firstUniqChar("loveleetcode"));

        System.out.println("//////////////////");

        int[] nums = new int[]{5, -3, 5};
        System.out.println(maxSubarraySumCircular(nums));
        nums = new int[]{1, -2, 3, -2};
        System.out.println(maxSubarraySumCircular(nums));
    }


    /**
     * 387. 字符串中的第一个唯一字符
     *
     * @param s
     * @return
     */
    public static int firstUniqChar(String s) {
        int[] charCount = new int[122];
        for (int i = 0; i < s.length(); i++) {
            charCount[(int) s.charAt(i)] += 1;
        }
        for (int i = 0; i < s.length(); i++) {
            if (charCount[(int) s.charAt(i)] == 1) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 918. 环形子数组的最大和
     *
     * @param nums
     * @return
     */
    public static int maxSubarraySumCircular(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int len = nums.length;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < len; i++) {
            int sum = Integer.MIN_VALUE;
            for (int j = i; j < len + i; j++) {
                if (sum == Integer.MIN_VALUE || sum + nums[j % len] < nums[j % len]) {
                    sum = nums[j % len];
                } else {
                    sum += nums[j % len];
                }
                max = Math.max(max, sum);
            }
        }
        return max;
    }

    /**
     * 349. 两个数组的交集
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersection(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        List<Integer> res = new ArrayList<>();
        int[] tempResult = new int[Math.max(nums1.length, nums2.length)];
        int count = 0;
        for (int i = 0; i < nums2.length; i++) {
            if (i == 0 || nums2[i - 1] != nums2[i]) {
                if (Arrays.binarySearch(nums1, nums2[i]) >= 0) {
                    tempResult[count] = nums2[i];
                    count++;
                }
            }
        }
        return Arrays.copyOf(tempResult, count);
    }

}

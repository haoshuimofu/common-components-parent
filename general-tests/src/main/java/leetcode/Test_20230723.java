package leetcode;

import java.util.Arrays;

public class Test_20230723 {

    public static void main(String[] args) {
        System.out.println((int) 'a');
        System.out.println((int) 'z');
        System.out.println(firstUniqChar("loveleetcode"));

        System.out.println("//////////////////");

        int[] nums = new int[]{5, -3, 5};
        nums = new int[]{2, -2, 2, 7, 8, 0};
        System.out.println(maxSubarraySumCircular(nums));
        System.out.println(maxSubarraySumCircular1(nums));
        System.out.println();
        System.out.println(maxSubarraySumCircular2(nums));
//        nums = new int[]{1, -2, 3, -2};
//        System.out.println(maxSubarraySumCircular(nums));
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
     * 暴力解法-超时
     *
     * @param nums
     * @return
     */
    public static int maxSubarraySumCircular(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // 第一轮求最大和, 其实就是另一题【53. 最大子数组和】
        int max = nums[0];
        int sum = max;
        int allSum = sum;
        for (int i = 1; i < nums.length; i++) {
            if (sum < 0) {
                sum = nums[i];
            } else {
                sum += nums[i];
            }
            max = Math.max(max, sum);
            allSum += nums[i];
        }
        // 第一轮已经把非循环状态下最大值求出来了
        for (int i = 1; i < nums.length; i++) {
            allSum -= nums[i - 1];
            sum = allSum;
            for (int j = nums.length; j < nums.length + i; j++) {
                if (sum < 0) {
                    sum = nums[j % nums.length];
                } else {
                    sum += nums[j % nums.length];
                }
                max = Math.max(max, sum);
            }
        }
        return max;
    }

    public static int maxSubarraySumCircular2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int max = nums[0];
        int min = max;
        int totalSum = max;
        int tempMax = max;
        int tempMin = min;
        for (int i = 1; i < nums.length; i++) {
            totalSum += nums[i];
            tempMax = Math.max(tempMax + nums[i], nums[i]);
            tempMin = Math.min(tempMin + nums[i], nums[i]);

            max = Math.max(max, tempMax);
            min = Math.min(min, tempMin);
        }
        if (min == totalSum) {
            return max;
        }
        return Math.max(max, totalSum - min);
    }

    //=====================================================

    public static int maxSubarraySumCircular1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int len = 2 * nums.length - 1;
        int max = nums[0];
        int sum = max;
        int startIndex = 0;
        int toIndex = 1;
        while (startIndex < nums.length && toIndex < len) {
            if (sum + nums[toIndex % nums.length] < nums[toIndex % nums.length]) {
                sum = nums[toIndex % nums.length];
                startIndex = toIndex;
            } else {
                sum += nums[toIndex % nums.length];
            }
            max = Math.max(max, sum);
            if (toIndex - startIndex == nums.length - 1 && startIndex < nums.length - 1) {
                startIndex++;
                sum = nums[startIndex];
                toIndex = startIndex + 1;
            } else {
                toIndex++;
            }
        }
        return max;
    }

    // ==================================================================

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

package leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author dewu.de
 * @date 2023-08-28 9:57 下午
 */
public class Test_20230828 {

    /**
     * 128. 最长连续序列
     *
     * @param nums
     * @return
     */
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        int max = 0;
        for (Integer num : set) {
            if (!set.contains(num - 1)) {
                int len = 1;
                int currNum = num;
                while (set.contains(currNum + 1)) {
                    len++;
                    currNum++;
                }
                max = Math.max(max, len);
            }
        }
        return max;
    }

    public int longestConsecutive1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Arrays.sort(nums);
        int max = 1;
        int from = 0;
        int repeat = 0;
        for (int i = 1; i < nums.length; i++) {
            int value = nums[i] - nums[i - 1];
            if (value == 0) {
                repeat++;
            } else if (value != 1) {
                max = Math.max(max, i - from - repeat);
                from = i;
                repeat = 0;
            }
            if (i == nums.length - 1) {
                max = Math.max(max, i - from + 1 - repeat);
            }
        }
        return max;
    }


    /**
     * 剑指 Offer 59 - I. 滑动窗口的最大值
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (k == 1) {
            return nums;
        }
        int[] result = new int[nums.length - k + 1];
        int index = 0;
        int from = 0;
        int max = Integer.MIN_VALUE;
        int maxCount = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i - from + 1 < k) {
                if (nums[i] > max) {
                    maxCount = 1;
                    max = nums[i];
                } else if (nums[i] == max) {
                    maxCount++;
                }
            } else if (i - from + 1 == k) {
                if (nums[i] > max) {
                    result[index] = nums[i];
                    index++;

                    max = nums[i];
                    maxCount = 1;
                    from++;
                } else {
                    if (nums[i] == max) {
                        maxCount++;
                    } else {
                        max = Math.max(max, nums[i]);
                    }
                    result[index] = max;
                    index++;

                    // 向右滑动一位
                    if (nums[from] < max) {
                        from++;
                    } else if (nums[from] == max) {
                        from++;
                        // 重新找最大值
                        if (maxCount == 1) {
                            max = Integer.MIN_VALUE;
                            for (int j = from; j <= i; j++) {
                                if (nums[j] > max) {
                                    maxCount = 1;
                                    max = nums[j];
                                } else if (nums[j] == max) {
                                    maxCount++;
                                }
                            }
                        } else {
                            maxCount--;
                        }
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Test_20230828 test = new Test_20230828();
        System.out.println(test.longestConsecutive1(new int[]{1, 2, 0, 1}));
        System.out.println(Arrays.toString(test.maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3)));
    }

}

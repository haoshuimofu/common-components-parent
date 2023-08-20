package leetcode;

import com.alibaba.fastjson.JSON;

import java.util.*;

public class Test_20230820 {

    public static void main(String[] args) {
        Test_20230820 test = new Test_20230820();
        System.out.println(JSON.toJSONString(test.findContinuousSequence(9)));
//        System.out.println(test.reverseWords("  hello world  "));
        System.out.println(test.reverseWords(" "));

    }

    /**
     * 剑指 Offer 39. 数组中出现次数超过一半的数字
     *
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        int k = nums.length / 2;
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int num : nums) {
            int count = countMap.getOrDefault(num, 0);
            if (count >= k) {
                return num;
            } else {
                countMap.put(num, count + 1);
            }
        }
        return -1;
    }

    /**
     * 剑指 Offer 57. 和为s的两个数字
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return null;
        }
        int low = 0;
        int high = nums.length - 1;
        while (low < high) {
            if (nums[low] > target) {
                break;
            } else if (nums[low] == target) {
                if (nums[high] == 0) {
                    return new int[]{nums[low], nums[high]};
                } else {
                    break;
                }
            } else {
                // nums[low] < target
                int sum = nums[low] + nums[high];
                if (sum == target) {
                    return new int[]{nums[low], nums[high]};
                } else if (sum < target) {
                    low++;
                } else {
                    high--;
                }
            }
        }
        return null;
    }

    /**
     * 剑指 Offer 57 - II. 和为s的连续正数序列
     *
     * @param target
     * @return
     */
    public int[][] findContinuousSequence(int target) {
        int k = target / 2 + (target % 2 == 0 ? 0 : 1);
        int[] nums = new int[k]; // [1-k]
        for (int i = 0; i < k; i++) {
            nums[i] = i + 1;
        }
        List<int[]> res = new ArrayList<>();
        int maxLen = 0;
        // 求连续正整数数列
        int sum = nums[0];
        int from = 0;
        int index = 1;
        while (index < nums.length) {
            if (nums[index] >= target) {
                break;
            }
            sum += nums[index];
            if (sum == target) {
                res.add(Arrays.copyOfRange(nums, from, index + 1));
                maxLen = Math.max(maxLen, index - from + 1);
                sum -= nums[from];
                from++;
                index++;
            } else if (sum < target) {
                index++;
            } else {
                // sum > target
                sum -= nums[from];
                sum -= nums[index];
                from++;
            }
        }
        int[][] result = new int[res.size()][maxLen];
        for (int i = 0; i < res.size(); i++) {
            result[i] = res.get(i);
        }
        return result;
    }

    public int[][] findContinuousSequence1(int target) {
        int k = target / 2 + (target % 2 == 0 ? 0 : 1);
        List<int[]> res = new ArrayList<>();
        int maxLen = 0;
        // 求连续正整数数列
        int sum = 1;
        int left = 1;
        int right = 2;
        while (right <= k) {
            sum += right;
            if (sum == target) {
                int[] arr = new int[right - left + 1];
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = left + i;
                }
                res.add(arr);
                maxLen = Math.max(maxLen, arr.length);
                sum -= left;
                left++;
                right++;
            } else if (sum < target) {
                right++;
            } else {
                // sum > target
                sum -= left;
                sum -= right;
                left++;
            }
        }
        int[][] result = new int[res.size()][maxLen];
        for (int i = 0; i < res.size(); i++) {
            result[i] = res.get(i);
        }
        return result;
    }

    /**
     * 剑指 Offer 58 - I. 翻转单词顺序
     *
     * <p>
     * s = "the sky is blue"
     * 输出："blue is sky the"
     * </p>
     *
     * @param s
     * @return
     */
    public String reverseWords(String s) {
        if (s == null || s.length() < 1) {
            return s;
        }
        char[] chs = new char[s.length()];
        int index = 0;
        int right = s.length() - 1;
        int left = right;
        while (left >= 0) {
            int ch = s.charAt(left);
            if (ch == ' ') {
                if (s.charAt(right) == ' ') {
                    left--;
                } else {
                    for (int i = left + 1; i <= right; i++) {
                        chs[index] = s.charAt(i);
                        index++;
                    }
                    right = left;
                    left--;
                }
            } else {
                if (s.charAt(right) != ' ') {
                    left--;
                } else {
                    // left + 1 ---> right都是空格
                    if (right != s.length() - 1) {
                        chs[index] = ' ';
                        index++;
                    }
                    right = left;
                    left--;
                }

            }
        }
        if (left == -1) {
            if (s.charAt(right) != ' ') {
                for (int i = left + 1; i <= right; i++) {
                    chs[index] = s.charAt(i);
                    index++;
                }
            }
        }
        return new String(chs, 0, index);
    }

}

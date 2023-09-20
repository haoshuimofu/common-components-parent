package leetcode;

import java.util.*;

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


        System.out.println("///////////");
        System.out.println(test.nextGreaterElement(12));

        System.out.println("///////////");
        System.out.println(test.isIsomorphic("foo", "bar"));
    }

    /**
     * 31. 下一个排列
     *
     * @param nums
     */
    public void nextPermutation(int[] nums) {
        int right = nums.length - 1;
        int sourceIndex = -1;
        int destIndex = -1;
        while (right >= 1) {
            int left = right - 1;
            while (left > destIndex) {
                if (nums[left] < nums[right]) {
                    if (destIndex == -1) {
                        // 初始化第一个交换的位置
                        destIndex = left;
                        sourceIndex = right;
                    } else {
                        // 如果有交换的数字在第一个右侧，或者在同一位置但比上一个数字要小
                        destIndex = left;
                        sourceIndex = right;
                    }
                    break;
                }
                left--;
            }
            right--;
        }
        if (destIndex != -1) {
            int temp = nums[sourceIndex];
            nums[sourceIndex] = nums[destIndex];
            nums[destIndex] = temp;
            Arrays.sort(nums, destIndex + 1, nums.length);
        } else {
            Arrays.sort(nums);
        }
    }

    /**
     * 556. 下一个更大元素 III
     *
     * @param n
     * @return
     */
    public int nextGreaterElement(int n) {
        Deque<Integer> deque = new ArrayDeque<>();
        int num = n;
        while (num > 0) {
            deque.addFirst(num % 10);
            num /= 10;
        }
        int[] nums = new int[deque.size()];
        int index = 0;
        while (!deque.isEmpty()) {
            nums[index++] = deque.pollFirst();
        }
        System.out.println(Arrays.toString(nums));
        nextPermutation(nums);
        int factor = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            num += nums[i] * factor;
            factor *= 10;
        }
        return n >= num ? -1 : num;
    }

    /**
     * 205. 同构字符串
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        Map<Character, Integer> smap = new HashMap<>();
        Map<Character, Integer> tmap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char ch1 = s.charAt(i);
            int sindex = smap.getOrDefault(ch1, -1);
            char ch2 = t.charAt(i);
            int tindex = tmap.getOrDefault(ch2, -1);
            if (sindex != tindex) {
                return false;
            }
            smap.put(ch1, i);
            tmap.put(ch2, i);
        }
        return true;
    }
}

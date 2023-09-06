package leetcode;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dewu.de
 * @date 2023-09-06 10:05 上午
 */
public class Test_20230906 {


    public static void main(String[] args) {
        Test_20230906 test = new Test_20230906();
        System.out.println(Arrays.toString(test.action(new int[]{1, 7, 3}, new int[]{1, 0, 3, 9, 5, 7}))); // 3,-1,9

    }

    public int[] action(int[] nums1, int[] nums2) {
        Map<Integer, Integer> nextMap = new HashMap<>();
        Deque<Integer> stack = new ArrayDeque<>();
        for (int num : nums2) {
            while (!stack.isEmpty() && num > stack.peek()) {
                nextMap.put(stack.pop(), num);
            }
            stack.push(num);
        }
        int[] next = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            next[i] = nextMap.getOrDefault(nums1[i], -1);
        }
        return next;
    }

}

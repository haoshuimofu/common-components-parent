package leetcode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author dewu.de
 * @date 2023-08-01 11:04 下午
 */
public class Test_20230801 {

    public static void main(String[] args) {

        int[] nums = new int[]{2, 1, 4};
        System.out.println(sumOfPower(nums));

    }

    /**
     * 2681. 英雄的力量
     *
     * @param nums
     * @return
     */
    public static int sumOfPower(int[] nums) {
        Arrays.sort(nums);
        long sum = 0;
        long factor = (long) Math.pow(10, 9) + 7;
        for (int i = nums.length - 1; i >= 0; i--) {
            long base = (long) Math.pow(nums[i], 2);
            for (int j = i; j >= 0; j--) {
                System.out.print(j + " ");

                sum += base * nums[j];
            }
            System.out.println();
        }

        PriorityQueue<Integer> min = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        });
        PriorityQueue<Integer> max = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o2, o1);
            }
        });
        return (int) (sum % factor);
    }

    public void doSumOfPower(int[] nums, int currIndex, PriorityQueue<Integer> min, PriorityQueue<Integer> max, int[] sum, int mod) {
        if (currIndex >= nums.length) {
            return;
        }
        min.add(nums[currIndex]);
        max.add(nums[currIndex]);

        int maxValue = max.poll();
        int minValue = min.poll();
        int currSum = maxValue * maxValue * minValue;
        sum[0] += currSum % mod;

        for (int i = currIndex + 1; i < nums.length; i++) {
            doSumOfPower(nums, i, min, max, sum, mod);
        }


    }
}

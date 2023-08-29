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

        Test_20230801 test = new Test_20230801();
        int[] nums = new int[]{2, 1, 4};
        System.out.println(test.sumOfPower(nums));

    }

    /**
     * 2681. 英雄的力量
     *
     * @param nums
     * @return
     */
    public int sumOfPower(int[] nums) {
        int mod = (int) Math.pow(10, 9) + 7;
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
        int[] sum = new int[1];
        for (int i = 0; i < nums.length; i++) {
            doSumOfPower(nums, i, min, max, sum, mod);
        }
        return (int) (sum[0] % mod);
    }

    public void doSumOfPower(int[] nums, int currIndex, PriorityQueue<Integer> min, PriorityQueue<Integer> max, int[] sum, int mod) {
        if (currIndex >= nums.length) {
            return;
        }
        Integer value = Integer.valueOf(nums[currIndex]);
        min.add(value);
        max.add(value);

        int maxValue = max.peek();
        int minValue = min.peek();
        long currSum = maxValue * maxValue * minValue;
        sum[0] += currSum % mod;

        for (int i = currIndex + 1; i < nums.length; i++) {
            doSumOfPower(nums, i, min, max, sum, mod);
        }
        // 回溯
        min.remove(value);
        max.remove(value);
    }


    public int sumOfPower1(int[] nums) {
        Arrays.sort(nums);
        long sum = 0;
        int sumFactor = 0;
        for (int num : nums) {
            sum += num;
        }
        for (int i = nums.length - 1; i >= 0; i--) {
            int value = nums[i];


        }
        return 0;


    }
}

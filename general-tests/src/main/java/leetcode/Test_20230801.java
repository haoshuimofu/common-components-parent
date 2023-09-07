package leetcode;

import java.util.*;

/**
 * @author dewu.de
 * @date 2023-08-01 11:04 下午
 */
public class Test_20230801 {

    public static void main(String[] args) {

        Test_20230801 test = new Test_20230801();
        long startMillis = System.currentTimeMillis();
        int[] nums = new int[10];
        for (int i = 0; i < 10; i++) {
            nums[i] = i + 1;
        }
        test.sumOfPower(nums);
        System.out.println("10 - DFS+回溯耗时: " + (System.currentTimeMillis() - startMillis));

        startMillis = System.currentTimeMillis();
        nums = new int[20];
        for (int i = 0; i < 20; i++) {
            nums[i] = i + 1;
        }
        test.sumOfPower(nums);
        System.out.println("20 - DFS+回溯耗时: " + (System.currentTimeMillis() - startMillis));

        startMillis = System.currentTimeMillis();
        nums = new int[40];
        for (int i = 0; i < 40; i++) {
            nums[i] = i + 1;
        }
        test.sumOfPower(nums);
        System.out.println("40 - DFS+回溯耗时: " + (System.currentTimeMillis() - startMillis));

    }

    /**
     * 2681. 英雄的力量
     *
     * @param nums
     * @return
     */
    public int sumOfPower(int[] nums) {
        int mod = (int) Math.pow(10, 9) + 7;
        Arrays.sort(nums);
        List<Integer> path = new ArrayList<>();
        int[] sum = new int[1];
        for (int i = 0; i < nums.length; i++) {
            doSumOfPower(nums, i, path, sum, mod);
        }
        return sum[0] % mod;
    }

    public void doSumOfPower(int[] nums, int currIndex, List<Integer> path, int[] sum, int mod) {
        if (currIndex >= nums.length) {
            return;
        }
        int value = nums[currIndex];
        path.add(value);
        int min = path.get(0);
        int max = path.get(path.size() - 1);
        int currSum = max * max * min;
        sum[0] += currSum % mod;

        for (int i = currIndex + 1; i < nums.length; i++) {
            doSumOfPower(nums, i, path, sum, mod);
        }
        path.remove(path.size() - 1);
    }

}

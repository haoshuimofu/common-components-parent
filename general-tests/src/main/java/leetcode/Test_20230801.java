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
        nums = new int[30];
        for (int i = 0; i < 30; i++) {
            nums[i] = i + 1;
        }
        test.sumOfPower(nums);
        System.out.println("30 - DFS+回溯耗时: " + (System.currentTimeMillis() - startMillis));

    }

    /**
     * 2681. 英雄的力量
     * <p>
     * 用DFS+回溯, 时间复杂度真是丧心病狂, 当搜索深度和分支因子较大时, 耗时成指数倍上升
     * 10 - DFS+回溯耗时: 2
     * 20 - DFS+回溯耗时: 22
     * 40 - DFS+回溯耗时: 7779590
     * </p>
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

package leetcode;

import java.util.Arrays;

/**
 * @author dewu.de
 * @date 2023-08-01 11:04 下午
 */
public class Test_20230801 {

    public static void main(String[] args) {

        int[] nums = new int[]{2,1,4};
        System.out.println(sumOfPower(nums));

    }

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
        return (int) (sum % factor);
    }
}

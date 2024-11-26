package study.algorithm.sort;

import com.alibaba.fastjson.JSON;

/**
 * 选择排序
 *
 * @author dewu.de
 * @date 2023-08-21 11:39 上午
 */
public class SelectSort {

    public static void main(String[] args) {

        int[] nums = {5, 9, 3, 1, 7, 6, 8, 2, 4};
        sort1(nums);
        System.out.println(JSON.toJSONString(nums));

    }

    public static void sort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        // 思路：双重循环
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] > nums[j]) {
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
            }
        }
    }

    public static void sort1(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        int index = nums.length - 1;
        while (index > 0) {
            for (int i = 1; i <= index; i++) {
                if (nums[0] < nums[i]) {
                    int temp = nums[i];
                    nums[i] = nums[0];
                    nums[0] = temp;
                }
            }
            int temp = nums[0];
            nums[0] = nums[index];
            nums[index] = temp;
            index--;
        }
    }
}

package study.algorithm.sort;

import com.alibaba.fastjson.JSON;

/**
 * 选择排序
 *
 * @author dewu.de
 * @date 2023-08-21 11:43 上午
 */
public class BubbleSort {

    public static void main(String[] args) {

        int[] nums = {5, 9, 3, 1, 7, 6, 8, 2, 4};
        sort(nums);
        System.out.println(JSON.toJSONString(nums));

    }

    public static void sort(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = 0; j < nums.length - 1 - i; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }
    }
}

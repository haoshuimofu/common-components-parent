package study.algorithm.sort;

import com.alibaba.fastjson.JSON;

/**
 * 冒泡排序
 *
 * @author dewu.de
 * @date 2023-08-21 11:39 上午
 */
public class SelectSort {

    public static void main(String[] args) {

        int[] nums = {5, 9, 3, 1, 7, 6, 8, 2, 4};
        sort(nums);
        System.out.println(JSON.toJSONString(nums));

    }

    public static void sort(int[] nums) {
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
}

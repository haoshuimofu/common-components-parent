package study.algorithm.sort;

import com.alibaba.fastjson.JSON;

/**
 * 冒泡排序
 * 思路: 双重循环，相邻比较大小，满足条件则交换位置
 *
 * @author dewu.de
 * @date 2023-08-21 11:43 上午
 */
public class BubbleSort {

    public static void main(String[] args) {

        int[] nums = {5, 9, 3, 1, 7, 6, 8, 2, 4};
        sort1(nums);
        System.out.println(JSON.toJSONString(nums));

    }

    public static void sort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        // 思路：双重循环，相邻两个元素比较交换
        for (int i = 0; i < nums.length - 1; i++) {
            // 内循环，相邻元素交换，大的交换到后面
            // 循环结束，内循环区间最大值就被交换到了区间尾部
            for (int j = 0; j < nums.length - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }
    }


    public static void sort1(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
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

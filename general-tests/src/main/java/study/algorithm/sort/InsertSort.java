package study.algorithm.sort;

import com.alibaba.fastjson.JSON;

/**
 * @author dewu.de
 * @date 2023-08-21 12:24 下午
 */
public class InsertSort {

    public static void main(String[] args) {

        int[] nums = {5, 9, 3, 1, 7, 6, 8, 2, 4};
        sort(nums);
        System.out.println(JSON.toJSONString(nums));

    }

    public static void sort(int[] nums) {
        if (nums == null || nums.length < 2)
            return;
        for (int i = 1; i < nums.length; i++) {
            int curr = nums[i];
            int left = i - 1;
            while (left >= 0 && nums[left] > curr) {
                nums[left + 1] = nums[left];
                left--;
            }
            nums[++left] = curr;
        }
    }
}

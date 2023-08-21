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
        for (int i = 1; i < nums.length; i++) {
            // 可以理解为[0, i-1]已经排序好了
            // 如果 num[i] >= num[i-1], 那么自然[0, i]也是排序好的
            // 如果 num[i]  < num[i-1], 把num[i]插入到[0, i-1]之中
            int currValue = nums[i];
            if (currValue < nums[i - 1]) {
                // 把[0, i-1]之中的 > num[i]的都往后移一位
                int index = i - 1;
                while (index >= 0 && nums[index] > currValue) {
                    int temp = nums[index + 1];
                    nums[index + 1] = nums[index];
                    nums[index] = temp;
                    index--;
                }
                // 最后把num[i]插入
                nums[++index] = currValue;
            }
        }
    }
}

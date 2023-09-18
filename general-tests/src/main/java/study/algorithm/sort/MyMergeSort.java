package study.algorithm.sort;

import java.util.Arrays;

/**
 * @author dewu.de
 * @date 2023-09-18 8:13 下午
 */
public class MyMergeSort {


    public static void main(String[] args) {
        int[] nums = {5, 9, 3, 1, 7, 6, 8, 2, 4};
        sort(nums);
        System.out.println(Arrays.toString(nums));
    }


    public static void sort(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }
        int[] temp = new int[nums.length];
        partialSort(nums, 0, nums.length - 1, temp);
    }

    public static void partialSort(int[] nums, int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) / 2;
            partialSort(nums, left, mid, temp);
            partialSort(nums, mid + 1, right, temp);
            merge(nums, left, right, mid, temp);
        }
    }

    public static void merge(int[] nums, int left, int right, int mid, int[] temp) {
        int l = left;
        int r = mid + 1;
        int index = 0;
        while (l <= mid && r <= right) {
            if (nums[l] >= nums[r]) {
                temp[index++] = nums[r++];
            } else {
                temp[index++] = nums[l++];
            }
        }
        while (l <= mid) {
            temp[index++] = nums[l++];
        }
        while (r <= left) {
            temp[index++] = nums[r++];
        }
        System.arraycopy(temp, 0, nums, left, index);
    }

}

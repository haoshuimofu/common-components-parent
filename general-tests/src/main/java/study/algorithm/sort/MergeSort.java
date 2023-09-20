package study.algorithm.sort;

import java.util.Arrays;

/**
 * <p> nums = [5, 9, 3, 1, 7, 6, 8, 2, 4], length = 9, mid = 9/2 =4 </p>
 * <p>[5, 9, 3, 1, 4] -4- [6, 8, 2, 4] </p>
 * <p>[5, 9, 3] -2- [1, 4] -4- [6, 8] -6- [2, 4]</p>
 * <p>[5 9] -1- [3] -2- [1] -3- [4] -4- [6] -5- [8] -6- [2] -7- [4]</p>
 * 归并排序
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] nums = {5, 9, 3, 1, 7, 6, 8, 2, 4};
        sort(nums);
        System.out.println(Arrays.toString(nums));
    }

    public static void sort(int[] nums) {
        mergeSort(nums, 0, nums.length - 1, new int[nums.length]);
    }

    private static void mergeSort(int[] nums, int from, int to, int[] temp) {
        // from < to, 限定[from, to]至少包括2个元素, 1个元素可以任务默认就是有序的
        if (from < to) {
            // 分析一下 from + 1 = to, 这是 from = mid, to = mid + 1
            int mid = (from + to) / 2;
            mergeSort(nums, from, mid, temp);
            mergeSort(nums, mid + 1, to, temp);
            merge(nums, from, mid, to, temp);
        }
    }

    private static void merge(int[] nums, int from, int mid, int to, int[] temp) {
        int i = from;
        int j = mid + 1;
        int index = 0;
        while (i <= mid && j <= to) {
            if (nums[i] > nums[j]) {
                temp[index++] = nums[j++];
            } else {
                temp[index++] = nums[i++];
            }
        }
        while (i <= mid) {
            temp[index++] = nums[i++];
        }
        while (j <= to) {
            temp[index++] = nums[j++];
        }
        System.arraycopy(temp, 0, nums, from, index);
    }
}

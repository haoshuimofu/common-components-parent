package study.algorithm.sort;

/**
 * 快排
 * 参考：https://blog.csdn.net/qq_43471489/article/details/125582437
 *
 * @author dewu.de
 * @date 2023-08-21 2:08 下午
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {5, 9, 3, 1, 7, 6, 8, 2, 4};
        quickSort(arr, 0, arr.length - 1);

        System.out.println("排序结果：");
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }

    public static void quickSort(int[] nums, int from, int to) {
        if (from < to) {
            int pivotIndex = partition(nums, from, to);
            quickSort(nums, from, pivotIndex - 1);
            quickSort(nums, pivotIndex + 1, to);
        }
    }

    private static int partition(int[] nums, int from, int to) {
        // 基准值, 单独记录, 所以from位置可以被重新赋值
        int pivotValue = nums[from];
        int left = from;
        int right = to;
        while (left < right) {
            // right向左, 找到第一个比基准值小的元素
            while (left < right && nums[right] >= pivotValue) {
                right--;
            }
            // 前面的while跳出时, 要么left == right, 要么num[right] < 基准值
            nums[left] = nums[right];

            // left向右, 找到第一个比基准值大的元素
            while (left < right && nums[left] <= pivotValue) {
                left++;
            }
            nums[right] = nums[left];
        }
        nums[left] = pivotValue;
        return left;
    }
}

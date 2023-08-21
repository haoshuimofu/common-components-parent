package study.algorithm.sort;

/**
 * @author dewu.de
 * @date 2023-08-21 11:29 上午
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

    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);

            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    public static int partition(int[] arr, int low, int high) {
        int pivotValue = arr[high];
        int i = low - 1;
        System.out.println("i="+i);

        for (int j = low; j < high; j++) {
            if (arr[j] < pivotValue) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}


package study.algorithm;

import com.alibaba.fastjson.JSON;

/**
 * @author dewu.de
 * @date 2023-08-21 10:44 上午
 */
public class QuickSort {
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivot = partition(arr, low, high);
            System.out.println(JSON.toJSONString(arr));
            quickSort(arr, low, pivot - 1);
            quickSort(arr, pivot + 1, high);
        }
    }

    public static int partition(int[] arr, int low, int high) {
        int pivot = arr[low];  // 选择第一个元素作为基准
        int i = low;
        int j = high;

        while (i < j) {
            while (i < j && arr[j] >= pivot) {
                j--;
            }
            // 找到第一个arr[j] < pivot的元素
            arr[i] = arr[j];
            System.out.println(JSON.toJSONString(arr));

            while (i < j && arr[i] <= pivot) {
                i++;
            }
            // 找到第一个arr[i] > pivot的元素
            arr[j] = arr[i];
            System.out.println(JSON.toJSONString(arr));
            System.out.println("");
        }

        arr[i] = pivot;
        System.out.println(JSON.toJSONString(arr));
        return i;
    }

    public static void main(String[] args) {
        int[] arr = {5, 3, 8, 4, 2, 7, 1, 6};
        //  i                    j
        // {5, 3, 8, 4, 2, 7, 1, 6}
        //  i                 j
        // {1, 3, 8, 4, 2, 7, 1, 6}
        //        i           j
        // {1, 3, 8, 4, 2, 7, 8, 6}

        //        i           j
        // {1, 3, 8, 4, 2, 7, 8, 6}
        //        i     j
        // {1, 3, 2, 4, 2, 7, 8, 6}
        //        i     j
        // {1, 3, 2, 4, 2, 7, 8, 6}

        //        i     j
        // {1, 3, 2, 4, 2, 7, 8, 6}
        // {1, 3, 8, 4, 2, 7, 8, 6}



        // {1, 3, 5, 4, 2, 7, 3, 6}
        System.out.println("arr=" + JSON.toJSONString(arr));
        quickSort(arr, 0, arr.length - 1);

        for (int num : arr) {
            System.out.print(num + " ");  // 输出：1 2 3 4 5 6 7 8
        }
    }
}

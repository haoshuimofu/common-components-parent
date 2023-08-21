package study.algorithm.sort;

/**
 * 插入排序 > 选择排序 > 冒泡排序
 *
 *
 * @author dewu.de
 * @date 2023-08-21 12:13 下午
 */
public class SortTest {


    public static void main(String[] args) {

        int round = 1000_000;

        long start = System.currentTimeMillis();
        for (int i = 0; i < round; i++) {
            int[] nums = new int[]{5, 9, 3, 1, 7, 6, 8, 2, 4};
            BubbleSort.sort(nums);
        }
        System.out.println("冒泡排序: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < round; i++) {
            int[] nums = new int[]{5, 9, 3, 1, 7, 6, 8, 2, 4};
            SelectSort.sort(nums);
        }
        System.out.println("选择排序: " + (System.currentTimeMillis() - start));


        start = System.currentTimeMillis();
        for (int i = 0; i < round; i++) {
            int[] nums = new int[]{5, 9, 3, 1, 7, 6, 8, 2, 4};
            InsertSort.sort(nums);
        }
        System.out.println("插入排序: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < round; i++) {
            int[] nums = new int[]{5, 9, 3, 1, 7, 6, 8, 2, 4};
            QuickSort.quickSort(nums, 0, nums.length - 1);
        }
        System.out.println("快速排序: " + (System.currentTimeMillis() - start));


    }


}

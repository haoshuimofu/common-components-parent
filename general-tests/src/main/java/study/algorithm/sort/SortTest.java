package study.algorithm.sort;

/**
 * 插入排序 > 选择排序 > 冒泡排序
 * <p>
 * 冒泡排序、选择排序、插入排序和快速排序是常见的排序算法，它们在不同情况下的性能表现有所差异。
 * <p>
 * 冒泡排序（Bubble Sort）：冒泡排序是一种简单但效率较低的排序算法。它的基本思想是通过相邻元素的比较和交换，将较大的元素逐渐“冒泡”到数组的尾部。时间复杂度为O(n^2)。
 * <p>
 * 选择排序（Selection Sort）：选择排序是一种简单直观的排序算法。它的基本思想是每次从待排序序列中选择最小（或最大）的元素放到已排序序列的末尾。时间复杂度为O(n^2)。
 * <p>
 * 插入排序（Insertion Sort）：插入排序是一种简单且稳定的排序算法。它的基本思想是将数组分为已排序和未排序两部分，每次从未排序部分中取出一个元素，插入到已排序部分的正确位置。时间复杂度为O(n^2)。
 * <p>
 * 快速排序（Quick Sort）：快速排序是一种高效的排序算法，通常被认为是最快的排序算法之一。它的基本思想是通过一次分区操作将数组分成两个独立的子序列，然后递归地对子序列进行排序。时间复杂度为O(nlogn)。
 * <p>
 * 从时间复杂度上看，快速排序是四种算法中最优的，其次是插入排序、选择排序和冒泡排序。因此，在处理大规模数据时，快速排序是最好的选择。然而，在数据量较小的情况下，插入排序和选择排序可能更加高效，因为它们的常数因子较小。
 * <p>
 * 此外，快速排序是一种原地排序算法，不需要额外的空间；而插入排序和选择排序需要额外的O(1)空间。冒泡排序的空间复杂度也是O(1)。
 * <p>
 * 总结起来，选择适当的排序算法取决于数据规模和性能要求。如果数据规模较小，可以使用插入排序、选择排序或冒泡排序；如果数据规模较大且要求排序速度快，可以选择快速排序。
 * </p>
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

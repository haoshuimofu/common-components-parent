package uitls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author dewu.de
 * @date 2023-09-05 10:25 上午
 */
public class BinarySearch {

    public static void main(String[] args) {
        System.out.println("binary search index=" + binarySearch(new int[]{3, 5, 6, 7, 8}, 6));
        List<Integer> numList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            if (i == 0) {
                numList.add(new Random().nextInt(10));
            } else {
                numList.add(numList.get(numList.size() - 1) + new Random().nextInt(10));
            }
        }
        int[] nums = numList.stream()
                .mapToInt(Integer::intValue)
                .toArray();
        System.out.println(Arrays.toString(nums));
        int round = 1000;
        for (int i = 0; i < round; i++) {
            int value = new Random().nextInt(10);
            int selfIndex = binarySearch(nums, value);
            int jdkIndex = Arrays.binarySearch(nums, value);
            if (jdkIndex >= 0) {
                if (nums[selfIndex] != nums[jdkIndex]) {
                    System.err.println("found value=" + value
                            + ", self_index=" + selfIndex + ", self_value=" + nums[selfIndex]
                            + ", jdk_index" + jdkIndex + ", jdk_value=" + nums[jdkIndex]);
                }
            } else {
                if (selfIndex != jdkIndex) {
                    System.err.println("not found value=" + value
                            + ", self_index=" + selfIndex + ", jdk_index=" + jdkIndex);
                }
            }
        }
    }

    // [3, 12, 20, 22, 31, 40, 44, 44, 49, 56], find 8

    public static int binarySearch(int[] arr, int num) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int midVal = arr[mid];
            if (midVal == num) {
                return mid;
            } else if (midVal > num) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -left - 1;
    }

}

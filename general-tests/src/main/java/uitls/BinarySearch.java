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
        System.out.println("insert position=" + searchInsertPosition(new int[]{3, 5, 6, 7, 8}, 6));
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
                if (selfIndex != jdkIndex) {
                    System.err.println("value=" + value + ", self=" + selfIndex + ", jdk=" + jdkIndex);
                }
            } else if (selfIndex != (jdkIndex + 1)) {
                System.err.println("value=" + value + ", self=" + selfIndex + ", jdk=" + jdkIndex);
            }
        }
    }

    public static int binarySearch(int[] arr, int num) {
        int position = Integer.MIN_VALUE;
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] >= num) {
                if (mid == 0 || arr[mid - 1] < num) {
                    position = arr[mid] == num ? mid : -mid;
                    break;
                } else {
                    right = mid - 1;
                }
            } else {
                if (mid == 0 || mid == arr.length - 1) {
                    position = mid + 1;
                    break;
                } else {
                    left = mid + 1;
                }
            }
        }
        return position;
    }

    public static int searchInsertPosition(int[] arr, int num) {
        int position = -1;
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] >= num) {
                if (mid == 0) {
                    break;
                } else if (arr[mid - 1] < num) {
                    position = mid - 1;
                    break;
                } else {
                    right = mid - 1;
                }
            } else {
                if (mid == arr.length - 1) {
                    position = mid;
                    break;
                } else {
                    left = mid + 1;
                }
            }
        }
        return position + 1;
    }
}

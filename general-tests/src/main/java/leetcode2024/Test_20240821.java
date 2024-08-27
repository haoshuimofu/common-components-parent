package leetcode2024;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Test_20240821 {

    public static void main(String[] args) {
        int[] nums = new int[]{1,1,1,1,2,2,3};
        System.out.println(new Test_20240821().removeDuplicates(nums));
        System.out.println(Arrays.toString(nums));
    }

    public int removeDuplicates(int[] nums) {
        int number = nums[0];
        int count = 1;
        int removeCount = 0;
        for (int i = 1; i < nums.length; i++) {
            nums[i - removeCount] = nums[i];
            if (nums[i] == number) {
                if (++count > 1) {
                    removeCount++;
                }
            } else {
                number = nums[i];
                count = 1;
            }
        }
        return nums.length;
    }

    public int[] twoSum(int[] numbers, int target) {
        int low = 0;
        int high  = numbers.length - 1;
        while (low < high) {
            int sum = numbers[low] + numbers[high];
            if (sum == target) {
                return new int[]{low + 2, high + 1};
//                low++;
//                high--;
            } else if (sum < target) {
                low++;
            } else {
                high--;
            }
        }
        return new int[]{};
    }

    Set<Integer> vals = new HashSet<>();
    public boolean findTarget(TreeNode root, int k) {
        if (root == null) {
            return false;
        }
        if (vals.contains(k - root.val)) {
            return true;
        }
        vals.add(root.val);
        return findTarget(root.left, k) || findTarget(root.right, k);
    }





}

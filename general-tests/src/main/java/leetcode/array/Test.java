package leetcode.array;

import java.util.HashMap;
import java.util.Map;

public class Test {

    /**
     * 暴力解法
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> numIndexMap = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            Integer sameNumIndex = numIndexMap.get(nums[i]);
            if (sameNumIndex == null) {
                numIndexMap.put(nums[i], i);
            } else if (nums[i] * 2 == target) {
                return new int[]{i, sameNumIndex};
            }
        }
        for (int i = 0; i < nums.length; i++) {
            Integer otherIndex = numIndexMap.get(target - nums[i]);
            if (otherIndex != null && otherIndex != i) {
                return new int[]{i, otherIndex};
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{3, 2, 4};
        int[] indexes = twoSum(nums, 6);
        for (int index : indexes) {
            System.out.println(index);
        }
    }

}

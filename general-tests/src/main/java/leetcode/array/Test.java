package leetcode.array;

import com.alibaba.fastjson.JSON;

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
        System.out.println(JSON.toJSONString(numIndexMap));
        for (int i = 0; i < nums.length; i++) {
            System.out.println("i=" + i + "; num=" + nums[i] + "; other=" + (target - nums[i]));
            System.out.println("other =" + numIndexMap.containsKey(target - nums[i]));
            Integer otherIndex = numIndexMap.get(target - nums[i]);
            if (otherIndex != null && otherIndex != i) {
                return new int[]{i, otherIndex};
            }
        }
        return null;
    }

    public static int[] twoSum11(int[] nums, int target) {
        Map<Integer, Integer> numIndexMap = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            if (numIndexMap.containsKey(nums[i])) {
                if (nums[i] * 2 == target) {
                    return new int[]{i, numIndexMap.get(nums[i])};
                }
            } else  {
                numIndexMap.put(nums[i], i);
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (numIndexMap.containsKey(target - nums[i])) {
                int otherIndex = numIndexMap.get(target - nums[i]);
                if (i != otherIndex) {
                    return new int[]{i, otherIndex};
                }
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


    public static int[] twoSum1(int[] nums, int target) {
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > target) continue;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] > target) continue;
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }

        }
        return null;
    }


}

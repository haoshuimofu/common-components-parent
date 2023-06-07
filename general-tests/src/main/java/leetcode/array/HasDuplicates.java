package leetcode.array;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 判断数组是否存在相同元素
 */
public class HasDuplicates {

    public static void main(String[] args) {

    }


    public boolean containsDuplicateBySort(int[] nums) {
        Arrays.sort(nums);


        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                return true;
            }
        }
        return false;
    }

    public boolean containsDuplicateBySet(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        return set.size() != nums.length;
    }

    /**
     * 暴力解法-超时
     *
     * @param nums
     * @return
     */
    public boolean containsDuplicate(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }

}

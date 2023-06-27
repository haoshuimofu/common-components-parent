package leetcode;

import java.util.ArrayList;
import java.util.List;

public class Test_20230623 {

    public static void main(String[] args) {

    }

    public static List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length < 3) {
            return null;
        }
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length - 1; j++) {
                int target = -(nums[i] + nums[j]);
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[k] == target) {
                        List<Integer> list = new ArrayList<>(3);
                        list.add(i);
                        list.add(j);
                        list.add(k);
                        result.add(list);
                    }
                }
            }
        }
        return result;
    }
}

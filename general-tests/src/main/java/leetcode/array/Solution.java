package leetcode.array;

/**
 * @author dewu.de
 * @date 2023-06-05 1:26 下午
 */
public class Solution {

    public static int removeDuplicates(int[] nums) {
        display(nums);
        int removeCount = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            int notSameIndex = -1;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] != nums[j]) {
                    notSameIndex = j;
                    break;
                }
            }
            if (notSameIndex > i) {
                int remove = notSameIndex - i - 1;
                if (remove > 0) {
                    removeCount += remove;
                }
                for (int j = i + 1; j < nums.length - remove; j++) {
                    nums[j] = nums[j + remove];
                }
            }
        }
        display(nums);
        int k = 1;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i+1]) {
                k = i+1;
                break;
            }
        }
        return k;
    }

    private static void display(int[] nums) {
        StringBuilder sb = new StringBuilder();
        for (int num : nums) {
            sb.append(num + " ");
        }
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        int[] nums = new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        nums = new int[]{1, 1, 1, 2};
        System.out.println(removeDuplicates(nums));
    }

}

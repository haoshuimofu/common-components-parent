package leetcode;

/**
 * @author dewu.de
 * @date 2023-07-26 10:08 上午
 */
public class Test_20230726 {

    public static void main(String[] args) {
        int[] nums = new int[]{2, 3, 1, 1, 4};
//        System.out.println(jump(nums));
//        nums = new int[]{2, 3, 0, 1, 4};
//        System.out.println(jump(nums));
        nums = new int[]{5, 6, 4, 4, 6, 9, 4, 4, 7, 4, 4, 8, 2, 6, 8, 1, 5, 9, 6, 5, 2, 7, 9, 7, 9, 6, 9, 4, 1, 6, 8, 8, 4, 4, 2, 0, 3, 8, 5};
        System.out.println(jump(nums) + ", " + jump1(nums));
//        nums = new int[]{1, 2, 1, 1, 1};
//        System.out.println(jump(nums));
    }

    public static int jump(int[] nums) {
        int[] counter = new int[1];
        counter[0] = nums.length;
        int[] visitSteps = new int[nums.length];
        minSteps(nums, 0, 0, counter, visitSteps);
        return counter[0] == nums.length ? 0 : counter[0];
    }

    public static void minSteps(int[] nums, int index, int steps, int[] counter, int[] visitSteps) {
        if (index > nums.length - 1
                || nums[index] == 0
                || (visitSteps[index] > 0 && steps >= visitSteps[index])) {
            return;
        }
        visitSteps[index] = steps;
        for (int i = 1; i <= nums[index]; i++) {
            if (index + i >= nums.length - 1) {
                counter[0] = Math.min(counter[0], steps + 1);
                if (index + i < nums.length) {
                    visitSteps[index + i] = steps + 1;
                }
            } else {
                if (index + i < nums.length - 1) {
                    minSteps(nums, index + i, steps + 1, counter, visitSteps);
                }
            }
        }
    }

    //=============

    /**
     * 通过-只击败了15.46%的java
     *
     * @param nums
     * @return
     */
    public static int jump1(int[] nums) {
        int[] minSteps = new int[nums.length];
        for (int i = 1; i < nums.length; i++) {
            minSteps[i] = nums.length;
        }
        for (int i = 0; i < nums.length; i++) {
            for (int j = 1; j <= nums[i]; j++) {
                if (i + j < nums.length && minSteps[i + j] > minSteps[i] + 1) {
                    minSteps[i + j] = minSteps[i] + 1;
                }
            }
        }
        return minSteps[nums.length - 1];
    }
}

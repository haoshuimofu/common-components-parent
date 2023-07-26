package leetcode;

/**
 * @author dewu.de
 * @date 2023-07-26 10:08 上午
 */
public class Test_20230726 {

    public static void main(String[] args) {
        int[] nums = new int[]{2, 3, 1, 1, 4};
        System.out.println(jump(nums));
        nums = new int[]{2, 3, 0, 1, 4};
        System.out.println(jump(nums));
        nums = new int[]{5, 6, 4, 4, 6, 9, 4, 4, 7, 4, 4, 8, 2, 6, 8, 1, 5, 9, 6, 5, 2, 7, 9, 7, 9, 6, 9, 4, 1, 6, 8, 8, 4, 4, 2, 0, 3, 8, 5};
        System.out.println(jump(nums));
        nums = new int[]{1, 2, 1, 1, 1};
        System.out.println(jump(nums));
    }

    /**
     * 通过-只击败了15.46%的java
     *
     * @param nums
     * @return
     */
    public static int jump(int[] nums) {
        int[] minSteps = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            for (int j = 1; j <= nums[i]; j++) {
                if (i + j < nums.length && (minSteps[i + j] == 0 || minSteps[i + j] > minSteps[i] + 1)) {
                    minSteps[i + j] = minSteps[i] + 1;
                }
            }
        }
        return minSteps[nums.length - 1];
    }

    public static int jump1(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }
        int[] minSteps = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            for (int j = nums[i]; j >= 1; j--) {
                if (i + j >= nums.length - 1) {
                    return minSteps[i] + 1;
                } else if (minSteps[i + j] == 0 || minSteps[i + j] > minSteps[i] + 1) {
                    minSteps[i + j] = minSteps[i] + 1;
                }
            }
        }
        return minSteps[nums.length - 1];
    }

}

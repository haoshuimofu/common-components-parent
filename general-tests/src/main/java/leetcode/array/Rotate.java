package leetcode.array;

// 1 2 3 4 5 6 7
// 0 0 0 1 2 3 4 5 6 7
//
public class Rotate {
    public static void rotate(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return;
        }
        display(nums);
        System.out.println("---");
        for (int i = 1; i <= k; i++) {
            for (int j = nums.length - 1; j > 0; j--) {
                int temp = nums[j];
                nums[j] = nums[j - 1];
                nums[j - 1] = temp;
            }
            display(nums);
            System.out.println("---");
        }
    }


    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7};
        // 反转数组演示
        display(nums);
        reverseArray(nums, 0, nums.length - 1);
        display(nums);

        System.out.println();

        nums = new int[]{1, 2};
        rotateByReverse(nums, 2);
        display(nums);

        System.out.println();

        nums = new int[]{1, 2, 3, 4, 5, 6, 7};
        rotateByReverse(nums, 3);
        display(nums);

    }

    /**
     * 旋转-基于数组反转实现
     * 原始: 1 2 3 4 5 6 7
     * 反转: 7 6 5 4 3 2 1
     * 继续: 5 6 7 1 2 3 4
     *
     * @param nums
     * @param k
     */
    public static void rotateByReverse(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 0) {
            return;
        }
        reverseArray(nums, 0, nums.length - 1);
        reverseArray(nums, 0, k - 1);
        reverseArray(nums, k, nums.length - 1);
        if (k < nums.length) {
        }
    }

    /**
     * 数组元素顺序反转
     *
     * @param nums
     * @param start
     * @param end
     */
    private static void reverseArray(int[] nums, int start, int end) {
        if (nums == null || nums.length == 0 || start < 0 || end >= nums.length || start >= end) {
            return;
        }
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }

    private static void display(int[] nums) {
        StringBuilder sb = new StringBuilder();
        for (int num : nums) {
            sb.append(num).append(" ");
        }
        System.out.println(sb.toString());
    }

}

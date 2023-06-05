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


    public static void rotate1(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return;
        }
        display(nums);
        System.out.println("---");
        for (int i = 0; i < nums.length - k; i++) {
            int temp = nums[i + k];
            nums[i + k] = nums[i];
            nums[i] = temp;

            display(nums);

            int index = (i + k + k) % nums.length;
            temp = nums[index];
            nums[index] = nums[i];
            nums[i] = temp;
            display(nums);
            System.out.println();

        }
    }

    private static void display(int[] nums) {
        StringBuilder sb = new StringBuilder();
        for (int num : nums) {
            sb.append(num).append(" ");
        }
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7};

        System.out.println();
        rotate1(nums, 3);

        System.out.println();
    }

}

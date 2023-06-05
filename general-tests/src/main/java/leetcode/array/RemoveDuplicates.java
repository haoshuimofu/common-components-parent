package leetcode.array;

public class RemoveDuplicates {


    private static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        display(nums);
        int slow = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[slow] != nums[i]) {
                nums[slow + 1] = nums[i];
                slow++;
                display(nums);
            }
        }
        return slow + 1;
    }

    private static void display(int[] nums) {
        StringBuilder sb = new StringBuilder();
        for (int num : nums) {
            sb.append(num).append(" ");
        }
        System.out.println(sb.toString());
    }


    public static int removeDuplicates1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        display(nums);
        int count = 1;
        int index = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[index - 1]) {
                continue;
            }
            nums[index] = nums[i];
            index++;
            count++;
            display(nums);
        }
        return count;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 1, 2, 2, 3};
        nums = new int[]{1, 1, 1, 2};
//        int count = removeDuplicates1(nums);
//        System.out.println(count);

        System.out.println(removeDuplicates2(nums));

    }

    public static int removeDuplicates2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        display(nums);
        int[] result = new int[nums.length];
        int index = 0;
        result[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != result[index]) {
                result[++index] = nums[i];
                display(result);
            }
        }
        return index + 1;

    }

}

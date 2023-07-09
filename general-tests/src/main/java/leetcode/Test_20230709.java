package leetcode;

public class Test_20230709 {

    public static void main(String[] args) {
        System.out.println(waysToStep(61));
        int a = 752119970;
        System.out.println(a > Integer.MAX_VALUE);
    }

    /**
     * 面试题 08.01. 三步问题
     *
     * @param n
     * @return
     */
    public static int waysToStep(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;
        if (n == 3) return 4;

        long n_3 = 1;
        long n_2 = 2;
        long n_1 = 4;
        long value = 0;
        for (int i = 4; i <= n; i++) {
            value = (n_3 + n_2 + n_1) % 1000000007;

            n_3 = n_2;
            n_2 = n_1;
            n_1 = value;
        }
        return (int) value;
    }


    /**
     * 面试题 16.17. 连续数列
     *
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        int sum = nums[0];
        int maxSum = sum;
        if (nums.length > 1) {
            for (int i = 1; i < nums.length; i++) {
                int tempSum = sum + nums[i];
                if (tempSum < nums[i]) {
                    sum = nums[i];
                } else {
                    sum = tempSum;
                }
                if (sum > maxSum) {
                    maxSum = sum;
                }
            }
        }
        return maxSum;
    }

}

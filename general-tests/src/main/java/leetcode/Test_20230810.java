package leetcode;

public class Test_20230810 {

    public static void main(String[] args) {
        System.out.println(isPowerOfThree(3));
    }

    public static boolean isPowerOfThree(int n) {
        if (n < 1) {
            // 3的x次幂n为整数, 那n肯定>=1
            return false;
        } else if (n == 1) {
            // 3的0次幂为1
            return true;
        }
        while (true) {
            if (n % 3 != 0) {
                // n都不能被3整除, 那n肯定不是3的x次幂
                return false;
            }
            n /= 3;
            if (n == 1) {
                return true;
            }
        }
//        return true;
    }

}

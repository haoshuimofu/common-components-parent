package leetcode;

public class Test_20230810 {

    public static void main(String[] args) {
        System.out.println(isPowerOfThree(3));
    }

    /**
     * 326. 3的幂
     *
     * @param n
     * @return
     */
    public static boolean isPowerOfThree(int n) {
        while (n > 0) {
            if (n == 1) {
                return true;
            }
            if (n % 3 != 0) {
                return false;
            }
            n /= 3;
        }
        return false;
    }

}

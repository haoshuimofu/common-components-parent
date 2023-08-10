package leetcode;

public class Test_20230810 {

    public static void main(String[] args) {
//        System.out.println(isPowerOfThree(19684));
        for (int i = 0; i <= 6; i++) {
            System.out.println(i % 3);
        }
    }

    public static boolean isPowerOfThree(int n) {
        if (n == 0) {
            return false;
        } else if (n == 1) {
            return true;
        }
        int sum = 0;
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum % 3 == 0;
    }

}

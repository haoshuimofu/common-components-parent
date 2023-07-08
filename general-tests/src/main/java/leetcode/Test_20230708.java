package leetcode;

public class Test_20230708 {

    public static void main(String[] args) {
        int v = 1000000007;
        System.out.println(v);
        System.out.println(fib(48));
    }

    /**
     * 剑指 Offer 10- I. 斐波那契数列
     *
     * @param n
     * @return
     */
    public static int fib(int n) {
        int value = 0;
        if (n <= 1) {
            value = n;
        } else {
            int n_2 = 0;
            int n_1 = 1;
            for (int i = 2; i <= n; i++) {
                value = (n_1 + n_2) % 1000000007;
                n_2 = n_1;
                n_1 = value;
            }
        }
        return value;
    }

}

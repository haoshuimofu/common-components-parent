package leetcode;

/**
 * @author dewu.de
 * @date 2023-08-08 6:07 下午
 */
public class Test_20230808 {

    public static void main(String[] args) {
        System.out.println(myPow(34.00515, -3) + "; " + myPow1(34.00515, -3));
        System.out.println(myPow(1.99364, -11) + "; " + myPow1(1.99364, -11));
        System.out.println(myPow(1.72777, 7) + "; " + myPow1(1.72777, 7));
        System.out.println(myPow(0.00001, 2147483647) + "; " + myPow1(0.00001, 2147483647)); // java.lang.OutOfMemoryError: Requested array size exceeds VM limit

        for (int i = 0; i <= 16; i++) {
            System.out.println(-i + "/2 = " + -i / 2);
        }

    }

    /**
     * 剑指 Offer 16. 数值的整数次方
     * 递归解法, 逻辑如下:
     * <ul>
     *     <li> 0/2 = 0 = 0 + 0       </li>
     *     <li> 1/2 = 0 = 0 + (0 + 1) </li>
     *     <li> 2/2 = 1 = 1 + 1       </li>
     *     <li> 3/2 = 1 = 1 + (1 + 1) </li>
     *     <li> 4/2 = 2 = 2 + 2       </li>
     *     <li> 5/2 = 2 = 2 + (2 + 1) </li>
     *     <li> 6/2 = 3 = 3 + 3       </li>
     *     <li> 7/2 = 3 = 3 + (3 + 1) </li>
     *     <li> 8/2 = 4 = 4 + 4       </li>
     *     <li> 从下往上看 </li>
     * </li>
     *
     * @param x
     * @param n
     * @return
     */
    public static double myPow(double x, int n) {
        if (n == 0) {
            return 1; // 0次幂为1
        } else if (n == 1) {
            return x; // 1次幂x
        } else if (n == -1) {
            return 1 / x; // -1次幂就是x倒数
        }
        // 求x的n/2次幂
        double half = myPow(x, n / 2);
        // x的n/2次幂结果 再平方
        double value = half * half;
        // 如果n不是偶数, value是不准确的
        if (n % 2 != 0) {
            // n为正数, value = x^(n/2) * x^(n/2+1) = half * (half * x) = value * x
            // n为负数, value = x^(n/2) * x^(n/2-1) = half * (half * x^-1) = value * 1/x
            value = n > 0 ? value * x : value * myPow(x, -1);
        }
        return value;
    }

    /**
     * 快速次幂 + 迭代
     *
     * @param x
     * @param n
     * @return
     */
    public static double myPow1(double x, int n) {
        double value = 1.0;
        double xv = x;
        while (n != 0) {
            if (n % 2 != 0) {
                value *= (n > 0 ? xv : 1 / xv);
            }
            xv *= xv;
            n /= 2;
        }
        return value;
    }

}

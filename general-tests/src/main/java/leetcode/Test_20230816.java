package leetcode;

/**
 * @author dewu.de
 * @date 2023-08-16 7:48 下午
 */
public class Test_20230816 {

    public static void main(String[] args) {
        Test_20230816 test = new Test_20230816();
        System.out.println(test.mySqrt(2147395599));
        System.out.println(test.myPow(-1, Integer.MIN_VALUE));
    }

    /**
     * 69. x 的平方根
     *
     * @param x
     * @return
     */
    public int mySqrt(int x) {
        int num = 1;
        while (num <= x / num) {
            // num平方 <= x
            num *= 2;
        }
        // 循环终止时, 说明 num平方 > x, x平方根真值在(num/2, num]区间内
        int low = num / 2;
        int high = num;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (x / mid == mid) {
                return mid;
            } else if (x / mid > mid) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low - 1;
    }

    /**
     * 50. Pow(x, n)
     * 求x的正数n次方
     */
    public double myPow(double x, int n) {
        if (n == 0) {
            // 任何数字的0次幂都等于1, 包括0
            return 1;
        } else if (Math.abs(n) == 1) {
            return n == 1 ? x : 1 / x;
        }
        double half = myPow(x, n / 2);
        double value = half * half;
        if (n % 2 != 0) {
            value *= n > 0 ? x : 1 / x;
        }
        return value;
    }

}

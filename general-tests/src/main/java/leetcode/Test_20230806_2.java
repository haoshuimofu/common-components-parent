package leetcode;

/**
 * @author dewu.de
 * @date 2023-08-06 10:21 下午
 */
public class Test_20230806_2 {

    public static void main(String[] args) {
        System.out.println(integerBreak(2));
        System.out.println(integerBreak(10));
        System.out.println(integerBreak(120) % 1000000007);
        System.out.println(Integer.MAX_VALUE);
        System.out.println(1000000007l);
//        System.out.println(maxSplitInt(10, 9));
    }

    /**
     * 343. 整数拆分
     *
     * @param n
     * @return
     */
    public static int integerBreak(int n) {
        int max = Integer.MIN_VALUE;
        for (int i = 2; i <= n; i++) {
            max = Math.max(max, maxSplitInt(n, i));
        }
        return max;
    }

    public static int maxSplitInt(int n, int k) {
        int base = n / k;
        int delta = n % k;
        int count = 0;
        int total = 1;
        for (int i = 0; i < k; i++) {
            int subVal = base;
            if (count < delta) {
                subVal += 1;
                count++;
            }
            total = total * subVal;
        }
        return total;
    }

    /**
     * 剑指 Offer 14- I. 剪绳子
     *
     * @param n
     * @return
     */
    public int cuttingRope(int n) {
        return integerBreak(n);
    }

}

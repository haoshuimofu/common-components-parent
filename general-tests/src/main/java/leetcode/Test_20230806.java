package leetcode;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

import java.math.BigInteger;

public class Test_20230806 {

    public static void main(String[] args) {
        System.out.println(numWays(4));
        System.out.println(cuttingRope(120));


        int a = Integer.MAX_VALUE;
        int b = 2;
//        int result = Math.multiplyExact(a, b); //如果溢出则抛异常

    }

    /**
     * 剑指 Offer 10- II. 青蛙跳台阶问题
     *
     * @param n
     * @return
     */
    public static int numWays(int n) {
        if (n <= 1) {
            return 1;
        } else if (n == 2) {
            return 2;
        }
        int[] ways = new int[n + 1];
        ways[0] = 1;
        ways[1] = 1;
        ways[2] = 2;
        for (int i = 3; i <= n; i++) {
            ways[i] = (ways[i - 1] + ways[i - 2]) % 1000000007;
        }
        return ways[n];
    }

    /**
     * 剑指 Offer 14- II. 剪绳子 II
     *
     * @param n
     * @return
     */
    public static int cuttingRope(int n) {
        BigInteger max = BigInteger.ZERO;
        for (int i = 2; i <= n; i++) {
            BigInteger temp = doCuttingRope(n, i);
            if (max.compareTo(temp) < 0) {
                max = temp;
            }
        }
        //System.out.println(ObjectSizeCalculator.getObjectSize(max));
        return max.mod(BigInteger.valueOf(1000000007)).intValue();
    }

    /**
     * leetcode 不识别BigInteger类, 擦
     *
     * @param n
     * @param k
     * @return
     */
    public static BigInteger doCuttingRope(int n, int k) {
        int base = n / k;
        int delta = n % k;
        BigInteger total = BigInteger.ONE;
        for (int i = 0; i < k; i++) {
            if (i < delta) {
                total = total.multiply(BigInteger.valueOf(base + 1));
            } else {
                total = total.multiply(BigInteger.valueOf(base));
            }
        }
        return total;
    }

}

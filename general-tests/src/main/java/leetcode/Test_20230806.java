package leetcode;

public class Test_20230806 {

    public static void main(String[] args) {
        System.out.println(numWays(4));
        System.out.println(cuttingRope(120));

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
        long max = 0;
        for (int i = 2; i <= n; i++) {
            max = Math.max(max, maxSplitInt(n, i));
            System.out.println(maxSplitInt(n, i));
        }
        return (int) (max % 1000000007);
    }

    public static long maxSplitInt(int n, int k) {
        int base = n / k;
        int delta = n % k;
        int count = 0;
        long total = 1;
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

}

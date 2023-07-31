package leetcode;

public class Test_20230731 {

    public static void main(String[] args) {

        System.out.println(sum(35));
        System.out.println(movingCount(2, 3, 1));

    }

    /**
     * 剑指 Offer 13. 机器人的运动范围
     * 解题思路: 和《剑指 Offer 12. 矩阵中的路径》类似
     *
     * @param m
     * @param n
     * @param k
     * @return
     */
    public static int movingCount(int m, int n, int k) {
        int[] count = new int[1];
        int[][] visit = new int[m][n];
        moving(m, n, 0, 0, k, visit, count);
        return count[0];
    }

    public static void moving(int m, int n, int i, int j, int k, int[][] visit, int[] count) {
        if (i >= m || j >= n || visit[i][j] == 1 || sum(i, j) > k) {
            return;
        }
        System.out.println(String.format("(i,j)=(%d,%d)", i, j));
        count[0] = count[0] + 1;
        visit[i][j] = 1;

        // 上下左右
        if (i > 0) {
            moving(m, n, i - 1, j, k, visit, count);
        }
        if (i < m - 1) {
            moving(m, n, i + 1, j, k, visit, count);
        }
        if (j > 0) {
            moving(m, n, i, j - 1, k, visit, count);
        }
        if (j < n - 1) {
            moving(m, n, i, j + 1, k, visit, count);
        }
    }

    private static int sum(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num = num / 10;
        }
        return sum;
    }

    private static int sum(int num1, int num2) {
        return sum(num1) + sum(num2);
    }
}

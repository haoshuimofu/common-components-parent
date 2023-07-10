package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dewu.de
 * @date 2023-07-10 1:04 下午
 */
public class Test_20230710 {


    public static void main(String[] args) {
        // 杨辉三角
        List<List<Integer>> result = generate(5);
        for (List<Integer> integers : result) {
            for (Integer integer : integers) {
                System.out.print(integer + " ");
            }
            System.out.println();
        }
        // 最小路径和
        int[][] grid = new int[3][3];
        grid[0] = new int[]{1, 3, 1};
        grid[1] = new int[]{1, 5, 1};
        grid[2] = new int[]{4, 2, 1};
        System.out.println(minPathSum(grid));

    }

    /**
     * 杨辉三角
     * <p>
     * 1
     * 1 1
     * 1 2 1
     *
     * @param numRows
     * @return
     */
    public static List<List<Integer>> generate(int numRows) {
        int[][] dp = new int[numRows][numRows];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j <= i; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = 1;
                } else {
                    dp[i][j] = dp[i - 1][j];
                    if (j >= 1) {
                        dp[i][j] += dp[i - 1][j - 1];
                    }
                }
            }
        }
        List<List<Integer>> result = new ArrayList<>(numRows);
        for (int i = 0; i < numRows; i++) {
            List<Integer> list = new ArrayList<>(i + 1);
            for (int j = 0; j <= i; j++) {
                list.add(dp[i][j]);
            }
            result.add(list);
        }
        return result;
    }

    /**
     * 最小路径和
     * m*n个格子
     * 1 3 1
     * 1 5 1
     * 4 2 1
     * <p>
     * dp[m,n] = min(dp[m][n-1], dp[m-1][n]) + grid[m][n]
     *
     * @param grid
     * @return
     */
    public static int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int value = grid[i][j];
                if (i >= 1 && j >= 1) {
                    value += Math.min(dp[i][j - 1], dp[i - 1][j]);
                } else if (i >= 1) {
                    value += dp[i - 1][j];
                } else if (j >= 1) {
                    value += dp[i][j - 1];
                }
                dp[i][j] = value;
            }
        }
        return dp[m-1][n-1];
    }
}

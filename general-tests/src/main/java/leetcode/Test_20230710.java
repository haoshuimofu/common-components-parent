package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dewu.de
 * @date 2023-07-10 1:04 下午
 */
public class Test_20230710 {


    public static void main(String[] args) {
        List<List<Integer>> result = generate(5);
        for (List<Integer> integers : result) {
            for (Integer integer : integers) {
                System.out.print(integer + " ");
            }
            System.out.println();
        }
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
}

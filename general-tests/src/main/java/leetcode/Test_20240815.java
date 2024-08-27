package leetcode;

import java.util.Stack;

public class Test_20240815 {

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if ('(' == ch || '{' == ch || '[' == ch) {
                stack.push(ch);
            } else if (')' == ch) {
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop();
                } else {
                    stack.push(ch);
                }
            } else if ('}' == ch) {
                if (!stack.isEmpty() && stack.peek() == '{') {
                    stack.pop();
                } else {
                    stack.push(ch);
                }
            } else if (']' == ch) {
                if (!stack.isEmpty() && stack.peek() == '[') {
                    stack.pop();
                } else {
                    stack.push(ch);
                }
            }
        }
        return stack.isEmpty();
    }


    public int uniquePaths(int m, int n) {
        int[] max = new int[1];
        doUniquePaths(m, n, 1, 1, max);
        return max[0];
    }

    public void doUniquePaths(int m, int n, int row, int column, int[] max) {
        if (row == m && column == n) {
            max[0] += 1;
        } else {
            if (row == m) {
                doUniquePaths(m, n, row, column + 1, max);
            } else if (column == n) {
                doUniquePaths(m, n, row + 1, column, max);
            } else {
                doUniquePaths(m, n, row, column + 1, max);
                doUniquePaths(m, n, row + 1, column, max);
            }
        }
    }

    public int uniquePaths_dp(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = 1;
                } else {
                    if (i == 0) {
                        dp[i][j] += dp[i][j - 1];
                    } else if (j == 0) {
                        dp[i][j] += dp[i - 1][j];
                    } else {
                        dp[i][j] += (dp[i - 1][j] + dp[i][j - 1]);
                    }
                }
            }
        }
        return dp[m - 1][n - 1];
    }


}

package leetcode;

public class Test_20230729 {

    public static void main(String[] args) {
        char[][] board = new char[3][4];
        board[0] = new char[]{'A', 'B', 'C', 'E'};
        board[1] = new char[]{'S', 'F', 'C', 'S'};
        board[2] = new char[]{'A', 'D', 'E', 'E'};
        System.out.println(exist(board, "ABCB"));
        System.out.println(exist(board, "ABCCED"));

        char[][] board1 = new char[2][2];
        board1[0] = new char[]{'a', 'b'};
        board1[1] = new char[]{'c', 'd'};
        System.out.println(exist(board1, "cdba"));
    }

    /**
     * 剑指 Offer 12. 矩阵中的路径
     * 解题思路: DFS + 回溯
     *
     * @param board
     * @param word
     * @return
     */
    public static boolean exist(char[][] board, String word) {
        int m = board.length - 1;
        int n = board[0].length - 1;
        int[][] visit = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (board[i][j] == word.charAt(0)) {
                    boolean result = discovery(board, m, n, i, j, word, 0, visit);
                    if (result) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean discovery(char[][] board, int m, int n,
                                    int i, int j,
                                    String word, int charIndex,
                                    int[][] visit) {
        if (i > m || j > n || charIndex >= word.length() || visit[i][j] == 1) {
            return false;
        }
        // 标记已访问
        visit[i][j] = 1;
        char ch = word.charAt(charIndex);
        if (board[i][j] != ch) {
            // 此路不通, 回撤, 恢复标记位
            visit[i][j] = 0;
            return false;
        }
        if (charIndex == word.length() - 1) {
            return true;
        }
        boolean match = (i > 0 && discovery(board, m, n, i - 1, j, word, charIndex + 1, visit))
                || (i < m && discovery(board, m, n, i + 1, j, word, charIndex + 1, visit))
                || (j > 0 && discovery(board, m, n, i, j - 1, word, charIndex + 1, visit))
                || (j < n && discovery(board, m, n, i, j + 1, word, charIndex + 1, visit));
        if (!match) {
            // 上下左右四路不通, 回撤, 恢复标记位
            visit[i][j] = 0;
        }
        return match;
    }

}

package leetcode;

import java.util.ArrayList;
import java.util.List;

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


    public static boolean exist(char[][] board, String word) {
        int m = board.length - 1;
        int n = board[0].length - 1;
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (board[i][j] == word.charAt(0)) {
                    boolean result = discovery(board, m, n, i, j, new ArrayList<>(), word, 0);
                    if (result) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean discovery(char[][] board, int m, int n,
                                    int i, int j, List<int[]> path,
                                    String word, int charIndex) {
        if (i > m || j > n
                || charIndex >= word.length()
                || path.stream().anyMatch(arr -> arr[0] == i && arr[1] == j)) {
            return false;
        }
        char ch = word.charAt(charIndex);
        if (board[i][j] != ch) {
            return false;
        }
        path.add(new int[]{i, j});
        if (charIndex == word.length() - 1) {
            return true;
        }
        boolean match = false;
        if (i > 0 && board[i - 1][j] == word.charAt(charIndex + 1)) {
            match = discovery(board, m, n, i - 1, j, new ArrayList<>(path), word, charIndex + 1);
        }
        if (!match && i < m && board[i + 1][j] == word.charAt(charIndex + 1)) {
            match = discovery(board, m, n, i + 1, j, new ArrayList<>(path), word, charIndex + 1);
        }
        if (!match && j > 0 && board[i][j - 1] == word.charAt(charIndex + 1)) {
            match = discovery(board, m, n, i, j - 1, new ArrayList<>(path), word, charIndex + 1);
        }
        if (!match && j < n && board[i][j + 1] == word.charAt(charIndex + 1)) {
            match = discovery(board, m, n, i, j + 1, new ArrayList<>(path), word, charIndex + 1);
        }
        return match;
    }

}

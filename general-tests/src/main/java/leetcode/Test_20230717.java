package leetcode;

import java.util.ArrayDeque;
import java.util.Stack;

/**
 * @author dewu.de
 * @date 2023-07-17 11:44 上午
 */
public class Test_20230717 {

    public static void main(String[] args) {
        System.out.println(addStrings("11", "123"));
        System.out.println(addStrings("456", "77"));
        System.out.println(addStrings("0", "0"));

        System.out.println("----");

        char[][] grid = new char[4][5];
        grid[0] = new char[]{'1', '1', '0', '0', '0'};
        grid[1] = new char[]{'1', '1', '0', '0', '0'};
        grid[2] = new char[]{'0', '0', '1', '0', '0'};
        grid[3] = new char[]{'0', '0', '0', '1', '1'};

        grid = new char[3][3];
        grid[0] = new char[]{'0', '1', '0'};
        grid[1] = new char[]{'1', '0', '1'};
        grid[2] = new char[]{'0', '1', '0'};

        grid = new char[4][5];
        grid[0] = new char[]{'1', '1', '0', '0', '0'};
        grid[1] = new char[]{'1', '1', '0', '0', '0'};
        grid[2] = new char[]{'0', '0', '1', '0', '0'};
        grid[3] = new char[]{'0', '0', '0', '1', '1'};
        System.out.println("岛屿数 = " + bfs(grid));
    }

    /**
     * 1 1 0 0 0
     * 1 1 0 0 0
     * 0 0 1 0 0
     * 0 0 0 1 1
     * 3
     * <p>
     * 1
     * 1 1
     *
     * @param grid
     * @return
     */
    public static int numIslands(char[][] grid) {
        return bfs(grid);
    }

    public static int bfs(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] visit = new int[m][n];
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        queue.addFirst(new int[]{0, 0});
        boolean lastLevelHasIsland = false; // 上一层是否有岛
        int numIslands = 0;
        while (!queue.isEmpty()) {
            boolean currLevelHasIslandWithLastLevel = false; // 这一层是否有岛屿与上一层岛屿相连
            boolean currLevelHasIslandWithNextLevel = false; // 这一层是否有岛屿与下一层岛屿相连
            int size = queue.size();
            for (int k = 0; k < size; k++) {
                int[] node = queue.pollFirst();
                int i = node[0];
                int j = node[1];
                if (visit[i][j] == 0) {
                    System.out.print(grid[i][j] + " ");
                    visit[i][j] = 1;
                    if (grid[i][j] == '1') {
                        // 岛屿与上一层岛屿相连
                        if ((i > 0 && grid[i - 1][j] == '1') || (j > 0 && grid[i][j - 1] == '1')) {
                            currLevelHasIslandWithLastLevel = true;
                        }
                        // 岛屿是否与下一层岛屿相连
                        if ((i < m - 1 && grid[i + 1][j] == '1') || (j < n - 1 && grid[i][j + 1] == '1')) {
                            currLevelHasIslandWithNextLevel = true;
                        }
                        if ((!currLevelHasIslandWithLastLevel && !currLevelHasIslandWithNextLevel) || (i == m - 1 && j == n - 1)) {
                            numIslands++;
                        }
                    }
                    if (i < m - 1 && visit[i + 1][j] == 0) {
                        queue.addLast(new int[]{i + 1, j});
                    }
                    if (j < n - 1 && visit[i][j + 1] == 0) {
                        queue.addLast(new int[]{i, j + 1});
                    }
                }
            }
            if (lastLevelHasIsland) {
                if (!currLevelHasIslandWithLastLevel) {
                    numIslands++;
                    lastLevelHasIsland = false;
                }
            } else {
                if (currLevelHasIslandWithNextLevel) {
                    lastLevelHasIsland = true;
                }
            }
            System.out.println();
        }
        return numIslands;
    }

    public static String addStrings(String num1, String num2) {
        if (num1 == null) {
            return num2;
        } else if (num2 == null) {
            return num1;
        } else {
            char[] chs = new char[Math.max(num1.length(), num2.length()) + 1];
            int index = chs.length - 1;
            int index1 = num1.length() - 1;
            int index2 = num2.length() - 1;
            int jinwei = 0;
            while (index1 >= 0 || index2 >= 0) {
                int value = jinwei;
                if (index1 >= 0) {
                    value += ((int) num1.charAt(index1) - 48);
                }
                if (index2 >= 0) {
                    value += ((int) num2.charAt(index2) - 48);
                }
                chs[index] = (char) (value % 10 + 48);
                jinwei = value > 9 ? 1 : 0;

                index--;
                index1--;
                index2--;
            }
            chs[0] = (char) (jinwei + 48);
            return jinwei > 0 ? new String(chs) : new String(chs, 1, chs.length - 1);
        }
    }

}

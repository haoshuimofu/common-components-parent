package leetcode;

import java.util.ArrayDeque;

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

//        grid = new char[3][3];
//        grid[0] = new char[]{'0', '1', '0'};
//        grid[1] = new char[]{'1', '0', '1'};
//        grid[2] = new char[]{'0', '1', '0'};

        grid = new char[1][7];
        grid[0] = new char[]{'1', '0', '1', '1', '0', '1', '1'};
        System.out.println("岛屿数 = " + numIslands(grid));
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
        if (grid == null || grid.length == 0) {
            return 0;
        }
        return dfs(grid);
    }

    /**
     * dfs效率高, 没有额外的数据结果, bfs需要借助queue层序遍历
     *
     * @param grid
     * @return
     */
    public static int dfs(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int numOfIsland = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    numOfIsland++;
                    subDfs(grid, i, j);
                }
            }
        }
        return numOfIsland;
    }

    private static void subDfs(char[][] grid, int i, int j) {
        if (grid[i][j] == '0') {
            return;
        }
        grid[i][j] = '0';
        if (i > 0 && grid[i - 1][j] == '1') {
            subDfs(grid, i - 1, j);
        }
        if (i < grid.length - 1 && grid[i + 1][j] == '1') {
            subDfs(grid, i + 1, j);
        }
        if (j > 0 && grid[i][j - 1] == '1') {
            subDfs(grid, i, j - 1);
        }
        if (j < grid[0].length - 1 && grid[i][j + 1] == '1') {
            subDfs(grid, i, j + 1);
        }
    }

    /**
     * bfs效率不高
     *
     * @param grid
     * @return
     */
    public static int bfs(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int numOfIsland = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    numOfIsland++;
                    subBfs(grid, i, j);
                }
            }
        }
        return numOfIsland;
    }

    private static void subBfs(char[][] grid, int i, int j) {
        if (grid[i][j] == '0') {
            return;
        }
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        queue.addFirst(new int[]{i, j});
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int k = 0; k < size; k++) {
                int[] point = queue.removeLast();
                int row = point[0];
                int column = point[1];
                grid[row][column] = '0';
                if (row < grid.length - 1 && grid[row + 1][column] == '1') {
                    queue.addLast(new int[]{row + 1, column});
                }
                if (row > 0 && grid[row - 1][column] == '1') {
                    queue.addLast(new int[]{row - 1, column});
                }
                if (column < grid[0].length - 1 && grid[row][column + 1] == '1') {
                    queue.addLast(new int[]{row, column + 1});
                }
                if (column > 0 && grid[row][column - 1] == '1') {
                    queue.addLast(new int[]{row, column - 1});
                }
            }
        }
    }

    /**
     * 200. 岛屿数量 ERROR
     *
     * @param grid
     * @return
     */
    public static int bfsError(char[][] grid) {
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

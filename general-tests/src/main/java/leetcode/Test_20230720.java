package leetcode;

import java.util.ArrayDeque;

/**
 * @author dewu.de
 * @date 2023-07-20 10:12 上午
 */
public class Test_20230720 {


    public static void main(String[] args) {

        int[][] mat = new int[5][5];
        mat[0] = new int[]{0, 1, 0, 1, 1};
        mat[1] = new int[]{1, 1, 0, 0, 1};
        mat[2] = new int[]{0, 0, 0, 1, 0};
        mat[3] = new int[]{1, 0, 1, 1, 1};
        mat[4] = new int[]{1, 0, 0, 0, 1};
        int[][] matrix = updateMatrix(mat);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }


    }

    /**
     * 01矩阵
     *
     * @param mat
     */
    public static int[][] updateMatrix(int[][] mat) {
        if (mat == null || mat.length == 0) {
            return mat;
        }
        int rows = mat.length;
        int columns = mat[0].length;
        int[][] matrix = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (mat[i][j] == 0) {
                    matrix[i][j] = 0;
                } else {
                    int[][] visit = new int[rows][columns];
                    matrix[i][j] = findNearestZero(mat, i, j, 0, visit);
                }
            }
        }
        return matrix;
    }

    public static int findNearestZero(int[][] mat, int i, int j, int dis, int[][] visit) {
        visit[i][j] = 1;
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        if (i > 0 && visit[i - 1][j] == 0) {
            if (mat[i - 1][j] == 0) {
                return dis + 1;
            } else {
                queue.addLast(new int[]{i - 1, j});
                visit[i - 1][j] = 1;
            }
        }
        if (i < mat.length - 1 && visit[i + 1][j] == 0) {
            if (mat[i + 1][j] == 0) {
                return dis + 1;
            } else {
                queue.addLast(new int[]{i + 1, j});
                visit[i + 1][j] = 1;
            }
        }

        if (j > 0 && visit[i][j - 1] == 0) {
            if (mat[i][j - 1] == 0) {
                return dis + 1;
            } else {
                queue.addLast(new int[]{i, j - 1});
                visit[i][j - 1] = 1;
            }
        }

        if (j < mat[0].length - 1 && visit[i][j + 1] == 0) {
            if (mat[i][j + 1] == 0) {
                return dis + 1;
            } else {
                queue.addLast(new int[]{i, j + 1});
                visit[i][j + 1] = 1;
            }
        }

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int k = 0; k < size; k++) {
                int[] node = queue.removeFirst();
                int tempDis = findNearestZero(mat, node[0], node[1], dis + 1, visit);
                if (tempDis < Integer.MAX_VALUE) {
                    return tempDis;
                }
            }
        }
        return Integer.MAX_VALUE;
    }
}

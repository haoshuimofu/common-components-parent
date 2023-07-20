package leetcode;

import com.alibaba.fastjson.JSON;

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
//        int[][] matrix = updateMatrix(mat);

        Integer[][] imat = JSON.parseObject("[[1,0,1,1,0,0,1,0,0,1],[0,1,1,0,1,0,1,0,1,1],[0,0,1,0,1,0,0,1,0,0],[1,0,1,0,1,1,1,1,1,1],[0,1,0,1,1,0,0,0,0,1],[0,0,1,0,1,1,1,0,1,0],[0,1,0,1,0,1,0,0,1,1],[1,0,0,0,1,1,1,1,0,1],[1,1,1,1,1,1,1,0,1,0],[1,1,1,1,0,1,0,0,1,1]]", Integer[][].class);
        int[][] newMat = new int[imat.length][imat[0].length];
        for (int i = 0; i < imat.length; i++) {
            for (int j = 0; j < imat[i].length; j++) {
                newMat[i][j] = imat[i][j];
                System.out.print(imat[i][j] + " ");
            }
            System.out.println();
        }
        int[][] matrix = updateMatrix(newMat);


        System.out.println();
        System.out.println("---------------------");
        System.out.println();

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
        int[][] visit = new int[rows][columns];
        int oneCount = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (mat[i][j] == 0) {
                    matrix[i][j] = 0;
                } else {
                    oneCount++;
                    ArrayDeque<int[]> deque = new ArrayDeque<>();
                    deque.addLast(new int[]{i, j});
                    int round = 0;
                    boolean flag = false;
                    while (!deque.isEmpty()) {
                        round++;
                        int size = deque.size();
                        for (int k = 0; k < size; k++) {
                            int[] node = deque.removeFirst();
                            if (findNearestZero(mat, node[0], node[1], visit, oneCount, deque)) {
                                matrix[i][j] = round;
                                flag = true;
                                break;
                            }
                        }
                        if (flag) {
                            break;
                        }
                    }
                }
            }
        }
        return matrix;
    }

    public static boolean findNearestZero(int[][] mat, int i, int j, int[][] visit, int oneCount, ArrayDeque<int[]> deque) {
        // 标记当前坐标在本轮已访问过
        visit[i][j] = oneCount;
        // 上
        if (i > 0 && visit[i - 1][j] != oneCount) {
            if (mat[i - 1][j] == 0) {
                return true;
            } else {
                deque.addLast(new int[]{i - 1, j});
                visit[i - 1][j] = oneCount;
            }
        }
        // 下
        if (i < mat.length - 1 && visit[i + 1][j] != oneCount) {
            if (mat[i + 1][j] == 0) {
                return true;
            } else {
                deque.addLast(new int[]{i + 1, j});
                visit[i + 1][j] = oneCount;
            }
        }
        // 左
        if (j > 0 && visit[i][j - 1] != oneCount) {
            if (mat[i][j - 1] == 0) {
                return true;
            } else {
                deque.addLast(new int[]{i, j - 1});
                visit[i][j - 1] = oneCount;
            }
        }
        // 右
        if (j < mat[0].length - 1 && visit[i][j + 1] != oneCount) {
            if (mat[i][j + 1] == 0) {
                return true;
            } else {
                deque.addLast(new int[]{i, j + 1});
                visit[i][j + 1] = oneCount;
            }
        }
        return false;
    }

}

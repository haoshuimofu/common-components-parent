package leetcode;

import com.alibaba.fastjson.JSON;

/**
 * @author dewu.de
 * @date 2023-07-27 1:15 下午
 */
public class Test_20230727 {

    public static void main(String[] args) {
        int[][] mat = new int[3][3];
        mat[0] = new int[]{1, 2, 3};
        mat[1] = new int[]{4, 5, 6};
        mat[2] = new int[]{1, 8, 9};
        System.out.println(JSON.toJSONString(findDiagonalOrder(mat)));
    }


    /**
     * 498. 对角线遍历
     * 1 2 3
     * 4 5 6
     * 7 8 9
     */
    public static int[] findDiagonalOrder(int[][] mat) {
        int[] res = new int[mat.length * mat[0].length];
        int index = 0;
        // i,j右上走i-1,j+1; 左下走i+1,j-1; 注意边界判断
        int i = 0, j = 0;
        boolean directIsRightUp = true;
        while (i < mat.length && j < mat[0].length) {
            res[index] = mat[i][j];
            index++;
            if (directIsRightUp) {
                if (i == 0) {
                    if (j == mat[0].length - 1) {
                        i++;
                    } else {
                        j++;
                    }
                    directIsRightUp = false;
                } else {
                    if (j == mat[0].length - 1) {
                        i++;
                        directIsRightUp = false;
                    } else {
                        i--;
                        j++;
                    }
                }
            } else {
                if (j == 0) {
                    if (i < mat.length - 1) {
                        i++;
                    } else {
                        j++;
                    }
                    directIsRightUp = true;
                } else {
                    if (i < mat.length - 1) {
                        i++;
                        j--;
                    } else {
                        j++;
                        directIsRightUp = true;
                    }
                }
            }
        }
        return res;
    }
}

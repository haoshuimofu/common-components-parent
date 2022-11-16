package com.demo.algorithm;

/**
 * @author dewu.de
 * @date 2022-11-16 7:34 下午
 */
public class ArrayReverse {

    public static void main(String[] args) {

        int l1 = 3;
        int l2 = 4;
        int[][] oa = new int[l1][l2];
        for (int i = 0; i < oa.length; i++) {
            for (int j = 0; j < oa[i].length; j++) {
                oa[i][j] = Integer.valueOf((i + 1) + "" + (j + 1));
            }
        }

        int[][] da = new int[l2][l1];
        for (int i = 0; i < oa.length; i++) {
            for (int j = 0; j < oa[i].length; j++) {
                da[j][l1 - i - 1] = oa[i][j];
            }
        }
        print(oa);
        System.out.println("------------");
        print(da);

    }

    private static void print(int[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j] + ", ");
            }
            System.out.println();
        }
    }

}

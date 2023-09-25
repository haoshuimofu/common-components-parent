package leetcode;

import java.util.ArrayList;
import java.util.List;

public class Test_20230925 {

    public static void main(String[] args) {
        System.out.println((int) '0');
        System.out.println((int) '1');
        System.out.println((int) '9');
        System.out.println((int) 'A');
        System.out.println((int) 'Z');
        System.out.println((int) 'a');
        System.out.println((int) 'z');
        Test_20230925 test = new Test_20230925();
        System.out.println(test.letterCombinations("23"));
        System.out.println(test.detectCapitalUse("FlaG"));
    }

    /**
     * 74. 搜索二维矩阵
     *
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        int left = 0;
        int right = m * n - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int row = mid / n;
            int column = mid % n;
            int midVal = matrix[row][column];
            if (midVal == target) {
                return true;
            } else if (midVal > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return false;
    }

    /**
     * 17. 电话号码的字母组合
     *
     * @param digits
     * @return
     */
    public List<String> letterCombinations(String digits) {
        int[][] matrix = new int[10][4];
        matrix[2] = new int[]{'a', 'b', 'c'};
        matrix[3] = new int[]{'d', 'e', 'f'};
        matrix[4] = new int[]{'g', 'h', 'i'};
        matrix[5] = new int[]{'j', 'k', 'l'};
        matrix[6] = new int[]{'m', 'n', 'o'};
        matrix[7] = new int[]{'p', 'q', 'r', 's'};
        matrix[8] = new int[]{'t', 'u', 'v'};
        matrix[9] = new int[]{'w', 'x', 'y', 'z'};
        List<String> result = new ArrayList<>();
        dfs(digits, 0, matrix, new char[digits.length()], result);
        return result;
    }

    private void dfs(String digits, int index, int[][] matrix, char[] chars, List<String> result) {
        if (index >= digits.length()) {
            return;
        }
        int[] subChars = matrix[digits.charAt(index) - 48];
        for (int i = 0; i < subChars.length; i++) {
            if (subChars[i] > 48) {
                chars[index] = (char) subChars[i];
                if (++index == digits.length()) {
                    result.add(new String(chars, 0, index));
                }
                dfs(digits, index, matrix, chars, result);
                index--;
            }
        }
    }

    /**
     * 543. 二叉树的直径
     *
     * @param root
     * @return
     */
    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 0;

    }

    /**
     * 520. 检测大写字母
     *
     * @param word
     * @return
     */
    public boolean detectCapitalUse(String word) {
        char first = word.charAt(0);
        boolean upFirst = first <= 90;
        boolean upper = false;
        boolean lower = false;
        for (int i = 1; i < word.length(); i++) {
            char curr = word.charAt(i);
            if (curr <= 90) {
                if (!upFirst || lower) {
                    return false;
                }
                upper = true;
            } else {
                if (upper) {
                    return false;
                }
                lower = true;
            }
        }
        return true;
    }
}

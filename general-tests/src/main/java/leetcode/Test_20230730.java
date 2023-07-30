package leetcode;

public class Test_20230730 {

    public static void main(String[] args) {
        int[][] matrix = new int[2][1];
        matrix[0] = new int[]{-1};
        matrix[1] = new int[]{-1};
        System.out.println(findNumberIn2DArray(matrix, -2));


        matrix = new int[5][5];
        matrix[0] = new int[]{1, 2, 3, 4, 5};
        matrix[1] = new int[]{6, 7, 8, 9, 10};
        matrix[2] = new int[]{11, 12, 13, 14, 15};
        matrix[3] = new int[]{16, 17, 18, 19, 20};
        matrix[4] = new int[]{21, 22, 23, 24, 25};
        System.out.println(findNumberIn2DArray(matrix, 19));


    }


    /**
     * 剑指 Offer 04. 二维数组中的查找
     * 解题思路-非对角线 横向和竖向二分查找
     *
     * @param matrix
     * @param target
     * @return
     */
    public static boolean findNumberIn2DArray(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        int i = 0;
        int j = 0;
        int from;
        int to;
        while (i <= matrix.length - 1 && j <= matrix[0].length - 1) {
            from = j;
            to = matrix[0].length - 1;
            while (from <= to) {
                int mid = (from + to) >> 1;
                if (matrix[i][mid] == target) {
                    return true;
                } else if (matrix[i][mid] > target) {
                    to = mid - 1;
                } else {
                    from = mid + 1;
                }
            }
            from = i;
            to = matrix.length - 1;
            while (from <= to) {
                int mid = (from + to) >> 1;
                if (matrix[mid][j] == target) {
                    return true;
                } else if (matrix[mid][j] > target) {
                    to = mid - 1;
                } else {
                    from = mid + 1;
                }
            }
            i++;
            j++;
        }
        return false;
    }

    /**
     * 剑指 Offer 05. 替换空格
     *
     * @param s
     * @return
     */
    public String replaceSpace(String s) {
        char[] chs = new char[30000];
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                chs[count++] = '%';
                chs[count++] = '2';
                chs[count++] = '0';
            } else {
                chs[count++] = s.charAt(i);
            }
        }
        return new String(chs, 0, count);
    }

    public String replaceSpace1(String s) {
        StringBuilder sb = new StringBuilder();
        int from = -1;
        int to = -1;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ') {
                if (from == -1) {
                    from = i;
                    to = i + 1;
                } else {
                    to++;
                }
                if (i == s.length() - 1) {
                    sb.append(s, from, to);
                }
            } else if (to > from) {
                sb.append(s, from, to);
                from = -1;
                to = -1;
            }
        }
        return sb.toString();
    }

}

package leetcode;

import java.util.*;

public class Test_20230831 {


    /**
     * 73. 矩阵置零
     * O(mn)的额外空间
     *
     * @param matrix
     */
    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] copy = new int[m][n];
        for (int i = 0; i < m; i++) {
            copy[i] = Arrays.copyOf(matrix[i], n);
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (copy[i][j] == 0) {

                    for (int x = 0; x < n; x++) {
                        matrix[i][x] = 0;
                    }
                    for (int x = 0; x < m; x++) {
                        matrix[x][j] = 0;
                    }
                }
            }
        }
    }

    /**
     * 垃圾-比O(mn)额外空间的慢很多很多
     *
     * @param matrix
     */
    public void setZeroes1(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[] rows = new int[m];
        int[] cloumns = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    rows[i] = 0;
                    cloumns[j] = 0;
                }
            }
        }
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < cloumns.length; j++) {
                if (rows[i] == 0 && cloumns[j] == 0) {
                    for (int x = 0; x < j; x++) {
                        matrix[i][x] = 0;
                    }
                    for (int x = 0; x < i; x++) {
                        matrix[x][j] = 0;
                    }
                }
            }
        }
    }

    // =============

    /**
     * 剑指 Offer 38. 字符串的排列
     *
     * @param s
     * @return
     */
    public String[] permutation(String s) {
        char[] chs = s.toCharArray();
        Arrays.sort(chs);
        List<String> strList = new ArrayList<>();
        boolean[] visit = new boolean[chs.length];
        char[] pathChs = new char[chs.length];
        int[] pathLen = new int[1];
        for (int i = 0; i < chs.length; i++) {
            if (i == 0 || chs[i] != chs[i - 1]) {
                doPermutation(chs, visit, pathChs, pathLen, i, strList);
            }
        }
        String[] sts = new String[strList.size()];
        int index = 0;
        for (String st : strList) {
            sts[index++] = st;
        }
        return sts;
    }

    public void doPermutation(char[] chs, boolean[] visit, char[] pathChs, int[] pathLen, int currIndex, List<String> strList) {
        if (pathLen[0] == chs.length) {
            return;
        }
        visit[currIndex] = true;
        pathChs[pathLen[0]] = chs[currIndex];
        pathLen[0]++;
        if (pathLen[0] == chs.length) {
            strList.add(new String(pathChs, 0, chs.length));
        } else {
            for (int i = 0; i < chs.length; i++) {
                // 第二条件很巧妙啊
                if (visit[i] || (i > 0 && !visit[i - 1] && chs[i - 1] == chs[i])) {
                    continue;
                }
                doPermutation(chs, visit, pathChs, pathLen, i, strList);
            }
        }
        // 回撤
        visit[currIndex] = false;
        pathLen[0]--;
    }

    // ====================

    /**
     * 108. 将有序数组转换为二叉搜索树
     *
     * @param nums
     * @return
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return dosSortedArrayToBST(nums, 0, nums.length - 1);
    }

    public TreeNode dosSortedArrayToBST(int[] nums, int from, int to) {
        int diff = to - from;
        if (diff < 0) {
            return null;
        } else if (diff == 0) {
            return new TreeNode(nums[from]);
        } else if (diff == 1) {
            TreeNode node = new TreeNode(nums[to]);
            node.left = new TreeNode(nums[from]);
            return node;
        } else if (diff == 2) {
            TreeNode node = new TreeNode(nums[from + 1]);
            node.left = new TreeNode(nums[from]);
            node.right = new TreeNode(nums[to]);
            return node;
        }
        int mid = (from + to) / 2;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = dosSortedArrayToBST(nums, from, mid - 1);
        node.right = dosSortedArrayToBST(nums, mid + 1, to);
        return node;
    }


    public static void main(String[] args) {
        Test_20230831 test = new Test_20230831();
        System.out.println(Arrays.toString(test.permutation("aab")));
    }

}

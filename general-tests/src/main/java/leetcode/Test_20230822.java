package leetcode;

import com.alibaba.fastjson.JSON;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Test_20230822 {

    public static void main(String[] args) {
        Test_20230822 test = new Test_20230822();
        System.out.println(JSON.toJSONString(test.combine(4, 2)));
    }


    /**
     * 77. 组合
     * TODO 待求最优解
     *
     * @param n
     * @param k
     * @return
     */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        ArrayDeque<Integer> path = new ArrayDeque<>();
        for (int i = 1; i <= n; i++) {
            // 选项值数量 >= k
            if (n - i + 1 >= k) {
                doCombine(n, k, i, path, result);
            }
        }
        return result;
    }

    private void doCombine(int n, int k, int curr, ArrayDeque<Integer> path, List<List<Integer>> result) {
        if (path.size() >= k) {
            return;
        }
        path.addLast(curr);
        if (path.size() == k) {
            result.add(new ArrayList<>(path));
        } else {
            for (int i = curr + 1; i <= n; i++) {
                doCombine(n, k, i, path, result);
            }
        }
        path.removeLast();
    }

    /**
     * 240. 搜索二维矩阵 II
     * TODO - 待求最优解 -Z字形求解
     *
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        int i = 0;
        int j = 0;
        while (i < m && j < n) {
            int low = i;
            int high = n - 1;
            while (low <= high) {
                int mid = (low + high) / 2;
                if (matrix[i][mid] == target) {
                    return true;
                } else if (matrix[i][mid] > target) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            low = i;
            high = m - 1;
            while (low <= high) {
                int mid = (low + high) / 2;
                if (matrix[mid][j] == target) {
                    return true;
                } else if (matrix[mid][j] > target) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            i++;
            j++;
        }
        return false;
    }

}

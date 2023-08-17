package leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author dewu.de
 * @date 2023-08-17 10:03 上午
 */
public class Test_20230817 {

    public static void main(String[] args) {
        Test_20230817 test = new Test_20230817();
        System.out.println(test.isPowerOfTwo(2));
        System.out.println(test.isPowerOfTwo(3));
        System.out.println(test.isPowerOfTwo(4));
    }

    public boolean isPowerOfTwo(int n) {
        while (n > 0) {
            if (n == 1) {
                return true;
            }
            if (n % 2 != 0) {
                return false;
            }
            n /= 2;
        }
        return false;
    }

    /**
     * 228. 汇总区间
     *
     * @param nums
     * @return
     */
    public List<String> summaryRanges(int[] nums) {
        if (nums == null) {
            return null;
        }
        if (nums.length == 0) {
            return Collections.emptyList();
        } else if (nums.length == 1) {
            return Collections.singletonList(String.valueOf(nums[0]));
        }
        List<String> res = new ArrayList<>();
        int from = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) {
                throw new IllegalArgumentException("倒序了");
            } else if (nums[i] == nums[i - 1]) {
                from = i;
            } else {
                if (nums[i] > nums[i - 1] + 1) {
                    if (from == i - 1) {
                        res.add(String.valueOf(nums[i - 1]));
                    } else {
                        res.add(nums[from] + "->" + nums[i - 1]);
                    }
                    from = i;
                }
            }
            if (i == nums.length - 1) {
                if (from == i) {
                    res.add(String.valueOf(nums[i]));
                } else {
                    res.add(nums[from] + "->" + nums[i]);
                }
            }
        }
        return res;
    }

    /**
     * 剑指Offer 47. 礼物的最大价值
     *
     * @param grid
     * @return
     */
    public int maxValue(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] value = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                value[i][j] = grid[i][j];
                int max = 0;
                if (j > 0) {
                    max = value[i][j - 1];
                }
                if (i > 0) {
                    max = Math.max(max, value[i - 1][j]);
                }
                value[i][j] += max;
            }
        }
        return value[m - 1][n - 1];
    }

    public int maxValue1(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] value = new int[m][n];
        value[0][0] = grid[0][0];
        return doMaxValue1(grid, m - 1, n - 1, value);
    }

    public int doMaxValue1(int[][] grid, int i, int j, int[][] value) {
        if (i < 0 || j < 0) {
            return 0;
        }
        if (value[i][j] > 0) {
            return value[i][j];
        }
        value[i][j] = Math.max(doMaxValue1(grid, i - 1, j, value), doMaxValue1(grid, i, j - 1, value)) + grid[i][j];
        return value[i][j];
    }

}

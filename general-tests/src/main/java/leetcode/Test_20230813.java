package leetcode;

import java.util.*;

public class Test_20230813 {

    public static void main(String[] args) {

        Test_20230813 test = new Test_20230813();
        System.out.println(test.containsNearbyDuplicate(new int[]{1, 2, 3, 1, 2, 3}, 2));
        System.out.println(test.uniquePaths(3, 7));

        int[][] grids = new int[1][1];
        grids[0] = new int[]{1, 0};
        System.out.println(test.uniquePathsWithObstacles(grids));

    }

    /**
     * 226. 翻转二叉树
     *
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {

        if (root == null) {
            return root;
        }
        TreeNode temp = root.right;
        root.right = root.left;
        root.left = temp;
        invertTree(root.left);
        invertTree(root.left);
        return root;

    }

    /**
     * 257. 二叉树的所有路径
     *
     * @param root
     * @return
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        doBinaryTreePaths(root, new ArrayList<>(), paths);
        return paths;
    }

    private void doBinaryTreePaths(TreeNode node, List<Integer> nodeValues, List<String> paths) {
        if (node == null) {
            return;
        }
        nodeValues.add(node.val);
        if (node.left == null && node.right == null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < nodeValues.size(); i++) {
                sb.append(nodeValues.get(i));
                if (i != nodeValues.size() - 1) {
                    sb.append("->");
                }
            }
            paths.add(sb.toString());
        } else {
            doBinaryTreePaths(node.left, nodeValues, paths);
            doBinaryTreePaths(node.right, nodeValues, paths);
        }
        nodeValues.remove(nodeValues.size() - 1);
    }

    /**
     * 219. 存在重复元素 II
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
//        for (int i = 0; i < nums.length - 1; i++) {
//            for (int j = i + 1; j < nums.length; j++) {
//                if (nums[j] == nums[i] && j - k <= k) {
//                    return true;
//                }
//            }
//        }
//        return false;
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            List<Integer> indexList = map.get(nums[i]);
            if (indexList == null) {
                indexList = new ArrayList<>();
                indexList.add(i);
                map.put(nums[i], indexList);
            } else {
                for (Integer idx : indexList) {
                    if (i - idx <= k) {
                        return true;
                    }
                }
                indexList.add(i);
            }
        }
        return false;
    }

    /**
     * 62. 不同路径
     *
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths(int m, int n) {
        int[] count = new int[1];
        doUniquePaths(m, n, 0, 0, count);
        return count[0];
    }

    // 超时
    public void doUniquePaths(int m, int n, int rows, int columns, int[] count) {
        if (rows == m - 1 && columns == n - 1) {
            count[0]++;
            return;
        }
        if (rows < m - 1) {
            doUniquePaths(m, n, rows + 1, columns, count);
        }
        if (columns < n - 1) {
            doUniquePaths(m, n, rows, columns + 1, count);
        }
    }

    /**
     * paths[m][n] = paths[m][n-1] + paths[m-1][n];
     *
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths1(int m, int n) {
        int[][] paths = new int[m][n];
        return doUniquePaths1(m - 1, n - 1, paths);
    }

    public int doUniquePaths1(int rows, int columns, int[][] paths) {
        if (rows + columns == 0) {
            return 1;
        } else if (rows + columns == 1) {
            return 1;
        }
        int value = paths[rows][columns];
        if (value == 0) {
            if (columns > 0) {
                value += doUniquePaths1(rows, columns - 1, paths);
            }
            if (rows > 0) {
                value += doUniquePaths1(rows - 1, columns, paths);

            }
            paths[rows][columns] = value;
        }
        return value;
    }

    /**
     * 63. 不同路径 II
     *
     * @param obstacleGrid
     * @return
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int[][] paths = new int[obstacleGrid.length][obstacleGrid[0].length];
        return doUniquePathsWithObstacles(obstacleGrid, obstacleGrid.length - 1, obstacleGrid[0].length - 1, paths);
    }

    public int doUniquePathsWithObstacles(int[][] obstacleGrid, int rows, int columns, int[][] paths) {
        if (obstacleGrid[rows][columns] == 1) {
            return 0;
        }
        if (rows + columns == 0) {
            return 1 - obstacleGrid[rows][columns];
        }
        int value = paths[rows][columns];
        if (value == 0) {
            if (columns > 0) {
                value += doUniquePathsWithObstacles(obstacleGrid, rows, columns - 1, paths);
            }
            if (rows > 0) {
                value += doUniquePathsWithObstacles(obstacleGrid, rows - 1, columns, paths);

            }
            paths[rows][columns] = value;
        }
        return value;
    }

    public int uniquePathsWithObstacles1(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] paths = new int[m][n];
        if (obstacleGrid[0][0] == 0) {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (obstacleGrid[i][j] == 1) {
                        paths[i][j] = 0;
                    } else if (i == 0 && j == 0) {
                        paths[i][j] = 1;
                    } else {
                        int value = 0;
                        if (i > 0) {
                            value += paths[i - 1][j];

                        }
                        if (j > 0) {
                            value += paths[i][j - 1];
                        }
                        paths[i][j] = value;
                    }


                }
            }
        }
        return paths[m - 1][n - 1];
    }

}

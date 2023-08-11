package leetcode;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class Test_20230811 {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);

        Test_20230811 test = new Test_20230811();
        System.out.println(test.sumNumbers(root));

        System.out.println("///////////////////////");

        System.out.println(JSON.toJSONString(test.combinationSum(new int[]{2, 3, 6, 7}, 7)));

        System.out.println("///////////////////////");

//        System.out.println(test.numSubarrayProductLessThanK1(new int[]{10, 9, 10, 4, 3, 8, 3, 3, 6, 2, 10, 10, 9, 3}, 19));
        System.out.println(test.numSubarrayProductLessThanK1(new int[]{10, 5, 2, 6}, 100));

    }

    /**
     * LCR 049. 求根节点到叶节点数字之和
     *
     * @param root
     * @return
     */
    public int sumNumbers(TreeNode root) {
        int[] sum = new int[1];
        sumTree(root, new ArrayList<>(), sum);
        return sum[0];
    }


    private void sumTree(TreeNode node, List<Integer> path, int[] sum) {
        if (node == null) {
            return;
        }
        path.add(node.val);
        if (node.left == null && node.right == null) {
            sum[0] += sum(path);
        } else {
            sumTree(node.left, path, sum);
            sumTree(node.right, path, sum);
        }
        path.remove(path.size() - 1);
    }

    private int sum(List<Integer> nums) {
        int size = nums.size() - 1;
        int sum = 0;
        int factor = 1;
        while (size >= 0) {
            sum += nums.get(size) * factor;
            size--;
            factor *= 10;
        }
        return sum;
    }

    /**
     * 34. 在排序数组中查找元素的第一个和最后一个位置
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        int left = -1;
        int right = -1;
        int low = 0;
        int high = nums.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[mid] == target) {
                left = mid; // 不停交换left
                if (right == -1) {
                    right = mid;
                }
                if (mid == 0) {
                    break;
                } else {
                    if (nums[mid - 1] == nums[mid]) {
                        left = mid - 1;
                        high = mid - 1;
                    } else {
                        break;
                    }
                }
            } else if (nums[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        if (left == -1) {
            return new int[]{left, right};
        } else {
            // 向有拓展right
            while (right < nums.length && nums[right] == target) {
                right++;
            }
            return new int[]{left, right - 1};
        }
    }

    /**
     * 39. 组合总和
     *
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        boolean[] visit = new boolean[candidates.length];
        int[] sum = new int[1];
        for (int i = 0; i < candidates.length; i++) {
            combination(candidates, target, i, visit, new ArrayList<>(), new ArrayList<>(), i, sum, res);
        }
        return res;
    }

    private void combination(int[] candidates, int target, int fromIndex,
                             boolean[] visit, List<Integer> pathIndex, List<Integer> path,
                             int currIndex, int[] sum, List<List<Integer>> paths) {
        if (currIndex >= candidates.length) {
            return;
        }
        visit[currIndex] = true;
        sum[0] += candidates[currIndex];
        pathIndex.add(currIndex);
        path.add(candidates[currIndex]);

//        System.out.println(JSON.toJSONString(path));
        if (sum[0] == target) {
            paths.add(new ArrayList<>(path));
        }
        for (int i = 0; i < candidates.length; i++) {
            if (!visit[i] && i > fromIndex) {
                combination(candidates, target, fromIndex, visit, pathIndex, path, i, sum, paths);
            }
        }
        visit[currIndex] = false;
        sum[0] -= candidates[currIndex];
        pathIndex.remove(pathIndex.size() - 1);
        path.remove(path.size() - 1);
    }

    /**
     * LCR 009. 乘积小于 K 的子数组 - 不连续
     * <p>
     * 也是求子集
     *
     * @param nums
     * @param k
     * @return
     */
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        double[] valueAndCount = new double[2];
        for (int i = 0; i < nums.length; i++) {
            valueAndCount[0] = 1;
            doNumSubarrayProductLessThanK(nums, k, i, valueAndCount, new ArrayList<>());
        }
        return (int) valueAndCount[1];
    }

    public void doNumSubarrayProductLessThanK(int[] nums, int k, int currIndex, double[] valueAndCount, List<Integer> path) {
        if (currIndex >= nums.length) {
            return;
        }
        path.add(nums[currIndex]);
        System.out.println(JSON.toJSONString(path));
        double factor = k * 1.0 / nums[currIndex];
        if (valueAndCount[0] >= factor) {
            System.out.println(JSON.toJSONString(path) + "---");
            path.remove(path.size() - 1);
            return;
        }
        valueAndCount[0] *= nums[currIndex];
        valueAndCount[1]++;
        for (int i = currIndex + 1; i <= currIndex + 1; i++) {
            doNumSubarrayProductLessThanK(nums, k, i, valueAndCount, path);
        }
        path.remove(path.size() - 1);
        valueAndCount[0] /= nums[currIndex];
    }

    /**
     * 乘积小于 K 的子数组
     * -->连续子数组
     */
    public int numSubarrayProductLessThanK1(int[] nums, int k) {
        double value = nums[0] < k ? nums[0] : 1;
        int count = nums[0] < k ? 1 : 0;
        int left = 0;
        int right = 1;
        boolean[] flag = new boolean[nums.length];
        while (right < nums.length) {
            if (nums[right] >= k) {
                value = 1;
                right++;
                left = right;
            } else {
                if (!flag[right]) {
                    flag[right] = true;
                    count++;
                }
                if (right > left) {
                    if (k / value <= nums[right]) {
                        // value * nums[i] >= k
                        value /= nums[left];
                        left++;
                    } else {
                        value *= nums[right];
                        count++;
                        right++;
                    }
                } else {
                    value *= nums[right];
                    right++;
                }
            }
        }
        return count;
    }
}

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

//        System.out.println(JSON.toJSONString(test.combinationSum(new int[]{2, 3, 6, 7}, 7)));
        System.out.println(JSON.toJSONString(test.combinationSum(new int[]{3, 5, 8}, 11)));

        System.out.println("///////////////////////");

//        System.out.println(test.numSubarrayProductLessThanK(new int[]{10, 9, 10, 4, 3, 8, 3, 3, 6, 2, 10, 10, 9, 3}, 19));
        System.out.println(test.numSubarrayProductLessThanK(new int[]{10, 5, 2, 6}, 100));

        System.out.println(Math.pow(10, 9) > Integer.MAX_VALUE);

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
     * TODO tag再优化
     *
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        int[] sum = new int[1];
        for (int i = 0; i < candidates.length; i++) {
            doCombination(candidates, target, i, sum, path, result);
        }
        return result;
    }

    private void doCombination(int[] candidates, int target, int currIndex, int[] sum,
                               List<Integer> path, List<List<Integer>> result) {
        if (sum[0] >= target || currIndex >= candidates.length) {
            return;
        }
        int value = candidates[currIndex];
        path.add(value);
        sum[0] += value;
        if (sum[0] == target) {
            result.add(new ArrayList<>(path));
        } else if (sum[0] < target) {
            for (int j = currIndex + 1; j < candidates.length; j++) {
                doCombination(candidates, target, j, sum, path, result);
            }
            int delta = target - sum[0];
            int repeat = delta / value;
            if (delta > 0 && repeat > 0) {
                for (int i = 1; i <= repeat; i++) {
                    path.add(value);
                    sum[0] += value;
                    if (sum[0] == target) {
                        result.add(new ArrayList<>(path));
                        break;
                    } else if (sum[0] < target) {
                        for (int j = currIndex + 1; j < candidates.length; j++) {
                            doCombination(candidates, target, j, sum, path, result);
                        }
                    } else {
                        break;
                    }
                }
                for (int i = 1; i <= repeat; i++) {
                    path.remove(path.size() - 1);
                    sum[0] -= value;
                }
            }
        }
        path.remove(path.size() - 1);
        sum[0] -= value;
    }

    /**
     * LCR 009. 乘积小于 K 的子数组
     * 713. 乘积小于 K 的子数组
     *
     * @param nums
     * @param k
     * @return
     */
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if (k == 0) {
            return 0;
        }
        int value = 1;
        int count = 0;
        int from = 0;
        int index = 0;
        while (index < nums.length) {
            if (nums[index] >= k) {
                value = 1;
                index++;
                from = index;
            } else {
                if (value * nums[index] >= k) {
                    value /= nums[from];
                    from++;
                } else {
                    value *= nums[index];
                    count += (index - from + 1);
                    index++;
                }
            }
        }
        return count;
    }

    /**
     * 面试题 02.03. 删除中间节点
     *
     * @param node
     */
    public void deleteNode(ListNode node) {
        ListNode next = node.next;
        ListNode nextNext = next.next;
        next.next = null;
        node.next = nextNext;
        node.val = next.val;
    }
}

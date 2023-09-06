package leetcode;

import com.alibaba.fastjson.JSON;
import java.util.*;

/**
 * @author dewu.de
 * @date 2023-09-06 10:05 上午
 */
public class Test_20230906 {


    public static void main(String[] args) {
        Test_20230906 test = new Test_20230906();
        System.out.println(Arrays.toString(test.nextGreaterElement(new int[]{1, 7, 3}, new int[]{1, 0, 3, 9, 5, 7}))); // 3,-1,9
        System.out.println(Arrays.toString(test.nextGreaterElements(new int[]{1, 2, 1})));
        System.out.println(test.nextGreaterElement_III(2100));

        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out.println(JSON.toJSONString(test.levelOrderBottom(root)));

    }

    /**
     * 496. 下一个更大元素 I
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> nextMap = new HashMap<>();
        Deque<Integer> stack = new ArrayDeque<>();
        for (int num : nums2) {
            while (!stack.isEmpty() && num > stack.peek()) {
                int val = stack.pop();
                if (!nextMap.containsKey(val)) {
                    nextMap.put(val, num);
                }
            }
            stack.push(num);
        }
        int[] next = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            next[i] = nextMap.getOrDefault(nums1[i], -1);
        }
        return next;
    }

    /**
     * 503. 下一个更大元素 II
     * [100,1,11,1,120,111,123,1,-1,-100]
     * [120,11,120,120,123,123,-1,100,100,100]
     *
     * @param nums
     * @return
     */
    public int[] nextGreaterElements(int[] nums) {
        // 声明一个结果, 全部用-1填充
        int[] result = new int[nums.length];
        Arrays.fill(result, 0, nums.length, -1);
        // 单调栈, element[0] = num, element[1] = index
        Deque<int[]> stack = new ArrayDeque<>();
        for (int i = 0; i < 2 * nums.length - 1; i++) {
            int num = nums[i % nums.length];
            while (!stack.isEmpty() && num > stack.peek()[0]) {
                // 弹出头部元素
                int[] numAndIndex = stack.pop();
                // 如果下标超过了nums最大值就什么都不做, 否则记录对应下标的下一个更大元素值
                if (numAndIndex[1] < nums.length) {
                    result[numAndIndex[1]] = num;
                }
            }
            stack.push(new int[]{num, i});
        }
        return result;
    }

    /**
     * 556. 下一个更大元素 III
     *
     * @param n
     * @return
     */
    public int nextGreaterElement_III(int n) {
        List<Integer> nums = new ArrayList<>();
        while (n > 0) {
            nums.add(n % 10);
            n /= 10;
        }
        System.out.println(JSON.toJSONString(nums));
        Collections.reverse(nums);
        System.out.println(JSON.toJSONString(nums));
        for (int i = 1; i < nums.size(); i++) {
            if (nums.get(i) > nums.get(i - 1)) {

            }
        }
        return -1;
    }

    /**
     * 107. 二叉树的层序遍历 II
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.addLast(root);
        while (!stack.isEmpty()) {
            int size = stack.size();
            List<Integer> vals = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                TreeNode node = stack.pollFirst();
                vals.add(node.val);
                if (node.left != null) {
                    stack.addLast(node.left);
                }
                if (node.right != null) {
                    stack.addLast(node.right);
                }
            }
            result.add(vals);
        }
        Collections.reverse(result);
        return result;
    }

}

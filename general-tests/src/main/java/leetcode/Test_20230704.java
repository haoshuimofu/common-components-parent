package leetcode;

/**
 * @author dewu.de
 * @date 2023-07-04 9:52 上午
 */
public class Test_20230704 {

    public TreeNode searchBST(TreeNode root, int val) {
        return doSearchBST(root, val);
    }

    public TreeNode doSearchBST(TreeNode node, int val) {
        if (node != null) {
            if (node.val == val) {
                return node;
            } else if (node.val > val) {
                return doSearchBST(node.left, val);
            } else {
                return doSearchBST(node.right, val);
            }
        }
        return null;
    }


    public static void main(String[] args) {
        int[] nums = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println("雨水=" + trap(nums));
    }

    /**
     * 接雨水
     * 快慢指针: slow=0, fast=1
     *
     * @param height
     * @return
     */
    public static int trap(int[] height) {
        // 数组长度小于3根本盛不了水
        if (height == null || height.length <= 2) {
            return 0;
        }
        int sum = 0;
        int slow = 0;
        int fast = -1;
        while (slow < height.length - 1) {
            fast = slow + 1;
            if (height[slow] == 0 || height[fast] >= height[slow]) {
                slow++;
                continue;
            }
            int nearMax = -1;  // 第一个 >= slow值 的下标
            int closeMin = -1; // 值 < slow值 且 值最接近 slow值的下标
            while (fast < height.length) {
                if (height[fast] >= height[slow]) {
                    nearMax = fast;
                    break;
                }
                if (height[fast] < height[slow] && height[fast] > 0 && fast - slow > 1) {
                    if (closeMin == -1 || height[fast] >= height[closeMin]) {
                        closeMin = fast;
                    }
                }
                fast++;
            }
            if (nearMax != -1) {
                for (int i = slow + 1; i < nearMax; i++) {
                    sum += (height[slow] - height[i]);
                }
                slow = nearMax;
            } else if (closeMin != -1) {
                for (int i = slow + 1; i < closeMin; i++) {
                    if (height[i] < height[closeMin]) {
                        sum += (height[closeMin] - height[i]);
                    }
                }
                slow = closeMin;
            } else {
                break;
            }
        }
        return sum;
    }
}



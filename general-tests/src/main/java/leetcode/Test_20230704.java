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
        while (slow < height.length - 1) {
            int fast = slow + 1;
            if (height[slow] == 0 || height[fast] >= height[slow]) {
                slow++;
                continue;
            }
            int nearMax = -1; // 比slow值最大最近的下标
            int nearMin = -1; // 比slow值小且最接近slow值且距slow最远的下标
            while (fast < height.length) {
                // 第一个比slow值大的且不相邻的下标
                if (height[fast] >= height[slow]) {
                    nearMax = fast;
                    break;
                }
                // 比slow值小且值最近slow的下标
                if (height[fast] < height[slow] && height[fast] > 0 && fast - slow > 1) {
                    if (nearMin == -1 || height[fast] >= height[nearMin]) {
                        nearMin = fast;
                    }
                }
                fast++;
            }
            if (nearMax != -1) {
                for (int i = slow + 1; i < nearMax; i++) {
                    sum += (height[slow] - height[i]);
                }
                slow = nearMax;
            } else if (nearMin != -1) {
                for (int i = slow + 1; i < nearMin; i++) {
                    if (height[i] < height[nearMin]) {
                        sum += (height[nearMin] - height[i]);
                    }
                }
                slow = nearMin;
            } else {
                break;
            }
        }
        return sum;
    }
}



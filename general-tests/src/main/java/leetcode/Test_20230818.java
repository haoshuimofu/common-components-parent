package leetcode;

import java.util.Arrays;

/**
 * @author dewu.de
 * @date 2023-08-18 11:25 上午
 */
public class Test_20230818 {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(2);
        root.left.left.left = new TreeNode(3);
        root.left.left.right = new TreeNode(-2);
        root.left.right.right = new TreeNode(1);

        root.right = new TreeNode(-3);
        root.right.right = new TreeNode(11);

        Test_20230818 test = new Test_20230818();
        System.out.println(test.missingNumber(new int[]{0, 1, 3}));

        //
        TreeNode r = new TreeNode(3);
        r.right = new TreeNode(4);
        r.left = new TreeNode(1);
        r.left.right = new TreeNode(2);
        System.out.println(test.kthLargest(r, 1));

    }

    /**
     * * [3,9,20, 15,7]
     * * [9,3,15,20, 7]
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return doBuildTree(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    private TreeNode doBuildTree(int[] preorder, int preFrom, int preTo,
                                 int[] inorder, int inFrom, int inTo) {
        if (preFrom > preTo) {
            return null;
        }
        TreeNode node = new TreeNode(preorder[preFrom]);
        if (preFrom < preTo) {
            // 找node.val在inorder中的下标, 找出left节点数
            int leftCount = 0;
            for (int i = inFrom; i <= inTo; i++) {
                if (inorder[i] == node.val) {
                    leftCount = i - inFrom;
                    break;
                }
            }
            if (leftCount > 0) {
                node.left = doBuildTree(preorder, preFrom + 1, preFrom + leftCount, inorder, inFrom, inFrom + leftCount);
            }
            if (inFrom + leftCount < inTo) {
                node.right = doBuildTree(preorder, preFrom + leftCount + 1, preTo, inorder, inFrom + leftCount + 1, inTo);
            }
        }
        return node;
    }

    //=====

    /**
     * 剑指 Offer 50. 第一个只出现一次的字符
     *
     * @param s
     * @return
     */
    public char firstUniqChar(String s) {
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[(int) s.charAt(i) - 97] += 1;
        }
        for (int i = 0; i < s.length(); i++) {
            if (count[(int) s.charAt(i) - 97] == 1) {
                return s.charAt(i);
            }
        }
        return ' ';
    }

    /**
     * 剑指 Offer 40. 最小的k个数
     *
     * @param arr
     * @param k
     * @return
     */
    public int[] getLeastNumbers(int[] arr, int k) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (i == k) {
                break;
            }
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        int[] res = new int[k];
        System.arraycopy(arr, 0, res, 0, k);
        return res;
    }

    /**
     * Arrays.sort比自己手写排序快多了
     *
     * @param arr
     * @param k
     * @return
     */
    public int[] getLeastNumbers1(int[] arr, int k) {
        Arrays.sort(arr);
        int[] res = new int[k];
        System.arraycopy(arr, 0, res, 0, k);
        return res;
    }

    /**
     * 剑指 Offer 53 - II. 0～n-1中缺失的数字
     *
     * @param nums
     * @return
     */
    public int missingNumber(int[] nums) {
        int[] flag = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            flag[nums[i]] = 1;
        }
        for (int i = 0; i < flag.length; i++) {
            if (flag[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 剑指 Offer 53 - I. 在排序数组中查找数字 I
     *
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        int targetIndex = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[mid] == target) {
                if (mid == 0 || nums[mid - 1] != target) {
                    targetIndex = mid;
                    break;
                } else {
                    high = mid - 1;
                }
            } else if (nums[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        int count = 0;
        if (targetIndex >= 0) {
            while (targetIndex <= nums.length - 1 && nums[targetIndex] == target) {
                count++;
                targetIndex++;
            }
        }
        return count;
    }

    /**
     * 剑指 Offer 54. 二叉搜索树的第k大节点
     * 关键点: 如何终止递归???
     *
     * @param root
     * @param k
     * @return
     */
    public int kthLargest(TreeNode root, int k) {
        int[] res = new int[2];
        doKthLargest(root, k, res);
        return res[1];
    }

    public void doKthLargest(TreeNode node, int k, int[] res) {
        if (node == null || res[0] >= k) {
            return;
        }
        doKthLargest(node.right, k, res);
        if (res[0] < k) {
            res[0]++;
            res[1] = node.val;
            if (res[0] == k) {
                return;
            }
            if (res[0] < k) {
                doKthLargest(node.left, k, res);
            }
        }
    }

}

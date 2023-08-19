package leetcode;

import com.alibaba.fastjson.JSON;
import com.demo.algorithm.Tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test_20230819 {

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        Test_20230819 test = new Test_20230819();

        head = new ListNode(3);
        head.next = new ListNode(5);
        System.out.println(test.reverseBetween(head, 1, 2));

        System.out.println(test.removeDuplicates(new int[]{1, 1, 1, 2, 2, 3}));

        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out.println(test.isBalanced(root));

        int[][] matrix = new int[3][3];
        matrix[0] = new int[]{1, 2, 3};
        matrix[1] = new int[]{4, 5, 6};
        matrix[2] = new int[]{7, 8, 9};
        System.out.println(JSON.toJSONString(test.spiralOrder(matrix)));
    }

    /**
     * 二叉搜索树中第K小的元素
     *
     * @param root
     * @param k
     * @return
     */
    public int kthSmallest(TreeNode root, int k) {
        int[] indexValue = new int[2];
        doKthSamllest(root, k, indexValue);
        return indexValue[1];
    }

    private void doKthSamllest(TreeNode node, int k, int[] indexValue) {
        if (node == null || indexValue[0] >= k) {
            return;
        }
        doKthSamllest(node.left, k, indexValue);
        if (indexValue[0] < k) {
            indexValue[0]++;
            indexValue[1] = node.val;
            if (indexValue[0] < k) {
                doKthSamllest(node.right, k, indexValue);
            }
        }
    }

    /**
     * 92. 反转链表 II
     *
     * @param head
     * @param left
     * @param right
     * @return
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || head.next == null || left == right) {
            return head;
        }
        boolean leftFlag = false; // 是否已经找到了left
        boolean rightFlag = false; // 是否找到了right
        ListNode tempHead = null;
        ListNode tempTail = null;
        int index = 0;
        ListNode prev = null;
        ListNode node = head;
        while (node != null) {
            index++;
            ListNode next = node.next;
            if (index == left) {
                leftFlag = true;
                // 将node从原链表中删除
                node.next = null;
                if (prev == null) {
                    head = next;
                } else {
                    prev.next = next;
                }
                // 以node创建temp链表
                tempHead = node;
                tempTail = node;

                node = next;
            } else if (index < left) {
                prev = node;
                node = next;
            } else {
                // 继续执行 - 将node从原链表中删除
                node.next = null;
                if (prev == null) {
                    head = next;
                } else {
                    prev.next = next;
                }
                // node加到temp链表头部
                node.next = tempHead;
                tempHead = node;

                if (index == right) {
                    rightFlag = true;
                    break;
                }
                node = next;
            }
        }
        if (leftFlag) {
            if (rightFlag) {
                if (prev == null) {
                    tempTail.next = head;
                    return tempHead;
                } else {
                    ListNode next = prev.next;
                    prev.next = tempHead;
                    tempTail.next = next;
                    return head;
                }
            } else {
                // 没有匹配到right, 需要再把temp还原到原链表中
                node = tempHead;
                while (node != null) {
                    ListNode next = node.next;
                    node.next = null;
                    if (prev == null) {
                        node.next = head;
                        head = node;
                    } else {
                        node.next = prev.next;
                        prev.next = node;
                    }
                    node = next;
                }
                return head;
            }
        }
        return head;
    }

    /**
     * 287. 寻找重复数 - TODO 待求最优解
     *
     * @param nums
     * @return
     */
    public int findDuplicate(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 1; i++) {
            if ((i == 0 && nums[i] == nums[i + 1]) || (i > 0 && ((nums[i] == nums[i - 1]) || (nums[i] == nums[i + 1])))) {
                return nums[i];
            }
        }
        return -1;
    }

    /**
     * 80. 删除有序数组中的重复项 II
     *
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length < 3) {
            return nums == null ? 0 : nums.length;
        }
        int deleteCount = 0; // 记录删除了多少个元素
        int from = 0;
        for (int i = 1; i < nums.length; i++) {
            // 当前元素和前一个不一致
            if (nums[i] != nums[i - 1]) {
                int tempDeleteCount = i - from - 2;
                if (tempDeleteCount > 0) {
                    // nums[i-1]重复超过2
                    deleteCount += tempDeleteCount;
                }
                from = i;
            } else if (i == nums.length - 1) {
                int tempDeleteCount = i - from + 1 - 2;
                if (tempDeleteCount > 0) {
                    deleteCount += tempDeleteCount;
                }
            }
            nums[i - deleteCount] = nums[i];
        }
        return nums.length - deleteCount;
    }

    /**
     * 148. 排序链表
     *
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        int[] flag = new int[2 * (int) Math.pow(10, 5) + 1];
        ListNode node = head;
        while (node != null) {
        }
        return null;
    }

    /**
     * 剑指 Offer 55 - II. 平衡二叉树
     * <p>
     * 平衡二叉树定义: 书中的每个节点左右子树高度差都不超过1
     *
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {
        boolean balanced = root == null || Math.abs(getTreeHeight(root.left, 1) - getTreeHeight(root.right, 1)) <= 1;
        if (balanced && root != null) {
            balanced = isBalanced(root.left) && isBalanced(root.right);
        }
        return balanced;
    }

    public int getTreeHeight(TreeNode node, int height) {
        if (node == null) {
            return height;
        }
        if (node.left == null && node.right == null) {
            return height + 1;
        }
        return Math.max(getTreeHeight(node.left, height + 1), getTreeHeight(node.right, height + 1));
    }

    /**
     * 剑指 Offer 29. 顺时针打印矩阵
     *
     * @param matrix
     * @return
     */
    public int[] spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return new int[0];
        } else if (matrix.length == 1) {
            return matrix[0];
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] visit = new int[m][n];
        int[] nums = new int[m * n];
        int index = 0;
        int dir = 0; // 0-右, 1-下, 2-左, 3-上
        int i = 0;
        int j = 0;
        while (i >= 0 && i < m && j >= 0 && j < n && visit[i][j] == 0) {
            visit[i][j] = 1;
            nums[index] = matrix[i][j];
            index++;
            if (dir == 0) {
                if (j == n - 1 || visit[i][j + 1] == 1) {
                    i++;
                    dir = 1;
                } else {
                    j++;
                }
            } else if (dir == 1) {
                if (i == m - 1 || visit[i + 1][j] == 1) {
                    j--;
                    dir = 2;
                } else {
                    i++;
                }
            } else if (dir == 2) {
                if (j == 0 || visit[i][j - 1] == 1) {
                    i--;
                    dir = 3;
                } else {
                    j--;
                }
            } else if (dir == 3) {
                if (i == 0 || visit[i - 1][j] == 1) {
                    j++;
                    dir = 0;
                } else {
                    i--;
                }
            }

        }
        return nums;
    }

    private List<Integer> arrayToList(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }
        List<Integer> list = new ArrayList<>(nums.length);
        for (int num : nums) {
            list.add(num);
        }
        return list;
    }

}

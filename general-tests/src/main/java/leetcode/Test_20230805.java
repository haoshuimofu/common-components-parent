package leetcode;

import com.demo.algorithm.Tree;
import com.sun.org.apache.bcel.internal.generic.RET;

public class Test_20230805 {

    public static void main(String[] args) {
        TreeNode A = new TreeNode(4);
        A.left = new TreeNode(2);
        A.right = new TreeNode(3);
        A.left.left = new TreeNode(4);
        A.left.right = new TreeNode(5);
        A.left.left.left = new TreeNode(8);
        A.left.left.right = new TreeNode(9);

        A.right.left = new TreeNode(6);
        A.right.right = new TreeNode(7);

        TreeNode B = new TreeNode(4);
        B.left = new TreeNode(8);
        B.right = new TreeNode(9);
        System.out.println(isSubStructure(A, B));

    }

    /**
     * 剑指 Offer 26. 树的子结构
     *
     * @param A
     * @param B
     * @return
     */
    public static boolean isSubStructure(TreeNode A, TreeNode B) {
        if (A == null || B == null) {
            return false;
        }
        if (A.val == B.val) {
            if (isSub(A, B)) {
                return true;
            } else if (isSubStructure(A.left, B) || isSubStructure(A.right, B)) {
                return true;
            }
        } else {
            if (isSubStructure(A.left, B) || isSubStructure(A.right, B)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSub(TreeNode a, TreeNode b) {
        if (b == null) {
            return true;
        } else {
            if (a == null || a.val != b.val) {
                return false;
            }
            return isSub(a.left, b.left) && isSub(a.right, b.right);
        }
    }

    /**
     * 剑指 Offer 25. 合并两个排序的链表
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = null;
        ListNode prev = null;
        ListNode a = l1;
        ListNode b = l2;
        while (true) {
            if (a == null) {
                if (head == null) {
                    head = b;
                    break;
                } else {
                    prev.next = b;
                    break;
                }
            } else if (b == null) {
                if (head == null) {
                    head = a;
                    break;
                } else {
                    prev.next = a;
                    break;
                }
            } else {
                if (a.val <= b.val) {
                    ListNode next = a.next;
                    a.next = null;
                    if (head == null) {
                        head = a;
                        prev = a;
                    } else {
                        prev.next = a;
                        prev = a;
                    }
                    a = next;
                } else {
                    ListNode next = b.next;
                    b.next = null;
                    if (head == null) {
                        head = b;
                        prev = b;
                    } else {
                        prev.next = b;
                        prev = b;
                    }
                    b = next;
                }
            }
        }
        return head;
    }

    /**
     * 剑指 Offer 27. 二叉树的镜像
     * @param root
     * @return
     */
    public TreeNode mirrorTree(TreeNode root) {
        if (root == null) {
            return root;
        }
        TreeNode left = root.left;
        root.left = root.right;
        root.right = left;
        mirrorTree(root.left);
        mirrorTree(root.right);
        return  root;
    }

}

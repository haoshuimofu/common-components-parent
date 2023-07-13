package leetcode;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author dewu.de
 * @date 2023-07-13 9:34 上午
 */
public class Test_20230713 {

    public static void main(String[] args) {
        System.out.println(alternateDigitSum(521));

        ListNode n_1 = new ListNode(1);
        ListNode n_2 = new ListNode(2);
        n_1.next = n_2;

        ListNode n_3 = new ListNode(3);
        ListNode n_4 = new ListNode(4);
        n_3.next = n_4;

        ListNode n_5 = new ListNode(5);
        ListNode n_6 = new ListNode(6);
        ListNode n_7 = new ListNode(7);
        n_5.next = n_6;
        n_6.next = n_7;

        n_2.next = n_5;
        n_4.next = n_5;
        ListNode crossedNode = getIntersectionNode(n_1, n_3);
        System.out.println(crossedNode.val);
    }

    /**
     * 2544. 交替数字和
     *
     * @param n
     * @return
     */
    public static int alternateDigitSum(int n) {
        Stack<Integer> stack = new Stack<>();
        while (n > 0) {
            stack.push(n % 10);
            n = n / 10;
        }
        int sum = 0;
        int index = 0;
        while (!stack.isEmpty()) {
            if (index % 2 == 0) {
                sum += stack.pop();
            } else {
                sum -= stack.pop();
            }
            index++;
        }
        return sum;
    }

    /**
     * 160. 相交链表
     * 将A tail -> head, 使问题转变为: 求环形链表的入环点
     *
     * @param headA
     * @param headB
     * @return
     */
    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode tail = null;
        ListNode node = headA;
        while (node != null) {
            tail = node;
            node = node.next;
        }
        // 成环形链表
        tail.next = headA;
        // 快慢指针走B, 判断相遇点
        ListNode slow = headB;
        ListNode fast = headB;
        boolean init = true;
        while (fast != null) {
            if (slow == fast) {
                if (init) {
                    init = false;
                } else {
                    break;
                }
            }
            slow = slow.next;
            fast = fast.next;
            if (fast != null) {
                fast = fast.next;
            }
        }
        // 没有环, 说明AB没有交叉点, A去环后直接返回null
        if (fast == null) {
            tail.next = null;
            return null;
        }
        // 判断入环点
        fast = headB;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        // 去环
        tail.next = null;
        return slow;
    }

    /**
     * 931. 下降路径最小和
     * 37 + 8 的击败率，有待提高。
     * 75 + 24, 单次冒泡比 Arrays.sort效率高多了
     *
     * @param matrix
     * @return
     */
    public int minFallingPathSum(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0) {
                    dp[i][j] = matrix[i][j];
                } else {
                    int min = dp[i - 1][j];
                    if (j > 0) {
                        min = Math.min(min, dp[i - 1][j - 1]);
                    }
                    if (j < n - 1) {
                        min = Math.min(min, dp[i - 1][j + 1]);
                    }
                    dp[i][j] = min + matrix[i][j];
                }
            }
        }
        // Arrays.sort(dp[m - 1]);
        int min = dp[m - 1][0];
        for (int i = 1; i < n; i++) {
            if (dp[m - 1][i] < min) {
                min = dp[m - 1][i];
            }
        }
        return min;
    }
}

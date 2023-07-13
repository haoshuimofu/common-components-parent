package leetcode;

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
}

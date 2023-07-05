package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dewu.de
 * @date 2023-07-05 10:06 上午
 */
public class Test_20230705 {

    public static void main(String[] args) {
        // [3,2,0,-4]
        ListNode node_3 = new ListNode(3);
        ListNode node_2 = new ListNode(2);
        ListNode node_0 = new ListNode(0);
        ListNode node__4 = new ListNode(-4);
        node_3.next = node_2;
        node_2.next = node_0;
        node_0.next = node__4;
        node__4.next = node_2;
        System.out.println(detectCycle(node_3));
    }


    /**
     * 环形链表-判断链表是否有环
     *
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        // 快慢指针, 都从head开始
        // 即使fast=head.next.next开始也没问题，只要有环fast能更快追上slow
        ListNode slow = head;
        ListNode fast = head;
        boolean init = true;
        while (fast != null && slow != null) {
            if (fast == slow) {
                if (init) {
                    init = false;
                } else {
                    return true;
                }
            }
            slow = slow.next;
            fast = fast.next != null ? fast.next.next : null;
        }
        return false;
    }

    /**
     * 环形链表 II - 判断环形链表的入环点
     *
     * @param head
     * @return
     */
    public static ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        // 这里快慢一定要从同一点出发！！！
        ListNode slow = head;
        ListNode fast = head;
        boolean init = true;
        while (fast != null && slow != null) {
            if (fast == slow) {
                if (init) {
                    init = false;
                } else {
                    break;
                }
            }
            slow = slow.next;
            fast = fast.next != null ? fast.next.next : null;
        }
        if (fast == null) {
            return null;
        }
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    /**
     * 快慢指针判断环 + Map判断环的入点
     *
     * @param head
     * @return
     */
    public static ListNode detectCycleByHash(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        // 这里fast就可以先走
        ListNode slow = head;
        ListNode fast = head.next.next;
        while (fast != null && slow != null) {
            if (fast == slow) {
                break;
            }
            slow = slow.next;
            fast = fast.next != null ? fast.next.next : null;
        }
        if (fast == null) {
            return null;
        }
        slow = head;
        Map<ListNode, ListNode> nodeMap = new HashMap<>();
        while (!nodeMap.containsKey(slow)) {
            nodeMap.put(slow, slow);
            slow = slow.next;
        }
        return slow;
    }
}

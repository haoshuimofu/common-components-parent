package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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


        ListNode n_1 = new ListNode(1);
        ListNode n_2 = new ListNode(2);
        ListNode n_3 = new ListNode(3);
        ListNode n_4 = new ListNode(4);
        n_1.next = n_2;
        n_2.next = n_3;
        n_3.next = n_4;

        reorderList(n_1);

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

    /**
     * 重排链表
     * TODO 不是最优解
     *
     * @param head
     */
    public static void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }
        List<ListNode> nodes = new ArrayList<>();
        ListNode node = head;
        ListNode next;
        while (node != null) {
            next = node.next;
            node.next = null;
            nodes.add(node);
            node = next;
        }
        int low = 0;
        int high = nodes.size() - 1;
        ListNode prev = null;
        while (low < high) {
            if (prev != null) {
                prev.next = nodes.get(low);
            }
            nodes.get(low).next = nodes.get(high);
            prev = nodes.get(high);
            low++;
            high--;
        }
        if (low == high) {
            prev.next = nodes.get(low);
        }
    }
}

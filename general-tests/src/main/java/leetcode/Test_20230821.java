package leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Test_20230821 {

    /**
     * 148. 排序链表
     * ---不是最优解
     *
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode prev = head;
        ListNode node = head.next;
        while (node != null) {
            ListNode next = node.next;
            if (node.val < prev.val) {
                // 把node插入到[head, prev]中
                ListNode tempPrev = null;
                ListNode tempNode = head;
                while (tempNode != prev && tempNode.val < node.val) {
                    tempPrev = tempNode;
                    tempNode = tempNode.next;
                }
                // tempNode.val >= node.val
                node.next = null;
                if (tempPrev == null) {
                    node.next = head;
                    head = node;
                    prev.next = next;
                } else {
                    ListNode tempNext = tempNode.next;
                    tempPrev.next = node;
                    node.next = tempNode;
                }
                prev.next = next;
                node = next;
            } else {
                prev = node;
                node = next;
            }
        }
        return head;
    }


    public ListNode sortList1(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        List<ListNode> nodes = new ArrayList<>();
        ListNode node = head;
        while (node != null) {
            ListNode next = node.next;
            node.next = null;
            nodes.add(node);
            node = next;
        }
        Collections.sort(nodes, new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return Integer.compare(o1.val, o2.val);
            }
        });

        head = nodes.get(0);
        ListNode prev = head;
        for (int i = 1; i < nodes.size() ; i++) {
            nodes.get(i-1).next = nodes.get(i);
        }
        return head;
    }

}

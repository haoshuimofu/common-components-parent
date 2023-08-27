package leetcode;

/**
 * @author dewu.de
 * @date 2023-08-27 10:31 下午
 */
public class Test_20230827 {

    /**
     * 25. K 个一组翻转链表
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k <= 1) {
            return head;
        }
        int count = 0;
        ListNode prev = null;
        ListNode node = head;

        ListNode tempHead = null;
        ListNode tempTail = null;

        while (node != null) {
            ListNode next = node.next;
            node.next = null;

            // 将node从原链表中删除, 加到temp链表head
            if (prev == null) {
                head = next;
            } else {
                prev.next = next;
            }

            if (tempHead == null) {
                tempHead = node;
                tempTail = node;
            } else {
                node.next = tempHead;
                tempHead = node;
            }
            count++;

            if (count == k) {
                // 将反转的K个节点的temp链表加到
                if (prev == null) {
                    tempTail.next = head;
                    head = tempHead;
                } else {
                    ListNode tempNext = prev.next;
                    prev.next = tempHead;
                    tempTail.next = tempNext;

                }
                prev = tempTail;
                count = 0;
                tempHead = null;
                tempTail = null;
            }
            node = next;
        }

        if (count > 0) {
            // 最后一组没有K个, 再反转加到链表尾部
            node = tempHead;
            while (node != null) {
                ListNode next = node.next;
                node.next = null;

                ListNode tempNext = prev.next;
                prev.next = node;
                node.next = tempNext;
                node = next;
            }
        }
        return head;
    }


    /**
     * 82. 删除排序链表中的重复元素 II
     *
     * @param head
     * @return
     */
    public ListNode deleteDuplicates(ListNode head) {

        ListNode prev = null;
        ListNode node = head;
        while (node != null && node.next != null) {
            ListNode next = node.next;
            if (node.val != next.val) {
                prev = node;
                node = next;
            } else {
                // 删除next节点 - continue
                ListNode tempNext = next;
                while (tempNext != null && tempNext.val == node.val) {
                    ListNode targetNext = tempNext.next;
                    tempNext.next = null;
                    node.next = targetNext;
                    tempNext = targetNext;
                }
                // 再删除node
                if (prev == null) {
                    head = tempNext;
                } else {
                    prev.next = tempNext;
                }
                node = tempNext;
            }
        }
        return head;
    }

    /**
     * 55. 跳跃游戏
     *
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {
        int max = Integer.MIN_VALUE;
        int from = 0;
        int to = 0;
        while (from <= to) {
            for (int i = from; i <= to; i++) {
                max = Math.max(max, i + nums[i]);
                if (max >= nums.length - 1) {
                    return true;
                }
                if (i == to) {
                    from = to + 1;
                    to = max;
                }
            }
        }
        return false;
    }

}

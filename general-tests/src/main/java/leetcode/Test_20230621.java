package leetcode;

/**
 * @author dewu.de
 * @date 2023-06-21 3:31 下午
 */
public class Test_20230621 {

    public static void main(String[] args) {
        ListNode node_1_1 = new ListNode(1);
        ListNode node_1_2 = new ListNode(2);
        ListNode node_1_4 = new ListNode(4);
        node_1_1.next = node_1_2;
        node_1_2.next = node_1_4;

        ListNode node_2_1 = new ListNode(1);
        ListNode node_2_3 = new ListNode(3);
        ListNode node_2_4 = new ListNode(4);
        node_2_1.next = node_2_3;
        node_2_3.next = node_2_4;

        ListNode resList = mergeTwoLists(node_1_1, node_2_1);


        while (resList != null) {
            System.out.print(resList.val);
            resList = resList.next;
        }


    }

    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        ListNode head = null;
        ListNode prev = null;
        while (list1 != null || list2 != null) {
            if (list1 == null) {
                prev.next = list2;
                return head;
            } else if (list2 == null) {
                prev.next = list1;
                return head;
            } else {
                if (list1.val <= list2.val) {
                    if (head == null) {
                        head = list1;
                    } else {
                        prev.next = list1;
                    }
                    prev = list1;
                    list1 = list1.next;
                } else {
                    if (head == null) {
                        head = list2;
                    } else {
                        prev.next = list2;
                    }
                    prev = list2;
                    list2 = list2.next;
                }
            }
        }
        return head;
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

}

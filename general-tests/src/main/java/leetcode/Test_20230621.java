package leetcode;

/**
 * @author dewu.de
 * @date 2023-06-21 3:31 下午
 */
public class Test_20230621 {

    public static void main(String[] args) {
//        ListNode node_1_1 = new ListNode(1);
//        ListNode node_1_2 = new ListNode(2);
//        ListNode node_1_4 = new ListNode(4);
//        node_1_1.next = node_1_2;
//        node_1_2.next = node_1_4;
//
//        ListNode node_2_1 = new ListNode(1);
//        ListNode node_2_3 = new ListNode(3);
//        ListNode node_2_4 = new ListNode(4);
//        node_2_1.next = node_2_3;
//        node_2_3.next = node_2_4;
//
//        ListNode resList = mergeTwoLists(node_1_1, node_2_1);
//
//
//        while (resList != null) {
//            System.out.print(resList.val);
//            resList = resList.next;
//        }

        ListNode kNode_1 = buildListNode(new int[]{1, 4, 5});
//        displayListNode(kNode_1);
        ListNode kNode_2 = buildListNode(new int[]{1, 3, 4});
//        displayListNode(kNode_2);
        ListNode kNode_3 = buildListNode(new int[]{2, 6});
//        displayListNode(kNode_3);


        kNode_1 = buildListNode(new int[]{1, 2, 2});
        kNode_2 = buildListNode(new int[]{1, 1, 2});
        ListNode kNodeResult = mergeKLists(new ListNode[]{kNode_1, kNode_2});
        while (kNodeResult != null) {
            System.out.print(kNodeResult.val);
            kNodeResult = kNodeResult.next;
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

    public static ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        if (lists.length == 1) {
            return lists[0];
        }
        ListNode[] nodes = new ListNode[lists.length];
        ListNode head = null;
        int headIndex = -1;
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null && (head == null || head.val > lists[i].val)) {
                head = lists[i];
                headIndex = i;
            }
            nodes[i] = lists[i];
        }
        if (head == null) {
            return null;
        }
        nodes[headIndex] = head.next;
        ListNode prev = head;
        prev.next = null;

        while (true) {
            System.out.println("----");
            displayListNode(head);
            System.out.println();
            for (ListNode node : nodes) {
                displayListNode(node);
            }

            ListNode next = null;
            int nextIndex = -1;
            for (int i = 0; i < nodes.length; i++) {
                ListNode node = nodes[i];
                if (node == null) {
                    continue;
                }
                if (node.val == prev.val) {
                    while (true) {
                        if (node != null) {
                            if (prev.val == node.val) {
                                prev.next = node;
                                prev = node;
                                node = node.next;
                            } else {
                                if (next == null) {
                                    next = node;
                                    nextIndex = i;
                                } else if (next.val > node.val) {
                                    next = node;
                                    nextIndex = i;
                                }
                                nodes[i] = node;
                                break;
                            }
                        } else {
                            nodes[i] = null;
                            break;
                        }
                    }
                } else {
                    if (next == null) {
                        next = node;
                        nextIndex = i;
                    } else if (next.val > node.val) {
                        next = node;
                        nextIndex = i;
                    }
                }
            }
            if (next == null) {
                break;
            } else {
                prev.next = next;
                prev = next;
                nodes[nextIndex] = next.next;
            }
        }
        return head;
    }

    private static ListNode buildListNode(int[] vals) {
        if (vals == null || vals.length == 0) {
            return null;
        }
        ListNode head = null;
        ListNode prev = null;
        for (int val : vals) {
            ListNode curr = new ListNode(val);
            if (head == null) {
                head = curr;
                prev = head;
            } else {
                prev.next = curr;
                prev = curr;
            }
        }
        return head;
    }

    private static void displayListNode(ListNode head) {
        ListNode curr = head;
        while (curr != null) {
            System.out.print(curr.val + " ");
            curr = curr.next;
        }
        System.out.println();
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

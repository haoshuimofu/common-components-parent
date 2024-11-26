package leetcode2024;

public class Test_20241125 {


    public static void main(String[] args) {
        Test_20241125 test = new Test_20241125();
        int[] colors = new int[]{0, 1, 0, 0, 1};
        System.out.println(test.numberOfAlternatingGroups(colors));
    }

    /**
     * 743. 网络延迟时间
     *
     * @param times
     * @param n
     * @param k
     * @return
     */
    public int networkDelayTime(int[][] times, int n, int k) {

        return -1;
    }


    /**
     * 3206. 交替组 I
     *
     * @param colors
     * @return
     */
    public int numberOfAlternatingGroups(int[] colors) {
        int count = 0;
        int temp = colors[0] != colors[1] ? 2 : 1;
        for (int i = 2; i <= colors.length + 1; i++) {
            if (colors[i % colors.length] != colors[(i - 1) % colors.length]) {
                temp++;
            } else {
                temp = 1;
            }
            if (temp == 3) {
                count++;
                temp = 2;
            }
        }
        return count;
    }

    /**
     * 86. 分隔链表
     *
     * @param head
     * @param x
     * @return
     */
    public ListNode partition(ListNode head, int x) {

        ListNode minHead = null;
        ListNode minTail = null;

        ListNode maxHead = null;
        ListNode maxTail = null;

        ListNode node = head;
        while (node != null) {
            if (node.val < x) {
                if (minHead == null) {
                    minHead = node;
                    minTail = node;
                } else {
                    minTail.next = node;
                    minTail = node;
                }
            } else {
                if (maxHead == null) {
                    maxHead = node;
                    maxTail = node;
                } else {
                    maxTail.next = node;
                    maxTail = node;
                }
            }
            ListNode nextNode = node.next;
            node.next = null;
            node = nextNode;
        }
        if (minTail != null) {
            minTail.next = maxHead;
            return minHead;
        } else {
            return maxHead;
        }
    }

}

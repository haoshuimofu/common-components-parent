package leetcode;

import com.alibaba.fastjson.JSON;

import java.util.Stack;

/**
 * @author dewu.de
 * @date 2023-08-09 8:03 下午
 */
public class Test_20230809 {

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(printNumbers(1)));
    }

    /**
     * 剑指 Offer 06. 从尾到头打印链表
     *
     * @param head
     * @return
     */
    public int[] reversePrint(ListNode head) {
        int size = 0;
        Stack<Integer> stack = new Stack<>();
        ListNode node = head;
        while (node != null) {
            stack.add(node.val);
            size++;
            node = node.next;
        }
        int[] res = new int[size];
        size = 0;
        while (!stack.isEmpty()) {
            res[size] = stack.pop();
            size++;
        }
        return res;
    }

    public static int[] reversePrint1(ListNode head) {
        int[] vals = new int[10001];
        int size = 0;
        ListNode node = head;
        while (node != null) {
            vals[size] = node.val;
            size++;
            node = node.next;
        }
        int slow = 0;
        int high = size - 1;
        while (slow < high) {
            int temp = vals[high];
            vals[high] = vals[slow];
            vals[slow] = temp;
            slow++;
            high--;
        }
        int[] res = new int[size];
        System.arraycopy(vals, 0, res, 0, size);
        return res;
    }

    /**
     * 剑指 Offer 18. 删除链表的节点
     *
     * @param head
     * @param val
     * @return
     */
    public ListNode deleteNode(ListNode head, int val) {
        ListNode node = head;
        ListNode prev = null;
        while (node != null) {
            if (node.val == val) {
                ListNode next = node.next;
                node.next = null;
                if (prev == null) {
                    head = next;
                } else {
                    prev.next = next;
                }
                break;
            } else {
                prev = node;
                node = node.next;
            }
        }
        return head;
    }

    /**
     * 剑指 Offer 17. 打印从1到最大的n位数
     *
     * @param n
     * @return
     */
    public static int[] printNumbers(int n) {
        int size = (int) Math.pow(10, n);
        int[] res = new int[size - 1];
        for (int i = 1; i < size; i++) {
            res[i - 1] = i;
        }
        return res;
    }

}

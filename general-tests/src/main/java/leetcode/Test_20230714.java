package leetcode;

import java.util.ArrayDeque;

public class Test_20230714 {

    /**
     * 209. 长度最小的子数组
     *
     * @param target
     * @param nums
     * @return
     */
    public int minSubArrayLen(int target, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int sum = 0;
        int low = 0;
        int high = 0;
        int min = 0;
        while (high < nums.length) {
            if (nums[high] >= target) {
                min = 1;
                break;
            }
            sum += nums[high];
            if (sum >= target) {
                if (min == 0) {
                    min = high - low + 1;
                } else {
                    min = Math.min(min, high - low + 1);
                }
                sum -= (nums[low] + nums[high]);
                low++;
            } else {
                high++;
            }
        }
        return min;
    }

    /**
     * 29. 两数相除
     *
     * @param dividend
     * @param divisor
     * @return
     */
    public static int divide(int dividend, int divisor) {
        if (dividend == 0) {
            return 0;
        }
        long result = 0;
        if (divisor == 1) {
            result = Long.valueOf(dividend);
        } else if (divisor == -1) {
            result = -Long.valueOf(dividend);
        } else {
            long sum = 0;
            long num = dividend;
            num = Math.abs(num);
            long div = divisor;
            div = Math.abs(div);
            while (true) {
                sum += div;
                result++;
                if (sum == num) {
                    break;
                } else if (sum > num) {
                    result--;
                    break;
                }
            }
            if ((dividend > 0 && divisor < 0) || (dividend < 0 && divisor > 0)) {
                result = -result;
            }
        }
        if (result > 0) {
            result = Math.min(result, Long.valueOf(Integer.MAX_VALUE));
        } else if (result < 0) {
            result = Math.max(result, Long.valueOf(Integer.MIN_VALUE));
        }
        return (int) result;
    }

    public static void main(String[] args) {
        System.out.println(divide(-2147483648, 4));
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        System.out.println(oddEvenList(n1));
    }

    /**
     * 奇偶链表
     *
     * @param head
     * @return
     */
    public static ListNode oddEvenList(ListNode head) {
        if (head == null) {
            return null;
        }
        int index = 1;
        ListNode jishuLast = head;
        ListNode oushuFirst = head.next;

        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode next = curr.next;
            if (index >= 3) {
                if (index % 2 == 1) {
                    curr.next = null;
                    prev.next = next;
                    curr.next = oushuFirst;
                    jishuLast.next = curr;
                    jishuLast = curr;
                    curr = next;
                } else {
                    prev = curr;
                    curr = next;
                }
            } else {
                prev = curr;
                curr = next;
            }
            index++;
        }
        return head;
    }

    /**
     * 回文链表
     *
     * @param head
     * @return
     */
    public static boolean isPalindrome(ListNode head) {
        if (head == null) {
            return false;
        }
        ArrayDeque<ListNode> deque = new ArrayDeque<>();
        ListNode node = head;
        while (node != null) {
            deque.addLast(node);
            node = node.next;
        }

        ListNode first;
        ListNode last;
        while (true) {
            first = deque.pollFirst();
            last = deque.pollLast();
            if (first == null && last == null) {
                break;
            } else if (first != null && last != null) {
                if (first.val != last.val) {
                    return false;
                }
            } else {
                return true;
            }
        }
        return true;

    }

}

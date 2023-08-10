package leetcode;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author dewu.de
 * @date 2023-08-09 8:03 下午
 */
public class Test_20230809 {

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(printNumbers(1)));
        System.out.println(JSON.toJSONString(exchange(new int[]{1, 3, 5})));
        System.out.println(JSON.toJSONString(exchange1(new int[]{1, 3, 5})));
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
        // int size = (int) Math.pow(10, n);
        int size = 0;
        int factor = 10;
        while (n > 0) {
            size += factor;
            factor *= 10;
            n--;
        }
        int[] res = new int[size - 1];
        for (int i = 1; i < size; i++) {
            res[i - 1] = i;
        }
        return res;
    }

    /**
     * 剑指 Offer 21. 调整数组顺序使奇数位于偶数前面
     *
     * @param nums
     * @return
     */
    public static int[] exchange(int[] nums) {
        if (nums == null || nums.length < 2) {
            return nums;
        }
        int[] res = new int[nums.length];
        int left = 0;
        int right = nums.length - 1;
        int slow = left;
        int high = right;
        while (slow < high) {
            if (nums[slow] % 2 == 0) {
                res[right] = nums[slow];
                right--;
            } else {
                res[left] = nums[slow];
                left++;
            }
            slow++;

            if (nums[high] % 2 == 0) {
                res[right] = nums[high];
                right--;
            } else {
                res[left] = nums[high];
                left++;
            }
            high--;
        }
        if (slow == high) {
            res[left] = nums[slow];
        }
        return res;
    }

    public static int[] exchange1(int[] nums) {
        if (nums == null || nums.length < 2) {
            return nums;
        }
        int left = -1; // 偶数
        int right = 0; // 奇数
        while (right < nums.length) {
            if (nums[right] % 2 == 0) {
                if (left == -1) {
                    left = right;
                }
            } else if (left != -1) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
            }
            right++;
        }
        return nums;
    }

    /**
     * LCR 004. 只出现一次的数字 II
     *
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if ((i == 0 && i < nums.length - 1 && nums[i] == nums[i + 1])
                    || (i > 0 && (nums[i] == nums[i - 1] || (i < nums.length - 1 && nums[i] == nums[i + 1])))) {
                continue;
            }
            return nums[i];
        }
        return -1;
    }


    public int singleNumber1(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            boolean match = false;
            for (int j = i; j < nums.length; j++) {
                if (j > i && nums[i] == nums[j]) {
                    match = true;
                    break;
                }
            }
            if (!match) {
                return nums[i];
            }
        }
        return -1;
    }

}

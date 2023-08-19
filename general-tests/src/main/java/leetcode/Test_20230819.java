package leetcode;

import java.util.Arrays;

public class Test_20230819 {

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        Test_20230819 test = new Test_20230819();

        head = new ListNode(3);
        head.next = new ListNode(5);
        System.out.println(test.reverseBetween(head, 1, 2));

        System.out.println(test.removeDuplicates(new int[]{1, 1, 1, 2, 2, 3}));
    }

    /**
     * 二叉搜索树中第K小的元素
     *
     * @param root
     * @param k
     * @return
     */
    public int kthSmallest(TreeNode root, int k) {
        int[] indexValue = new int[2];
        doKthSamllest(root, k, indexValue);
        return indexValue[1];
    }

    private void doKthSamllest(TreeNode node, int k, int[] indexValue) {
        if (node == null || indexValue[0] >= k) {
            return;
        }
        doKthSamllest(node.left, k, indexValue);
        if (indexValue[0] < k) {
            indexValue[0]++;
            indexValue[1] = node.val;
            if (indexValue[0] < k) {
                doKthSamllest(node.right, k, indexValue);
            }
        }
    }

    /**
     * 92. 反转链表 II
     *
     * @param head
     * @param left
     * @param right
     * @return
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || head.next == null || left == right) {
            return head;
        }
        boolean leftFlag = false; // 是否已经找到了left
        boolean rightFlag = false; // 是否找到了right
        ListNode tempHead = null;
        ListNode tempTail = null;
        int index = 0;
        ListNode prev = null;
        ListNode node = head;
        while (node != null) {
            index++;
            ListNode next = node.next;
            if (index == left) {
                leftFlag = true;
                // 将node从原链表中删除
                node.next = null;
                if (prev == null) {
                    head = next;
                } else {
                    prev.next = next;
                }
                // 以node创建temp链表
                tempHead = node;
                tempTail = node;

                node = next;
            } else if (index < left) {
                prev = node;
                node = next;
            } else {
                // 继续执行 - 将node从原链表中删除
                node.next = null;
                if (prev == null) {
                    head = next;
                } else {
                    prev.next = next;
                }
                // node加到temp链表头部
                node.next = tempHead;
                tempHead = node;

                if (index == right) {
                    rightFlag = true;
                    break;
                }
                node = next;
            }
        }
        if (leftFlag) {
            if (rightFlag) {
                if (prev == null) {
                    tempTail.next = head;
                    return tempHead;
                } else {
                    ListNode next = prev.next;
                    prev.next = tempHead;
                    tempTail.next = next;
                    return head;
                }
            } else {
                // 没有匹配到right, 需要再把temp还原到原链表中
                node = tempHead;
                while (node != null) {
                    ListNode next = node.next;
                    node.next = null;
                    if (prev == null) {
                        node.next = head;
                        head = node;
                    } else {
                        node.next = prev.next;
                        prev.next = node;
                    }
                    node = next;
                }
                return head;
            }
        }
        return head;
    }

    /**
     * 287. 寻找重复数 - TODO 待求最优解
     *
     * @param nums
     * @return
     */
    public int findDuplicate(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 1; i++) {
            if ((i == 0 && nums[i] == nums[i + 1]) || (i > 0 && ((nums[i] == nums[i - 1]) || (nums[i] == nums[i + 1])))) {
                return nums[i];
            }
        }
        return -1;
    }

    /**
     * 80. 删除有序数组中的重复项 II
     *
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length < 3) {
            return nums == null ? 0 : nums.length;
        }
        int deleteCount = 0; // 记录删除了多少个元素
        int from = 0;
        for (int i = 1; i < nums.length; i++) {
            // 当前元素和前一个不一致
            if (nums[i] != nums[i - 1]) {
                int tempDeleteCount = i - from - 2;
                if (tempDeleteCount > 0) {
                    // nums[i-1]重复超过2
                    deleteCount += tempDeleteCount;
                }
                from = i;
            } else if (i == nums.length - 1) {
                int tempDeleteCount = i - from + 1 - 2;
                if (tempDeleteCount > 0) {
                    deleteCount += tempDeleteCount;
                }
            }
            nums[i - deleteCount] = nums[i];
        }
        return nums.length - deleteCount;
    }

    /**
     * 148. 排序链表
     *
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        int[] flag = new int[2 * (int) Math.pow(10, 5) + 1];
        ListNode node = head;
        while (node != null) {
        }
        return null;
    }

}

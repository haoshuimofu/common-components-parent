package leetcode;

import java.util.*;

/**
 * @author dewu.de
 * @date 2023-08-28 9:57 下午
 */
public class Test_20230828 {

    /**
     * 128. 最长连续序列
     *
     * @param nums
     * @return
     */
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        int max = 0;
        for (Integer num : set) {
            if (!set.contains(num - 1)) {
                int len = 1;
                int currNum = num;
                while (set.contains(currNum + 1)) {
                    len++;
                    currNum++;
                }
                max = Math.max(max, len);
            }
        }
        return max;
    }

    public int longestConsecutive1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Arrays.sort(nums);
        int max = 1;
        int from = 0;
        int repeat = 0;
        for (int i = 1; i < nums.length; i++) {
            int value = nums[i] - nums[i - 1];
            if (value == 0) {
                repeat++;
            } else if (value != 1) {
                max = Math.max(max, i - from - repeat);
                from = i;
                repeat = 0;
            }
            if (i == nums.length - 1) {
                max = Math.max(max, i - from + 1 - repeat);
            }
        }
        return max;
    }


    /**
     * 剑指 Offer 59 - I. 滑动窗口的最大值 - 暴力求解
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (k == 1) {
            return nums;
        }
        int[] result = new int[nums.length - k + 1];
        int index = 0;
        int from = 0;
        int max = Integer.MIN_VALUE;
        int maxCount = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i - from + 1 < k) {
                if (nums[i] > max) {
                    maxCount = 1;
                    max = nums[i];
                } else if (nums[i] == max) {
                    maxCount++;
                }
            } else if (i - from + 1 == k) {
                if (nums[i] > max) {
                    result[index] = nums[i];
                    index++;

                    max = nums[i];
                    maxCount = 1;
                    from++;
                } else {
                    if (nums[i] == max) {
                        maxCount++;
                    } else {
                        max = Math.max(max, nums[i]);
                    }
                    result[index] = max;
                    index++;

                    // 向右滑动一位
                    if (nums[from] < max) {
                        from++;
                    } else if (nums[from] == max) {
                        from++;
                        // 重新找最大值
                        if (maxCount == 1) {
                            max = Integer.MIN_VALUE;
                            for (int j = from; j <= i; j++) {
                                if (nums[j] > max) {
                                    maxCount = 1;
                                    max = nums[j];
                                } else if (nums[j] == max) {
                                    maxCount++;
                                }
                            }
                        } else {
                            maxCount--;
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 剑指 Offer 59 - I. 滑动窗口的最大值
     * 239. 滑动窗口最大值
     * 优先级队列
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow_PQ(int[] nums, int k) {
        if (k == 1) {
            return nums;
        }
        // 优先级队列, 按数值-下标倒序排列
        PriorityQueue<int[]> queue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[0] == o1[0] ? o2[1] - o1[1] : o2[0] - o1[0];
            }
        });
        // 先把前k个值放入队列, 第一个窗口
        for (int i = 0; i < k; i++) {
            queue.offer(new int[]{nums[i], i});
        }
        int[] window = new int[nums.length - k + 1];
        int index = 0;
        window[index++] = queue.peek()[0]; // 第一个窗口的最大值
        for (int i = k; i < nums.length; i++) {
            // 把当前值放入队列
            queue.offer(new int[]{nums[i], i});
            // 如果最大值的下标在窗口左侧外则删除它
            while (queue.peek()[1] <= i - k) {
                queue.poll();
            }
            // 当前窗口的最大值
            window[index++] = queue.peek()[0];
        }
        return window;
    }

    /**
     * 剑指 Offer 59 - I. 滑动窗口的最大值
     * 239. 滑动窗口最大值
     * 单调队列
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow_Q(int[] nums, int k) {
        Deque<Integer> deque = new ArrayDeque<>(); // 双向队列
        // 先把前K的数值对应的下标按需放入队列
        for (int i = 0; i < k; i++) {
            // 当队列不为空, 当前数值大于队尾下标对应的数值时, 删除队尾元素
            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                deque.pollLast();
            }
            // 把当前数字存入队尾
            deque.offerLast(i);
        }
        int[] window = new int[nums.length - k + 1];
        int index = 0;
        // 第一个窗口的最大值
        window[index++] = nums[deque.peekFirst()];
        for (int i = k; i < nums.length; i++) {
            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.offerLast(i);
            while (deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }
            window[index++] = nums[deque.peekFirst()];
        }
        return window;
    }

    public static void main(String[] args) {
        Test_20230828 test = new Test_20230828();
        System.out.println(test.longestConsecutive1(new int[]{1, 2, 0, 1}));
        System.out.println(Arrays.toString(test.maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3)));
        System.out.println(Arrays.toString(test.maxSlidingWindow_Q(new int[]{9, 7, 10, 6, 5}, 3)));
    }

}

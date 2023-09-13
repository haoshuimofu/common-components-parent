package leetcode;

import java.util.*;

public class Test_20230913 {

    /**
     * 2208. 将数组和减半的最少操作次数
     * 大顶堆 - 优先级队列
     *
     * @param nums
     * @return
     */
    public int halveArray(int[] nums) {
        Map<Double, Integer> numCountMap = new HashMap<>();
        PriorityQueue<Double> queue = new PriorityQueue<>(Comparator.reverseOrder());
        double sum = 0;
        for (int num : nums) {
            double value = (double) num;
            sum += value;
            int cnt = numCountMap.getOrDefault(value, 0);
            if (cnt == 0) {
                queue.offer(value);
            }
            numCountMap.put(value, cnt + 1);
        }
        double half = sum / 2;
        int count = 0;
        while (sum > half) {
            double num = queue.poll();
            double halfNum = num / 2;
            int numCount = numCountMap.get(num);
            int opCount = numCount;
            while (opCount > 0 && sum > half) {
                sum -= halfNum;
                opCount--;
            }
            opCount = numCount - opCount;
            count += opCount;
            int halfNumCount = numCountMap.getOrDefault(halfNum, 0);
            if (halfNumCount == 0) {
                queue.offer(halfNum);
            }
            numCountMap.put(halfNum, halfNumCount + opCount);
            numCountMap.put(num, numCount - opCount);

        }
        return count;
    }

    /**
     * 极端超时
     *
     * @param nums
     * @return
     */
    public int halveArray1(int[] nums) {
        Arrays.sort(nums);
        double[] dd = new double[nums.length];
        double sum = 0;
        for (int i = 0; i < nums.length; i++) {
            double d = (double) nums[i];
            sum += d;
            dd[i] = d;
        }
        double half = sum / 2;
        int count = 0;
        while (sum > half) {
            double d = dd[dd.length - 1] / 2;
            sum -= d;
            if (dd.length > 1) {
                if (d >= dd[dd.length - 2]) {
                    dd[dd.length - 1] = d;
                } else {
                    // 二分查找插入位置
                    int left = 0;
                    int right = dd.length - 2;
                    int position = -1;
                    while (left <= right) {
                        int mid = (left + right) / 2;
                        if (dd[mid] > d) {
                            if (mid == 0 || dd[mid - 1] <= d) {
                                position = Math.max(0, mid);
                                break;
                            } else {
                                right = mid - 1;
                            }
                        } else {
                            left = mid + 1;
                        }
                    }
                    // 移位
                    for (int i = dd.length - 1; i >= position + 1; i--) {
                        dd[i] = dd[i - 1];
                    }
                    dd[position] = d;
                }
            } else {
                dd[dd.length - 1] = d;
            }
            count++;
        }
        return count;
    }

    public int halveArray2(int[] nums) {
        double[] dd = new double[nums.length];
        double sum = 0;
        for (int i = 0; i < nums.length; i++) {
            double d = (double) nums[i];
            sum += d;
            dd[i] = d;
        }
        double half = sum / 2;
        System.out.println("sum=" + sum + ", half=" + half);
        int count = 0;
        while (sum > half) {
            // 选择最大的一个元素
            int index = 0;
            double max = dd[index];
            for (int i = 1; i < dd.length; i++) {
                if (dd[i] > max) {
                    max = dd[i];
                    index = i;
                }
            }
            max /= 2;
            dd[index] = max;
            sum -= max;
            count++;
        }
        System.out.println("sum=" + sum + ", half=" + half);
        return count;
    }

    public static void main(String[] args) {
        Test_20230913 test = new Test_20230913();
        int[] nums = new int[]{32, 98, 23, 14, 67, 40, 26, 9, 96, 96, 91, 76, 4, 40, 42, 2, 31, 13, 16, 37, 62, 2, 27, 25, 100, 94, 14, 3, 48, 56, 64, 59, 33, 10, 74, 47, 73, 72, 89, 69, 15, 79, 22, 18, 53, 62, 20, 9, 76, 64};
//        System.out.println(test.halveArray1(nums));
        System.out.println(test.halveArray(nums));
        System.out.println(test.halveArray2(nums));
    }
}

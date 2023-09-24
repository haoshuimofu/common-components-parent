package leetcode;

import leetcode.annotation.PerfectAnswer;

import java.util.*;

/**
 * @author dewu.de
 * @date 2023-09-22 1:40 下午
 */
public class Test_20230922 {

    public static void main(String[] args) {
        Test_20230922 test = new Test_20230922();
        System.out.println(test.canCompleteCircuit_1(new int[]{1, 2, 3, 4, 5}, new int[]{3, 4, 5, 1, 2}));

        int[] gas = new int[1000];
        int[] cost = new int[1000];
        long startMillis = System.currentTimeMillis();
        System.out.println("cost=" + (System.currentTimeMillis() - startMillis)
                + ", position=" + test.canCompleteCircuit(gas, cost));

        Arrays.fill(gas, 1);
        Arrays.fill(cost, 1);
        startMillis = System.currentTimeMillis();
        System.out.println("cost=" + (System.currentTimeMillis() - startMillis)
                + ", position=" + test.canCompleteCircuit(gas, cost));

        gas = new int[]{2, 3, 4};
        cost = new int[]{3, 4, 3};
        startMillis = System.currentTimeMillis();
        System.out.println("cost=" + (System.currentTimeMillis() - startMillis)
                + ", position=" + test.canCompleteCircuit(gas, cost));


        int[] nums = new int[]{3, 0, 6, 1, 5};
        System.out.println(test.hIndex(null));
    }

    /**
     * 134.加油站
     *
     * @param gas
     * @param cost
     * @return
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int gasCount = gas.length;
        for (int i = 0; i < gasCount; i++) {
            int position = i;
            if (gas[position] <= 0 || gas[position] < cost[position]) {
                continue;
            }
            int limit = i + gasCount;
            int moreGas = 0;
            int index = position;
            while ((position < limit) && (moreGas + gas[index]) >= cost[index]) {
                moreGas += (gas[index] - cost[index]);
                index = ++position % gasCount;
            }
            if (position == limit) {
                return i;
            }
        }
        return -1;
    }

    @PerfectAnswer
    public int canCompleteCircuit_1(int[] gas, int[] cost) {
        int count = gas.length;
        int i = 0;
        // 从0位置开始尝试能否走一圈
        while (i < count) {
            int totalGas = 0;
            int totalCost = 0;
            int step = 0; // 走过的加油站数
            while (step < count) {
                int index = (i + step) % count;
                totalGas += gas[index];
                totalCost += cost[index];
                if (totalGas < totalCost) {
                    // 如果从i出发到当前加油站走不下了, 说明从后面step个加油站出发都无法到达下一站
                    // 所以i向右偏移step+1个位置重试
                    break;
                }
                step++;
            }
            if (step == count) {
                return i;
            }
            i = i + step + 1;

        }
        return -1;
    }


    /**
     * 238. 除自身以外数组的乘积
     *
     * @param nums
     * @return
     */
    public int[] productExceptSelf(int[] nums) {
        int size = nums.length;
        int[] left = new int[size];
        left[0] = nums[0];
        int[] right = new int[size];
        right[size - 1] = nums[size - 1];
        for (int i = 1; i < size; i++) {
            left[i] = left[i - 1] * nums[i];
        }
        for (int i = size - 2; i >= 0; i--) {
            right[i] = right[i + 1] * nums[i];
        }
        int[] result = new int[size];
        result[0] = right[1];
        result[size - 1] = left[size - 2];
        for (int i = 1; i <= size - 2; i++) {
            result[i] = left[i - 1] * right[i + 1];
        }
        return result;
    }

    /**
     * 120. 三角形最小路径和
     *
     * @param triangle
     * @return
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        int size = triangle.size();
        int[] lastDp = new int[size];
        lastDp[0] = triangle.get(0).get(0);
        int[] currDp = new int[size];
        for (int i = 1; i < size; i++) {
            currDp[0] = lastDp[0] + triangle.get(i).get(0);
            for (int j = 1; j < i; j++) {
                currDp[j] = Math.min(lastDp[i - 1], lastDp[i]) + triangle.get(i).get(j);
            }
            currDp[i] = lastDp[i - 1] + triangle.get(i).get(i);
            int[] tempDp = currDp;
            currDp = lastDp;
            lastDp = tempDp;
        }
        int minPath = lastDp[0];
        for (int i = 1; i < size; i++) {
            minPath = Math.min(minPath, lastDp[i]);
        }
        return minPath;
    }

    /**
     * 274. H 指数
     *
     * @param citations
     * @return
     */
    public int hIndex(int[] citations) {
        Arrays.sort(citations);
        int max = 0;
        for (int i = citations.length - 1; i >= 0; i--) {
            int count = citations.length - i;
            if (citations[i] >= count) {
                max = count;
            } else {
                break;
            }
        }
        return max;
    }

}

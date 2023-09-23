package leetcode;

import java.util.Arrays;

/**
 * @author dewu.de
 * @date 2023-09-22 1:40 下午
 */
public class Test_20230922 {

    public static void main(String[] args) {
        Test_20230922 test = new Test_20230922();
        System.out.println(test.canCompleteCircuit1(new int[]{1, 2, 3, 4, 5}, new int[]{3, 4, 5, 1, 2}));

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

    public int canCompleteCircuit1(int[] gas, int[] cost) {
        int steps = gas.length;
        int[] stations = new int[2 * steps - 1];
        for (int i = 0; i < steps; i++) {
            stations[i] = gas[i] - cost[i];
        }
        System.arraycopy(stations, 0, stations, steps, steps - 1);
        int left = 0;
        int count = 0;
        int sum = 0;
        while (left < steps && count < steps) {
            sum += stations[left + count];
            count++;
            if (sum < 0) {
                sum -= stations[left + count];
                count--;
                if (stations[left] < 0) {
                    left++;
                    sum -= stations[left];
                    count--;
                } else {
                    // 起点位置

                }
            }

        }
//        for (int i = 0; i < steps; i++) {
//            int sum = 0;
//            for (int j = 0; j <= steps; j++) {
//                sum += stations[i + j];
//                if (sum < 0) {
//                    break;
//                }
//            }
//            if (sum >= 0) {
//                return i;
//            }
//        }
        return -1;
    }
}

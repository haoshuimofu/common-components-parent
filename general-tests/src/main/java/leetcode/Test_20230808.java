package leetcode;

import java.util.Arrays;

/**
 * @author dewu.de
 * @date 2023-08-08 6:07 下午
 */
public class Test_20230808 {

    public static void main(String[] args) {
        System.out.println(myPow(3, 4));
    }

    public static double myPow(double x, int n) {
        int absN = Math.abs(n);
        double[] values = new double[absN + 1];
        values[0] = 1;
        if (absN > 0) {
            int index = 1;
            values[1] = x;
            while (2 * index <= n) {
                values[2 * index] = values[index] * values[index];
                index *= 2;
            }
            if (values[n] == 0) {
                index = index / 2;
                // 说明 n在区间(index, 2*index)中, 在这个区间找 n-index
                int low = 0;
                int high = index;
                int target = -1;
                while (low <= high) {
                    int mid = (low + high) / 2;
                    if (mid == n - index) {
                        target = mid;
                        break;
                    } else if (mid > n - index) {
                        high = mid - 1;
                    } else {
                        low = mid + 1;
                    }
                }
                if (values[target] > 0) {
                    return values[index] * values[target];
                }
                // target两边
                low = target - 1;
                high = target + 1;


            }

        }
        return 0;
    }
}

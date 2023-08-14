package leetcode;


import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author dewu.de
 * @date 2023-08-14 2:06 下午
 */
public class Test_20230814 {

    public static void main(String[] args) {
        System.out.println(divide(-2147483648, 2));
        System.out.println(Integer.MIN_VALUE);
        System.out.println(Integer.MAX_VALUE);
        System.out.println((Integer.MIN_VALUE + 1) / Integer.MIN_VALUE);
        System.out.println(Integer.MAX_VALUE / Integer.MIN_VALUE);

        Test_20230814 test = new Test_20230814();
        System.out.println(JSON.toJSONString(test.singleNumbers(new int[]{4, 1, 4, 6})));
    }

    /**
     * 被除数 和 除数 区间范围[-2^31, 2^31-1]
     *
     * @param dividend
     * @param divisor
     * @return
     */
    public static int divide(int dividend, int divisor) {
        if (divisor == -1) {
            if (dividend == Integer.MIN_VALUE) {
                return Integer.MAX_VALUE;
            } else {
                return -dividend;
            }
        } else if (divisor == 1) {
            return dividend;
        } else if (divisor == Integer.MIN_VALUE) {
            // 当除数是 int最小值时, 除非除数也是最小值，这时结果等于1, 否则商等于0
            return dividend == Integer.MIN_VALUE ? 1 : 0;
        }
        int divisorAbs = Math.abs(divisor);
        int dividendAbs = dividend == Integer.MIN_VALUE ? Math.abs(dividend + divisorAbs) : Math.abs(dividend);
        int res = div(dividendAbs, divisorAbs);
        // 被除数 和 除数 符号相反, 商为负数
        if ((dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0)) {
            res = -res;
        }
        if (dividend == Integer.MIN_VALUE) {
            /**
             * -6 / -2 = 3
             * -4 / -2 = 2  +1
             *
             * -6 / 2 = -3
             * -4 / 2 = -2  -1
             */
            if (divisor > 0) {
                res -= 1;
            } else {
                res += 1;
            }
        }
        return res;
    }

    public static int div(int a, int b) {
        // 只考虑整数, 如果a<b, a/b值为小数取整就等于0了
        if (a < b) {
            return 0;
        }
        int count = 1;
        int doubledB = b; // b翻倍
        // b不停翻2倍, 一直<=a, 同时倍数count也翻倍哦
        // while (doubledB + doubledB <= a) { // 有可能溢出
        while (doubledB <= a - doubledB) {
            count = count + count;
            doubledB = doubledB + doubledB;
        }
        // doubledB = count * b, a - doubledB in [0, doubledB)
        return count + div(a - doubledB, b);
    }

    /**
     * 剑指 Offer 56 - I. 数组中数字出现的次数
     */
    public int[] singleNumbers(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (!set.add(num)) {
                set.remove(num);
            }
        }
        int[] res = new int[set.size()];
        int index = 0;
        for (int num : set) {
            res[index] = num;
            index++;
        }
        return res;
    }

    /**
     * 剑指 Offer 56 - II. 数组中数字出现的次数 II
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        Map<Integer, Set<Integer>> countNums = new HashMap<>();
        for (int num : nums) {
            Set<Integer> sets = countNums.get(1);
            if (sets == null) {
                sets = new HashSet<>();
                sets.add(num);
                countNums.put(1, sets);
            } else if (sets.contains(num)){

            }
        }


    }
}

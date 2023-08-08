package leetcode;

/**
 * @author dewu.de
 * @date 2023-08-08 6:07 下午
 */
public class Test_20230808 {

    public static void main(String[] args) {
//        System.out.println(myPow(1.99364, 11));
//        System.out.println(myPow(1.72777, 7));
//        System.out.println(myPow1(1.72777, 7));
//        System.out.println(myPow(0.00001, 2147483647)); // java.lang.OutOfMemoryError: Requested array size exceeds VM limit
        System.out.println(myPow1(0.00001, 2147483647));

    }

    /**
     * 剑指 Offer 16. 数值的整数次方
     * 执行结果 - 超出内存限制
     *
     * @param x
     * @param n
     * @return
     */
    public static double myPow(double x, int n) {
        if (n == 0) {
            return 1; // x的0次幂=1
        }
        int absN = Math.abs(n);
        double[] values = new double[absN];
        values[0] = x;
        if (absN > 1) {
            int index = 1;
            while (2 * index <= absN) {
                values[2 * index - 1] = values[index - 1] * values[index - 1];
                index *= 2;
                if (index == absN) {
                    break;
                }
            }
            if (values[absN - 1] == 0) {
                // 跳出循环, 说明2*index > n, n次幂还没求解出, 也说n在区间(index, 2*index)之间
                int step = index / 2;
                while (step > 0) {
                    if (index + step == absN) {
                        values[absN - 1] = values[index - 1] * values[step - 1];
                        break;
                    } else if (index + step > absN) {
                        step = step / 2;
                    } else {
                        // index+step -> n-1
                        values[index + step - 1] = values[index - 1] * values[step - 1];
                        for (int i = index + step; i < absN; i++) {
                            values[i] = values[i - 1] * x;
                        }
                        break;
                    }
                }
            }
        }
        if (n > 0) {
            return values[absN - 1];
        } else {
            return 1 / values[absN - 1];
        }
    }

    public static double myPow1(double x, int n) {
        if (n == 0) {
            return 1; // x的0次幂=1
        }
        long absN = Math.abs(n);
        double value = x;
        if (absN > 1) {
            boolean found = false;
            long index = 1;
            while (2 * index <= absN) {
                index *= 2;
                value = value * value;
                if (index == absN) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                //  index+1 -> n
                long subIndex = index / 2;
                while (index + subIndex <= n) {
                    value = value + value / 2;
                    index = index + subIndex / 2;
                }
                for (int i = (int) index + 1; i <= n; i++) {
                    value = value * x;
                }
            }
        }
        return n > 0 ? value : 1 / value;
    }

}

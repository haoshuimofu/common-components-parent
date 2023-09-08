package leetcode;

/**
 * @author dewu.de
 * @date 2023-09-08 7:51 下午
 */
public class Test_20230908 {

    public static void main(String[] args) {
        Test_20230908 test = new Test_20230908();
        System.out.println(test.findNthDigit(1000000000));
        System.out.println(test.reverseStr("abcd", 3));
    }

    /**
     * 剑指 Offer 44. 数字序列中某一位的数字
     * 400. 第 N 位数字
     *
     * @param n
     * @return
     */
    public int findNthDigit(int n) {
        if (n < 10) {
            return n;
        }
        int numLen = 1; // 位数
        long from = 0;
        long to = 9;
        long sumLen = 9; // 序号从0开始, 这里取值-1

        while (sumLen < n) {
            numLen++;
            from = to + 1;
            to = (long) Math.pow(10, numLen) - 1;
            sumLen += (to - from + 1) * numLen;
        }
        // 循环结束, 说明第n位所在的位置对应的数字是numLen位数
        sumLen -= (to - from + 1) * numLen;
        long index = (n - sumLen) / numLen;
        if ((n - sumLen) % numLen != 0) {
            index++;
        }
        long targetNum = from - 1 + index;
        long deltaLen = n - sumLen - (targetNum - from) * numLen;

        int factor = (int) Math.pow(10, numLen - deltaLen);
        return (int) ((targetNum / factor) % 10);
    }

    /**
     * 541. 反转字符串 II
     *
     * @param s
     * @param k
     * @return
     */
    public String reverseStr(String s, int k) {
        int dk = 2 * k;
        char[] chs = new char[s.length()];
        int index = 0;
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            count++;
            if (count == dk) {
                for (int j = i - k; j > i - dk; j--) {
                    chs[index++] = s.charAt(j);
                }
                for (int j = i - k + 1; j <= i; j++) {
                    chs[index++] = s.charAt(j);
                }
                count = 0;
            }
        }
        if (count > 0) {
            int from = s.length() - count;
            int to = Math.min(from + k, s.length());
            for (int i = to - 1; i >= from; i--) {
                chs[index++] = s.charAt(i);
            }
            for (int i = to; i < s.length(); i++) {
                chs[index++] = s.charAt(i);
            }
        }
        return new String(chs, 0, chs.length);
    }
}

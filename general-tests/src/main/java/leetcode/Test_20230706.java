package leetcode;

/**
 * @author dewu.de
 * @date 2023-07-06 9:55 上午
 */
public class Test_20230706 {

    public static void main(String[] args) {
        System.out.println((int) '0');
        System.out.println((int) '1');
        String a = "1010", b = "1011";
        System.out.println(addBinary("0", "0"));
        System.out.println('1');
    }

    /**
     * 二进制求和
     * char[]数组存储: 时间和内存击败率99.64% 和 79.10%
     * ArrayList存储: 击败率分别只有 31% 和 28%
     *
     * @param a
     * @param b
     * @return
     */
    public static String addBinary(String a, String b) {
        if (a == null || a.length() == 0) {
            return b;
        } else if (b == null || b.length() == 0) {
            return a;
        } else {
            int index = 1;
            int aIdx = a.length() - index;
            int bIdx = b.length() - index;
            int add = 0;
            char[] chs = new char[Math.max(a.length(), b.length()) + 1];
            int chIdx = chs.length - 1;
            while (aIdx >= 0 || bIdx >= 0) {
                int aChar = aIdx >= 0 ? a.charAt(aIdx) - 48 : 0;
                int bChar = bIdx >= 0 ? b.charAt(bIdx) - 48 : 0;
                int sum = aChar + bChar + add;
                chs[chIdx] = sum % 2 == 0 ? '0' : '1';
                chIdx--;
                add = sum / 2;
                aIdx = a.length() - ++index;
                bIdx = b.length() - index;
            }
            if (add > 0) {
                chs[0] = '1';
                return new String(chs);
            } else {
                return new String(chs, 1, chs.length - 1);
            }
        }
    }

}

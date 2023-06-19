package leetcode;

/**
 * @author dewu.de
 * @date 2023-06-19 10:38 上午
 */
public class Test_20230619 {

    public static void main(String[] args) {

        int round = 0;
        System.out.println(123 / 10);
        System.out.println(12 / 10);
        System.out.println(1 / 10);
        System.out.println("---");

        System.out.println(reverse(-123));
    }

    /**
     * 123
     * <p>
     * 3=123%10, res=300=3*100, x=123/10=12
     * <p>
     * 2=12%10,  res=320=res+2*10, x=12/10=1
     * <p>
     * 1=1%10,   res=321=res+1*1, x=1/10=0
     *
     * @param x
     * @return
     */
    public static int reverse(int x) {
        if (x > -9 && x < 9) {
            return x;
        }
        if (x == Integer.MIN_VALUE || x == Integer.MAX_VALUE) {
            return 0;
        }
        int vx = Math.abs(x); // x值, 会变动
        int digits = (int) Math.log10(vx);
        long res = 0; // 最终值, long防止int溢出

        while (vx > 0) {
            int a = vx % 10;
            vx = vx / 10;
            res += a * Math.pow(10, digits);
            digits--;
        }
        if (x > 0) {
            return res > Integer.MAX_VALUE ? 0 : (int) res;
        } else {
            return res - Integer.MAX_VALUE > 1 ? 0 : (int) (-res);
        }
    }


    public static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        } else if (x <= 9) {
            return true;
        }
        String s = String.valueOf(x);
        int low = 0;
        int high = s.length() - 1;
        boolean flag = true;
        while (low < high) {
            if (s.charAt(low) != s.charAt(high)) {
                flag = false;
                break;
            }
            low++;
            high--;
        }
        return flag;
    }

}

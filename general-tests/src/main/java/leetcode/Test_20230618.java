package leetcode;

public class Test_20230618 {


    public static void main(String[] args) {
        System.out.println(longestPalindrome("ccd"));
        System.out.println(longestPalindrome("aaaa"));
        System.out.println(reverse(123));
        System.out.println(reverse1(123));
        System.out.println(reverse2(123));
    }

    /**
     * 暴力解法
     *
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {
        if (s == null || s.length() == 1 || (s.length() == 2 && s.charAt(0) == s.charAt(1))) {
            return s;
        }
        int size = 1;
        String res = s.substring(0, 1);
        for (int i = 0; i < s.length(); i++) {
            for (int j = s.length() - 1; j >= i + 1; j--) {
                int low = i;
                int high = j;
                boolean match = true;
                while (low <= high) {
                    if (s.charAt(low) != s.charAt(high)) {
                        match = false;
                        break;
                    }
                    low++;
                    high--;
                }
                if (match) {
                    int tempSize = j - i + 1;
                    if (size < tempSize) {
                        size = tempSize;
                        res = s.substring(i, j + 1);
                    }
                    break;
                }
            }
        }
        return res;
    }


    public static int reverse(int x) {
        if (x > -9 && x < 9) {
            return x;
        }
        if (x == Integer.MIN_VALUE || x == Integer.MAX_VALUE) {
            return 0;
        }
        String str = String.valueOf(Math.abs(x));
        String[] strs = str.split("");
        int low = 0;
        int high = strs.length - 1;
        while (low < high) {
            String lowVal = strs[low];
            strs[low] = strs[high];
            strs[high] = lowVal;
            low++;
            high--;
        }

        long res = 0;
        for (int i = 0; i < strs.length; i++) {
            res += Long.valueOf(strs[i]) * (long) Math.pow(10, strs.length - i - 1);
        }

        if (x > 0) {
            return res > Integer.MAX_VALUE ? 0 : (int) res;
        } else {
            return res - Integer.MAX_VALUE > 1 ? 0 : (int) (0 - res);
        }
    }

    public static int reverse1(int x) {
        if (x > -9 && x < 9) {
            return x;
        }
        if (x == Integer.MIN_VALUE || x == Integer.MAX_VALUE) {
            return 0;
        }
        String str = String.valueOf(Math.abs(x));
        int len = str.length();
        int low = 0;
        int high = len - 1;
        long res = 0;
        while (low <= high) {
            if (low == high) {
                res += Long.valueOf(String.valueOf(str.charAt(high))) * Math.pow(10, len - low - 1);
            } else {
                res += Long.valueOf(String.valueOf(str.charAt(high))) * Math.pow(10, len - low - 1);
                res += Long.valueOf(String.valueOf(str.charAt(low))) * Math.pow(10, low);
            }
            low++;
            high--;
        }
        if (x > 0) {
            return res > Integer.MAX_VALUE ? 0 : (int) res;
        } else {
            return res - Integer.MAX_VALUE > 1 ? 0 : (int) (0 - res);
        }
    }


    public static int reverse2(int x) {
        if (x > -9 && x < 9) {
            return x;
        }
        if (x == Integer.MIN_VALUE || x == Integer.MAX_VALUE) {
            return 0;
        }
        String[] strs = String.valueOf(Math.abs(x)).split("");
        StringBuilder sb = new StringBuilder();
        for (int i = strs.length-1; i >=0; i--) {
            sb.append(strs[i]);
        }
        long res = Long.valueOf(sb.toString());
        if (x > 0) {
            return res > Integer.MAX_VALUE ? 0 : (int) res;
        } else {
            return res - Integer.MAX_VALUE > 1 ? 0 : (int) (0 - res);
        }
    }

}

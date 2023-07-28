package leetcode;

/**
 * @author dewu.de
 * @date 2023-07-28 2:01 下午
 */
public class Test_20230728 {

    public static void main(String[] args) {

        System.out.println((int) 'A');
        System.out.println((int) 'Z');
        System.out.println((int) 'a');
        System.out.println((int) 'z');
        System.out.println((int) '0');
        System.out.println((int) '9');

        System.out.println(isPalindrome("A man, a plan, a canal: Panama"));
        System.out.println(isPalindrome1("A man, a plan, a canal: Panama"));

    }

    /**
     * 验证回文串
     *
     * @param s
     * @return
     */
    public static boolean isPalindrome(String s) {
        char[] chs = new char[s.length()];
        int index = -1;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (isUpperChar(ch)) {
                chs[++index] = (char) ((int) ch + 32);
            } else if (isLowerChar(ch) || isNumberChar(ch)) {
                chs[++index] = ch;
            }
        }

        int low = 0;
        int high = index;
        while (low <= high) {
            if (chs[low] != chs[high]) {
                return false;
            }
            low++;
            high--;
        }
        return true;
    }

    public static boolean isUpperChar(char ch) {
        return (int) ch >= 65 && (int) ch <= 90;
    }

    public static boolean isLowerChar(char ch) {
        return (int) ch >= 97 && (int) ch <= 122;
    }

    public static boolean isNumberChar(char ch) {
        return (int) ch >= 48 && (int) ch <= 57;
    }

    //===========================================

    public static boolean isPalindrome1(String s) {
        int low = 0;
        int high = s.length() - 1;
        int lowCount = 0;
        int highCount = 0;
        while (low <= high) {
            char lowCh = toLowerChar(s.charAt(low));
            char highCh = toLowerChar(s.charAt(high));
            if (isNumberOrLowerChar(lowCh) && !isNumberOrLowerChar(highCh)) {
                high--;
            } else if (!isNumberOrLowerChar(lowCh) && isNumberOrLowerChar(highCh)) {
                low++;
            } else if (isNumberOrLowerChar(lowCh) && isNumberOrLowerChar(highCh)) {
                if (lowCh != highCh) {
                    return false;
                }
                low++;
                high--;
            } else {
                low++;
                high--;
            }
        }
        return true;
    }

    public static char toLowerChar(char ch) {
        if (isUpperChar(ch)) {
            return (char) ((int) ch + 32);
        }
        return ch;
    }

    public static boolean isNumberOrLowerChar(char ch) {
        return isNumberChar(ch) || isLowerChar(ch);
    }

}

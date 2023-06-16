package leetcode.array;

import java.util.HashSet;
import java.util.Set;

/**
 * @author dewu.de
 * @date 2023-06-16 1:56 下午
 */
public class Test20230616 {


    public static void main(String[] args) {
//        System.out.println(getMax("abcabcbb"));
//        System.out.println(getMax("bbbbb"));
//        System.out.println(getMax("pwwkew"));
        System.out.println(getMax1("au"));
//        System.out.println(getMax("asjrgapa"));
    }

    private static int getMax1(String str) {
        if (str == null) {
            return 0;
        } else if (str.length() == 1) {
            return 1;
        }
        int len = str.length();
        int max = 1;
        for (int i = 0; i < len - 1; i++) {
//            if (max >= (len - i)) {
//                break;
//            }
            Set<Integer> charSet = new HashSet<>();
            charSet.add((int) str.charAt(i));
            for (int j = i + 1; j < len; j++) {
                int charVal = str.charAt(j);

                if (charSet.contains(charVal)) {
                    break;
                } else {
                    charSet.add(charVal);
                }
            }
            max = Math.max(max, charSet.size());
        }
        return 0;
    }


    private static int getMax(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int len = str.length();
        int resLen = 1;
        for (int i = 0; i < len; i++) {
            Set<Integer> charIntSet = new HashSet<>();
            char ch = str.charAt(i);
            charIntSet.add((int) ch);
            int low = i - 1;
            int high = i + 1;
            boolean lowFlag = true;
            boolean highFlag = true;
            while ((low >= 0 && lowFlag) || (high < len && highFlag)) {
                if (low >= 0) {
                    int lowVal = str.charAt(low);
                    if (charIntSet.contains(lowVal)) {
                        lowFlag = false;
                        low++;
                    } else {
                        charIntSet.add(lowVal);
                        if (low > 0) {
                            low--;
                        }
                    }
                }
                if (high < len) {
                    int highVal = str.charAt(high);
                    if (charIntSet.contains(highVal)) {
                        highFlag = false;
                        high--;
                    } else {
                        charIntSet.add(highVal);
                        if (high < len) {
                            high++;
                        }
                    }
                }
            }
            int currMax = Math.min(len - 1, high) - Math.max(0, low) + 1;
            resLen = Math.max(currMax, resLen);
        }
        return resLen;
    }

}

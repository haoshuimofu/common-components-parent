package leetcode;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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

        System.out.println("////////");

        int[][] intervals = new int[4][2];
        intervals[0] = new int[]{1, 3};
        intervals[1] = new int[]{2, 6};
        intervals[2] = new int[]{8, 10};
        intervals[3] = new int[]{15, 18};
        System.out.println(JSON.toJSONString(merge(intervals)));

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

    // ======================================================

    /**
     * 56. 合并区间
     *
     * @param intervals
     * @return
     */
    public static int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o1[0], o2[0]);
            }
        });
        List<int[]> intervalList = new ArrayList<>();
        int startNum = Integer.MIN_VALUE;
        int endNum = 0;
        int index = 0;
        while (index < intervals.length) {
            int[] currInterval = intervals[index];
            if (startNum == Integer.MIN_VALUE) {
                startNum = currInterval[0];
                endNum = currInterval[currInterval.length - 1];
            } else {
                if (currInterval[0] <= endNum) {
                    endNum = Math.max(endNum, currInterval[currInterval.length - 1]);
                } else {
                    intervalList.add(new int[]{startNum, endNum});
                    startNum = currInterval[0];
                    endNum = currInterval[currInterval.length - 1];
                }
            }
            if (index == intervals.length - 1) {
                intervalList.add(new int[]{startNum, endNum});
            }
            index++;
        }
        int[][] result = new int[intervalList.size()][2];
        for (int i = 0; i < intervalList.size(); i++) {
            result[i] = intervalList.get(i);
        }
        return result;
    }

    /**
     * 剑指 Offer II 074. 合并区间
     *
     * @param intervals
     * @return
     */
    public static int[][] merge1(int[][] intervals) {
        if (intervals.length == 1) {
            return intervals;
        }
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o1[0], o2[0]);
            }
        });
        int[][] resIntervals = new int[intervals.length][2];
        int count = 0;
        int startNum = 0;
        int endNum = 0;
        int index = 0;
        while (index < intervals.length) {
            int[] currInterval = intervals[index];
            if (index == 0) {
                startNum = currInterval[0];
                endNum = currInterval[currInterval.length - 1];
            } else {
                if (currInterval[0] <= endNum) {
                    endNum = Math.max(endNum, currInterval[currInterval.length - 1]);
                } else {
                    resIntervals[count] = new int[]{startNum, endNum};
                    count++;
                    startNum = currInterval[0];
                    endNum = currInterval[currInterval.length - 1];
                }
            }
            if (index == intervals.length - 1) {
                resIntervals[count] = new int[]{startNum, endNum};
            }
            index++;
        }
        return Arrays.copyOf(resIntervals, count);
    }

}

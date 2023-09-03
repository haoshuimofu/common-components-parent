package leetcode;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dewu.de
 * @date 2023-08-29 7:26 下午
 */
public class Test_20230829 {

    public static void main(String[] args) {
        Test_20230829 test = new Test_20230829();
        System.out.println(test.getPermutation(3, 2));
        System.out.println("//////////////////////");
        System.out.println(test.lengthOfLIS(new int[]{0, 1, 0, 3, 2, 3}));
        System.out.println("//////////////////////");
        System.out.println(test.addDigits(110));
    }


    public String getPermutation(int n, int k) {
        int[] visit = new int[n + 1];
        List<Integer> path = new ArrayList<>(n);
        for (int i = 1; i <= n; i++) {
            if (visit[0] < k) {
                doGetPermutation(n, k, visit, i, path);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (Integer num : path) {
            sb.append(num);
        }
        return sb.toString();

    }

    public void doGetPermutation(int n, int k, int[] visit, int currNum, List<Integer> path) {
        if (visit[0] == k) {
            return;
        }
        visit[currNum] = 1;
        path.add(currNum);
        if (path.size() == n) {
            visit[0]++;
            if (visit[0] == k) {
                return;
            }
        } else {
            for (int i = 1; i <= n; i++) {
                if (visit[i] == 0) {
                    doGetPermutation(n, k, visit, i, path);
                }
            }
        }
        if (visit[0] != k) {
            visit[currNum] = 0;
            path.remove(path.size() - 1);
        }
    }


    /**
     * 面试题 01.01. 判定字符是否唯一
     *
     * @param astr
     * @return
     */
    public boolean isUnique(String astr) {
        int[] flag = new int[26];
        for (int i = 0; i < astr.length(); i++) {
            flag[astr.charAt(i) - 'a']++;
        }
        for (int i = 0; i < flag.length; i++) {
            if (flag[i] > 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 面试题 01.02. 判定是否互为字符重排
     *
     * @param s1
     * @param s2
     * @return
     */
    public boolean CheckPermutation(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        int[] flag1 = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            flag1[s1.charAt(i) - 'a']++;
        }
        int[] flag2 = new int[26];
        for (int i = 0; i < s2.length(); i++) {
            flag2[s2.charAt(i) - 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            if (flag1[i] != flag2[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 面试题 01.03. URL化
     *
     * @param S
     * @param length
     * @return
     */
    public String replaceSpaces(String S, int length) {
        char[] chs = new char[S.length() * 3];
        int index = 0;
        for (int i = 0; i < S.length(); i++) {
            char ch = S.charAt(i);
            if (ch == ' ') {
                chs[index++] = '%';
                chs[index++] = '2';
                chs[index++] = '0';
            } else {
                chs[index++] = ch;
            }
        }
        return new String(chs, 0, index);
    }

    /**
     * 面试题 01.04. 回文排列
     *
     * @param s
     * @return
     */
    public boolean canPermutePalindrome(String s) {
        Map<Character, Integer> count = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            count.put(ch, count.getOrDefault(ch, 0) + 1);
        }
        int mid = 0;
        for (Integer value : count.values()) {
            if (value % 2 != 0) {
                mid++;
            }
        }
        return mid < 2;
    }

    /**
     * 216. 组合总和 III
     *
     * @param k
     * @param n
     * @return
     */
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        int[] sum = new int[1];
        for (int i = 1; i <= 9; i++) {
            if (9 - i + 1 >= k) {
                doCombinationSum3(k, n, i, sum, new ArrayList<>(), result);
            }
        }
        return result;
    }

    public void doCombinationSum3(int k, int n, int num, int[] sum, List<Integer> path, List<List<Integer>> result) {
        if (num > 9 || path.size() >= k) {
            return;
        }
        sum[0] += num;
        path.add(num);
        if (path.size() == k) {
            if (sum[0] == n) {
                result.add(new ArrayList<>(path));
            }
        } else {
            for (int i = num + 1; i <= 9; i++) {
                doCombinationSum3(k, n, i, sum, path, result);
            }
        }
        sum[0] -= num;
        path.remove(path.size() - 1);
    }

    /**
     * 300. 最长递增子序列 - DFS
     *
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        } else if (nums.length == 1) {
            return 1;
        }
        int[] maxLen = new int[1];
        for (int i = 0; i < nums.length; i++) {
            if (i == nums.length - 1 || (nums[i] < nums[i + 1] && maxLen[0] < nums.length - i)) {
                doLengthOfLIS(nums, Integer.MIN_VALUE, 0, i, maxLen);
            }
        }
        return maxLen[0];
    }

    public void doLengthOfLIS(int[] nums, int lastNum, int len, int currIndex, int[] maxLen) {
        if (currIndex >= nums.length) {
            return;
        }
        int currValue = nums[currIndex];
        if (currValue > lastNum) {
            lastNum = currValue;
            maxLen[0] = Math.max(maxLen[0], ++len);
        }
        for (int i = currIndex + 1; i < nums.length; i++) {
            doLengthOfLIS(nums, lastNum, len, i, maxLen);
        }
    }

    /**
     * 300. 最长递增子序列 - DP
     *
     * @param nums
     * @return
     */
    public int lengthOfLIS_DP(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        } else if (nums.length == 1) {
            return 1;
        }
        int[] dp = new int[nums.length];
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            dp[0] = 1;
            for (int j = 1; j < i; j++) {

            }
        }
        return 0;
    }

    /**
     * 258. 各位相加
     *
     * @param num
     * @return
     */
    public int addDigits(int num) {
        if (num < 10) {
            return num;
        }
        int sum = 0;
        while (num >= 10) {
            sum = 0;
            while (num > 0) {
                sum += num % 10;
                num /= 10;
            }
            num = sum;
        }
        return sum;
    }

}

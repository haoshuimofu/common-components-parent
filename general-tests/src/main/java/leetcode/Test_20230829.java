package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dewu.de
 * @date 2023-08-29 7:26 下午
 */
public class Test_20230829 {

    public static void main(String[] args) {
        Test_20230829 test = new Test_20230829();
        System.out.println(test.getPermutation(3, 2));
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
}

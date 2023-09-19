package leetcode;

import java.util.HashMap;
import java.util.Map;

public class Test_20230919 {
    public static void main(String[] args) {
        Test_20230919 test = new Test_20230919();
        System.out.println(test.canConstruct("aa", "aab"));
    }


    public boolean canConstruct(String ransomNote, String magazine) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < magazine.length(); i++) {
            char ch = magazine.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }
        for (int i = 0; i < ransomNote.length(); i++) {
            char ch = ransomNote.charAt(i);
            int count = map.getOrDefault(ch, 0);
            if (count == 0) {
                return false;
            } else {
                map.put(ch, --count);
            }
        }
        return true;
    }

    /**
     * 58. 最后一个单词的长度
     *
     * @param s
     * @return
     */
    public int lengthOfLastWord(String s) {
        int end = -1;
        int max = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char ch = s.charAt(i);
            if (ch != ' ') {
                if (end == -1) {
                    end = i;
                    max = 1;
                } else {
                    max = Math.max(max, end - i + 1);
                }
            } else {
                if (end != -1) {
                    break;
                }
            }
        }
        return max;
    }

}

package leetcode;

import com.alibaba.fastjson.JSON;

import java.util.*;

public class Test_20230901 {

    /**
     * 2418. 按身高排序
     *
     * @param names
     * @param heights
     * @return
     */
    public String[] sortPeople(String[] names, int[] heights) {

        Map<Integer, Integer> heightIndexMap = new HashMap<>(heights.length);
        for (int i = 0; i < heights.length; i++) {
            heightIndexMap.put(heights[i], i);
        }
        Arrays.sort(heights);
        String[] sortedNames = new String[names.length];
        int index = 0;
        for (int i = heights.length - 1; i >= 0; i--) {
            sortedNames[index++] = names[heightIndexMap.get(heights[i])];
        }
        return sortedNames;

    }

    public String[] permutation(String S) {
        List<String> strList = new ArrayList<>();
        boolean[] visit = new boolean[S.length()];
        char[] chs = new char[S.length()];
        int[] len = new int[1];
        for (int i = 0; i < S.length(); i++) {
            doPermutation(S, i, visit, chs, len, strList);
        }
        return strList.toArray(new String[]{});
    }

    public void doPermutation(String S, int index, boolean[] visit, char[] chs, int[] len, List<String> strList) {
        visit[index] = true;
        chs[len[0]] = S.charAt(index);
        len[0]++;
        if (len[0] == S.length()) {
            strList.add(new String(chs, 0, S.length()));
        } else {
            for (int i = 0; i < S.length(); i++) {
                if (!visit[i]) {
                    doPermutation(S, i, visit, chs, len, strList);
                }
            }
        }
        visit[index] = false;
        len[0]--;
    }


    /**
     * 剑指 Offer 68 - I. 二叉搜索树的最近公共祖先
     * 235. 二叉搜索树的最近公共祖先
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 搜索二叉树 left < root < right
        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestor(root.left, p, q);
        } else if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestor(root.right, p, q);
        } else {
            return root;
        }
    }

    /**
     * 剑指 Offer 61. 扑克牌中的顺子
     *
     * @param nums
     * @return
     */
    public boolean isStraight(int[] nums) {
        Arrays.sort(nums);
        int kingCount = 0;
        int diffCount = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] == 0) {
                kingCount++;
            } else if (nums[i - 1] != 0) {
                if (nums[i] == nums[i - 1]) {
                    return false;
                }
                diffCount += Math.abs(nums[i - 1] + 1 - nums[i]);
            }
        }
        return kingCount >= diffCount;
    }

    /**
     * 剑指 Offer 62. 圆圈中最后剩下的数字
     *
     * @param n
     * @param m
     * @return
     */
    public int lastRemaining(int n, int m) {
        if (n == 1 || m == 1) {
            return n - 1;
        }
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(i);
        }
        int num = 0;
        int from = 0;
        while (!list.isEmpty()) {
            int index = (from + m - 1) % list.size();
            num = list.remove(index);
            from = index;
        }
        return num;
    }

    /**
     * 3. 无重复字符的最长子串
     * 剑指 Offer 48. 最长不含重复字符的子字符串
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        } else if (s.length() == 1) {
            return 1;
        }
        int max = 0;
        int left = 0;
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!set.add(ch)) {
                max = Math.max(max, i - left);
                // 从left一直删, 直到删除重复字符
                for (int j = left; j < i; j++) {
                    char tempCh = s.charAt(j);
                    if (tempCh != ch) {
                        set.remove(tempCh);
                    } else {
                        left = j + 1;
                        break;
                    }
                }
            } else if (i == s.length() - 1) {
                max = Math.max(max, i - left + 1);
            }
        }
        return max;
    }

    /**
     * 剑指 Offer 49. 丑数
     * 264. 丑数 II
     *
     * @param n
     * @return
     */
    public int nthUglyNumber(int n) {
        if (n <= 0) {
            return 0;
        }
        int[] factor = new int[]{2, 3, 5};
        Set<Long> set = new HashSet<>();
        PriorityQueue<Long> queue = new PriorityQueue<>();
        queue.offer(1L);
        int count = 0;
        long num = 0;
        while (count < n) {
            num = queue.poll();
            for (int i : factor) {
                long temp = num * i;
                if (set.add(temp)) {
                    queue.offer(temp);
                }
            }
            count++;
        }
        return (int) num;
    }

    public int nthUglyNumber_DP(int n) {
        int[] dp = new int[n + 1];
        dp[1] = 1;
        int p2 = 1, p3 = 1, p5 = 1;
        for (int i = 2; i <= n; i++) {
            int num2 = dp[p2] * 2, num3 = dp[p3] * 3, num5 = dp[p5] * 5;
            dp[i] = Math.min(Math.min(num2, num3), num5);
            if (dp[i] == num2) {
                p2++;
            }
            if (dp[i] == num3) {
                p3++;
            }
            if (dp[i] == num5) {
                p5++;
            }
        }
        return dp[n];
    }

    /**
     * 263. 丑数
     *
     * @param n
     * @return
     */
    public boolean isUgly(int n) {
        if (n <= 0) {
            return false;
        }
        while (n % 5 == 0) {
            n /= 5;
        }
        while (n % 3 == 0) {
            n /= 3;
        }
        while (n % 2 == 0) {
            n /= 2;
        }
        return n == 1;
    }

    /**
     * 448. 找到所有数组中消失的数字
     *
     * @param nums
     * @return
     */
    public List<Integer> findDisappearedNumbers(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        List<Integer> missNums = new ArrayList<>();
        for (int i = 1; i <= nums.length; i++) {
            if (!set.contains(i)) {
                missNums.add(i);
            }
        }
        return missNums;
    }

    public static void main(String[] args) {
        Test_20230901 test = new Test_20230901();
        System.out.println(test.isStraight(new int[]{9, 0, 6, 0, 10}));
        long startMillis = System.currentTimeMillis();
        System.out.println(test.lastRemaining(56795, 87778));
        System.out.println("coast=" + (System.currentTimeMillis() - startMillis));
        System.out.println(test.lengthOfLongestSubstring("aab"));
        System.out.println();
        System.err.println(test.nthUglyNumber(10));

    }
}

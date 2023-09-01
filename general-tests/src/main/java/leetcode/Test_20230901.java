package leetcode;

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

}

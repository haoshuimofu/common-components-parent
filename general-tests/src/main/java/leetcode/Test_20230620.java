package leetcode;

public class Test_20230620 {


    public static void main(String[] args) {
        System.out.println("123".substring(0, 0));

//        String[] strs = new String[]{"flower", "flow", "flight"};
//        System.out.println(longestCommonPrefix(strs));

        String[] strs = new String[]{"ab", "a"};
        System.out.println(longestCommonPrefix1(strs));

    }

    /**
     * 暴力解法超时
     *
     * @param height
     * @return
     */
    public int maxArea(int[] height) {
        if (height == null || height.length < 1) {
            return 0;
        }
        int maxArea = 0;
        for (int i = 0; i < height.length - 1; i++) {
            for (int j = i + 1; j < height.length; j++) {
                int x = j - i;
                int y = 0;
                if (height[i] > 0 && height[j] > 0) {
                    y = Math.min(height[i], height[j]);
                } else if (height[i] < 0 && height[j] < 0) {
                    y = -Math.max(height[i], height[j]);
                }
                if (y > 0) {
                    int area = x * y;
                    if (area > maxArea) {
                        maxArea = area;
                    }
                }
            }
        }
        return maxArea;
    }


    /**
     * 暴力解法超时
     *
     * @param height
     * @return
     */
    public int maxArea1(int[] height) {
        int low = 0;
        int high = height.length - 1;
        int max = 0;
        while (low < high) {
            max = Math.max(height[low], height[high]) * (high - low);
            if (height[low] < height[high]) {
                low++;
            } else {
                high--;
            }
        }
        return max;
    }

    /**
     * 寻找字符串的最长公共前缀
     *
     * @param strs
     * @return
     */
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null) {
            return null;
        } else if (strs.length == 1) {
            return strs[0];
        }

        int index = 0;
        while (true) {
            for (int i = 0; i < strs.length - 1; i++) {
                int minLen = Math.min(strs[i].length(), strs[i + 1].length());
                if (index == minLen) {
                    return strs[0].substring(0, index);
                }
                if (strs[i].charAt(index) != strs[i + 1].charAt(index)) {
                    return strs[0].substring(0, index);
                }
            }
            index++;
        }
    }

    /**
     * 寻找字符串的最长公共前缀
     * 先到长度小的字符串
     *
     * @param strs
     * @return
     */
    public static String longestCommonPrefix1(String[] strs) {
        if (strs == null) {
            return null;
        } else if (strs.length == 1) {
            return strs[0];
        }
        int index = -1;
        int len = 0;
        for (int i = 0; i < strs.length; i++) {
            if (index == -1) {
                index = i;
                len = strs[i].length();
            } else if (strs[i].length() < len) {
                index = i;
                len = strs[i].length();
            }
        }
        if (len == 0) {
            return "";
        }
        for (int i = 0; i < len; i++) {
            int prev = index - 1;
            int next = index + 1;
            boolean flag = true;
            while (prev >= 0 || next < strs.length) {
                if ((prev >= 0 && strs[prev].charAt(i) != strs[index].charAt(i))
                        || (next < strs.length && strs[next].charAt(i) != strs[index].charAt(i))) {
                    flag = false;
                    break;
                }
                prev--;
                next++;
            }
            if (!flag) {
                return strs[index].substring(0, i);
            }
        }
        return strs[index].substring(0, len);
    }

}

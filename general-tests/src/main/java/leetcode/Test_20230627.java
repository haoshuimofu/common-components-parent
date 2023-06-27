package leetcode;

/**
 * @author dewu.de
 * @date 2023-06-27 9:55 上午
 */
public class Test_20230627 {


    public static void main(String[] args) {
        int[] nums = new int[]{0, 1, 2, 4, 7, 9};
        System.out.println(searchInsert(nums, -1));

        String str = "the sky is blue";
        System.out.println("|" + reverseWords(str) + "|");

        System.out.println(strStr("sadbutsad", "sad"));
    }


    public static int searchInsert(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[mid] >= target) {
                if (mid == 0) {
                    return mid;
                }
                if (nums[mid - 1] < target) {
                    return mid;
                } else {
                    high = mid - 1;
                }
            } else if (nums[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }

    public static String reverseWords(String s) {
        int start = s.length() - 1;
        int end = start;
        StringBuilder sb = new StringBuilder();
        while (end >= 0) {
            if (s.charAt(end) != ' ') {
                if (end == 0) {
                    sb.append(s, 0, start + 1).append(' ');
                    break;
                } else {
                    end--;
                }
            } else {
                if (end < start) {
                    sb.append(s, end + 1, start + 1).append(' ');
                }
                start = end - 1;
                end = start;
            }
        }
        int len = sb.length();
        if (len == 0) {
            return sb.toString();
        }
        return sb.substring(0, len - 1);
    }

    public static int strStr(String haystack, String needle) {



        return 0;
    }

}

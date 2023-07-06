package leetcode;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dewu.de
 * @date 2023-07-06 9:55 上午
 */
public class Test_20230706 {

    public static void main(String[] args) {
        System.out.println((int) '0');
        System.out.println((int) '1');
        String a = "1010", b = "1011";
        System.out.println(addBinary("0", "0"));
        System.out.println('1');

        int[] threeNums = new int[]{-1, 0, 1, 2, -1, -4, -2, -3, 3, 0, 4};
        System.out.println(JSON.toJSONString(threeSum(threeNums)));
    }

    /**
     * 二进制求和
     * char[]数组存储: 时间和内存击败率99.64% 和 79.10%
     * ArrayList存储: 击败率分别只有 31% 和 28%
     *
     * @param a
     * @param b
     * @return
     */
    public static String addBinary(String a, String b) {
        if (a == null || a.length() == 0) {
            return b;
        } else if (b == null || b.length() == 0) {
            return a;
        } else {
            int index = 1;
            int aIdx = a.length() - index;
            int bIdx = b.length() - index;
            int add = 0;
            char[] chs = new char[Math.max(a.length(), b.length()) + 1];
            int chIdx = chs.length - 1;
            while (aIdx >= 0 || bIdx >= 0) {
                int aChar = aIdx >= 0 ? a.charAt(aIdx) - 48 : 0;
                int bChar = bIdx >= 0 ? b.charAt(bIdx) - 48 : 0;
                int sum = aChar + bChar + add;
                chs[chIdx] = sum % 2 == 0 ? '0' : '1';
                chIdx--;
                add = sum / 2;
                aIdx = a.length() - ++index;
                bIdx = b.length() - index;
            }
            if (add > 0) {
                chs[0] = '1';
                return new String(chs);
            } else {
                return new String(chs, 1, chs.length - 1);
            }
        }
    }

    /**
     * 三数之和
     * 总体思路: 先固定一个数，后面两个用首位双指针
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        // 如果数组长度<3, 根本就不存在三数之和
        if (nums == null || nums.length < 3) {
            return null;
        }
        // 排序, 用以去重
        Arrays.sort(nums);
        List<List<Integer>> resList = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) { // 这里-2与否无所谓
            // 如果i位置大于0, 后面的任意两数之和肯定>0, 直接中断即可
            if (nums[i] > 0) {
                break;
            }
            // 先固定i, i不能重复
            if (i == 0 || nums[i] != nums[i - 1]) {
                // 双指针
                int low = i + 1;
                int high = nums.length - 1;
                while (low < high) {
                    // i + low + high 三数之和
                    int num = nums[i] + nums[low] + nums[high];
                    if (num > 0) {
                        high--;
                    } else if (num < 0) {
                        low++;
                    } else {
                        resList.add(Arrays.asList(nums[i], nums[low], nums[high]));
                        // 记录 low & high对应的值
                        int lowVal = nums[low];
                        int highVal = nums[high];
                        // 已经满足三数之和=0, low向右移动, high向左移动, 当它们的值都不和原来的值相同时才会有新的组合
                        low++;
                        while (low < high) {
                            if (nums[low] != lowVal) {
                                break;
                            }
                            low++;
                        }
                        high--;
                        while (low < high) {
                            if (nums[high] != highVal) {
                                break;
                            }
                            high--;
                        }
                    }
                }
            }
        }
        return resList;
    }

}

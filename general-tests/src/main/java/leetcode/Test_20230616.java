package leetcode;

import java.util.LinkedHashSet;

/**
 * @author dewu.de
 * @date 2023-06-16 1:56 下午
 */
public class Test_20230616 {


    public static void main(String[] args) {
//        System.out.println(getMax("abcabcbb"));
//        System.out.println(getMax("bbbbb"));
//        System.out.println(getMax("pwwkew"));
//        System.out.println(getMax("au"));
//        System.out.println(getMax("asjrgapa"));

        int[] nums1 = new int[]{1, 2};
        int[] nums2 = new int[]{3, 4};

        System.out.println(findMedianSortedArrays(nums1, nums2));
    }

    private static int getMax(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int len = str.length();
        int max = 1;
        LinkedHashSet<Integer> charSet = new LinkedHashSet<>();
        for (int i = 0; i < len - 1; i++) {
            if (max >= (len - i)) {
                break;
            }
            charSet.clear();
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
        return max;
    }


    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        if (len1 == 0 || len2 == 0) {
            if (len1 == 0 && len2 == 0) {
                return 0;
            } else if (len1 == 0) {
                return getMidValue(nums2);
            } else {
                return getMidValue(nums1);
            }
        }
        int len = nums1.length + nums2.length;

        // 找到比minVal第k+1大的数字就行
        int minVal = Math.min(nums1[0], nums2[0]) - 1;
        int k = len / 2 + 1;
        int index1 = 0;
        int index2 = 0;
        int prevVal = minVal;
        int currVal = minVal;
        int currIndex = 0;
        while (((index1 < nums1.length) || (index2 < nums2.length)) && currIndex <= k) {

            int num1 = minVal;
            if (index1 < nums1.length) {
                num1 = nums1[index1];
            }

            int num2 = minVal;
            if (index2 < nums2.length) {
                num2 = nums2[index2];
            }

            if (num1 > minVal && num2 > minVal) {
                if (num1 == num2) {
                    index1++;
                    currIndex++;
                    prevVal = currVal;
                    currVal = num1;
                    if (currIndex == k) {
                        break;
                    }

                    index2++;
                    currIndex++;
                    prevVal = currVal;
                    currVal = num2;
                    if (currIndex == k) {
                        break;
                    }
                } else if (num1 < num2) {
                    index1++;
                    currIndex += 1;
                    prevVal = currVal;
                    currVal = num1;
                    if (currIndex == k) {
                        break;
                    }
                } else {
                    index2++;
                    currIndex += 1;
                    prevVal = currVal;
                    currVal = num2;
                    if (currIndex == k) {
                        break;
                    }
                }
            } else if (num1 > minVal) {
                index1++;
                currIndex += 1;
                prevVal = currVal;
                currVal = num1;
                if (currIndex == k) {
                    break;
                }
            } else if (num2 > minVal) {
                index2++;
                currIndex += 1;
                prevVal = currVal;
                currVal = num2;
                if (currIndex == k) {
                    break;
                }
            }
        }
        System.out.println();
        System.out.println("len=" + len + ", k=" + k + ", currIndex=" + currIndex + ", currVal=" + currVal + ", prevVal=" + prevVal);

        if (len % 2 == 1) {
            return currVal;
        } else {
            return (prevVal + currVal) / 2.0;
        }
    }

    private static double getMidValue(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        } else if (nums.length == 1) {
            return nums[0];
        } else if (nums.length == 2) {
            return (nums[0] + nums[1]) / 2.0;
        }
        int mid = nums.length / 2;
        if (nums.length % 2 == 1) {
            return nums[mid];
        } else {
            return (nums[mid-1] + nums[mid]) / 2.0;
        }
    }

}

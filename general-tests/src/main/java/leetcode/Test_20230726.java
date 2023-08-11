package leetcode;

/**
 * @author dewu.de
 * @date 2023-07-26 10:08 上午
 */
public class Test_20230726 {

    public static void main(String[] args) {
        int[] nums = new int[]{2, 3, 1, 1, 4};
        System.out.println(jump(nums));

        System.out.println("///////////////");
        System.out.println(firstBadVersion(2126753390));
        System.out.println("///////////////");
        System.out.println(hammingWeight(-3));
    }

    /**
     * 45. 跳跃游戏 II
     *
     * @param nums
     * @return
     */
    public static int jump(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }
        int[] minSteps = new int[nums.length];
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = 1; j <= nums[i]; j++) {
                if (i + j >= nums.length - 1) {
                    return minSteps[i] + 1;
                } else if (minSteps[i + j] == 0 || minSteps[i + j] > minSteps[i] + 1) {
                    minSteps[i + j] = minSteps[i] + 1;
                }
            }
        }
        return minSteps[nums.length - 1];
    }


    /**
     * 278. 第一个错误的版本
     *
     * @param n
     * @return
     */
    public static int firstBadVersion(int n) {
        int low = 1;
        int high = n;
        while (low >= 1 && low <= high) {
            int mid = low + ((high - low) >> 1);
            if (isBadVersion(mid)) {
                if (mid == 1 || !isBadVersion(mid - 1)) {
                    return mid;
                } else {
                    high = mid - 1;
                }
            } else {
                low = mid + 1;
            }
        }
        return 0;
    }

    public static boolean isBadVersion(int version) {
        return version >= 1702766719;
    }


    /**
     * 191. 位1的个数
     */
    // you need to treat n as an unsigned value
    public static int hammingWeight(int n) {
//       String bStr =  Integer.toBinaryString(n);
//       int count = 0;
//        for (int i = 0; i < bStr.length(); i++) {
//            if (bStr.charAt(i) == '1') {
//                count++;
//            }
//        }
//        return count;

        int ret = 0;
        for (int i = 0; i < 32; i++) {
            if ((n & (1 << i)) != 0) {
                ret++;
            }
        }
        return ret;

    }


}

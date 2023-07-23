package leetcode;

import java.util.Arrays;

public class Test_20230722 {

    public static void main(String[] args) {
        int[] nums = new int[]{2, 1, 1, 2};
        System.out.println(rob(nums));

        int[] nums2 = new int[]{200, 3, 140, 20, 10};
        System.out.println(rob2(nums2));
    }

    /**
     * 198. 打家劫舍
     * f(n) = max(max(f(n-2), f(n-3)) + nums[n], f(n-1))
     *
     * @param nums
     * @return
     */
    public static int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        } else if (nums.length == 1) {
            return nums[0];
        }
        int[] money = new int[nums.length];
        money[0] = nums[0];
        money[1] = nums[1];
        if (nums.length > 2) {
            money[2] = money[0] + nums[2];
            if (nums.length > 3) {
                for (int i = 3; i < nums.length; i++) {
                    money[i] = Math.max(money[i - 1], Math.max(money[i - 2], money[i - 3]) + nums[i]);
                }
            }
        }
        return Math.max(money[nums.length - 1], money[nums.length - 2]);
    }

    /**
     * 解题思路
     * 打家劫舍Ⅰ 这道题大家已经知道了吧，这个题就是稍微复杂了一些。
     * 约定：n位房间数，n=nums.length
     * 因为首位房间连着，说明从nums[0]至多只能走到nums[n-2];
     * 从nums[1]才可以走到nums[n-1].
     * <p>
     * 好好好！！！
     * 那就从nums[0]打家劫舍到nums[n-2]，计算最多金额。
     * 再从nums[1]打家劫舍到nums[n-1]，计算最多金额。
     * 两个最多金额较大值就是结果。
     * <p>
     * 作者：i3usy-paynekjn
     * 链接：https://leetcode.cn/problems/house-robber-ii/solution/da-jia-jie-she-ii-cong-0huo-1zou-da-jia-r1k31/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     *
     * @param nums
     * @return
     */
    public static int rob2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        } else if (nums.length == 1) {
            return nums[0];
        }
        // 分别从 num[0] 和 nums[1] 走打家劫舍1
        int money = rob(Arrays.copyOf(nums, nums.length - 1));
        int[] numsNew = new int[nums.length - 1];
        System.arraycopy(nums, 1, numsNew, 0, numsNew.length);
        return Math.max(money, rob(numsNew));
    }

}

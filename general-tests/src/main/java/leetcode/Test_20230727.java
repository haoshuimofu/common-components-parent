package leetcode;

import com.alibaba.fastjson.JSON;

/**
 * @author dewu.de
 * @date 2023-07-27 1:15 下午
 */
public class Test_20230727 {

    public static void main(String[] args) {
        int[][] mat = new int[3][3];
        mat[0] = new int[]{1, 2, 3};
        mat[1] = new int[]{4, 5, 6};
        mat[2] = new int[]{1, 8, 9};
        System.out.println(JSON.toJSONString(findDiagonalOrder(mat)));
        System.out.println("/////////");
        int[] nums = new int[]{1, 7, 3, 6, 5, 6};
//        System.out.println(pivotIndex(nums));
        nums = new int[]{-1, -1, -1, -1, 0, 0};
        System.out.println(pivotIndex(nums));
    }


    /**
     * 498. 对角线遍历
     * 1 2 3
     * 4 5 6
     * 7 8 9
     */
    public static int[] findDiagonalOrder(int[][] mat) {
        int[] res = new int[mat.length * mat[0].length];
        int index = 0;
        // i,j右上走i-1,j+1; 左下走i+1,j-1; 注意边界判断
        int i = 0, j = 0;
        boolean directIsRightUp = true;
        while (i < mat.length && j < mat[0].length) {
            res[index] = mat[i][j];
            index++;
            if (directIsRightUp) {
                if (i == 0) {
                    if (j == mat[0].length - 1) {
                        i++;
                    } else {
                        j++;
                    }
                    directIsRightUp = false;
                } else {
                    if (j == mat[0].length - 1) {
                        i++;
                        directIsRightUp = false;
                    } else {
                        i--;
                        j++;
                    }
                }
            } else {
                if (j == 0) {
                    if (i < mat.length - 1) {
                        i++;
                    } else {
                        j++;
                    }
                    directIsRightUp = true;
                } else {
                    if (i < mat.length - 1) {
                        i++;
                        j--;
                    } else {
                        j++;
                        directIsRightUp = true;
                    }
                }
            }
        }
        return res;
    }

    /**
     * 724. 寻找数组的中心下标
     * <p>
     * 给你一个整数数组 nums ，请计算数组的 中心下标 。
     * <p>
     * 数组 中心下标 是数组的一个下标，其左侧所有元素相加的和等于右侧所有元素相加的和。
     * <p>
     * 如果中心下标位于数组最左端，那么左侧数之和视为 0 ，因为在下标的左侧不存在元素。这一点对于中心下标位于数组最右端同样适用。
     * <p>
     * 如果数组有多个中心下标，应该返回 最靠近左边 的那一个。如果数组不存在中心下标，返回 -1 。
     * </p>
     *
     * @param nums
     * @return
     */
    public static int pivotIndex(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        } else if (nums.length == 1) {
            return 0;
        }
        int low = 0;
        int high = nums.length - 1;
        int sum = 0;
        int[] lowSum = new int[nums.length];
        while (low <= high) {
            if (low == 0) {
                lowSum[low] = nums[low];
            } else {
                lowSum[low] = lowSum[low - 1] + nums[low];
            }
            sum += nums[low];
            if (low != high) {
                sum += nums[high];
            }
            low++;
            high--;
        }
        for (int i = 0; i < nums.length; i++) {
            // 注意, 前面while条件不成立, low++后才不成立, 这里需要>=
            if (i >= low) {
                lowSum[i] = lowSum[i - 1] + nums[i];
            }
            if (i == 0) {
                if (sum - nums[i] == 0) {
                    return i;
                }
            } else if (i == nums.length - 1) {
                if (lowSum[i - 1] == 0) {
                    return i;
                }
            } else if (lowSum[i - 1] == sum - nums[i] - lowSum[i - 1]) {
                return i;
            }
        }
        return -1;
    }
}

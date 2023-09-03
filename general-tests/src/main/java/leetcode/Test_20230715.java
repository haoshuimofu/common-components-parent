package leetcode;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test_20230715 {

    /**
     * 268. 丢失的数字
     * 给定一个包含 [0, n] 中 n 个数的数组 nums ，找出 [0, n] 这个范围内没有出现在数组中的那个数。
     *
     * @param nums
     * @return
     */
    public int missingNumber(int[] nums) {
        int[] flags = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            flags[nums[i]] = 1;
        }
        for (int i = 0; i < flags.length; i++) {
            if (flags[i] == 0) {
                return i;
            }
        }
        return -1;

    }

    /**
     * 35. 搜索插入位置
     *
     * @param nums
     * @param target
     * @return
     */
    public static int searchInsert(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        int index = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[mid] == target) {
                index = mid;
                break;
            } else if (nums[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        if (index != -1) {
            while (index >= 1) {
                if (nums[index] != nums[index - 1]) {
                    break;
                }
                index--;
            }
            return index;
        } else {
            return low;
        }
    }

    /**
     * 18. 四数之和
     *
     * @param nums
     * @param target
     * @return
     */
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        if (nums == null || nums.length < 4) {
            return Collections.emptyList();
        }
        Arrays.sort(nums);
        long targetValue = (long) target;
        // 三数之和 + nums[fourIndex] = target
        List<List<Integer>> result = new ArrayList<>();
        int fourIndex = nums.length - 1;
        while (fourIndex >= 3) {
            long fourValue = nums[fourIndex];
            for (int i = 0; i < fourIndex; i++) {
                if (nums[i] > target && nums[i + 1] > 0) {
                    break;
                }
                if (i == 0 || nums[i] != nums[i - 1]) {
                    int low = i + 1;
                    int high = fourIndex - 1;
                    while (low < high) {
                        long lowValue = nums[low];
                        long highValue = nums[high];
                        long value = nums[i] + lowValue + highValue + fourValue;
                        if (value == targetValue) {
                            result.add(Arrays.asList(nums[i], nums[low], nums[high], (int) fourValue));
                            // i 和 fourIndex位置固定, low 和 high移动, 知道找到不同的值
                            low++;
                            while (low < high) {
                                if (nums[low] != lowValue) {
                                    break;
                                }
                                low++;
                            }

                            high--;
                            while (high > low) {
                                if (nums[high] != highValue) {
                                    break;
                                }
                                high--;
                            }
                        } else if (value > targetValue) {
                            high--;
                        } else {
                            low++;
                        }
                    }
                }
            }
            fourIndex--;
            while (fourIndex >= 3) {
                if (nums[fourIndex] != fourValue) {
                    break;
                }
                fourIndex--;
            }
        }
        return result;
    }

    /**
     * 18. 四数之和
     *
     * @param nums
     * @param target
     * @return
     */
    public List<List<Integer>> fourSum1(int[] nums, int target) {
        if (nums == null) {
            return null;
        } else if (nums.length < 4) {
            return new ArrayList<>();
        }
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        long[] sum = new long[1];
        List<Integer> path = new ArrayList<>(4);
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > target)
            if (i == 0 || nums[i] != nums[i - 1]) {
                doFourSum1(nums, target, path, result, sum, i);
            }
        }
        return result;
    }


    public void doFourSum1(int[] nums, int target, List<Integer> path, List<List<Integer>> result, long[] sum, int currIndex) {
        if (path.size() == 4) {
            return;
        }
        path.add(nums[currIndex]);
        sum[0] += nums[currIndex];
        if (path.size() == 4) {
            if (sum[0] == target) {
                result.add(new ArrayList<>(path));
            }
        } else {
            for (int i = currIndex + 1; i < nums.length; i++) {
                if (i == currIndex + 1 || nums[i] != nums[i - 1]) {
                    doFourSum1(nums, target, path, result, sum, i);
                }
            }
        }
        path.remove(path.size() - 1);
        sum[0] -= nums[currIndex];
    }

    public static void main(String[] args) {

        Test_20230715 test = new Test_20230715();
        int[] nums = new int[]{2, 2, 2, 2, 2};
        System.out.println(JSON.toJSONString(test.fourSum1(nums, 8)));


    }

}

package leetcode;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test_20230715 {

    public static void main(String[] args) {
        int[] nums = new int[]{1000000000, 1000000000, 1000000000, 1000000000};
        System.out.println(Integer.MIN_VALUE);
        System.out.println(JSON.toJSONString(fourSum(nums, -294967296)));

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

}

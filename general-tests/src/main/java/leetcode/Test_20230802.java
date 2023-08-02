package leetcode;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dewu.de
 * @date 2023-08-02 1:19 下午
 */
public class Test_20230802 {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3};
        List<List<Integer>> res = permute(nums);
        for (List<Integer> list : res) {
            System.out.println(JSON.toJSONString(list));
        }
    }

    /**
     * 46. 全排列
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> permute(int[] nums) {
        // 排序组合集结果
        boolean[] visit = new boolean[nums.length];
        List<List<Integer>> permutes = new ArrayList<>();
        dfs(nums, 0, visit, new ArrayList<>(), permutes);
        return permutes;
    }

    public static void dfs(int[] nums, int count, boolean[] visit, List<Integer> path, List<List<Integer>> permutes) {
        if (count == nums.length) {
            // 当前path长度和nums长度一致, 说明是一个完成的组合了, path拷贝存放到结果集中
            permutes.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (visit[i] == false) {
                path.add(nums[i]);
                visit[i] = true;
                System.out.println("dfs递归之前路径: " + JSON.toJSONString(path));
                dfs(nums, count + 1, visit, path, permutes);

                visit[i] = false;
                path.remove(path.size() - 1);
            }
        }
    }
}

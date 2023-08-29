package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dewu.de
 * @date 2023-08-29 7:26 下午
 */
public class Test_20230829 {

    public static void main(String[] args) {
        Test_20230829 test = new Test_20230829();
        System.out.println(test.getPermutation(3, 2));
    }


    public String getPermutation(int n, int k) {
        int[] visit = new int[n + 1];
        List<Integer> path = new ArrayList<>(n);
        for (int i = 1; i <= n; i++) {
            if (visit[0] <k) {
                doGetPermutation(n, k, visit, i, path);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (Integer num : path) {
            sb.append(num);
        }
        return sb.toString();

    }

    public void doGetPermutation(int n, int k, int[] visit, int currNum, List<Integer> path) {
        if (visit[0] == k) {
            return;
        }
        visit[currNum] = 1;
        path.add(currNum);
        if (path.size() == n) {
            visit[0]++;
            if (visit[0] == k) {
                return;
            }
        } else {
            for (int i = 1; i <= n; i++) {
                if (visit[i] == 0) {
                    doGetPermutation(n, k, visit, i, path);
                }
            }
        }
        if (visit[0] != k) {
            visit[currNum] = 0;
            path.remove(path.size() - 1);
        }
    }
}

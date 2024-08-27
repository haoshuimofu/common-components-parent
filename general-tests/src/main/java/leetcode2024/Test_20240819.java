package leetcode2024;

public class Test_20240819 {

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int size = m + n - 1;
        m--;
        n--;
        while (m >= 0 || n >= 0) {
            if (m >= 0) {
                if (n >= 0) {
                    if (nums1[m] < nums2[n]) {
                        nums1[size] = nums2[n];
                        size--;
                        n--;
                    } else if (nums1[m] > nums2[n]){
                        nums1[size] = nums1[m];
                        size--;
                        m--;
                    } else {
                        nums1[size] = nums1[m];
                        nums1[size - 1] = nums1[size];
                        size -= 2;
                        m--;
                        n--;
                    }
                } else {
                    nums1[size] = nums1[m];
                    size--;
                    m--;
                }
            } else {
                nums1[size] = nums2[n];
                size--;
                n--;
            }
        }
    }

    public int canCompleteCircuit(int[] gas, int[] cost) {
        int start = 0;
        int end = 0;
        int totalGas = 0;
        while (start <= gas.length - 1) {
            while (end - start <= gas.length) {
                int index = end % gas.length;
                totalGas += gas[index] - cost[index];
                if (totalGas >= 0) {
                    if (end - start == gas.length) {
                        return start;
                    } else {
                        end++;
                    }
                } else {
                    if (index > start) {
                        start = index;
                        end = start;
                        totalGas = 0;
                    } else {
                        start++;
                        end = start;
                        totalGas = 0;
                    }
                    break;
                }
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        int[] gas = new int[]{1,2,3,4,5};
        int[] cost = new int[]{3,4,5,1,2};
        System.out.println(new Test_20240819().canCompleteCircuit(gas, cost));
    }



}

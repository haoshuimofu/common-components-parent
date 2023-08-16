package leetcode;

/**
 * @author dewu.de
 * @date 2023-08-16 7:48 下午
 */
public class Test_20230816 {

    public static void main(String[] args) {
        Test_20230816 test = new Test_20230816();
        System.out.println(test.mySqrt(10));
        for (int i = 1; i <= 32; i++) {
            System.out.println(i + "平方根 = " + test.mySqrt(i));

        }
    }

    public int mySqrt(int x) {
        int num = 1;
        while (num * num <= x) {
            num *= 2;
        }
        // 真值在(num/2, num]区间内
        int low = num / 2;
        int high = num;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (mid * mid == x) {
                return mid;
            } else if (mid * mid > x) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return low - 1;
    }

}

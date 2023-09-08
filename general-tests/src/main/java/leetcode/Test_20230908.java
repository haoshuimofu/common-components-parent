package leetcode;

/**
 * @author dewu.de
 * @date 2023-09-08 7:51 下午
 */
public class Test_20230908 {

    public static void main(String[] args) {
        Test_20230908 test = new Test_20230908();
        System.out.println(test.findNthDigit(19));
    }


    public int findNthDigit(int n) {
        int numLen = 0; // 位数
        int numCount = 1; // 位数长度对应有多少个数字
        int sumLen = 0;

        while (sumLen < n) {
            numLen++;
            numCount *= 10;
            sumLen += numLen * numCount;
        }
//        System.out.println(numLen - 1 + " - " + numLen + " - " + sumLen);
        // 循环结束, 说明第n位所在的位置对应的数字是numLen位数
        // 二分查找, 逼出对应的数字
        int low = 1;
        int high = numCount;
        sumLen -= numLen * numCount;
//        System.out.println(sumLen);
        int position = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int addingLen = mid * numLen;
            if (sumLen + addingLen >= n) {
                if (sumLen + addingLen - numLen < n) {
                    System.out.println("OK");
                    position = mid;
                    break;
                } else {
                    high = mid - 1;
                }
            } else {
                low = mid + 1;
            }
        }
        int targetNum = (int) Math.pow(10, numLen - 1) + position - 1;
//        System.out.println(position + ", target=" + targetNum + ", subLen=" + sumLen);
        int deltaLen = n - (sumLen + (position - 1) * numLen) + 1;
//        System.out.println("deltaLen=" + deltaLen);

        int factor = (int) Math.pow(10, numLen - deltaLen);
        return (targetNum / Math.max(1, factor)) % 10;

    }
}

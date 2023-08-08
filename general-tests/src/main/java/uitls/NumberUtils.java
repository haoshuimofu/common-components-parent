package uitls;

public class NumberUtils {

    /**
     * Integer.toBinaryString 32位二进制表示
     * int一共8个字节共32位
     * 最高位是符号位
     * 正数范围: 2^31-1 2147483647
     * 负数范围: -2^31Z 2147483648
     * 本身toBinaryString函数, 输出负数就是32位的
     *
     * @param num
     * @return
     */
    public static String intTo32BinaryString(int num) {
        String binaryStr = Integer.toBinaryString(num);
        if (num >= 0) {
            while (binaryStr.length() < 32) {
                binaryStr = "0" + binaryStr;
            }
        }
        return binaryStr;
    }


    public static void main(String[] args) {
        System.out.println(intTo32BinaryString(7));
        System.out.println(intTo32BinaryString(-7));

        int num = -7;
        System.out.println(num + "的二进制: " + getBitOneCount(num));
        num = 7;
        System.out.println(num + "的二进制: " + getBitOneCount(num));

    }

    public static int getBitOneCount(int num) {
        int count = 0;
        if (num >= 0) {
            // 正数
            count = num & 1;
            while (num > 0) {
                num = num >> 1;
                if ((num & 1) == 1) {
                    count++;
                }
            }
        } else {
            // 负数二进制补码表示
            for (int i = 0; i < 32; i++) {
                if ((num & (1 << i)) != 0) {
                    count++;
                }
            }
        }
        return count;
    }
}

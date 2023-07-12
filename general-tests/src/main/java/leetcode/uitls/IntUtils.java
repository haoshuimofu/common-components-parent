package leetcode.uitls;

public class IntUtils {

    /**
     * Integer.toBinaryString 32位二进制表示
     * int一共8个字节共32位
     * 最高位是符号位
     * 正数范围: 2^31-1 2147483647
     * 负数范围: -2^21 2147483648
     * 本身toBinaryString函数, 输出负数就是32位的
     *
     * @param num
     * @return
     */
    public static String intToBinary32(int num) {
        String binaryStr = Integer.toBinaryString(num);
        if (num >= 0) {
            while (binaryStr.length() < 32) {
                binaryStr = "0" + binaryStr;
            }
        }
        return binaryStr;
    }


    public static void main(String[] args) {
        System.out.println(intToBinary32(2));
    }
}

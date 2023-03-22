package test.string;

/**
 * 速度: StringBuilder > StringBuffer ≈ String拼接
 * @author dewu.de
 * @date 2023-03-21 4:36 下午
 */
public class StringTest {

    public static void main(String[] args) {
        String str1 = "11";
        String str2 = "aa";
        String str3 = "22";
        String str4 = "bb";
        String str5 = "33";
        String str6 = "cc";

        int round = 100;
        int batch = 1000000;

        long start = System.currentTimeMillis();
        for (int i = 0; i < round; i++) {
            for (int j = 0; j < batch; j++) {
                String resStr = str1 + str2 + str3 + str4 + str5 + str6;
            }
        }
        System.out.println("10W次耗时: " + (System.currentTimeMillis() - start) / round);


        start = System.currentTimeMillis();
        for (int i = 0; i < round; i++) {
            for (int j = 0; j < batch; j++) {
                String resStr = new StringBuilder(str1)
                        .append(str2)
                        .append(str3)
                        .append(str4)
                        .append(str5)
                        .append(str6).toString();
            }
        }
        System.out.println("StringBuilder 10W次耗时: " + (System.currentTimeMillis() - start) / round);


        start = System.currentTimeMillis();
        for (int i = 0; i < round; i++) {
            for (int j = 0; j < batch; j++) {
                String resStr = new StringBuffer(str1)
                        .append(str2)
                        .append(str3)
                        .append(str4)
                        .append(str5)
                        .append(str6).toString();
            }
        }
        System.out.println("StringBuffer 10W次耗时: " + (System.currentTimeMillis() - start) / round);
    }

}

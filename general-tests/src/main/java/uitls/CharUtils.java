package uitls;

/**
 * @author dewu.de
 * @date 2023-08-18 7:42 下午
 */
public class CharUtils {

    public static void main(String[] args) {
        System.out.println(getCharIntValue('a'));//97
        System.out.println(getCharIntValue('z'));//122
        System.out.println(122 - 97 + 1);


    }

    public static int getCharIntValue(char ch) {
        return (int) ch;
    }
}

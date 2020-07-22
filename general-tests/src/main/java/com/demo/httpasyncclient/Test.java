package com.demo.httpasyncclient;

/**
 * @author wude
 * @date 2020/4/23 12:31
 */
public class Test {

    public static void main(String[] args) {
        String version1 = "9.8.0";
        String version2 = "9.8.0";
        System.err.println(compareVersion(version1, version2));
    }

    public static int compareVersion(String version1, String version2) {
        String[] vs1 = version1.split("\\.");
        String[] vs2 = version2.split("\\.");
        if (vs1.length == vs2.length) {
            for (int i = 0; i < vs1.length; i++) {
                int result = vs1[i].length() - vs2[i].length();
                if (result != 0) {
                    return result > 0 ? 1 : -1;
                }
                result = vs1[i].compareTo(vs2[i]);
                if (result != 0) {
                    return result;
                }
            }
        }
        return 0;
    }
}
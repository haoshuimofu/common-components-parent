package com.demo.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wude
 * @date 2021/6/10 11:03
 */
public class IPUtil {

    public static void main(String[] args) {

        List<String> ipList = new ArrayList<>();

        String ip = "2551551893";
        int round = 0;
        while (true) {
            int len = ip.length();

            for (int i = 1; i <= 3; i++) {

                for (int j = i + 1; j <= i + 3; j++) {

                    for (int x = j + 1; x <= j + 3; x++) {

                        for (int y = x + 1; y <= x + 3; y++) {

                            if (y > len) {
                                continue;
                            }
                            int round1 = Integer.valueOf(ip.substring(0, i));
                            int round2 = Integer.valueOf(ip.substring(i, j));
                            int round3 = Integer.valueOf(ip.substring(j, x));
                            int round4 = Integer.valueOf(ip.substring(x, y));
                            if (round1 <= 0 || round1 > 255) {
                                continue;
                            }
                            if (round2 <= 0 || round2 > 255) {
                                continue;
                            }
                            if (round3 <= 0 || round3 > 255) {
                                continue;
                            }
                            if (round4 <= 0 || round4 > 255) {
                                continue;
                            }
                            ipList.add(round1 + "." + round2 + "." + round3 + "." + round4);
                        }
                    }
                }
            }
            ip = ip.substring(1);
            if (ip.length() < 4) {
                break;
            }
        }
        for (String ipStr : ipList) {
            System.err.println(ipStr);
        }

    }

}
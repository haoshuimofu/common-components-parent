package com.demo.algorithm;

/**
 * @author wude
 * @date 2021/6/10 11:03
 */
public class IPUtil {

    public static void main(String[] args) {

        String ip = "2551551893";
        int len = ip.length();

        int round = 3;

        for (int i = 1; i <= 3 ; i++) {

            for (int j = i+1; j <=i+3 ; j++) {

                for (int x = j+1; x <=j+3 ; x++) {

                    for (int y = x+1; y <=x+3 ; y++) {

                        if (y != len) {
                            continue;
                        }
                        int round1 = Integer.valueOf(ip.substring(0, i));
                        int round2 = Integer.valueOf(ip.substring(i, j));
                        int round3 = Integer.valueOf(ip.substring(j, x));
                        int round4 = Integer.valueOf(ip.substring(x, y));
                        if (round1 <=0 || round1 > 255) {
                            continue;
                        }
                        if (round2 <=0 || round2 > 255) {
                            continue;
                        }
                        if (round3 <=0 || round3 > 255) {
                            continue;
                        }
                        if (round4 <=0 || round4 > 255) {
                            continue;
                        }

                        System.err.println(round1 + "." + round2 + "." + round3+ "." + round4);


                    }

                }


            }


        }



    }

}
package com.demo.jts;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoadNetUtils {
    /**
     * 计算直线距离
     *
     * @param aLng a点
     * @param aLat a点
     * @param bLng b点
     * @param bLat b点
     * @return
     */
    public static double calDis(double aLng, double aLat, double bLng, double bLat) {
        int radius = 6371;
        double dLat = Math.toRadians(bLat - aLat);
        double dLon = Math.toRadians(bLng - aLng);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(aLat))
                * Math.cos(Math.toRadians(bLat)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = radius * c;
        return d * 1000;
    }

    /**
     * 计算球面距离
     *
     * @param aLng a点
     * @param aLat a点
     * @param bLng b点
     * @param bLat b点
     * @return
     */
    public static double calSpherDistance(double aLng, double aLat, double bLng, double bLat) {
        return calDis(aLng, aLat, bLng, bLat) * 1.414;
    }

    public static void main(String[] args) {
        double lon1 = 121.0;
        double lat1 = 31.0;
        double lon2 = 121.01;
        double lat2 = 31.0;

        System.err.println(calDis(lon1, lat1, lon2, lat2));
        System.err.println(calSpherDistance(lon1, lat1, lon2, lat2));
    }
}

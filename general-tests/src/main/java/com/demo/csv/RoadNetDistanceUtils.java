package com.demo.csv;

import org.locationtech.spatial4j.io.GeohashUtils;

/**
 * @author dewu.de
 * @date 2021-12-27 5:38 下午
 */
public class RoadNetDistanceUtils {

    private static final Integer HSF_ROUTE_GEO_HASH_PRECISION = 5;

    /**
     * 根据OD对结算HSF一致性哈希值
     *
     * @param odPair
     * @return
     */
    public static String getHsfConsistent(OdPair odPair) {
        return getHsfConsistent(odPair.getOrigin().getLon(), odPair.getOrigin().getLat(),
                odPair.getDest().getLon(), odPair.getDest().getLon());
    }

    /**
     * 两个坐标点经纬度计算geohash值, 自然排序较小值作为HSF一致性哈希值
     *
     * @param lng1
     * @param lat1
     * @param lng2
     * @param lat2
     * @return
     */
    public static String getHsfConsistent(double lng1, double lat1, double lng2, double lat2) {
        String geohash1 = GeohashUtils.encodeLatLon(lat1, lng1, HSF_ROUTE_GEO_HASH_PRECISION);
        String geohash2 = GeohashUtils.encodeLatLon(lat2, lng2, HSF_ROUTE_GEO_HASH_PRECISION);
        return geohash1.compareTo(geohash2) > 0 ? geohash2 : geohash1;
    }

    /**
     * 计算OD对两个点之间的直线距离
     *
     * @param odPair OD对
     * @return OD对两点直线距离
     */
    public static double calDis(OdPair odPair) {
        return calDis(odPair.getOrigin().getLon(), odPair.getOrigin().getLat(),
                odPair.getDest().getLon(), odPair.getDest().getLat());
    }


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
     * @param odPair a点
     * @return
     */
    public static double calSphereDistance(OdPair odPair) {
        return calDis(odPair) * 1.414;
    }

}

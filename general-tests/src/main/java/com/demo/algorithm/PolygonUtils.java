package com.demo.algorithm;

import lombok.Getter;

/**
 * @author dewu.de
 * @date 2023-03-07 11:01 上午
 */
public class PolygonUtils {

    /**
     * 射线法判断多边形是否包含点, 点在多边形顶点或边上也算
     *
     * @param polygon 多边形点序列
     * @param p       点
     * @return
     */
    public static boolean containsPoint(Point[] polygon, Point p) {
        int cross = 0;
        for (int i = 0; i < polygon.length - 1; i++) {
            Point p1 = polygon[i];
            Point p2 = polygon[i + 1];
            if (p.equals(p1) || p.equals(p2)) {
                return true;
            }
            if (p1.getLat() == p2.getLat()
                    && p.getLat() == p1.getLat()
                    && ((p1.getLng() < p.getLng() && p.getLng() < p2.getLng()) || (p2.getLng() < p.getLng() && p.getLng() < p1.getLng()))) {
                return true;
            }
            if ((p1.getLat() < p.getLat() && p.getLat() <= p2.getLat()) || (p2.getLat() < p.getLat() && p.getLat() <= p1.getLat())) {
                double lng = p1.getLng() + (p.getLat() - p1.getLat()) * (p2.getLng() - p1.getLng()) / (p2.getLat() - p1.getLat());
                if (p.getLng() == lng) {
                    return true;
                }
                if (lng > p.getLng()) {
                    cross++;
                }
            }
        }
        return cross % 2 != 0;
    }

    @Getter
    class Point {
        private double lng;
        private double lat;
    }
}

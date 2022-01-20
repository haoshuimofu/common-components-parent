package com.demo.csv;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class OdPair {

    private Point origin;
    private Point dest;

    public OdPair(Point origin, Point dest) {
        this.origin = origin;
        this.dest = dest;
    }

    public Point getOrigin() {
        return origin;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    public Point getDest() {
        return dest;
    }

    public void setDest(Point dest) {
        this.dest = dest;
    }

    public static class Point {

        private double lon;
        private double lat;

        public Point(double lon, double lat) {
            this.lon = lon;
            this.lat = lat;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }
    }

}

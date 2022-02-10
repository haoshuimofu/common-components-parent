package com.demo.benchmark;

import org.locationtech.spatial4j.io.GeohashUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.File;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author dewu.de
 * @date 2022-01-14 3:40 下午
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class JmhDemo {

    //    @Benchmark
    public int sleepWhile() {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Benchmark
    public String geohash() {
        double random = new Random().nextDouble();
        double aLng = 112.90895590558648d;
        double aLat = 28.228380968794227d;
        double bLng = 112.913596;
        double bLat = 28.205178;
        String geohash1 =  GeohashUtils.encodeLatLon(aLat + random, aLng + random, 5);
        String geohash2 =  GeohashUtils.encodeLatLon(bLat + random, bLng + random, 5);
        return geohash1.compareTo(geohash2) > 0 ? geohash2 : geohash1;



    }

    /**
     * 计算直线距离
     *
     * @return
     */
//    @Benchmark
    public double calDis() {
        double aLng = 112.90895590558648d;
        double aLat = 28.228380968794227d;
        double bLng = 112.913596;
        double bLat = 28.205178;

        int radius = 6371;
        double dLat = Math.toRadians(bLat - aLat);
        double dLon = Math.toRadians(bLng - aLng);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(aLat))
                * Math.cos(Math.toRadians(bLat)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = radius * c;
        return d * 1000;
    }


    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(JmhDemo.class.getSimpleName())
                .forks(1)
                .warmupIterations(3)
                .measurementIterations(3)
                .build();
        new Runner(opt).run();

    }

}

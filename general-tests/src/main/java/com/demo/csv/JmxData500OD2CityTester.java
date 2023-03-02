package com.demo.csv;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dewu.de
 * @date 2022-12-05 5:57 下午
 */
public class JmxData500OD2CityTester {


    public static void main(String[] args) throws IOException {
        int batch = 500;

        int cityId1 = 23;
        String inputPath1 = "/Users/eleme/local/jmx/data/" + cityId1 + ".txt";

        int cityId2 = 3;
        String inputPath2 = "/Users/eleme/local/jmx/data/" + cityId2 + ".txt";

        int cityId3 = 4;
        String inputPath3 = "/Users/eleme/local/jmx/data/" + cityId3 + ".txt";


        String outPath = "/Users/eleme/local/jmx/data/" + cityId1 + "_500.csv";
        BufferedWriter writer = new BufferedWriter(new FileWriter(outPath));
        writer.write("od");
        writer.newLine();


        Reader reader1 = new FileReader(inputPath1);
        BufferedReader bufferedReader1 = new BufferedReader(reader1);

        Reader reader2 = new FileReader(inputPath2);
        BufferedReader bufferedReader2 = new BufferedReader(reader2);

//        Reader reader3 = new FileReader(inputPath3);
//        BufferedReader bufferedReader3 = new BufferedReader(reader3);

        List<OdPair> odPairs = new ArrayList<>(batch);
        int rows = 0;
        String line1, line2, line3;
        while ((line1 = bufferedReader1.readLine()) != null
                && (line2 = bufferedReader2.readLine()) != null
//                && (line3 = bufferedReader3.readLine()) != null
        ) {
            rows++;
            if (rows == 1) {
                continue;
            }
//            if (rows > 100) {
//                break;
//            }
            // city_id,origin_lng,origin_lat,dest_lng,dest_lat
            try {
                OdPair odPair1 = parseRecord(cityId1, line1);
                if (odPair1 != null) {
                    odPairs.add(odPair1);
                }
                OdPair odPair2 = parseRecord(cityId2, line2);
                if (odPair2 != null) {
                    odPairs.add(odPair2);
                }

//                OdPair odPair3 = parseRecord(cityId3, line3);
//                if (odPair3 != null) {
//                    odPairs.add(odPair3);
//                }

                if (odPairs.size() == batch) {
                    String od = JSON.toJSONString(odPairs).replace("\"\"", "\"");
                    String request = od;
                    System.out.println(request);
                    writer.write(request);
                    writer.newLine();
                    odPairs.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (odPairs.size() > 0) {
            String od = JSON.toJSONString(odPairs).replace("\"\"", "\"");
            String request = od;
            System.out.println(request);
            writer.write(request);
        }
        writer.flush();
        writer.close();

        reader1.close();
        reader2.close();
        System.err.println("rows=" + rows);
    }

    public static OdPair parseRecord(int cityId, String line) {
        // city_id,origin_lng,origin_lat,dest_lng,dest_lat
        String[] dataCols = line.split(",");
        if (dataCols.length != 5) {
            return null;
        }
        double originLng = Double.parseDouble(dataCols[1]);
        double originLat = Double.parseDouble(dataCols[2]);
        double destLng = Double.parseDouble(dataCols[3]);
        double destLat = Double.parseDouble(dataCols[4]);
        return new OdPair(cityId, new Point(originLng, originLat), new Point(destLng, destLat));
    }


    @Data
    @AllArgsConstructor
    static class Point {

        private double lng;
        private double lat;

    }

    @Data
    @AllArgsConstructor
    static class OdPair {
        private int cityId;
        private Point origin;
        private Point dest;
    }

}

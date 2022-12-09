package com.demo.csv;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dewu.de
 * @date 2022-12-05 5:57 下午
 */
public class JmxDataTester {


    public static void main(String[] args) throws IOException {
        String inputPath = "/Users/eleme/local/jmx/data/23.txt";
        File file = new File(inputPath);
//        if (file.exists()) {
//            file.delete();
//        }


        String path = "/Users/eleme/local/jmx/data/23_500od.json";
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path)));
        writer.write("city_id|od");
        writer.newLine();


        Reader in = new FileReader(inputPath);

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader("city_id", "origin_lng", "origin_lat", "dest_lng", "dest_lat").parse(in);

        BufferedReader reader = new BufferedReader(new FileReader(inputPath));
        List<OdPair> odPairs = new ArrayList<>(500);
        int rows = 0;
        for (CSVRecord record : records) {
            rows++;
            if (rows == 1) {
                continue;
            }
            if (rows > 100000) {
                break;
            }
            // city_id,origin_lng,origin_lat,dest_lng,dest_lat
            try {

                Pair<Integer, OdPair> pair = parseRecord(record);
                odPairs.add(pair.getRight());
                if (odPairs.size() == 500) {
                    String od = JSON.toJSONString(odPairs).replace("\"\"", "\"");

//                    System.out.println(od);
                    writer.write(od);
                    writer.newLine();
                    odPairs.clear();
                }


//                String od = JSON.toJSONString(Collections.singletonList(pair.getRight()));
//                printer.printRecord(pair.getLeft(), od);
            } catch (Exception e) {
                System.err.println(record.toList());
            }
        }
//        if (odPairs.size() > 0) {
//            String od = JSON.toJSONString(odPairs).replace("\"\"", "\"");
//
//            System.out.println(od);
//            writer.write(23 + "|" + od);
//
//        }
        writer.flush();
        writer.close();

        in.close();
        System.err.println("rows=" + rows);
    }

    public static Pair<Integer, OdPair> parseRecord(CSVRecord record) {
        // city_id,origin_lng,origin_lat,dest_lng,dest_lat
        int cityId = Integer.parseInt(record.get(0));

        double originLng = Double.parseDouble(record.get(1));
        double originLat = Double.parseDouble(record.get(2));
        double destLng = Double.parseDouble(record.get(3));
        double destLat = Double.parseDouble(record.get(4));
        OdPair odPair = new OdPair(new Point(originLng, originLat), new Point(destLng, destLat));
        return Pair.of(cityId, odPair);
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
        private Point origin;
        private Point dest;
    }

    static enum Headers {
        CITY_ID, OD
    }
}

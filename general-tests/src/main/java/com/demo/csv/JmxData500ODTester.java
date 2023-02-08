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
public class JmxData500ODTester {


    public static void main(String[] args) throws IOException {
        int cityId = 23;
        int batch = 500;

        String inputPath = "/Users/eleme/local/jmx/data/" + cityId + ".txt";
        File file = new File(inputPath);
//        if (file.exists()) {
//            file.delete();
//        }
        String path = "/Users/eleme/local/jmx/data/" + cityId + "_500.csv";
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path)));
        writer.write("id,msg_req");
        writer.newLine();


        Reader in = new FileReader(inputPath);


        BufferedReader reader = new BufferedReader(new FileReader(inputPath));
        List<OdPair> odPairs = new ArrayList<>(batch);
        int rows = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            rows++;
            if (rows == 1) {
                continue;
            }
//            if (rows > 100) {
//                break;
//            }
            // city_id,origin_lng,origin_lat,dest_lng,dest_lat
            try {
                OdPair odPair = parseRecord(line);
                if (odPair != null) {
                    odPairs.add(odPair);
                }
                if (odPairs.size() == batch) {
                    String od = JSON.toJSONString(odPairs).replace("\"\"", "\"");
                    String request = rows + "," + od;
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
            String request = rows + "," + od;
            System.out.println(request);
            writer.write(request);
        }
        writer.flush();
        writer.close();

        in.close();
        System.err.println("rows=" + rows);
    }

    public static OdPair parseRecord(String line) {
        // city_id,origin_lng,origin_lat,dest_lng,dest_lat
        String[] dataCols = line.split(",");
        if (dataCols.length != 5) {
            return null;
        }
        int cityId = Integer.parseInt(dataCols[0]);

        double originLng = Double.parseDouble(dataCols[1]);
        double originLat = Double.parseDouble(dataCols[2]);
        double destLng = Double.parseDouble(dataCols[3]);
        double destLat = Double.parseDouble(dataCols[4]);
        return new OdPair(new Point(originLng, originLat), new Point(destLng, destLat));
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

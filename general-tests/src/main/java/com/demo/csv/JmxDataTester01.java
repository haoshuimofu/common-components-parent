package com.demo.csv;

import com.alibaba.fastjson.JSON;
import com.demo.csv.model.BaseRequest;
import com.demo.csv.model.Point;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dewu.de
 * @date 2022-12-05 5:57 下午
 */
public class JmxDataTester01 {

    static String SOURCE = "test_eta";

    public static void main(String[] args) throws IOException {
        String inputPath = "/Users/eleme/local/jmx/data/1.txt";
        File file = new File(inputPath);
//        if (file.exists()) {
//            file.delete();
//        }


        String path = "/Users/eleme/local/jmx/data/1_path.csv";
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path)));
        writer.write("scene|request");
        writer.newLine();


        Reader in = new FileReader(inputPath);

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader("city_id", "origin_lng", "origin_lat", "dest_lng", "dest_lat").parse(in);

        BufferedReader reader = new BufferedReader(new FileReader(inputPath));
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
                Pair<Integer, JmxDataTester01.OdPair> pair = parseRecord(record);
                BaseRequest request = new BaseRequest();
                request.setSource(SOURCE);
                request.setCityId(1);
                request.setOrigin(pair.getRight().getOrigin());
                request.setDest(pair.getRight().getDest());

                String requestStr = JSON.toJSONString(request).replace("\"\"", "\"");

                System.out.println(requestStr);
                writer.write(SOURCE + "|" + requestStr);
                writer.newLine();


//                String od = JSON.toJSONString(Collections.singletonList(pair.getRight()));
//                printer.printRecord(pair.getLeft(), od);
            } catch (Exception e) {
                System.err.println(record.toList());
            }
        }

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
    static class OdPair {
        private Point origin;
        private Point dest;
    }


}

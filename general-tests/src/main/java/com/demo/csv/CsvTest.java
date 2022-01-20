package com.demo.csv;

import com.alibaba.fastjson.JSON;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

/**
 * @author dewu.de
 * @date 2022-01-13 4:27 下午
 */
public class CsvTest {

    public static void main(String[] args) throws Exception {
        String outPath = "/Users/eleme/devdoc/temp/roadnet_gateway_batch_2_50W.txt";
//        File file = new File(outPath);
//        if (file.exists()) {
//            file.delete();
//        }
        BufferedWriter bufferedWriter =
                new BufferedWriter(new FileWriter(outPath));

        String path = "/Users/eleme/devdoc/testcases_100_5000.csv";
        Reader in = new FileReader(path);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
        List<OdPair> data = new ArrayList<>();
        int rows = 0;
        int up100 = 0;
        int resultRow = 0;
        for (CSVRecord record : records) {
            if (rows > 1_000_000) {
                break;
            }


            OdPair odPair = new OdPair(new OdPair.Point(Double.parseDouble(record.get(0)), Double.parseDouble(record.get(1))),
                    new OdPair.Point(Double.parseDouble(record.get(2)), Double.parseDouble(record.get(3))));
            data.add(odPair);
            int distance = (int) RoadNetDistanceUtils.calSphereDistance(odPair);
            if (distance >= 1000 && distance < 3000) {
//                System.err.println("rows=" + rows + ", distance=" + distance);
//                System.out.println("[" + JSON.toJSONString(data) + "]");
                up100++;
            }
            if (data.size() == 2) {
                bufferedWriter.write("[" + JSON.toJSONString(data) + "]");
                bufferedWriter.newLine();
                data.clear();
                resultRow++;
//                System.err.println("resultRows=" + resultRow);
            }
            rows++;
        }
        if (!data.isEmpty()) {
            bufferedWriter.write("[" + JSON.toJSONString(data) + "]");
            bufferedWriter.newLine();
        }

        bufferedWriter.flush();
        in.close();
        System.err.println("-----");
        System.err.println("rows=" + rows + ", up100=" + up100);
    }
}

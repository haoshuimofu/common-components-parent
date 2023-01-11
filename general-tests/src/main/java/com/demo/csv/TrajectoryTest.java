package com.demo.csv;

import com.alibaba.fastjson.JSON;
import com.demo.csv.model.AdjustRequest;

import java.io.*;
import java.util.List;

/**
 * @author dewu.de
 * @date 2023-01-10 10:49 上午
 */
public class TrajectoryTest {


    public static void main(String[] args) throws IOException {

        String inputPath = "/Users/eleme/local/odpscmd/bin/trajecotry_request.csv";
        File file = new File(inputPath);
//        if (file.exists()) {
//            file.delete();
//        }
        String path = "/Users/eleme/local/odpscmd/bin/request_2w.csv";
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path)));
//        writer.write("city_id|od");
//        writer.newLine();


        Reader in = new FileReader(inputPath);


        BufferedReader reader = new BufferedReader(new FileReader(inputPath));
        int rows = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            rows++;
            if (rows > 20000) {
                break;
            }
            // city_id,origin_lng,origin_lat,dest_lng,dest_lat
            try {
                List<AdjustRequest> requests = JSON.parseArray(line, AdjustRequest.class);
//                String reqStr = "["+2+","+JSON.toJSONString(requests.get(0))+"]";
                String reqStr = JSON.toJSONString(requests.get(0));
                System.out.println(reqStr);
                writer.write(reqStr);
                writer.newLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        writer.flush();
        writer.close();

        in.close();
        System.err.println("rows=" + rows);
    }


}

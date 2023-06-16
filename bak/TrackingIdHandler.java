package com.demo.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;

/**
 * @author dewu.de
 * @date 2023-06-14 11:26 上午
 */
public class TrackingIdHandler {

    private static final String CSV = "/Users/eme/local/jmx/data/2023-06-14-14-59-46_EXPORT_CSV_28412005_181_0.csv";
    private static final String[] HEADERS = new String[]{"tracking_id", "platform_tracking_id", "shipping_option", "carrier_id", "source_id", "platform_merchant_id", "product_id", "created_at", "_dsql_guid"};

    public static void main(String[] args) throws Exception {

        core();

        mis();


    }


    public static void core() throws Exception {
        String outputFile = "/Users/eme/local/jmx/data/apollo-sos-core.csv";
//        File file = new File(outPath);
//        if (file.exists()) {
//            file.delete();
//        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        writer.write("tracking_id,shard_id");
        writer.newLine();


        Reader in = new FileReader(CSV);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader(HEADERS).parse(in);

        int rows = 0;
        for (CSVRecord record : records) {
            if (++rows == 1) {
                continue;
            }
//            if (rows >3) {
//                break;
//            }
            String trackingId = record.get("tracking_id");
            if (StringUtils.isNotEmpty(trackingId)) {
                Integer shardId = getShardIdByTrackingId(trackingId);
                if (shardId != null) {
                    writer.write(trackingId + "," + getShardIdByTrackingId(trackingId));
                    writer.newLine();
                }
            }
        }
        writer.flush();
        in.close();
        System.err.println("rows=" + rows);
    }

    public static void mis() throws Exception {
        String outputFile = "/Users/eme/local/jmx/data/apollo-elemis.csv";
//        File file = new File(outPath);
//        if (file.exists()) {
//            file.delete();
//        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        writer.write("platform_tracking_id,walle_id,product_id,shard_id");
        writer.newLine();


        Reader in = new FileReader(CSV);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader(HEADERS).parse(in);

        int rows = 0;
        for (CSVRecord record : records) {
            if (++rows == 1) {
                continue;
            }
            String trackingId = record.get("tracking_id");
            String platformTrackingId = record.get("platform_tracking_id");
            String shippingOption = record.get("shipping_option");
            String platformMerchantId = record.get("platform_merchant_id");
            String productId = record.get("product_id");
            if ("1".equals(shippingOption)) {
                writer.write(platformTrackingId + "," + platformMerchantId + "," + productId + "," + getShardIdByTrackingId(trackingId));
                writer.newLine();
            }
        }
        writer.flush();
        in.close();
        System.err.println("rows=" + rows);
    }


    public static Integer getShardIdByTrackingId(Long trackingId) {
        try {
            if (trackingId == null) {
                return null;
            } else {
                if (trackingId < 1000000000000000000L || trackingId > 9210000000000000000L) {
                    // 不等于19位不处理
                    return 0;
                } else {
                    return (int) (trackingId >> 10 & 31L);
                }
            }
        } catch (Exception var3) {
            return 0;
        }
    }

    public static Integer getShardIdByTrackingId(String trackingId) {
        if (StringUtils.isBlank(trackingId)) {
            return 0;
        }

        Long numTrackingId = Long.parseLong(trackingId);
        return getShardIdByTrackingId(numTrackingId);
    }

}

package test.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import test.json.model.JSONModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * @author dewu.de
 * @date 2023-04-06 2:34 下午
 */
public class JSONTest {


    public static void main(String[] args) throws Exception {
        int batch = 100_000;
        long startMillis = System.currentTimeMillis();
        for (int i = 0; i < batch; i++) {
            JSONModel model = new JSONModel();
            model.setId(UUID.randomUUID().toString());
            model.setContent(model.getId() + "_content");
            model.setNow(new Date());
            GsonUtils.toJSONString(model);
        }
        System.out.println("gson 10W次耗时: " + (System.currentTimeMillis() - startMillis) + "ms");

        startMillis = System.currentTimeMillis();
        for (int i = 0; i < batch; i++) {
            JSONModel model = new JSONModel();
            model.setId(UUID.randomUUID().toString());
            model.setContent(model.getId() + "_content");
            model.setNow(new Date());
            JSON.toJSONString(model);
        }
        System.out.println("fastjson 10W次耗时: " + (System.currentTimeMillis() - startMillis) + "ms");

        startMillis = System.currentTimeMillis();
        for (int i = 0; i < batch; i++) {
            JSONModel model = new JSONModel();
            model.setId(UUID.randomUUID().toString());
            model.setContent(model.getId() + "_content");
            model.setNow(new Date());
            JacksonUtils.toJSONString(model);
        }
        System.out.println("jackson 10W次耗时: " + (System.currentTimeMillis() - startMillis) + "ms");

        startMillis = System.currentTimeMillis();
        for (int i = 0; i < batch; i++) {
            JSONModel model = new JSONModel();
            model.setId(UUID.randomUUID().toString());
            model.setContent(model.getId() + "_content");
            model.setNow(new Date());
            model.toString();
        }
        System.out.println("toString 10W次耗时: " + (System.currentTimeMillis() - startMillis) + "ms");

        JSONModel testModel = new JSONModel();
        testModel.setId(UUID.randomUUID().toString());
        testModel.setContent(testModel.getId() + "_content");
        testModel.setNow(new Date());
        System.out.println("Gson序列化: " + new Gson().toJson(testModel));
        System.out.println("Gson序列化: " + GsonUtils.toJSONString(testModel));
        System.out.println("Fastjson序列化: " + JSON.toJSONString(testModel, SerializerFeature.UseISO8601DateFormat));
        System.out.println("Jackson序列化: " + JacksonUtils.toJSONString(testModel));
        System.out.println("toString: " + testModel.toString());


        String dateString = "Apr 7, 2023 10:21:34 AM";
        DateFormat originalFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a", Locale.ENGLISH);

        System.out.println(originalFormat.format(new Date()));

        Date date = originalFormat.parse(dateString);
        DateFormat targetFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.CHINESE);
        String formattedDate = targetFormat.format(date);
        System.out.println(formattedDate);

    }

}

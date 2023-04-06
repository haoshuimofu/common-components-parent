package test.json;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import test.json.model.JSONModel;

import java.util.Date;
import java.util.UUID;

/**
 * @author dewu.de
 * @date 2023-04-06 2:34 下午
 */
public class JSONTest {

    public static void main(String[] args) {
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
            model.toString();
        }
        System.out.println("toString 10W次耗时: " + (System.currentTimeMillis() - startMillis) + "ms");

        JSONModel testModel = new JSONModel();
        testModel.setId(UUID.randomUUID().toString());
        testModel.setContent(testModel.getId() + "_content");
        testModel.setNow(new Date());
        System.out.println(new Gson().toJson(testModel));
        System.out.println(GsonUtils.toJSONString(testModel));
        System.out.println(testModel.toString());
    }

}

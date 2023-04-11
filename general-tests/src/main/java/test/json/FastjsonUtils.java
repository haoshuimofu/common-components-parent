package test.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.List;

/**
 * @author dewu.de
 * @date 2023-04-07 10:39 上午
 */
public class FastjsonUtils {

    public static String toJSONString(Object obj) {
        return JSON.toJSONString(obj);
    }

    public static <T> T toJSONString(String jsonString, Class<T> clazz) {
        return JSON.parseObject(jsonString, clazz);
    }

    public static void main(String[] args) {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        List<Long> idList = JSON.parseObject(JSON.toJSONString(ids), new TypeReference<List<Long>>() {
        });
        if (idList.contains(1L)) {
            //执行业务逻辑
            System.out.println("1");
        } else {
            System.out.println("0");
        }


        List<Long> idList1 = JSON.parseObject(JSON.toJSONString(ids), List.class);
        if (idList1.contains(1L)) {
            //执行业务逻辑
            System.out.println("1-1");
        } else {
            System.out.println("0-0");

        }

    }
}
package test.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author dewu.de
 * @date 2023-04-06 2:36 下午
 */
public class GsonUtils {
    private static final Gson gson = new Gson();

//    private static final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();


    public static String toJSONString(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T toJSONString(String jsonString, Class<T> clazz) {
        return gson.fromJson(jsonString, clazz);
    }

}

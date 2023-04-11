package test.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author dewu.de
 * @date 2023-04-07 2:28 下午
 */
public class JacksonUtils {

    public static ObjectMapper objectMapper = new ObjectMapper();
    static {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy hh:mm:ss a", Locale.ENGLISH);
        objectMapper.setDateFormat(sdf);
    }

    public static String toJSONString(Object obj) throws IOException {
        return objectMapper.writeValueAsString(obj);
    }

    public static <T> T toJSONString(String jsonString, Class<T> clazz) throws IOException {
        return objectMapper.readValue(jsonString, clazz);
    }

}

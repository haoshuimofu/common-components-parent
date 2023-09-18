package test.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * @author dewu.de
 * @date 2023-09-18 2:58 下午
 */
public class FastjsonTest {

    public static void main(String[] args) {
        String data = "{\"user\":[117612297,118196873,126699690,162103467,162496259,201252995]}";
        Map<String, List<Long>> map = JSON.parseObject(data, new TypeReference<Map<String, List<Long>>>() {
        });
        map.forEach((key, value) -> System.out.println(key + " -> " + JSON.toJSONString(value)));
        System.out.println(JSON.toJSONString(map).equals(data));


        LocalTime time1 = LocalTime.of(20, 18);
        LocalTime time2 = LocalTime.of(8, 8);
        System.out.println(time1.isBefore(time2));

    }

}

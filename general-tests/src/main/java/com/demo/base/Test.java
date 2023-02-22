package com.demo.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author dewu.de
 * @date 2023-02-14 4:39 下午
 */
public class Test {

    public static void main(String[] args) {
        String data = "[{\"cityId\":1,\"version\":20221106},{\"cityId\":2,\"version\":20221106},{\"cityId\":3,\"version\":20221106},{\"cityId\":4,\"version\":20221106},{\"cityId\":5,\"version\":20221106},{\"cityId\":6,\"version\":20221106},{\"cityId\":7,\"version\":20221106},{\"cityId\":8,\"version\":20221106},{\"cityId\":9,\"version\":20221106},{\"cityId\":10,\"version\":20221106},{\"cityId\":11,\"version\":20221106},{\"cityId\":12,\"version\":20221106},{\"cityId\":13,\"version\":20221106},{\"cityId\":14,\"version\":20221106},{\"cityId\":15,\"version\":20221106},{\"cityId\":16,\"version\":20221106},{\"cityId\":17,\"version\":20221106},{\"cityId\":18,\"version\":20221106},{\"cityId\":19,\"version\":20221106},{\"cityId\":20,\"version\":20221106},{\"cityId\":21,\"version\":20221106},{\"cityId\":22,\"version\":20221106},{\"cityId\":23,\"version\":20221106},{\"cityId\":24,\"version\":20221106},{\"cityId\":25,\"version\":20221106},{\"cityId\":26,\"version\":20221106},{\"cityId\":27,\"version\":20221106},{\"cityId\":28,\"version\":20221106},{\"cityId\":29,\"version\":20221106},{\"cityId\":30,\"version\":20221106},{\"cityId\":31,\"version\":20221106},{\"cityId\":32,\"version\":20221106}]";

        JSONArray ja = JSONArray.parseArray(data);
        List<Integer> list = new ArrayList<>();
        for (Object o : ja) {
            list.add(((JSONObject) o).getIntValue("cityId"));
        }
        Collections.sort(list);
        System.out.println(JSON.toJSONString(list));

        StringBuffer sb = new StringBuffer("123");
        String d = sb.toString();
        System.out.println(d.substring(0, sb.length() - 1));
    }


}

package test.json;

import com.alibaba.fastjson.serializer.NameFilter;

/**
 * @author dewu.de
 * @date 2023-04-25 10:33 上午
 */
public class MyNameFilter implements NameFilter {
    @Override
    public String process(Object object, String name, Object value) {
//        System.out.println("class=" + object.getClass().getName() + ", name=" + name + ", value=" + value);

        if (name.equals("timeSlice")) {
            System.out.println("class=" + object.getClass().getName() + ", name=" + name + ", value=" + value);
            System.out.println(value.getClass().getName());
            return "isTimeSlice";
        }
        return name;
    }
}

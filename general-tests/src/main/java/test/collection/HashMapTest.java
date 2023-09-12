package test.collection;

import java.util.HashMap;

/**
 * @author dewu.de
 * @date 2023-02-14 1:52 下午
 */
public class HashMapTest {

    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>(10);
        map.put("1", "1");
        System.out.println(map.size());
    }
}

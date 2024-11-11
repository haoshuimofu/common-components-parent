package test.collection;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapTest {

    /**
     * https://cloud.tencent.com/developer/article/2195605
     * @param args
     */
    public static void main(String[] args) {
        hashMap();
        System.out.println("=====分割线=====");
        treeMap();
    }

    /**
     * 底层实现: 数组 + 链表/红黑树
     * 可以指定初始容量
     */
    public static void hashMap() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("b", "2");
        map.put("a", "1");
        map.put("c", "3");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey()+" ---> " + entry.getValue());
        }
    }

    /**
     * 红黑树，不需要指定初始容量
     */
    public static void treeMap() {
        TreeMap<String, String> map = new TreeMap<String, String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        map.put("b", "2");
        map.put("a", "1");
        map.put("c", "3");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey()+" ---> " + entry.getValue());
        }
    }


}

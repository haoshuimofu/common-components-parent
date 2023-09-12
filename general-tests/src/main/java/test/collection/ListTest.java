package test.collection;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;
import java.util.*;

/**
 * @author dewu.de
 * @date 2022-09-26 3:45 下午
 */
public class ListTest {

    public static void main(String[] args) {
        int size = 50_000;
        List<String> list = new ArrayList<>(size);
        Set<String> set = new HashSet<>();
        BloomFilter<String> filter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), size, 0.01);

        for (int i = 0; i < size; i++) {
            String uid = UUID.randomUUID().toString();
            list.add(uid);
            set.add(uid);
            filter.put(uid);
        }
        int mid = 2_0000;
        int batch = 10000;
        long startMillis = System.currentTimeMillis();
        for (int i = 0; i < batch; i++) {
            list.contains(list.get(mid + new Random().nextInt(1000)));
        }
        long totalMillis = System.currentTimeMillis() - startMillis;
        double avgMillis = Double.valueOf(totalMillis) / Double.valueOf(batch);
        System.out.println(String.format("总耗时=%dms, 平均耗时=%fms", totalMillis, avgMillis));

        System.out.println();

        startMillis = System.currentTimeMillis();
        for (int i = 0; i < batch; i++) {
            set.contains(list.get(mid + new Random().nextInt(1000)));
        }
        totalMillis = System.currentTimeMillis() - startMillis;
        avgMillis = Double.valueOf(totalMillis) / Double.valueOf(batch);
        System.out.println(String.format("总耗时=%dms, 平均耗时=%fms", totalMillis, avgMillis));

        System.out.println();

        startMillis = System.currentTimeMillis();
        for (int i = 0; i < batch; i++) {
            filter.mightContain(list.get(mid + new Random().nextInt(1000)));
        }
        totalMillis = System.currentTimeMillis() - startMillis;
        avgMillis = Double.valueOf(totalMillis) / Double.valueOf(batch);
        System.out.println(String.format("总耗时=%dms, 平均耗时=%fms", totalMillis, avgMillis));

    }

}

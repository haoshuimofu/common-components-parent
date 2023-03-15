package test.cache;

import test.cache.model.User;
import test.cache.serializer.OHCacheKryoSerializer;
import test.cache.serializer.OhcStringSerializer;
import org.caffinitas.ohc.Eviction;
import org.caffinitas.ohc.OHCache;
import org.caffinitas.ohc.OHCacheBuilder;

/**
 * @author dewu.de
 * @date 2023-03-07 2:22 下午
 */
public class OHCacheKryoTest {

    private static final OHCache<String, User> cache = OHCacheBuilder.<String, User>newBuilder()
            .keySerializer(new OhcStringSerializer())
            .valueSerializer(new OHCacheKryoSerializer())
            //.hashMode(HashAlgorithm.CRC32C)
            //单位是字节，默认2GB空间
            .capacity(2 * 1024 * 1024 * 1024L)
            .timeouts(true)
            .defaultTTLmillis(600 * 1000)
            .eviction(Eviction.LRU)
            .build();

    public static void main(String[] args) {


        String cacheKey = "wd";
        User user = cache.get(cacheKey);
        System.out.println("Step01: get=" + cacheKey);

        user = new User();
        user.setName("wd");
        user.setAge(34);
        user.setAddress("Shanghai");
        cache.put(cacheKey, user);
        System.out.println("Step02: put, key=" + cacheKey + ", value=" + user.format());


        user = cache.get(cacheKey);
        System.out.println("Step03: get after put, key=" + cacheKey + ", value=" + user.format());

        long start = System.currentTimeMillis();
        while (true) {
            start = System.currentTimeMillis();
            //只测试写入
            for (int j = 0; j < 500000; j++) {
                cache.putIfAbsent("" + j, new User("" + j, j, "" + j));
            }
            System.out.println("写入耗时:" + (System.currentTimeMillis() - start));
            //测试读写
            for (int j = 0; j < 500000; j++) {
                cache.get("" + j);
            }
            System.out.println("读取命中耗时:" + (System.currentTimeMillis() - start));
            //读写+读为命中
            for (int j = 0; j < 500000; j++) {
                cache.get("" + j + "noHits");
            }
            System.out.println("读取未命中耗时:" + (System.currentTimeMillis() - start));
            System.out.println("总耗时:" + (System.currentTimeMillis() - start));
        }

    }


}

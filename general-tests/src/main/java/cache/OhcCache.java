package cache;

import lombok.extern.slf4j.Slf4j;
import org.caffinitas.ohc.Eviction;
import org.caffinitas.ohc.OHCache;
import org.caffinitas.ohc.OHCacheBuilder;

@Slf4j
public class OhcCache {

    private static OHCache<String, String> ohCache = OHCacheBuilder.<String, String>newBuilder()
            .keySerializer(new OhcStringSerializer())
            .valueSerializer(new OhcStringSerializer())
            //.hashMode(HashAlgorithm.CRC32C)
            //单位是字节，默认2GB空间
            .capacity(2 * 1024 * 1024 * 1024L)
            .timeouts(true)
            .defaultTTLmillis(600 * 1000)
            .eviction(Eviction.LRU)
            .build();


    /**
     * 设置值
     *
     * @param k
     * @param v
     * @return
     */
    public static boolean put(String k, String v) {
        return put(k, v, 9223372036854775807L);
    }

    public static boolean put(String k, String v, Long time) {
        try {
            return ohCache.put(k, v, time);
        } catch (Exception e) {
            log.error("ohc cache put error", e);
            return false;
        }
    }

    public static String get(String k) {
        return ohCache.get(k);
    }

    public static void main(String[] args) throws Exception {
        while (true) {
            long start = System.currentTimeMillis();
            //只测试写入
            for (int j = 0; j < 500000; j++) {
                OhcCache.put("" + j, j + "");
            }
            System.out.println("写入耗时:" + (System.currentTimeMillis() - start));
            //测试读写
            for (int j = 0; j < 500000; j++) {
                System.out.println(OhcCache.get("" + j));
            }
            System.out.println("读取命中耗时:" + (System.currentTimeMillis() - start));
            //读写+读为命中
            for (int j = 0; j < 500000; j++) {
                OhcCache.get("" + j + "noHits");
            }
            System.out.println("读取未命中耗时:" + (System.currentTimeMillis() - start));
            System.out.println("总耗时:" + (System.currentTimeMillis() - start));
        }
    }

}
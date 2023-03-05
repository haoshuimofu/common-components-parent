import com.google.common.cache.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class GuavaCache {

    private static Cache<String, Object> cache = CacheBuilder.newBuilder()
            .maximumSize(1000000)
            .expireAfterWrite(60, TimeUnit.SECONDS)
            .concurrencyLevel(4)
            .initialCapacity(1000)
            //配置上recordStats,cache.stats()才能生效
            //.recordStats()
            .removalListener(new RemovalListener<String, Object>() {
                @Override
                public void onRemoval(RemovalNotification<String, Object> rn) {

                }
            }).build();


    /*
     *
     * @desction: 获取缓存
     */
    public static Object get(String key) {
        try {
            return StringUtils.isNotEmpty(key) ? cache.getIfPresent(key) : null;
        } catch (Exception e) {
            log.error("local cache by featureId 异常", e);
            return null;
        }
    }

    /*
     *
     * @desction: 放入缓存
     */
    public static void put(String key, Object value) {
        if (StringUtils.isNotEmpty(key) && value != null) {
            cache.put(key, value);
        }
    }

    /*
     *
     * @desction: 移除缓存
     */
    public static void remove(String key) {
        if (StringUtils.isNotEmpty(key)) {
            cache.invalidate(key);
        }
    }

    /*
     *
     * @desction: 批量删除缓存
     */
    public static void remove(List<String> keys) {
        if (keys != null && keys.size() > 0) {
            cache.invalidateAll(keys);
        }
    }

    public static CacheStats getStats() {
        return cache.stats();
    }

    /**
     * test
     *
     * @param args
     */

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        //只测试写入
        for (int j = 0; j < 500000; j++) {
            GuavaCache.put("" + j, j);
        }
        //测试读写
        for (int j = 0; j < 500000; j++) {
            GuavaCache.get("" + j);
        }
        //读写+读为命中
        for (int j = 0; j < 500000; j++) {
            GuavaCache.get("" + j + "noHits");
        }
        GuavaCache.cache.cleanUp();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

}
package test.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CaffeineCache {

    private static Cache<String, Object> caffeineCache = Caffeine.newBuilder()
            .maximumSize(1000000)
            .expireAfterWrite(60, TimeUnit.SECONDS)
            .initialCapacity(1000)
            //配置上recordStats,cache.stats()才能生效
            //.recordStats()
            .removalListener(new RemovalListener<String, Object>() {
                @Override
                public void onRemoval(@Nullable String key, @Nullable Object value, @NonNull RemovalCause cause) {

                }
            })
            .build();


    /*
     *
     * @desction: 获取缓存
     */
    public static Object get(String key) {
        try {
            return StringUtils.isNotEmpty(key) ? caffeineCache.getIfPresent(key) : null;
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
            caffeineCache.put(key, value);
        }
    }

    /*
     *
     * @desction: 移除缓存
     */
    public static void remove(String key) {
        if (StringUtils.isNotEmpty(key)) {
            caffeineCache.invalidate(key);
        }
    }

    /*
     *
     * @desction: 批量删除缓存
     */
    public static void remove(List<String> keys) {
        if (keys != null && keys.size() > 0) {
            caffeineCache.invalidateAll(keys);
        }
    }

    public static CacheStats getStats() {
        return caffeineCache.stats();
    }

    /**
     * test
     *
     * @param args
     */


    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        //只测试写入
        for (int j = 0; j < 500000; j++) {
            CaffeineCache.put("" + j, j);
        }
        //测试读写
        for (int j = 0; j < 500000; j++) {
            CaffeineCache.get("" + j);
        }
        //读写+读为命中
        for (int j = 0; j < 500000; j++) {
            CaffeineCache.get("" + j + "noHits");
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
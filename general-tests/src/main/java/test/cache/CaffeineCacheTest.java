package test.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CaffeineCacheTest {

    private static Cache<String, Integer> caffeineCache = Caffeine.newBuilder()
            .maximumSize(1000000)
            .expireAfterWrite(60, TimeUnit.SECONDS)
            .initialCapacity(1000)
            // 配置上recordStats,cache.stats()才能生效
            //.recordStats()
            .removalListener(new RemovalListener<String, Object>() {
                @Override
                public void onRemoval(@Nullable String key, @Nullable Object value, @NonNull RemovalCause cause) {

                }
            })
            .build();


    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        //只测试写入
        for (int j = 0; j < 500000; j++) {
            caffeineCache.put("" + j, j);
        }
        //测试读写
        for (int j = 0; j < 500000; j++) {
            caffeineCache.getIfPresent("" + j);
        }
        //读写+读为命中
        for (int j = 0; j < 500000; j++) {
            caffeineCache.getIfPresent("" + j + "noHits");
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
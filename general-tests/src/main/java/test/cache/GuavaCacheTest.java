package test.cache;

import com.google.common.cache.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class GuavaCacheTest {

    private static Cache<String, Integer> cache = CacheBuilder.newBuilder()
            .maximumSize(1000000)
            .expireAfterWrite(60, TimeUnit.SECONDS)
            .concurrencyLevel(4)
            .initialCapacity(1000)
            // 配置上recordStats,cache.stats()才能生效
            //.recordStats()
            .removalListener(new RemovalListener<String, Integer>() {
                @Override
                public void onRemoval(RemovalNotification<String, Integer> rn) {

                }
            }).build();


    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        //只测试写入
        for (int j = 0; j < 500000; j++) {
            cache.put("" + j, j);
        }
        //测试读写
        for (int j = 0; j < 500000; j++) {
            cache.getIfPresent("" + j);
        }
        //读写+读为命中
        for (int j = 0; j < 500000; j++) {
            cache.getIfPresent("" + j + "noHits");
        }
        cache.cleanUp();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

}
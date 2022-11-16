package com.demo.components.caffeine;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.index.strtree.STRtree;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author dewu.de
 * @date 2022-09-28 2:02 下午
 */
@RequiredArgsConstructor
@Getter
@Slf4j
public class CityAoiExpRoad {

    private static final int CITY_EXP_ROAD_CACHED_ITEM_MAX_SIZE = 30_000;

    private final STRtree aoiRTree;

    private final Cache<String, List<ExpRoad>> expRoadCache;


    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4,
                1024,
                500L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(true),
                Executors.defaultThreadFactory());
        ExpRoadAsyncCacheLoader expRoadAsyncCacheLoader = new ExpRoadAsyncCacheLoader();


        AsyncLoadingCache<String, List<ExpRoad>> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .maximumSize(CITY_EXP_ROAD_CACHED_ITEM_MAX_SIZE)
                .recordStats()
                .executor(executor)
                .buildAsync(expRoadAsyncCacheLoader);
        System.out.println(cache.get("1").join());


//        Function<String, List<ExpRoad>> mappingFuc = new Function<String, List<ExpRoad>>() {
//            @Override
//            public List<ExpRoad> apply(String key) {
//                log.info("加载值..." + key);
//                return Collections.singletonList(ExpRoad.builder().originAoiId(Integer.parseInt(key)).build());
//            }
//        };
//
//        CompletableFuture<List<ExpRoad>> future = cache.get("1", mappingFuc);
//
//        System.err.println(future.join());
//        future = cache.get("1", mappingFuc);
//        System.err.println(future.join());
//
//
//        try {
//            Thread.sleep(15_000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        future = cache.get("1", mappingFuc);
//        System.err.println(future.join());
    }


}



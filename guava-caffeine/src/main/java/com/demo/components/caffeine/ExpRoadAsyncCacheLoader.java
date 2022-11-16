package com.demo.components.caffeine;

import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * @author dewu.de
 * @date 2022-10-08 2:27 下午
 */
@Slf4j
@RequiredArgsConstructor
public class ExpRoadAsyncCacheLoader implements AsyncCacheLoader<String, List<ExpRoad>> {

    @Override
    public @NonNull CompletableFuture<List<ExpRoad>> asyncLoad(@NonNull String key, @NonNull Executor executor) {
        log.info("exp_road: async load...");
        return CompletableFuture.supplyAsync(() -> {
            log.info("async load: key={}", key);
            ExpRoad expRoad = ExpRoad.builder().originAoiId(Integer.parseInt(key)).build();
            return Collections.singletonList(expRoad);
        }, executor);
    }

    @Override
    public @NonNull CompletableFuture<Map<@NonNull String, @NonNull List<ExpRoad>>> asyncLoadAll(@NonNull Iterable<? extends @NonNull String> keys, @NonNull Executor executor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull CompletableFuture<List<ExpRoad>> asyncReload(@NonNull String key, @NonNull List<ExpRoad> oldValue, @NonNull Executor executor) {
        log.info("exp_road: async reload...");
        return asyncLoad(key, executor);
    }
}

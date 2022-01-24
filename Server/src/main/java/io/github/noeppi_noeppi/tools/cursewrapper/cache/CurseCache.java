package io.github.noeppi_noeppi.tools.cursewrapper.cache;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CurseCache {
    
    private final Map<CacheKey<?, ?>, Cache<?, ?>> caches;
    
    public CurseCache() {
        ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1);
        Map<CacheKey<?, ?>, Cache<?, ?>> map = new HashMap<>();
        addCache(CacheKey.SLUG, map, executor);
        addCache(CacheKey.PROJECT, map, executor);
        addCache(CacheKey.FILE, map, executor);
        addCache(CacheKey.CHANGELOG, map, executor);
        addCache(CacheKey.SEARCH, map, executor);
        addCache(CacheKey.FILES, map, executor);
        this.caches = Map.copyOf(map);
    }
    
    private static void addCache(CacheKey<?, ?> key, Map<CacheKey<?, ?>, Cache<?, ?>> map, ScheduledExecutorService executor) {
        Cache<?, ?> cache = new Cache<>(key);
        map.put(key, cache);
        executor.scheduleWithFixedDelay(cache::clear, key.cacheTimeMillis, key.cacheTimeMillis, TimeUnit.MILLISECONDS);
    }
    
    public <K, V> V get(CacheKey<K, V> cache, K key, CacheFunction<K, V> value) throws IOException {
        //noinspection unchecked
        return ((Cache<K, V>) this.caches.get(cache)).get(key, value);
    }

    public <K, V> void store(CacheKey<K, V> cache, K key, V value) {
        //noinspection unchecked
        ((Cache<K, V>) this.caches.get(cache)).store(key, value);
    }
    
    private static class Cache<K, V> {
        
        private final CacheKey<K, V> key;
        private final Map<K, V> map;

        private Cache(CacheKey<K, V> key) {
            this.key = key;
            this.map = new HashMap<>();
        }
        
        public V get(K key, CacheFunction<K, V> value) throws IOException {
            synchronized (this.key) {
                if (map.size() >= this.key.size && !map.containsKey(key)) {
                    this.clear();
                }
                if (!map.containsKey(key)) {
                    V result = value.apply(key);
                    map.put(key, result);
                    return result;
                } else {
                    return map.get(key);
                }
            }
        }
        
        public void store(K key, V value) {
            synchronized (this.key) {
                if (map.size() >= this.key.size && !map.containsKey(key)) {
                    this.clear();
                }
                // Always put the value as it has been requested anyway
                // and may be more up to date
                map.put(key, value);
            }
        }
        
        public void clear() {
            synchronized (this.key) {
                this.map.clear();
            }
        }
    }
}

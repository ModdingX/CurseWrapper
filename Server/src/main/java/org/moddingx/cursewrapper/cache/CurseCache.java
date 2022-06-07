package org.moddingx.cursewrapper.cache;

import org.moddingx.cursewrapper.backend.CurseApi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CurseCache {
    
    public final CurseApi api;
    private final Map<CacheKey<?, ?>, Cache<?, ?>> caches;
    
    public CurseCache(CurseApi api) {
        this.api = api;
        ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1);
        Map<CacheKey<?, ?>, Cache<?, ?>> map = new HashMap<>();
        addCache(api, CacheKey.SLUG, map, executor);
        addCache(api, CacheKey.PROJECT, map, executor);
        addCache(api, CacheKey.FILE, map, executor);
        addCache(api, CacheKey.CHANGELOG, map, executor);
        addCache(api, CacheKey.SEARCH, map, executor);
        addCache(api, CacheKey.FILES, map, executor);
        addCache(api, CacheKey.LATEST_FILE, map, executor);
        this.caches = Map.copyOf(map);
    }
    
    private static void addCache(CurseApi api, CacheKey<?, ?> key, Map<CacheKey<?, ?>, Cache<?, ?>> map, ScheduledExecutorService executor) {
        Cache<?, ?> cache = new Cache<>(api, key);
        map.put(key, cache);
        executor.scheduleWithFixedDelay(cache::clear, key.cacheTimeMillis, key.cacheTimeMillis, TimeUnit.MILLISECONDS);
    }
    
    public <K, V> V get(CacheKey<K, V> cache, K key, CacheFunction<K, V> value) throws IOException {
        //noinspection unchecked
        return ((Cache<K, V>) this.caches.get(cache)).get(key, value);
    }

    public <K, V> Optional<V> getCached(CacheKey<K, V> cache, K key) {
        //noinspection unchecked
        return ((Cache<K, V>) this.caches.get(cache)).getCached(key);
    }
    
    public <K, V> void store(CacheKey<K, V> cache, K key, V value) {
        //noinspection unchecked
        ((Cache<K, V>) this.caches.get(cache)).store(key, value);
    }
    
    public <T> T runLocked(CacheKey<?, ?> cache, Callable<T> action) {
        try {
            this.caches.get(cache).lock();
            return action.call();
        } catch (RuntimeException | Error e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Exception during locked cache action: " + cache, e);
        } finally {
            this.caches.get(cache).unlock();
        }
    }
    
    private static class Cache<K, V> {
        
        private final CurseApi api;
        private final CacheKey<K, V> key;
        private final Map<K, V> map;
        
        // Locked caches can't be cleared for a time
        private int locked;
        private boolean shouldClearAfterLocked;

        private Cache(CurseApi api, CacheKey<K, V> key) {
            this.api = api;
            this.key = key;
            this.map = new HashMap<>();
            this.locked = 0;
            this.shouldClearAfterLocked = false;
        }
        
        public V get(K key, CacheFunction<K, V> value) throws IOException {
            synchronized (this.key) {
                if (this.map.size() >= this.key.size && !this.map.containsKey(key)) {
                    this.clear();
                }
                if (!this.map.containsKey(key)) {
                    V result = value.apply(this.api, key);
                    this.map.put(key, result);
                    return result;
                } else {
                    return this.map.get(key);
                }
            }
        }
        
        public Optional<V> getCached(K key) {
            synchronized (this.key) {
                if (this.map.containsKey(key)) {
                    return Optional.of(this.map.get(key));
                } else {
                    return Optional.empty();
                }
            }
        }
        
        public void store(K key, V value) {
            synchronized (this.key) {
                if (this.map.size() >= this.key.size && !this.map.containsKey(key)) {
                    this.clear();
                }
                // Always put the value as it has been requested anyway
                // and may be more up to date
                this.map.put(key, value);
            }
        }
        
        public void lock() {
            synchronized (this.key) {
                this.locked += 1;
            }
        }
        
        public void unlock() {
            synchronized (this.key) {
                this.locked -= 1;
                if (this.shouldClearAfterLocked && this.locked <= 0) {
                    this.shouldClearAfterLocked = false;
                    this.map.clear();
                }
            }
        }
        
        public void clear() {
            synchronized (this.key) {
                if (this.locked <= 0) {
                    this.map.clear();
                } else {
                    this.shouldClearAfterLocked = true;
                }
            }
        }
    }
}

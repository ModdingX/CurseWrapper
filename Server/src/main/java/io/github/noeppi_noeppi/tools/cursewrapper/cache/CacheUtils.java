package io.github.noeppi_noeppi.tools.cursewrapper.cache;

import java.io.IOException;
import java.util.*;

public class CacheUtils {
    
    // bulk resolver may leave out some elements. They will be resolved using single resolver
    public static <T, M, R> List<R> bulkMap(CurseCache cache, CacheKey<T, R> key, List<T> entries, CacheFunction<T, R> singleResolver, CacheFunction<Set<T>, Map<T, R>> bulkResolver) throws IOException {
        Set<T> keys = new HashSet<>();
        List<R> resultList = new ArrayList<>(entries.size());
        for (T entry : entries) {
            Optional<R> cacheHit = cache.getCached(key, entry);
            if (cacheHit.isPresent()) {
                resultList.add(cacheHit.get());
            } else {
                resultList.add(null);
                keys.add(entry);
            }
        }
        
        if (!keys.isEmpty()) {
            Map<T, R> resolved = bulkResolver.apply(cache.api, keys);
            for (int i = 0; i < entries.size(); i++) {
                if (resultList.get(i) == null) {
                    T entryKey = entries.get(i);
                    if (resolved.containsKey(entryKey)) {
                        R entryValue = resolved.get(entryKey);
                        resultList.set(i, entryValue);
                        cache.store(key, entryKey, entryValue);
                    } else {
                        resultList.set(i, cache.get(key, entryKey, singleResolver));
                    }
                }
            }
        }
        
        return List.copyOf(resultList);
    }
}

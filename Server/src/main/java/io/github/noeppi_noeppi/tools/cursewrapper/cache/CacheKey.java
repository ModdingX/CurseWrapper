package io.github.noeppi_noeppi.tools.cursewrapper.cache;

import io.github.noeppi_noeppi.tools.cursewrapper.api.response.FileInfo;
import io.github.noeppi_noeppi.tools.cursewrapper.api.response.ModLoader;
import io.github.noeppi_noeppi.tools.cursewrapper.api.response.ProjectInfo;

import java.util.List;
import java.util.Optional;

public class CacheKey<K, V> {
    
    public static final CacheKey<Integer, String> SLUG = new CacheKey<>(400, 1000 * 60 * 60 * 5);
    public static final CacheKey<Integer, ProjectInfo> PROJECT = new CacheKey<>(100, 1000 * 60 * 60 * 3);
    public static final CacheKey<FileKey, FileInfo> FILE = new CacheKey<>(1000, 1000 * 60 * 60 * 3);
    public static final CacheKey<FileKey, String> CHANGELOG = new CacheKey<>(300, 1000 * 60 * 60);
    public static final CacheKey<SearchKey, List<Integer>> SEARCH = new CacheKey<>(200, 1000 * 60 * 30);
    public static final CacheKey<FilesKey, List<FileKey>> FILES = new CacheKey<>(200, 1000 * 60 * 30);
    
    public final int size;
    public final int cacheTimeMillis;
    
    private CacheKey(int size, int cacheTime) {
        this.size = size;
        this.cacheTimeMillis = cacheTime;
    }
    
    public record FileKey(int projectId, int fileId) {}
    public record SearchKey(String query, Optional<ModLoader> loader, Optional<String> version) {}
    public record FilesKey(int projectId, Optional<ModLoader> loader, Optional<String> version) {}
}

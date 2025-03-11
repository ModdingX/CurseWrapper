package org.moddingx.cursewrapper.cache;

import org.moddingx.cursewrapper.api.response.FileInfo;
import org.moddingx.cursewrapper.api.response.ModLoader;
import org.moddingx.cursewrapper.api.response.ProjectInfo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CacheKey<K, V> {
    
    public static final CacheKey<Integer, String> SLUG = new CacheKey<>(600, 1000 * 60 * 60 * 5);
    public static final CacheKey<Integer, ProjectInfo> PROJECT = new CacheKey<>(400, 1000 * 60 * 60 * 3);
    public static final CacheKey<FileKey, FileInfo> FILE = new CacheKey<>(2000, 1000 * 60 * 60 * 3);
    public static final CacheKey<FileKey, String> CHANGELOG = new CacheKey<>(300, 1000 * 60 * 60);
    public static final CacheKey<SearchKey, List<Integer>> SEARCH = new CacheKey<>(200, 1000 * 60 * 30);
    public static final CacheKey<FilesKey, List<FileKey>> FILES = new CacheKey<>(200, 1000 * 60 * 30);
    public static final CacheKey<FilesKey, Optional<FileKey>> LATEST_FILE = new CacheKey<>(100, 1000 * 60 * 5);
    
    public final int size;
    public final int cacheTimeMillis;
    
    private CacheKey(int size, int cacheTime) {
        this.size = size;
        this.cacheTimeMillis = cacheTime;
    }
    
    public record FileKey(int projectId, int fileId) {}
    public record SearchKey(String query, Set<ModLoader> loaders, Optional<String> version) {}
    public record FilesKey(int projectId, Set<ModLoader> loaders, Optional<String> version) {}
}

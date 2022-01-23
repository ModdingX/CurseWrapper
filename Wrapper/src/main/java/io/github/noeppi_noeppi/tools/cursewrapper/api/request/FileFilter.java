package io.github.noeppi_noeppi.tools.cursewrapper.api.request;

import io.github.noeppi_noeppi.tools.cursewrapper.api.response.ModLoader;

import java.util.Arrays;
import java.util.Set;

public class FileFilter {
    
    private static final FileFilter EMPTY = new FileFilter(Set.of(), Set.of());
    
    private final Set<ModLoader> loader;
    private final Set<String> versions;

    private FileFilter(Set<ModLoader> loader, Set<String> versions) {
        this.loader = Set.copyOf(loader);
        this.versions = Set.copyOf(versions);
    }

    public Set<ModLoader> loader() {
        return this.loader;
    }

    public Set<String> gameVersions() {
        return this.versions;
    }
    
    public static FileFilter empty() {
        return EMPTY;
    }
    
    public static FileFilter loader(ModLoader... loader) {
        return new FileFilter(Set.copyOf(Arrays.asList(loader)), Set.of());
    }
    
    public static FileFilter version(String... versions) {
        return new FileFilter(Set.of(), Set.copyOf(Arrays.asList(versions)));
    }

    public static FileFilter create(ModLoader loader, String versions) {
        return new FileFilter(Set.of(loader), Set.of(versions));
    }

    public static FileFilter create(ModLoader loader, Set<String> versions) {
        return new FileFilter(Set.of(loader), versions);
    }

    public static FileFilter create(Set<ModLoader> loader, String versions) {
        return new FileFilter(loader, Set.of(versions));
    }
    
    public static FileFilter create(Set<ModLoader> loader, Set<String> versions) {
        return new FileFilter(loader, versions);
    }
}

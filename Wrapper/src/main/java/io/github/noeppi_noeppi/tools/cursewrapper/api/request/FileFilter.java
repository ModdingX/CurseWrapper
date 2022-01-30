package io.github.noeppi_noeppi.tools.cursewrapper.api.request;

import io.github.noeppi_noeppi.tools.cursewrapper.api.response.ModLoader;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class FileFilter {
    
    private static final FileFilter EMPTY = new FileFilter(Optional.empty(), Optional.empty());
    
    private final Optional<ModLoader> loader;
    private final Optional<String> versions;

    private FileFilter(Optional<ModLoader> loader, Optional<String> version) {
        this.loader = loader;
        this.versions = version;
    }

    public Optional<ModLoader> loader() {
        return this.loader;
    }

    public Optional<String> gameVersion() {
        return this.versions;
    }
    
    public static FileFilter empty() {
        return EMPTY;
    }
    
    public static FileFilter loader(ModLoader loader) {
        return new FileFilter(Optional.of(loader), Optional.empty());
    }
    
    public static FileFilter version(String version) {
        return new FileFilter(Optional.empty(), Optional.of(version));
    }

    public static FileFilter create(ModLoader loader, String version) {
        return new FileFilter(Optional.of(loader), Optional.of(version));
    }
}

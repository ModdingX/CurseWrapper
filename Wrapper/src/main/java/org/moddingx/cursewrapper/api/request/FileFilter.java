package org.moddingx.cursewrapper.api.request;

import org.moddingx.cursewrapper.api.response.ModLoader;

import java.util.Optional;
import java.util.Set;

public record FileFilter(
        Optional<String> gameVersion,
        Set<ModLoader> loaders
) {
    
    private static final FileFilter EMPTY = new FileFilter(Optional.empty(), Set.of());
    
    public static FileFilter empty() {
        return EMPTY;
    }

    public static FileFilter version(String version) {
        return new FileFilter(Optional.of(version), Set.of());
    }

    public static FileFilter loader(ModLoader... loaders) {
        return new FileFilter(Optional.empty(), Set.of(loaders));
    }

    public static FileFilter create(String version, ModLoader... loaders) {
        return new FileFilter(Optional.of(version), Set.of(loaders));
    }
}

package org.moddingx.cursewrapper.api.request;

import org.moddingx.cursewrapper.api.response.ModLoader;

import java.util.Optional;

public record FileFilter(
        Optional<ModLoader> loader,
        Optional<String> gameVersion
) {
    
    private static final FileFilter EMPTY = new FileFilter(Optional.empty(), Optional.empty());
    
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

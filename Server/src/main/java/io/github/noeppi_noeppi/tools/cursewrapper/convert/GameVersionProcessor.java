package io.github.noeppi_noeppi.tools.cursewrapper.convert;

import io.github.noeppi_noeppi.tools.cursewrapper.api.response.ModLoader;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.structure.ModLoaderType;

import javax.annotation.Nullable;
import java.util.*;

// Still no real loader support, take the game versions apart here
public class GameVersionProcessor {

    public static GameVersionData data(List<String> gameVersions) {
        Set<ModLoader> loaders = new HashSet<>();
        Set<String> versions = new HashSet<>();
        for (String str : gameVersions) {
            switch (str.toLowerCase(Locale.ROOT)) {
                case "forge", "minecraftforge", "minecraft_forge" -> loaders.add(ModLoader.FORGE);
                case "fabric" -> loaders.add(ModLoader.FABRIC);
                case "rift" -> loaders.add(ModLoader.RIFT);
                case "liteloader", "lite_loader" -> loaders.add(ModLoader.LITE_LOADER);
                case "cauldron" -> loaders.add(ModLoader.CAULDRON);
                case "quilt" -> loaders.add(ModLoader.QUILT);
                default -> versions.add(str);
            }
        }

        // Many old files are not tagged for any loader.
        // We'll tag them as forge here
        if (loaders.isEmpty()) loaders.add(ModLoader.FORGE);
        
        // Quilt can load fabric mods
        if (loaders.contains(ModLoader.FABRIC)) loaders.add(ModLoader.QUILT);
        
        return new GameVersionData(loaders.stream().sorted().toList(), versions.stream().sorted().toList());
    }

    public static boolean check(List<String> gameVersions, @Nullable ModLoader loader, @Nullable String version) {
        GameVersionData data = data(gameVersions);
        return (loader == null || data.loader().contains(loader)) && (version == null || data.versions().contains(version));
    }

    // Never return other as this is passed to the CurseForge API
    // which does not know about OTHER
    public static Set<ModLoaderType> forLoader(ModLoader loader) {
        for (ModLoaderType type : ModLoaderType.values()) {
            if (type != ModLoaderType.OTHER && loader == type.loader) {
                if (type == ModLoaderType.QUILT) {
                    // Quilt can load fabric mods
                    return Set.of(ModLoaderType.FABRIC, ModLoaderType.QUILT);
                } else {
                    return Set.of(type);
                }
            }
        }
        return Set.of();
    }

    public record GameVersionData(List<ModLoader> loader, List<String> versions) {}
}

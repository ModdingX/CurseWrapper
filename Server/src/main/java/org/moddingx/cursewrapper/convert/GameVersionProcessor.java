package org.moddingx.cursewrapper.convert;

import jakarta.annotation.Nullable;
import org.moddingx.cursewrapper.api.response.FileEnvironment;
import org.moddingx.cursewrapper.api.response.ModLoader;
import org.moddingx.cursewrapper.backend.data.structure.ModLoaderType;

import java.util.*;

// Still no real loader support, take the game versions apart here
public class GameVersionProcessor {

    public static GameVersionData data(List<String> gameVersions) {
        Set<ModLoader> loaders = new HashSet<>();
        Set<String> versions = new HashSet<>();
        boolean client = false;
        boolean server = false;
        for (String str : gameVersions) {
            switch (str.toLowerCase(Locale.ROOT)) {
                case "forge", "minecraftforge", "minecraft_forge" -> loaders.add(ModLoader.FORGE);
                case "fabric" -> loaders.add(ModLoader.FABRIC);
                case "rift" -> loaders.add(ModLoader.RIFT);
                case "liteloader", "lite_loader" -> loaders.add(ModLoader.LITE_LOADER);
                case "cauldron" -> loaders.add(ModLoader.CAULDRON);
                case "quilt" -> loaders.add(ModLoader.QUILT);
                case "neoforge" -> loaders.add(ModLoader.NEOFORGE);
                case "client" -> client = true;
                case "server" -> server = true;
                default -> versions.add(str);
            }
        }

        // Many old files are not tagged for any loader.
        // We'll tag them as forge here
        if (loaders.isEmpty()) loaders.add(ModLoader.FORGE);
        
        // Quilt can load fabric mods
        if (loaders.contains(ModLoader.FABRIC)) loaders.add(ModLoader.QUILT);
        
        FileEnvironment environment = FileEnvironment.BOTH;
        if (client && !server) environment = FileEnvironment.CLIENT;
        if (server && !client) environment = FileEnvironment.SERVER;
        
        return new GameVersionData(loaders.stream().sorted().toList(), versions.stream().sorted().toList(), environment);
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

    public record GameVersionData(List<ModLoader> loader, List<String> versions, FileEnvironment environment) {}
}

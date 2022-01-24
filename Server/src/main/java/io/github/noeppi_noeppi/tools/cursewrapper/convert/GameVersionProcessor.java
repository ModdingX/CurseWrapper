package io.github.noeppi_noeppi.tools.cursewrapper.convert;

import io.github.noeppi_noeppi.tools.cursewrapper.api.response.ModLoader;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

// Still no real loader support, take the game versions apart here
public class GameVersionProcessor {
    
    public static GameVersionData data(List<String> gameVersions) {
        List<ModLoader> loaders = new ArrayList<>();
        List<String> versions = new ArrayList<>();
        for (String str : gameVersions) {
            switch (str.toLowerCase(Locale.ROOT)) {
                case "forge", "minecraftforge", "minecraft_forge" -> loaders.add(ModLoader.FORGE);
                case "fabric" -> loaders.add(ModLoader.FABRIC);
                case "rift" -> loaders.add(ModLoader.RIFT);
                case "liteloader", "lite_loader" -> loaders.add(ModLoader.LITE_LOADER);
                case "cauldron" -> loaders.add(ModLoader.CAULDRON);
                default -> versions.add(str);
            }
        }
        // Many old files are not tagged for any loader.
        // We'll tag them as forge here
        if (loaders.isEmpty()) loaders.add(ModLoader.FORGE);
        return new GameVersionData(List.copyOf(loaders), List.copyOf(versions));
    }
    
    public static boolean check(List<String> gameVersions, @Nullable ModLoader loader, @Nullable String version) {
        GameVersionData data = data(gameVersions);
        return (loader == null || data.loader().contains(loader)) && (version == null || data.versions().contains(version));
    }
    
    public record GameVersionData(List<ModLoader> loader, List<String> versions) {}
}

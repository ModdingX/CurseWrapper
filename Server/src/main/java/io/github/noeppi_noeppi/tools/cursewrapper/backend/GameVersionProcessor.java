package io.github.noeppi_noeppi.tools.cursewrapper.backend;

import io.github.noeppi_noeppi.tools.cursewrapper.api.response.ModLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// Still no real loader support, take the game versions apart here
public class GameVersionProcessor {
    
    private static GameVersionData data(List<String> gameVersions) {
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
        if (loaders.isEmpty()) loaders.add(ModLoader.UNKNOWN);
        return new GameVersionData(List.copyOf(loaders), List.copyOf(versions));
    }
    
    public record GameVersionData(List<ModLoader> loader, List<String> versions) {}
}

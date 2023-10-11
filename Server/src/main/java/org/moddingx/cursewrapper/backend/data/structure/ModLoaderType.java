package org.moddingx.cursewrapper.backend.data.structure;

import org.moddingx.cursewrapper.api.response.ModLoader;

public enum ModLoaderType implements CurseEnum {
    
    ANY(ModLoader.UNKNOWN, "any"),
    FORGE(ModLoader.FORGE),
    CAULDRON(ModLoader.CAULDRON),
    LITE_LOADER(ModLoader.LITE_LOADER),
    FABRIC(ModLoader.FABRIC),
    QUILT(ModLoader.QUILT),
    NEOFORGE(ModLoader.NEOFORGE), // At the time of writing this is undocumented. However it works and I assume the documentation will be updated some day.
    OTHER(ModLoader.UNKNOWN, "other");
    
    public final ModLoader loader;
    public final String id;
    
    ModLoaderType(ModLoader loader) {
        this(loader, loader.id);
    }
    
    ModLoaderType(ModLoader loader, String id) {
        this.loader = loader;
        this.id = id;
    }
}

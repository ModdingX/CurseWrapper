package org.moddingx.cursewrapper.backend.data.structure;

import org.moddingx.cursewrapper.api.response.ModLoader;

public enum ModLoaderType implements CurseEnum {
    
    ANY(ModLoader.UNKNOWN, "any"),
    FORGE(ModLoader.FORGE),
    CAULDRON(ModLoader.CAULDRON),
    LITE_LOADER(ModLoader.LITE_LOADER),
    FABRIC(ModLoader.FABRIC),
    QUILT(ModLoader.QUILT),
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

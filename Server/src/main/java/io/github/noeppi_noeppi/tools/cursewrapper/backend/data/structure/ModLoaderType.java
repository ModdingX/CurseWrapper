package io.github.noeppi_noeppi.tools.cursewrapper.backend.data.structure;

public enum ModLoaderType {
    
    ANY("any"),
    FORGE("forge"),
    CAULDRON("cauldron"),
    LITE_LOADER("lite_loader"),
    FABRIC("fabric");
    
    public final String id;
    
    ModLoaderType(String id) {
        this.id = id;
    }
}

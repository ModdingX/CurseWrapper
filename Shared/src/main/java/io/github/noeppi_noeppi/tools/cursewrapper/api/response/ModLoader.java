package io.github.noeppi_noeppi.tools.cursewrapper.api.response;

import java.util.Locale;

public enum ModLoader {
    
    UNKNOWN,
    FORGE,
    FABRIC,
    LITE_LOADER,
    RIFT,
    CAULDRON;
    
    public final String id;

    ModLoader() {
        this.id = this.name().toLowerCase(Locale.ROOT);
    }
    
    public static ModLoader get(String id) {
        try {
            return ModLoader.valueOf(id.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}

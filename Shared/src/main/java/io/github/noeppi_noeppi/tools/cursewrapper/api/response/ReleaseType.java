package io.github.noeppi_noeppi.tools.cursewrapper.api.response;

import java.util.Locale;

public enum ReleaseType {

    UNKNOWN,
    ALPHA,
    BETA,
    RELEASE;
    
    public final String id;

    ReleaseType() {
        this.id = this.name().toLowerCase(Locale.ROOT);
    }

    public static ReleaseType get(String id) {
        try {
            return ReleaseType.valueOf(id.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}

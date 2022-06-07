package org.moddingx.cursewrapper.api.response;

import java.util.Locale;

public enum RelationType {

    UNKNOWN,
    EMBEDDED,
    OPTIONAL,
    REQUIRED,
    TOOL,
    INCOMPATIBLE,
    INCLUDE;

    public final String id;

    RelationType() {
        this.id = this.name().toLowerCase(Locale.ROOT);
    }

    public static RelationType get(String id) {
        try {
            return RelationType.valueOf(id.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}

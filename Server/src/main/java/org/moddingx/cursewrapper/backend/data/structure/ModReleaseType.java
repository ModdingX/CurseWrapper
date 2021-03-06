package org.moddingx.cursewrapper.backend.data.structure;

import org.moddingx.cursewrapper.api.response.ReleaseType;

public enum ModReleaseType implements CurseEnum {
    
    OTHER(ReleaseType.UNKNOWN, "other"),
    RELEASE(ReleaseType.RELEASE),
    BETA(ReleaseType.BETA),
    ALPHA(ReleaseType.ALPHA);
    
    public final String id;
    public final ReleaseType type;

    ModReleaseType(ReleaseType type) {
        this(type, type.id);
    }

    ModReleaseType(ReleaseType type, String id) {
        this.id = id;
        this.type = type;
    }
}

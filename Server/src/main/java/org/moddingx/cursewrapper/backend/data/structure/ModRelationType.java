package org.moddingx.cursewrapper.backend.data.structure;

import org.moddingx.cursewrapper.api.response.RelationType;

public enum ModRelationType implements CurseEnum {
    
    OTHER(RelationType.UNKNOWN, "other"),
    EMBEDDED(RelationType.EMBEDDED, "embedded_library"),
    OPTIONAL(RelationType.OPTIONAL),
    REQUIRED(RelationType.REQUIRED),
    TOOL(RelationType.TOOL),
    INCOMPATIBLE(RelationType.INCOMPATIBLE),
    INCLUDE(RelationType.INCLUDE);
    
    public final String id;
    public final RelationType type;

    ModRelationType(RelationType type) {
        this(type, type.id);
    }
    
    ModRelationType(RelationType type, String id) {
        this.id = type.id;
        this.type = type;
    }
}

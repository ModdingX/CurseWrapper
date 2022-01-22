package io.github.noeppi_noeppi.tools.cursewrapper.backend.data.structure;

public enum RelationType {
    
    OTHER("other"),
    EMBEDDED("embedded_library"),
    OPTIONAL("optional"),
    REQUIRED("required"),
    TOOL("tool"),
    INCOMPATIBLE("incompatible"),
    INCLUDE("include");
    
    public final String id;

    RelationType(String id) {
        this.id = id;
    }
}

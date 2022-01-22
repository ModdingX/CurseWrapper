package io.github.noeppi_noeppi.tools.cursewrapper.backend.data.structure;

public enum ReleaseType {
    
    OTHER("other"),
    RELEASE("release"),
    BETA("beta"),
    ALPHA("alpha");
    
    public final String id;

    ReleaseType(String id) {
        this.id = id;
    }
}

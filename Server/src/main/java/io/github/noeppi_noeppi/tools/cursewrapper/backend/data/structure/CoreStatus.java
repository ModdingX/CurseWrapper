package io.github.noeppi_noeppi.tools.cursewrapper.backend.data.structure;

public enum CoreStatus {
    
    OTHER("invalid"),
    DRAFT("draft"),
    TEST("test"),
    PENDING("pending"),
    REJECTED("rejected"),
    APPROVED("approved"),
    LIVE("live");
    
    public final String id;

    CoreStatus(String id) {
        this.id = id;
    }
}

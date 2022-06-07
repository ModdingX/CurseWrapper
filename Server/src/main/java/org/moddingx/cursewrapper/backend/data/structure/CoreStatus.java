package org.moddingx.cursewrapper.backend.data.structure;

public enum CoreStatus implements CurseEnum {
    
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

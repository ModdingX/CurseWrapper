package io.github.noeppi_noeppi.tools.cursewrapper.backend.data.structure;

public enum ModStatus implements CurseEnum {
    
    OTHER("other"),
    NEW("new"),
    CHANGES_REQUESTED("changes_requested"),
    UNDER_SOFT_REVIEW("under_soft_review"),
    APPROVED("approved"),
    REJECTED("rejected"),
    CHANGES_MADE("resubmitted"),
    INACTIVE("inactive"),
    ABANDONED("abandoned"),
    DELETED("deleted"),
    UNDER_REVIEW("under_review");
    
    public final String id;

    ModStatus(String id) {
        this.id = id;
    }
}

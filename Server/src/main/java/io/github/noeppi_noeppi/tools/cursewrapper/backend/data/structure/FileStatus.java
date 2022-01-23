package io.github.noeppi_noeppi.tools.cursewrapper.backend.data.structure;

public enum FileStatus implements CurseEnum {
    
    OTHER("other"),
    PROCESSING("processing"),
    CHANGES_REQUESTED("changes_requested"),
    UNDER_REVIEW("under_review"),
    APPROVED("approved"),
    REJECTED("rejected"),
    MALWARE_DETECTED("malware"),
    DELETED("deleted"),
    ARCHIVED("archived"),
    TESTING("testing"),
    RELEASED("released"),
    READY_FOR_REVIEW("ready_for_review"),
    DEPRECATED("deprecated"),
    BAKING("baking"),
    AWAITING_PUBLISHING("awaiting_publishing"),
    FAILED_PUBLISHING("failed_publishing");
    
    public final String id;

    FileStatus(String id) {
        this.id = id;
    }
}

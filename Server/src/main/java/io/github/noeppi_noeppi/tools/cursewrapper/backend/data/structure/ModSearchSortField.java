package io.github.noeppi_noeppi.tools.cursewrapper.backend.data.structure;

public enum ModSearchSortField implements CurseEnum {

    FEATURED("featured"),
    POPULARITY("popularity"),
    LAST_UPDATED("last_updated"),
    NAME("name"),
    AUTHOR("author"),
    TOTAL_DOWNLOADS("total_downloads"),
    CATEGORY("category"),
    GAME_VERSION("game_version");

    public final String id;

    ModSearchSortField(String id) {
        this.id = id;
    }
}

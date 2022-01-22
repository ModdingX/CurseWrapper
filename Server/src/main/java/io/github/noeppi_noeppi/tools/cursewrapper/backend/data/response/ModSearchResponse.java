package io.github.noeppi_noeppi.tools.cursewrapper.backend.data.response;

import com.google.gson.annotations.Expose;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.CurseData;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.util.Pagination;

import java.util.List;

// mods/search
public class ModSearchResponse implements CurseData {
    
    @Expose public List<ModResponse.Mod> data;
    @Expose public Pagination pagination;

    public ModSearchResponse(List<ModResponse.Mod> data, Pagination pagination) {
        this.data = data;
        this.pagination = pagination;
    }
}

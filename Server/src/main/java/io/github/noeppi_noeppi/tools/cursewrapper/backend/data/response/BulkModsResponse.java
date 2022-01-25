package io.github.noeppi_noeppi.tools.cursewrapper.backend.data.response;

import com.google.gson.annotations.Expose;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.CurseData;

import java.util.List;

public class BulkModsResponse implements CurseData {
    
    @Expose public List<ModResponse.Mod> data;

    public BulkModsResponse(List<ModResponse.Mod> data) {
        this.data = data;
    }
}

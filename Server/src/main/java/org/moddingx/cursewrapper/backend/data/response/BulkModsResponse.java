package org.moddingx.cursewrapper.backend.data.response;

import com.google.gson.annotations.Expose;
import org.moddingx.cursewrapper.backend.CurseData;

import java.util.List;

public class BulkModsResponse implements CurseData {
    
    @Expose public List<ModResponse.Mod> data;

    public BulkModsResponse(List<ModResponse.Mod> data) {
        this.data = data;
    }
}

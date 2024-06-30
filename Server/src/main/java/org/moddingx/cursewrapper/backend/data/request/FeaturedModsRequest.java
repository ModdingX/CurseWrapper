package org.moddingx.cursewrapper.backend.data.request;

import com.google.gson.annotations.Expose;
import jakarta.annotation.Nullable;
import org.moddingx.cursewrapper.backend.CurseData;

import java.util.List;

public class FeaturedModsRequest implements CurseData {
    
    @Expose public int gameId;
    @Expose public List<Integer> excludedModIds;

    @Nullable
    @Expose public Integer gameVersionTypeId;

    public FeaturedModsRequest(int gameId, List<Integer> excludedModIds, @Nullable Integer gameVersionTypeId) {
        this.gameId = gameId;
        this.excludedModIds = excludedModIds;
        this.gameVersionTypeId = gameVersionTypeId;
    }
}

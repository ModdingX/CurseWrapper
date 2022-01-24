package io.github.noeppi_noeppi.tools.cursewrapper.backend.data.response;

import com.google.gson.annotations.Expose;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.CurseData;

import java.util.List;

// mods/featured
public class FeaturedModsResponse implements CurseData {

    @Expose public FeaturedMods data;

    public FeaturedModsResponse(FeaturedMods data) {
        this.data = data;
    }

    public static class FeaturedMods {
        
        @Expose public List<ModResponse.Mod> featured;
        @Expose public List<ModResponse.Mod> popular;
        @Expose public List<ModResponse.Mod> recentlyUpdated;

        public FeaturedMods(List<ModResponse.Mod> featured, List<ModResponse.Mod> popular, List<ModResponse.Mod> recentlyUpdated) {
            this.featured = featured;
            this.popular = popular;
            this.recentlyUpdated = recentlyUpdated;
        }
    }
}

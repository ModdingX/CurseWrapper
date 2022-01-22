package io.github.noeppi_noeppi.tools.cursewrapper.backend.data.response;

import com.google.gson.annotations.Expose;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.CurseData;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.structure.ApiStatus;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.structure.CoreStatus;

import java.util.Date;

// games/{gameId}
public class GameResponse implements CurseData {

    @Expose public Game data;

    public GameResponse(Game data) {
        this.data = data;
    }

    public static class Game {
        @Expose public int id;
        @Expose public String name;
        @Expose public String slug;
        @Expose public Date dateModified;
        @Expose public Assets assets;
        @Expose public CoreStatus status;
        @Expose public ApiStatus apiStatus;

        public Game(int id, String name, String slug, Date dateModified, Assets assets, CoreStatus status, ApiStatus apiStatus) {
            this.id = id;
            this.name = name;
            this.slug = slug;
            this.dateModified = dateModified;
            this.assets = assets;
            this.status = status;
            this.apiStatus = apiStatus;
        }
    }

    public static class Assets {

        @Expose public String iconUrl;
        @Expose public String titleUrl;
        @Expose public String coverUrl;

        public Assets(String iconUrl, String titleUrl, String coverUrl) {
            this.iconUrl = iconUrl;
            this.titleUrl = titleUrl;
            this.coverUrl = coverUrl;
        }
    }
}

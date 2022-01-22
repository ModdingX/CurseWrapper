package io.github.noeppi_noeppi.tools.cursewrapper.backend.data.response;

import com.google.gson.annotations.Expose;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.CurseData;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.util.Pagination;

import java.util.List;

// games
public class GamesResponse implements CurseData {
    
    @Expose public List<GameResponse.Game> data;
    @Expose public Pagination pagination;

    public GamesResponse(List<GameResponse.Game> data, Pagination pagination) {
        this.data = data;
        this.pagination = pagination;
    }
}

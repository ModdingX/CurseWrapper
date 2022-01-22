package io.github.noeppi_noeppi.tools.cursewrapper.backend.data.response;

import com.google.gson.annotations.Expose;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.CurseData;

import java.util.List;

// games/{gameId}/versions
public class GameVersionsResponse implements CurseData {
    
    public Data data;

    public GameVersionsResponse(Data data) {
        this.data = data;
    }

    public static class Data {
        
        @Expose public int type;
        @Expose public List<String> versions;

        public Data(int type, List<String> versions) {
            this.type = type;
            this.versions = versions;
        }
    }
}

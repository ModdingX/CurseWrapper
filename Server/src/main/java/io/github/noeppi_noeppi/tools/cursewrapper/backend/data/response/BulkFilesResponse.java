package io.github.noeppi_noeppi.tools.cursewrapper.backend.data.response;

import com.google.gson.annotations.Expose;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.CurseData;

import java.util.List;

public class BulkFilesResponse implements CurseData {
    
    @Expose public List<ModFileResponse.ModFile> data;

    public BulkFilesResponse(List<ModFileResponse.ModFile> data) {
        this.data = data;
    }
}
